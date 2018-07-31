package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import findingroom.cuonglm.poly.vn.findingroom.R;

public class UpdateRoomActivity extends AppCompatActivity {
    private ImageView img1UpdateDpt;
    private ImageView img2Dpt;
    private ImageView img3UpdateDpt;
    private ImageView img4UpdateDpt;
    private ImageView img5UpdateDpt;
    private ImageView img6UpdateDpt;
    private Button btnUpdateImageDpt;
    private EditText edtUpdateDiachiDpt;
    private EditText edtUpdateXaphuongDpt;
    private EditText edtUpdateQuanhuyenDpt;
    private EditText edtUpdateGiaDpt;
    private EditText edtUpdateSonguoiDpt;
    private EditText edtUpdateSodienthoaiDpt;
    private Button btnUpdateDpt;
    private Button btnUpdateCancleDpt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_room);
        widget();

    }

    private void widget(){
        img1UpdateDpt = (ImageView) findViewById(R.id.img1_update_dpt);
        img2Dpt = (ImageView) findViewById(R.id.img2_dpt);
        img3UpdateDpt = (ImageView) findViewById(R.id.img3_update_dpt);
        img4UpdateDpt = (ImageView) findViewById(R.id.img4_update_dpt);
        img5UpdateDpt = (ImageView) findViewById(R.id.img5_update_dpt);
        img6UpdateDpt = (ImageView) findViewById(R.id.img6_update_dpt);
        btnUpdateImageDpt = (Button) findViewById(R.id.btn_update_image_dpt);
        edtUpdateDiachiDpt = (EditText) findViewById(R.id.edt_update_diachi_dpt);
        edtUpdateXaphuongDpt = (EditText) findViewById(R.id.edt__update_xaphuong_dpt);
        edtUpdateQuanhuyenDpt = (EditText) findViewById(R.id.edt_update_quanhuyen_dpt);
        edtUpdateGiaDpt = (EditText) findViewById(R.id.edt_update_gia_dpt);
        edtUpdateSonguoiDpt = (EditText) findViewById(R.id.edt_update_songuoi_dpt);
        edtUpdateSodienthoaiDpt = (EditText) findViewById(R.id.edt_update_sodienthoai_dpt);
        btnUpdateDpt = (Button) findViewById(R.id.btn_update_dpt);
        btnUpdateCancleDpt = (Button) findViewById(R.id.btn_update_cancle_dpt);
    }
}
