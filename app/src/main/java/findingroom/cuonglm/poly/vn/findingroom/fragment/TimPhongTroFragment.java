package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import findingroom.cuonglm.poly.vn.findingroom.R;

public class TimPhongTroFragment extends Fragment {
    private CacPhongDaDangFragment CacPhongDaDangFragment;
    private MapsFragment mapsFragment;
    private BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tim_phong_tro,container,false);
        CacPhongDaDangFragment = new CacPhongDaDangFragment();
        mapsFragment = new MapsFragment();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (CacPhongDaDangFragment.isAdded()) {
            transaction.show(CacPhongDaDangFragment);
        } else {
            transaction.add(R.id.frameTimPhong, CacPhongDaDangFragment);

        }
        transaction.commit();

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bottomList:
                        if (mapsFragment.isVisible()){
                            transaction.hide(mapsFragment);
                        }
                        if (CacPhongDaDangFragment.isAdded()) {
                            transaction.show(CacPhongDaDangFragment);
                        } else {
                            transaction.add(R.id.frameTimPhong, CacPhongDaDangFragment);

                        }

                        transaction.commit();
                        break;

                    case R.id.bottmMap:
                        if (CacPhongDaDangFragment.isVisible()){
                            transaction.hide(CacPhongDaDangFragment);
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

        return view;
    }


}
