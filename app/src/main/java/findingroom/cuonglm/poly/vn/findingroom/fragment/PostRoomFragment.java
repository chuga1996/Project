package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.app.Fragment;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.rest.DoingWithAPI;

public class PostRoomFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private Toolbar toolbarDeital;
    List<ImageView> imgList;
    private ImageView img1Dpt;
    private EditText edtDiachiDpt;
    private EditText edtXaphuongDpt;
    private Spinner spnQuanhuyenDpt;
    private EditText edtGiaDpt;
    private EditText edtSonguoiDpt;
    private EditText edtSodienthoaiDpt;
    private Button btnUploadImageDpt;
    private Button btnPostDpt;
    private Button btnCancleDpt;
    private Bitmap bitmap;
    private static final int RESULT_LOAD_IMAGE = 100;
    private int current = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dang_phong_tro,container,false);

        widget(view);
        imgList.add(img1Dpt);
        return view;
    }

    public void widget(View view) {
        toolbarDeital = (Toolbar) view.findViewById(R.id.toolbar_deital);
        img1Dpt = (ImageView) view.findViewById(R.id.img1_dpt);
        edtDiachiDpt = (EditText) view.findViewById(R.id.edt_diachi_dpt);
        edtXaphuongDpt = (EditText) view.findViewById(R.id.edt_xaphuong_dpt);
        spnQuanhuyenDpt = (Spinner) view.findViewById(R.id.spinner_quanhuyen_dpt);
        edtGiaDpt = (EditText) view.findViewById(R.id.edt_gia_dpt);
        edtSonguoiDpt = (EditText) view.findViewById(R.id.edt_songuoi_dpt);
        edtSodienthoaiDpt = (EditText) view.findViewById(R.id.edt_sodienthoai_dpt);
        btnPostDpt = (Button) view.findViewById(R.id.btn_post_dpt);
        btnCancleDpt = (Button) view.findViewById(R.id.btn_cancle_dpt);
        btnUploadImageDpt = (Button) view.findViewById(R.id.btn_upload_image_dpt);
        btnPostDpt.setOnClickListener(this);
        btnCancleDpt.setOnClickListener(this);
        btnUploadImageDpt.setOnClickListener(this);
        imgList = new ArrayList<>();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_image_dpt:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);
                break;
            case R.id.btn_post_dpt:
                String address = edtDiachiDpt.getText().toString() + ", " + edtXaphuongDpt.getText().toString();
//                String district =
                String price  = edtGiaDpt.getText().toString();
                int people = Integer.parseInt(edtSonguoiDpt.getText().toString());
                String phone = edtSodienthoaiDpt.getText().toString();

                String content = "{\"district\":1,\"price\":2000000,\"people\":3,\"image\":[{\"link\":\"google.com.vn\"},{\"link\":\"asdasc.acasc\"}],\"phone\":\"01646100980\"}";
                DoingWithAPI.uploadPost(getContext(),"address","abc","admin","admin");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == getActivity().RESULT_OK) {
                    try {
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        if (current < 6){
                            imgList.get(current).setImageBitmap(bitmap);
                            current++;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getActivity(), "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
