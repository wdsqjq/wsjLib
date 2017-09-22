package com.example.wsj.circleloading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CircleLoadingView loadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RefreshTicketView refreshTicketView=new RefreshTicketView(this);
        setContentView(refreshTicketView);
        refreshTicketView.start();

//        PostManLoadingView view=new PostManLoadingView(this);
//        setContentView(view);
//        view.start();

//        setContentView(R.layout.activity_main);

        /*loadingView= (CircleLoadingView) findViewById(R.id.loadingView);
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
        }).start();*/
    }
}
