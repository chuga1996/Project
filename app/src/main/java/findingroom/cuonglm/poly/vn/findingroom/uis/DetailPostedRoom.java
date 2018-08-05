package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;

import java.io.IOException;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.rest.RestClient2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPostedRoom extends AppCompatActivity {
    private ImageView imgAvatar;
    private TextView edtDiachiDpt;
    private TextView edtQuanhuyen;
    private TextView edtGia;
    private TextView edtSonguoi;
    private TextView edtSodienthoai;
    private Button button;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_posted);

        getActionBar();
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String district = intent.getStringExtra("district");
        String price = intent.getStringExtra("price");
        String people = intent.getStringExtra("people");
        String phone = intent.getStringExtra("phone");
        String image = intent.getStringExtra("image");
        id = intent.getIntExtra("id",0);
        imgAvatar = (ImageView) findViewById(R.id.img_dadang);
        edtDiachiDpt = (TextView) findViewById(R.id.tv_dadang_diachi);
        edtQuanhuyen = (TextView) findViewById(R.id.tv_dadang_quanhuyen);
        edtGia = (TextView) findViewById(R.id.tv_dadang_gia);
        edtSonguoi = (TextView) findViewById(R.id.tv_dadang_songuoi);
        edtSodienthoai = (TextView) findViewById(R.id.tv_dadang_sdt);
//        button = findViewById(R.id.buttonDelete);

        Glide.with(DetailPostedRoom.this)
                .load(image)
                .into(imgAvatar);
        edtDiachiDpt.setText(address);
        edtQuanhuyen.setText(district);
        edtGia.setText(price);
        edtSonguoi.setText(people);
        edtSodienthoai.setText(phone);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Call<JsonElement> callDeleteRoom = RestClient2.getApiInterface().deletePost(id,true);
//                callDeleteRoom.enqueue(new Callback<JsonElement>() {
//                    @Override
//                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                        if(response.isSuccessful()){
//                            Log.e("respone",response.body().toString());
//                        }else{
//                            try {
//                                Log.e("respone",response.errorBody().string());
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<JsonElement> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
//            }
//        });
    }
}
