package per.wangsj.myview;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static per.wangsj.myview.R.layout.item;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvDetail;
    private CommonAdapter<String> mAdapter;
    List<String> datas=new ArrayList<>();

    private String[] viewArray = {"画心", "蜘蛛网图", "圆形加载", "仿postman加载", "仿去哪网刷票效果", "摆动的小球"};
    private String[] viewGroupArray = {"Titanic", "饼状图"};
    private String[] ActivityArray = {"Titanic", "蜘蛛网图"};
    private String[] otherArray = {"Titanic", "圆形加载", "仿postman加载"};
    private String[][] arrays={viewArray,viewGroupArray,ActivityArray,otherArray};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData(0);
    }

    private void initView() {
        rvDetail = (RecyclerView) findViewById(R.id.rv_detail);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));
        rvDetail.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.inflateMenu(R.menu.main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initData(int index) {
        datas.clear();
        datas.addAll(Arrays.asList(arrays[index]));
        if (mAdapter == null) {
            mAdapter = new CommonAdapter<String>(this, item, datas) {
                @Override
                protected void convert(ViewHolder viewHolder, String s, final int i) {
                    viewHolder.setText(R.id.tv_item, s);
                    viewHolder.setOnClickListener(R.id.tv_item, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                            intent.putExtra("index", i);
                            intent.putExtra("item", i);
                            startActivity(intent);
                        }
                    });
                }
            };
            rvDetail.setAdapter(mAdapter);
        }
        mAdapter.notifyDataSetChanged();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_view) {
            initData(0);
        } else if (id == R.id.nav_viewgroup) {
            initData(1);
        } else if (id == R.id.nav_in_activity) {
            initData(2);
        } else if (id == R.id.nav_other) {
            initData(3);
        } else if (id == R.id.nav_share) {
            initData(0);
        } else if (id == R.id.nav_send) {
            initData(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
