package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import findingroom.cuonglm.poly.vn.findingroom.R;

public class DeitalActivity extends AppCompatActivity {
    private ImageView imgAvatar;
    private TextView edtDiachiDpt;
    private TextView edtQuanhuyen;
    private TextView edtGia;
    private TextView edtSonguoi;
    private TextView edtSodienthoai;


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

        imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        edtDiachiDpt = (TextView) findViewById(R.id.edt_diachi_dpt);
        edtQuanhuyen = (TextView) findViewById(R.id.edt_quanhuyen);
        edtGia = (TextView) findViewById(R.id.edt_gia);
        edtSonguoi = (TextView) findViewById(R.id.edt_songuoi);
        edtSodienthoai = (TextView) findViewById(R.id.edt_sodienthoai);

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
