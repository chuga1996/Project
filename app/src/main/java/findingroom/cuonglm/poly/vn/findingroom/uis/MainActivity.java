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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.fragment.PostedRoomFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.AboutFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.PostRoomFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout frameLayout;
    private PostRoomFragment postRoomFragment;
    private PostedRoomFragment postedRoomFragment;
    private AboutFragment gioiThieuFragment;
    private View navHeader;
    private String displayname,email;
    TextView navDisplayName,navDisplayEmail;
    String username, password;
    int idAuthor;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Giới thiệu");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);
        navDisplayName = navHeader.findViewById(R.id.navHeaderDisplayName);
        navDisplayEmail = navHeader.findViewById(R.id.navHeaderEmail);
       if (savedInstanceState==null){
           Intent intent = getIntent();
           idAuthor = intent.getIntExtra("id",0);
           displayname =intent.getStringExtra("displayname");
           email =intent.getStringExtra("email");
           username =intent.getStringExtra("username");
           password =intent.getStringExtra("password");
       }
        navDisplayName.setText(displayname);
        navDisplayEmail.setText(email);





        gioiThieuFragment = new AboutFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
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
        startActivity(new Intent(MainActivity.this, FindRoomActivity.class));
    }

    private boolean flag = true;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (flag == false){
            super.onBackPressed();
        } else {
            flag = false;
            Toast.makeText(this, "Nhấn back thêm một lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int id = item.getItemId();
        Bundle bundlePostedroom = new Bundle();
        bundlePostedroom.putInt("id",idAuthor);
        postedRoomFragment = new PostedRoomFragment();
        postedRoomFragment.setArguments(bundlePostedroom);


        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        bundle.putString("password",password);
        postRoomFragment = new PostRoomFragment();
        postRoomFragment.setArguments(bundle);


        frameLayout = (FrameLayout) findViewById(R.id.frameMain);
        if (id == R.id.nav_find) {
            startActivity(new Intent(MainActivity.this, FindRoomActivity.class));

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
            toolbar.setTitle("Đăng phòng trọ");
            transaction.commit();

        } else if (id == R.id.nav_postedRoom) {
            if (postedRoomFragment.isAdded()) {
                transaction.show(postedRoomFragment);
            } else {
                transaction.add(R.id.frameMain, postedRoomFragment);
            }
            for (Fragment x : fragmentManager.getFragments()) {
                if (x != postedRoomFragment && x.isAdded()) {
                    transaction.hide(x);
                }
            }
            toolbar.setTitle("Các phòng đã đăng");
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
            toolbar.setTitle("Giới thiệu");
            transaction.commit();

        } else if (id == R.id.nav_thoat) {
            final AlertDialog.Builder dialog3 = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
            dialog3.setTitle("Bạn có chắc là muốn thoát");
            dialog3.setCancelable(true);
            dialog3.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.exit(1);
                }
            });
            dialog3.show();
        }
        item.setCheckable(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("id",idAuthor);
        outState.putString("displayname",displayname);
        outState.putString("email",email);
        outState.putString("username",username);
        outState.putString("password",password);
        Log.e("displayname",displayname);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        displayname =savedInstanceState.getString("displayname");
        email = savedInstanceState.getString("email");
        username = savedInstanceState.getString("username");
        password = savedInstanceState.getString("password");
        idAuthor = savedInstanceState.getInt("id");
        navDisplayName.setText(displayname);
        navDisplayEmail.setText(email);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        postRoomFragment.onActivityResult(requestCode, resultCode, data);
    }
}
