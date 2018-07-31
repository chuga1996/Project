package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.fragment.CacPhongDaDangFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.CacPhongDaThichFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.GioiThieuFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.PostRoomFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout frameLayout;
    private PostRoomFragment postRoomFragment;
    private CacPhongDaDangFragment cacPhongDaDangFragment;
    private CacPhongDaThichFragment cacPhongDaThichFragment;
    private GioiThieuFragment gioiThieuFragment;
    private View navHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Finding Home");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);
        TextView navDisplayName = navHeader.findViewById(R.id.navHeaderDisplayName);
        TextView navDisplayEmail = navHeader.findViewById(R.id.navHeaderEmail);
        navDisplayName.setText(intent.getStringExtra("displayname"));
        navDisplayEmail.setText(intent.getStringExtra("email"));



        postRoomFragment = new PostRoomFragment();
        frameLayout = (FrameLayout) findViewById(R.id.frameMain);
        cacPhongDaDangFragment = new CacPhongDaDangFragment();
        cacPhongDaThichFragment = new CacPhongDaThichFragment();
        gioiThieuFragment = new GioiThieuFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (cacPhongDaThichFragment.isAdded()) {
            transaction.show(cacPhongDaThichFragment);
        } else {
            transaction.add(R.id.frameMain, cacPhongDaThichFragment);
        }
        for (Fragment x : fragmentManager.getFragments()) {
            if (x != cacPhongDaThichFragment && x.isAdded()) {
                transaction.hide(x);
            }
        }
        transaction.commit();

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
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_find) {
            startActivity(new Intent(MainActivity.this, TimPhongTroActivity.class));

        } else if (id == R.id.nav_likedRoom) {
            if (cacPhongDaThichFragment.isAdded()) {
                transaction.show(cacPhongDaThichFragment);
            } else {
                transaction.add(R.id.frameMain, cacPhongDaThichFragment);
            }
            for (Fragment x : fragmentManager.getFragments()) {
                if (x != cacPhongDaThichFragment && x.isAdded()) {
                    transaction.hide(x);
                }
            }
            transaction.commit();

        } else if (id == R.id.nav_postRoom) {
            if (postRoomFragment.isAdded()) {
                transaction.show(postRoomFragment);
            } else {
                transaction.add(R.id.frameMain, postRoomFragment);
            }
            for (Fragment x : fragmentManager.getFragments()) {
                if (x != postRoomFragment && x.isAdded()) {
                    transaction.hide(x);
                }
            }
            transaction.commit();

        } else if (id == R.id.nav_postedRoom) {
            if (cacPhongDaDangFragment.isAdded()) {
                transaction.show(cacPhongDaDangFragment);
            } else {
                transaction.add(R.id.frameMain, cacPhongDaDangFragment);
            }
            for (Fragment x : fragmentManager.getFragments()) {
                if (x != cacPhongDaDangFragment && x.isAdded()) {
                    transaction.hide(x);
                }
            }
            transaction.commit();

        } else if (id == R.id.nav_gioithieu) {
            if (gioiThieuFragment.isAdded()) {
                transaction.show(gioiThieuFragment);
            } else {
                transaction.add(R.id.frameMain, gioiThieuFragment);
            }
            for (Fragment x : fragmentManager.getFragments()) {
                if (x != gioiThieuFragment && x.isAdded()) {
                    transaction.hide(x);
                }
            }
            transaction.commit();

        } else if (id == R.id.nav_thoat) {
            final AlertDialog.Builder dialog3 = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
            dialog3.setTitle("Bạn có chắc là muốn thoát");
            dialog3.setCancelable(true);
            dialog3.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            });
            dialog3.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
