package per.wsj.commonlib.net;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;
import per.wsj.commonlib.utils.LogUtil;

public class LogInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("\n" + request.method());
        stringBuffer.append("  " + request.url());

        Headers requestHeaders = request.headers();
        for (int i = 0, count = requestHeaders.size(); i < count; i++) {
            String name = requestHeaders.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                stringBuffer.append(" --" + name + ": " + requestHeaders.value(i));
            }
        }

        if (hasRequestBody && !bodyEncoded(request.headers())) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (isPlaintext(buffer)) {
                stringBuffer.append("  " + buffer.readString(charset));
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();

        stringBuffer.append("\ncode:" + response.code());
        stringBuffer.append("  message:" + response.message());
        stringBuffer.append("  time:" + tookMs + "ms");

        Headers responseHeaders = response.headers();
        for (int i = 0, count = responseHeaders.size(); i < count; i++) {
            String name = responseHeaders.name(i);
            if ("Date".equalsIgnoreCase(name)) {
                stringBuffer.append("  " + name + ": " + responseHeaders.value(i));
            }
        }

        if (HttpHeaders.hasBody(response) && !bodyEncoded(response.headers())) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    LogUtil.LOGE("NetLogInterceptor;Couldn't decode the response body; charset is likely malformed." + e.toString());
                    LogUtil.LOGD("NetLogInterceptor" + stringBuffer.toString());
                    return response;
                }
            }

            if (isPlaintext(buffer) && contentLength != 0) {
                stringBuffer.append("\nresponse=" + buffer.clone().readString(charset));
            }
        }

        LogUtil.LOGD("NetLogInterceptor" + stringBuffer.toString());

        return response;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}