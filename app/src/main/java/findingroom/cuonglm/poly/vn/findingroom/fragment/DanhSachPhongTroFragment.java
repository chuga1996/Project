package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.adapter.CacPhongDaDangAdapter;
import findingroom.cuonglm.poly.vn.findingroom.adapter.DanhSachAdapter;
import findingroom.cuonglm.poly.vn.findingroom.model.PhongTro;

public class DanhSachPhongTroFragment extends Fragment {
    private RecyclerView recyclerView;
    private DanhSachAdapter adapter;
    private List<PhongTro> phongTroList;
    private Spinner spnQuanhuyen;
    private Spinner spnXaphuong;
    private String []quanhuyenlist = {"Hà Nội" , "Đà Nẵng" , "TP HCM", "Cà Mau"};
    private String []xaphuonglist = {"Hoàn Kiếm" , "Tây Hồ" , "Nam Từ Liêm", "Hà Đông"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_sach_phong_tro,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_danhsach);
        phongTroList = new ArrayList<>();
        adapter = new DanhSachAdapter(phongTroList);
        spnQuanhuyen = (Spinner) view.findViewById(R.id.spn_quanhuyen);
        spnXaphuong = (Spinner) view.findViewById(R.id.spn_xaphuong);

        ArrayAdapter<String> adapterspnQH = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, quanhuyenlist);
        adapterspnQH.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        ArrayAdapter<String> adapterspnXP = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, xaphuonglist);
        adapterspnXP.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnQuanhuyen.setAdapter(adapterspnQH);
        spnXaphuong.setAdapter(adapterspnXP);

        spnQuanhuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = quanhuyenlist[position];
                Toast.makeText(getActivity(), selection+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnXaphuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection1 = xaphuonglist[position];
                Toast.makeText(getActivity(), selection1+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        fakeData();


        return view;
    }


    private void fakeData(){
        for (int i = 0 ; i < 40;i++){
            PhongTro phongTro = new PhongTro(R.drawable.ic_launcher_background,"30 Ham Nghi, My Dinh","20","200.000");
            phongTroList.add(phongTro);
        }
        adapter.notifyDataSetChanged();
    }
}
