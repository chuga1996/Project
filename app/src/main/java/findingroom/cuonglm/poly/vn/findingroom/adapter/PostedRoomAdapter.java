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

import com.bumptech.glide.Glide;

import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.model.Room;
import findingroom.cuonglm.poly.vn.findingroom.uis.DetailPostedRoom;

public class PostedRoomAdapter extends RecyclerView.Adapter<PostedRoomAdapter.ViewHolder> {
    private List<Room> roomList;
    Context context;
    public PostedRoomAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public PostedRoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_room,parent,false);
        context= view.getContext();


        return new PostedRoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostedRoomAdapter.ViewHolder holder, final int position) {
        Room room = roomList.get(position);
//        holder.imgHinhanhphongtro.setImageResource(room.getmImg());
        holder.tvDiachi.setText(room.getFullAddress());
        holder.tvSonguoi.setText("Số người: "+ room.getPeople()+" người/phòng");
        holder.tvGia.setText("Giá: "+ String.format("%,d",room.getPrice()).replace(",",".") +" đ");
        Glide.with(context)
                .load(room.getImgResource())
                .into(holder.imgHinhanhphongtro);

        holder.tvChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent((Activity)context , DetailPostedRoom.class);
//                intent.putExtra("imageresource", roomList.get(position).getImgResource());
                intent.putExtra("address", roomList.get(position).getAddress());
                intent.putExtra("district", roomList.get(position).getDistrict());
                intent.putExtra("price", roomList.get(position).getPrice()+"");
                intent.putExtra("people", roomList.get(position).getPeople()+"");
                intent.putExtra("phone", roomList.get(position).getPhone()+"");
                intent.putExtra("image",roomList.get(position).getImgResource());
                intent.putExtra("id",roomList.get(position).getId());
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
