package per.wsj.commonlib.net;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Description:
 * Created by wangsj on 17-12-19.
 */

public interface ApiService {

    /////////////////////////////////////////通用方法//////////////////////////////////////////////////

    @GET("{url}")
    Observable<ResponseBody> executeGet(
            @Path(value = "url", encoded = true) String url);

    @GET("{url}")
    Observable<ResponseBody> executeGet(
            @Path(value = "url", encoded = true) String url,
            @QueryMap(encoded = true) Map<String, Object> map);

    @POST("{url}")
    Observable<ResponseBody> executePost(
            @Path(value = "url", encoded = true) String url,
            @Body RequestBody request);

//    @Multipart
//    @POST("{url}")
//    Observable<ResponseBody> upLoadFile(
//            @Path("url") String url,
//            @Part("image\\\\"; filename=\\"image.jpg") RequestBody avatar);


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
