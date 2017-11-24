package com.example.wsj.parent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import per.wsj.commonlib.pulltorefreshlib.PullToRefreshLayout;

public class Main3Activity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener, PullToRefreshLayout.OnPullListener {
    private ViewPager vp;

    List<Fragment> mFragments=new ArrayList<>();

    private PullToRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        vp = (ViewPager) findViewById(R.id.vp);
        mFragments.add(BlankFragment.newInstance("A",""));
        mFragments.add(BlankFragment.newInstance("B",""));
        mFragments.add(BlankFragment.newInstance("C",""));
        FragmentStatePagerAdapter adapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };

        vp.setAdapter(adapter);

        refreshLayout= (PullToRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnPullListener(this);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Main3Activity.this, "aa", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
