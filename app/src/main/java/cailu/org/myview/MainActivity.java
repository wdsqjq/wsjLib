package cailu.org.myview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import cailu.org.myview.circleloading.CircleLoadingView;
import cailu.org.myview.circleloading.PostManLoadingView;
import cailu.org.myview.circleloading.RefreshTicketView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        int flag=5;
        switch (flag){
            case 0:
                setContentView(new FreeFall(this));
                break;
            case 1:
                // 蜘蛛网图
                RadarView radarView=new RadarView(this);
                setContentView(radarView);
                break;
            case 2:
                //微信雷达搜索
                setContentView(R.layout.activity_main2);
                break;
            case 3:
                //去哪网刷票
                RefreshTicketView refreshTicketView=new RefreshTicketView(this);
                setContentView(refreshTicketView);
                refreshTicketView.start();
                break;
            case 4:
                //postman加载效果
                PostManLoadingView view=new PostManLoadingView(this);
                setContentView(view);
                view.start();
                break;
            case 5:
                // 圆形加载进度
                final CircleLoadingView loadingView= new CircleLoadingView(this);
                setContentView(loadingView);
                loadingView.setOnCompletedInterface(new CircleLoadingView.onCompletedInterface() {
                    @Override
                    public void complete() {
                        Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <=100; i++) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            final int finalI = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingView.setProgress(finalI);
                                }
                            });
                        }
                    }
                }).start();
                break;

        }
        /*
        //测试
        setContentView(new TestView(this));*/
    }
}
