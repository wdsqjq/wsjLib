package per.wangsj.myview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import per.wangsj.myview.circleloading.CircleLoadingView;
import per.wangsj.myview.circleloading.PostManLoadingView;
import per.wangsj.myview.circleloading.RefreshTicketView;
import per.wangsj.myview.love.DrawHeartView;
import per.wangsj.myview.spidernet.RadarView;
import per.wangsj.myview.swingball.SwingBall;

public class ViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        int flag=getIntent().getIntExtra("item",0);
        switch (flag){
            case 0:
                setContentView(new DrawHeartView(this));
                /*TitanicTextView textView=new TitanicTextView(this);
                textView.setText("Fuck U!");
                setContentView(textView);
                new Titanic().start(textView);*/
                break;
            case 1:
                // 蜘蛛网图
                RadarView radarView=new RadarView(this);
                setContentView(radarView);
                break;
            case 2:
                // 圆形加载进度
                final CircleLoadingView loadingView= new CircleLoadingView(this);
                setContentView(loadingView);
                loadingView.setOnCompletedInterface(new CircleLoadingView.onCompletedInterface() {
                    @Override
                    public void complete() {
                        Toast.makeText(ViewActivity.this, "完成", Toast.LENGTH_SHORT).show();
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
            case 3:
                //postman加载效果
                PostManLoadingView view=new PostManLoadingView(this);
                setContentView(view);
                view.start();
                break;
            case 4:
                //去哪网刷票
                RefreshTicketView refreshTicketView=new RefreshTicketView(this);
                setContentView(refreshTicketView);
                refreshTicketView.start();
                break;
            case 5:
                setContentView(new SwingBall(this));
                break;
            case 6:
                //微信雷达搜索
                setContentView(R.layout.radar_activity);
                break;

        }
        /*
        //测试
        setContentView(new TestView(this));*/
    }
}
