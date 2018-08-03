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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.model.PhongTro;
import findingroom.cuonglm.poly.vn.findingroom.model.Room;
import findingroom.cuonglm.poly.vn.findingroom.uis.DeitalActivity;

public class CacPhongDaDangAdapter extends RecyclerView.Adapter<CacPhongDaDangAdapter.ViewHolder> {
    private List<Room> roomList;
    Context context;
    public CacPhongDaDangAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public CacPhongDaDangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_cacphongdadang_recyclerview,parent,false);
        context= view.getContext();


        return new CacPhongDaDangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CacPhongDaDangAdapter.ViewHolder holder, final int position) {
        Room room = roomList.get(position);
//        holder.imgHinhanhphongtro.setImageResource(room.getmImg());
        holder.tvDiachi.setText(room.getFullAddress());
        holder.tvSonguoi.setText("Số người: "+ room.getPeople()+" người/phòng");
        holder.tvGia.setText("Giá: "+ room.getPrice() +" đ");
        Glide.with(context)
                .load(room.getImgResource())
                .into(holder.imgHinhanhphongtro);

        holder.tvChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent((Activity)context , DeitalActivity.class);
//                intent.putExtra("imageresource", roomList.get(position).getImgResource());
                intent.putExtra("address", roomList.get(position).getAddress());
                intent.putExtra("district", roomList.get(position).getDistrict());
                intent.putExtra("price", roomList.get(position).getPrice()+"");
                intent.putExtra("people", roomList.get(position).getPeople()+"");
                intent.putExtra("phone", roomList.get(position).getPhone()+"");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomList.size();
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
