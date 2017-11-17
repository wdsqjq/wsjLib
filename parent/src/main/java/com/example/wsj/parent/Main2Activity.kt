package com.example.wsj.parent

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import java.util.*

class Main2Activity : AppCompatActivity() ,BlankFragment.OnFragmentInteractionListener{

    private var vp: ViewPager? = null
    internal var mFragments: MutableList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        vp = findViewById(R.id.vp) as ViewPager?
        mFragments.add(BlankFragment.newInstance("A", ""))
        mFragments.add(BlankFragment.newInstance("B", ""))
        mFragments.add(BlankFragment.newInstance("C", ""))
        var adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }
        vp?.setAdapter(adapter)
    }

    override fun onFragmentInteraction(uri: Uri?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
