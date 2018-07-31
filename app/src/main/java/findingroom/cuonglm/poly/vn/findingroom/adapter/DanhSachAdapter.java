package findingroom.cuonglm.poly.vn.findingroom.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.model.PhongTro;
import findingroom.cuonglm.poly.vn.findingroom.uis.DeitalActivity;
import findingroom.cuonglm.poly.vn.findingroom.uis.UpdateRoomActivity;

public class DanhSachAdapter extends RecyclerView.Adapter<DanhSachAdapter.ViewHolder> {
    private List<PhongTro> phongTroList;

    public DanhSachAdapter(List<PhongTro> phongTroList) {
        this.phongTroList = phongTroList;
    }

    @NonNull
    @Override
    public DanhSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_cacphongdadang_recyclerview,parent,false);
        Context context = view.getContext();


        return new DanhSachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DanhSachAdapter.ViewHolder holder, final int position) {
        PhongTro phongTro = phongTroList.get(position);
        holder.imgHinhanhphongtro.setImageResource(phongTro.getmImg());
        holder.tvDiachi.setText(phongTro.getmDiaChi());
        holder.tvSonguoi.setText(phongTro.getmSoNguoi());
        holder.tvGia.setText(phongTro.getmGia());

        holder.tvChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent((Activity)context , DeitalActivity.class));
            }
        });

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
