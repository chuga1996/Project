package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.rest.RestClient2;
import okhttp3.Credentials;
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
    private String username, password;
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

        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        imgAvatar = (ImageView) findViewById(R.id.img_dadang);
        edtDiachiDpt = (TextView) findViewById(R.id.tv_dadang_diachi);
        edtQuanhuyen = (TextView) findViewById(R.id.tv_dadang_quanhuyen);
        edtGia = (TextView) findViewById(R.id.tv_dadang_gia);
        edtSonguoi = (TextView) findViewById(R.id.tv_dadang_songuoi);
        edtSodienthoai = (TextView) findViewById(R.id.tv_dadang_sdt);
        button = findViewById(R.id.btn_deletePost);

        Glide.with(DetailPostedRoom.this)
                .load(image)
                .into(imgAvatar);
        edtDiachiDpt.setText(address);
        edtQuanhuyen.setText(district);
        edtGia.setText(price);
        edtSonguoi.setText(people);
        edtSodienthoai.setText(phone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("user", username + password);
                String auth = Credentials.basic(username, password);
                Call<JsonElement> callDeletePost = RestClient2.getApiInterface().deletePostbyID(id,"true",auth);
                callDeletePost.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if (response.isSuccessful()){
                            JsonObject check = response.body().getAsJsonObject();
                            Log.e("respone", response.body().toString());
                            boolean deleted = check.get("deleted").getAsBoolean();
                            if (deleted){
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPostedRoom.this);
                                builder.setMessage("Xoá thành công");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("id", id);
                                        setResult(Activity.RESULT_OK, returnIntent);
                                        Log.e("result", Activity.RESULT_OK+" " + id);
                                        finish();
                                    }
                                });
                                builder.show();
                            }
                        }else{
                            try {
                                Log.e("respone", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }


}
