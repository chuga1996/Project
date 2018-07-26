package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.adapter.CacPhongDaDangAdapter;
import findingroom.cuonglm.poly.vn.findingroom.model.PhongTro;

public class CacPhongDaDangFragment extends Fragment {
    RecyclerView recyclerView;
    CacPhongDaDangAdapter adapter;
    List<PhongTro> phongTroList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cac_phong_da_dang,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_cacphongdadang);
        phongTroList = new ArrayList<>();
        adapter = new CacPhongDaDangAdapter(phongTroList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fakeData();

        return view;
    }

    private void fakeData(){
        for (int i = 0 ; i < 40;i++){
            PhongTro phongTro = new PhongTro(R.drawable.ic_launcher_background,"29 Ham Nghi, My Dinh","20","200.000");
            phongTroList.add(phongTro);
        }
        adapter.notifyDataSetChanged();
    }
}
