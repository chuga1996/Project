package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.fragment.CacPhongDaDangFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.DanhSachPhongTroFragment;
import findingroom.cuonglm.poly.vn.findingroom.fragment.MapsFragment;

public class TimPhongTroActivity extends AppCompatActivity {
    private DanhSachPhongTroFragment danhSachPhongTroFragment;
    private MapsFragment mapsFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_phong_tro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_timphongtro);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        danhSachPhongTroFragment = new DanhSachPhongTroFragment();
        mapsFragment = new MapsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (danhSachPhongTroFragment.isAdded()) {
            transaction.show(danhSachPhongTroFragment);
        } else {
            transaction.add(R.id.frameTimPhong, danhSachPhongTroFragment);

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
                        if (mapsFragment.isVisible()){
                            transaction.hide(mapsFragment);
                        }
                        if (danhSachPhongTroFragment.isAdded()) {
                            transaction.show(danhSachPhongTroFragment);
                        } else {
                            transaction.add(R.id.frameTimPhong, danhSachPhongTroFragment);

                        }

                        transaction.commit();
                        break;

                    case R.id.bottmMap:
                        if (danhSachPhongTroFragment.isVisible()){
                            transaction.hide(danhSachPhongTroFragment);
                        }
                        if (mapsFragment.isAdded()) {
                            transaction.show(mapsFragment);
                        } else {
                            transaction.add(R.id.frameTimPhong, mapsFragment);

                        }
                        transaction.commit();
                        break;
                }

                return true;
            }
        });

    }

}
