package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import findingroom.cuonglm.poly.vn.findingroom.R;

public class DeitalActivity extends AppCompatActivity {
    private ImageView imgAvatar;
    private TextView edtDiachiDpt;
    private TextView edtQuanhuyen;
    private TextView edtGia;
    private TextView edtSonguoi;
    private TextView edtSodienthoai;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deital);

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String district = intent.getStringExtra("district");
        String price = intent.getStringExtra("price");
        String people = intent.getStringExtra("people");
        String phone = intent.getStringExtra("phone");
        String image = intent.getStringExtra("image");
        imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        edtDiachiDpt = (TextView) findViewById(R.id.edt_diachi_dpt);
        edtQuanhuyen = (TextView) findViewById(R.id.edt_quanhuyen);
        edtGia = (TextView) findViewById(R.id.edt_gia);
        edtSonguoi = (TextView) findViewById(R.id.edt_songuoi);
        edtSodienthoai = (TextView) findViewById(R.id.edt_sodienthoai);
        button = findViewById(R.id.buttonDetail);
        button.setText("Liên hệ");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+edtSodienthoai.getText()));
                if (ActivityCompat.checkSelfPermission(DeitalActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(callIntent);
            }
        });
        Glide.with(DeitalActivity.this)
                .load(image)
                .into(imgAvatar);
        edtDiachiDpt.setText(address);
        edtQuanhuyen.setText(district);
        edtGia.setText(price);
        edtSonguoi.setText(people);
        edtSodienthoai.setText(phone);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
