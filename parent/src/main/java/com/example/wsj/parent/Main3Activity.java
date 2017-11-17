package com.example.wsj.parent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {
    private ViewPager vp;

    List<Fragment> mFragments=new ArrayList<>();
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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
