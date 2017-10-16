package per.wangsj.myview.imageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import per.wangsj.myview.R;

public class ZoomRotateActivity extends AppCompatActivity {

    private ZoomRotateImageView zoomIv;
    private ImageButton btnRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_rotate);

        zoomIv = (ZoomRotateImageView) findViewById(R.id.zoomIv);
        btnRotate= (ImageButton) findViewById(R.id.btnRotate);
        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomIv.rotate(90);
            }
        });
    }
}
