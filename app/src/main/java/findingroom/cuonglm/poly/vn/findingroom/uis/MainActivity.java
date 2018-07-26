package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.fragment.CacPhongDaDangFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.MapsFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.PostRoomFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout frameLayout;
    private CacPhongDaDangFragment CacPhongDaDangFragment;
    private MapsFragment mapsFragment;
    private BottomNavigationView bottomNavigationView;
    private PostRoomFragment postRoomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        CacPhongDaDangFragment = new CacPhongDaDangFragment();
        mapsFragment = new MapsFragment();
        postRoomFragment = new PostRoomFragment();
        frameLayout = (FrameLayout) findViewById(R.id.frameMain);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (CacPhongDaDangFragment.isAdded()) {
            transaction.show(CacPhongDaDangFragment);
        } else {
            transaction.add(R.id.frameMain, CacPhongDaDangFragment);

        }
        for (Fragment x : fragmentManager.getFragments()) {
            if (x != CacPhongDaDangFragment && x.isAdded()) {
                transaction.hide(x);
            }
        }
        transaction.commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bottomList:
                        if (CacPhongDaDangFragment.isAdded()) {
                            transaction.show(CacPhongDaDangFragment);
                        } else {
                            transaction.add(R.id.frameMain, CacPhongDaDangFragment);

                        }
                        for (Fragment x : fragmentManager.getFragments()) {
                            if (x != CacPhongDaDangFragment && x.isAdded()) {
                                transaction.hide(x);
                            }
                        }
                        transaction.commit();
                        break;

                    case R.id.bottmMap:

                        if (mapsFragment.isAdded()) {
                            transaction.show(mapsFragment);
                        } else {
                            transaction.add(R.id.frameMain, mapsFragment);

                        }
                        for (Fragment x : fragmentManager.getFragments()) {
                            if (x != mapsFragment && x.isAdded()) {
                                transaction.hide(x);
                            }
                        }
                        transaction.commit();
                        break;
                }

                return true;
            }
        });

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_find) {

        } else if (id == R.id.nav_likedRoom) {

        } else if (id == R.id.nav_postRoom) {

        } else if (id == R.id.nav_postedRoom) {
            if (postRoomFragment.isAdded()) {
                transaction.show(mapsFragment);
            } else {
                transaction.add(R.id.frameMain, postRoomFragment);

            }
            for (Fragment x : fragmentManager.getFragments()) {
                if (x != postRoomFragment && x.isAdded()) {
                    transaction.hide(x);
                }
            }
            transaction.commit();

        } else if (id == R.id.nav_gioithieu) {

        } else if (id == R.id.nav_thoat) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
