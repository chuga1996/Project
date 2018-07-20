package findingroom.cuonglm.poly.vn.findingroom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.model.PhongTro;

public class CacPhongDaDangAdapter extends RecyclerView.Adapter<CacPhongDaDangAdapter.ViewHolder> {
    private List<PhongTro> phongTroList;

    public CacPhongDaDangAdapter(List<PhongTro> phongTroList) {
        this.phongTroList = phongTroList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_cacphongdadang_recyclerview,parent,false);
        final Context context = view.getContext();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        PhongTro phongTro = phongTroList.get(position);
        holder.imgHinhanhphongtro.setImageResource(phongTro.getmImg());
        holder.tvDiachi.setText(phongTro.getmDiaChi());
        holder.tvSonguoi.setText(phongTro.getmSoNguoi());
        holder.tvGia.setText(phongTro.getmGia());


    }

    @Override
    public int getItemCount() {
        return phongTroList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgHinhanhphongtro;
        private TextView tvDiachi;
        private TextView tvSonguoi;
        private TextView tvGia;
        private TextView tvChitiet;

        public ViewHolder(View itemView) {
            super(itemView);
            imgHinhanhphongtro = (ImageView) itemView.findViewById(R.id.img_hinhanhphongtro);
            tvDiachi = (TextView) itemView.findViewById(R.id.tv_diachi);
            tvSonguoi = (TextView) itemView.findViewById(R.id.tv_songuoi);
            tvGia = (TextView) itemView.findViewById(R.id.tv_gia);
            tvChitiet = (TextView) itemView.findViewById(R.id.tv_chitiet);
        }
    }
}
