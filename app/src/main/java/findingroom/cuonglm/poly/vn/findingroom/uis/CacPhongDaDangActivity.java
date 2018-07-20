package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.adapter.CacPhongDaDangAdapter;
import findingroom.cuonglm.poly.vn.findingroom.model.PhongTro;

public class CacPhongDaDangActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CacPhongDaDangAdapter adapter;
    List<PhongTro> phongTroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cac_phong_da_dang);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_cacphongdadang);
        phongTroList = new ArrayList<>();
        adapter = new CacPhongDaDangAdapter(phongTroList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fakeData();

    }
    private void fakeData(){
        for (int i = 0 ; i < 40;i++){
            PhongTro phongTro = new PhongTro(R.drawable.ic_launcher_background,"29 Ham Nghi, My Dinh","20","200.000");
            phongTroList.add(phongTro);
        }
        adapter.notifyDataSetChanged();
    }
}
