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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;

public class PostRoomFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private Toolbar toolbarDeital;
    List<ImageView> imgList;
    private ImageView img1Dpt;
    private ImageView img2Dpt;
    private ImageView img3Dpt;
    private ImageView img4Dpt;
    private ImageView img5Dpt;
    private ImageView img6Dpt;
    private EditText edtDiachiDpt;
    private EditText edtXaphuongDpt;
    private EditText edtQuanhuyenDpt;
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
        imgList.add(img2Dpt);
        imgList.add(img3Dpt);
        imgList.add(img4Dpt);
        imgList.add(img5Dpt);
        imgList.add(img6Dpt);
        return view;
    }

    public void widget(View view) {
        toolbarDeital = (Toolbar) view.findViewById(R.id.toolbar_deital);
        img1Dpt = (ImageView) view.findViewById(R.id.img1_dpt);
        img2Dpt = (ImageView) view.findViewById(R.id.img2_dpt);
        img3Dpt = (ImageView) view.findViewById(R.id.img3_dpt);
        img4Dpt = (ImageView) view.findViewById(R.id.img4_dpt);
        img5Dpt = (ImageView) view.findViewById(R.id.img5_dpt);
        img6Dpt = (ImageView) view.findViewById(R.id.img6_dpt);
        edtDiachiDpt = (EditText) view.findViewById(R.id.edt_diachi_dpt);
        edtXaphuongDpt = (EditText) view.findViewById(R.id.edt_xaphuong_dpt);
        edtQuanhuyenDpt = (EditText) view.findViewById(R.id.edt_quanhuyen_dpt);
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
