package per.wsj.kotlinapp.net

/**
 * Created by shiju.wang on 2018/1/2.
 */
class BaseVideoResponse {

    var code:String ?=null
    var msg:String ?=null
    var detail:List<BaseVideo>?=null

    class BaseVideo(val thumb:String,val title:String ,val url:String)
}