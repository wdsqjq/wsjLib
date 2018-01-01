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
        var url=Constants.VIDEO_URL+intent.getStringExtra("url")
        val title=intent.getStringExtra("title")
//
        videoplayer?.setUp(url,JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,title)
        videoplayer?.startVideo()

        tvVideoTitle?.setText(title)
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
