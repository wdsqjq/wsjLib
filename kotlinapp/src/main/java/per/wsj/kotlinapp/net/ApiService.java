package per.wsj.kotlinapp.net;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Description:
 * Created by wangsj on 17-12-19.
 */

public interface ApiService {

    @POST("GetArticleInfo")
    Observable<ArticleResponse> getArticle(ArticleRequest request);
}
