package per.wsj.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
//        var url="http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
//        var url="http://wangsj.cn:8080/kotlinapp/basevideo/05.avi"
        var url="http://192.168.0.105:8080/kotlinapp/basevideo/05.avi"
//
        videoplayer?.setUp(url,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"左手右手")
        videoplayer?.startVideo()

//        val jzVideoPlayerStandard = findViewById(R.id.videoplayer) as JZVideoPlayerStandard
//        jzVideoPlayerStandard.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, intent.getStringExtra("title"))

    }

    override fun onBackPressed() {
        if(JZVideoPlayer.backPress()){
            return;
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JZVideoPlayer.releaseAllVideos()
    }
}
