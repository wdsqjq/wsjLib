package cailu.org.mysurfaceview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new FreeFall(this));
        RadarView radarView=new RadarView(this);
//        radarView.setMainPaintColor(Color.BLUE);
        setContentView(radarView);
    }
}
