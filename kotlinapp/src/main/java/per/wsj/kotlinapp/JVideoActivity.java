package per.wsj.kotlinapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Description:
 * Created by wangsj on 17-12-19.
 */

public class JVideoActivity extends AppCompatActivity {

    private JZVideoPlayerStandard videoplayer;
    private String url="http://wangsj.cn:8080/kotlinapp/video/09.avi";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_video);

        initView();
    }

    private void initView() {
        videoplayer = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        videoplayer.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, getIntent().getStringExtra("title"));
        videoplayer.startVideo();
    }

}
