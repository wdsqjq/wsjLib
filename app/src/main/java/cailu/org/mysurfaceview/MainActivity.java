package cailu.org.mysurfaceview;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
//        setContentView(new FreeFall(this));

//        // 蜘蛛网图
//        RadarView radarView=new RadarView(this);
//        setContentView(radarView);

        //微信雷达搜索
        setContentView(R.layout.activity_main2);
    }
}
