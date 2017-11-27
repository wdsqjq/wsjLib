package per.wsj.kotlinapp

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import per.wsj.kotlinapp.myfragment.BasisFragment
import per.wsj.kotlinapp.myfragment.BlankFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    internal var mFragments: ArrayList<Fragment> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        initView()
    }

    private fun initView() {

        mFragments.add(BasisFragment.newInstance(1))
        mFragments.add(BlankFragment.newInstance(""))
        mFragments.add(BasisFragment.newInstance(3))
        var adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }
        viewPager.setAdapter(adapter)
        viewPager.setOnTouchListener { v, event -> true }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                viewPager.setCurrentItem(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                viewPager.setCurrentItem(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                viewPager.setCurrentItem(2)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
