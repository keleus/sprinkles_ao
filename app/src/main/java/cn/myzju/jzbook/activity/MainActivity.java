package cn.myzju.jzbook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.text.DecimalFormat;

import cn.myzju.jzbook.R;
import cn.myzju.jzbook.fragment.IndexFragment;

import static cn.myzju.jzbook.db.DbManager.cleanSession;
import static cn.myzju.jzbook.db.DbManager.sumTaily;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static MainActivity m=null;
    private SpaceNavigationView spaceNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction beginTransaction;
    private IndexFragment index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m=this;
        index=new IndexFragment();
        fragmentManager=getSupportFragmentManager();
        beginTransaction=fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.frame,index).commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("收支", R.drawable.fuzhi));
        spaceNavigationView.setCentreButtonIcon(R.drawable.icon_tianjia);
        spaceNavigationView.setCentreButtonColor(getResources().getColor(R.color.white));
        spaceNavigationView.addSpaceItem(new SpaceItem("统计", R.drawable.zhexiantu));
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                 Intent intent=new Intent(MainActivity.this, AddActivity.class); startActivity(intent);
                 MainActivity.this.finish();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemIndex==0) {
                    beginTransaction.replace(R.id.frame,index);
                }else if (itemIndex==1){
                    Toast.makeText(MainActivity.this,"统计", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_manage) {
            Intent intent=new Intent(MainActivity.this, SettingsActivity.class); startActivity(intent);
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this,"分享", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            cleanSession();
            MainActivity.this.finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static void res(){
        TextView textView=m.fragmentManager.getFragments().get(0).getView().findViewById(R.id.sum);
        double re=sumTaily();
        if (re==0){
            textView.setText("0.00");
        }else{
            DecimalFormat df = new DecimalFormat("#.00");
            textView.setText(df.format(re));
        }
    }

}
