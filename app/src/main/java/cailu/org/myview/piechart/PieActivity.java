package cailu.org.myview.piechart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import cailu.org.myview.R;

import static cailu.org.myview.R.id.pie;

public class PieActivity extends AppCompatActivity {

    PieView pieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);

        pieView= (PieView) findViewById(pie);

        final ArrayList<PieData> mData=new ArrayList<>();
        mData.add(new PieData("张三",30));
        mData.add(new PieData("李四",40));
        mData.add(new PieData("王五",50));
        pieView.setData(mData);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    mData.add(new PieData("赵6",60));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pieView.setData(mData);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
