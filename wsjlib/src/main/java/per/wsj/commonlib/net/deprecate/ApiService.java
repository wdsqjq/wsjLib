package per.wsj.commonlib.net.deprecate;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

/**
 * Description:
 * Created by wangsj on 17-12-19.
 * 2020/2/11 note:由于请求路径设置了encoded=true, 路径中包含？的回出错
 */

public interface ApiService {

    /////////////////////////////////////////通用方法//////////////////////////////////////////////////

    @GET("{url}")
    Observable<ResponseBody> executeGet(
            @Path(value = "url", encoded = true) String url,
            @HeaderMap Map<String, String> headers,
            @QueryMap(encoded = true) Map<String, Object> map);

    /**
     * 空返回体
     *
     * @param url
     * @return
     */
    @GET("{url}")
    Observable<Response<Void>> executeGet2(
            @Path(value = "url", encoded = true) String url);


    @POST("{url}")
    Observable<ResponseBody> executePost(
            @Path(value = "url", encoded = true) String url,
            @HeaderMap Map<String, String> headers,
            @Body RequestBody request);

    @POST("{url}")
    Observable<Response<Void>> executePost2(
            @Path(value = "url", encoded = true) String url,
            @HeaderMap Map<String, String> headers,
            @Body RequestBody request);

    @PUT("{url}")
    Observable<Response<Void>> executePut(
            @Path(value = "url", encoded = true) String url,
            @HeaderMap Map<String, String> headers);

    @DELETE("{url}")
    Observable<Response<Void>> executeDelete(
            @Path(value = "url", encoded = true) String url);

    @POST("{url}")
    Observable<ResponseBody> uploadFiles(
            @Path("url") String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap() Map<String, RequestBody> maps);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
