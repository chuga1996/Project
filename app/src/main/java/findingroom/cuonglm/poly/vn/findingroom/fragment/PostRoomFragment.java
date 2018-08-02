package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.model.Categories;
import findingroom.cuonglm.poly.vn.findingroom.rest.DoingWithAPI;
import findingroom.cuonglm.poly.vn.findingroom.rest.RestClient2;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRoomFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    private Toolbar toolbarDeital;
    List<ImageView> imgList;
    private ImageView img1Dpt;
    private EditText edtDiachiDpt;
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
    String username, password;
    String link;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_phong_tro, container, false);

        widget(view);
        imgList.add(img1Dpt);
        return view;
    }

    public void widget(View view) {
        img1Dpt = (ImageView) view.findViewById(R.id.img1_dpt);
        edtDiachiDpt = (EditText) view.findViewById(R.id.edt_diachi_dpt);
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
        list = new ArrayList<>();

        getCategories();
        Bundle bundle = getArguments();
        username = bundle.getString("username");
        password = bundle.getString("password");
    }
    int price;
    int people;
    String address,district;
    String phone;
    int idCategory = 1;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_upload_image_dpt:
                new ImagePicker.Builder(getActivity())
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
                break;
            case R.id.btn_post_dpt:
                if (edtDiachiDpt.getText().toString().isEmpty() && edtGiaDpt.getText().toString().isEmpty() && edtSodienthoaiDpt.getText().toString().isEmpty() && edtSonguoiDpt.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Không để trống các ô", Toast.LENGTH_LONG).show();
                } else {

                   address = edtDiachiDpt.getText().toString();
                   district = spnQuanhuyenDpt.getSelectedItem().toString();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getName().equalsIgnoreCase(district)) {
                            idCategory = list.get(i).getId();
                        }
                    }
                  price = Integer.parseInt(edtGiaDpt.getText().toString());
                    people = Integer.parseInt(edtSonguoiDpt.getText().toString());
                    phone = edtSodienthoaiDpt.getText().toString();
                    uploadImage();

                }

        }
    }

    String pathFile;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            List<String> mPaths = (List<String>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_PATH);
            pathFile = mPaths.get(0);
            Bitmap myBitmap = BitmapFactory.decodeFile(pathFile);
            img1Dpt.setImageBitmap(myBitmap);
        }
    }

    ArrayList<Categories> list;

    public void getCategories() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        Call<JsonElement> callGetCategories = RestClient2.getApiInterface().getCategories(40);
        callGetCategories.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                dialog.dismiss();
                JsonElement jsonElement = response.body();
                JsonArray listCategories = jsonElement.getAsJsonArray();
                for (int i = 0; i < listCategories.size(); i++) {
                    JsonObject cateogory = listCategories.get(i).getAsJsonObject();

                    int id = cateogory.get("id").getAsInt();
                    if (id != 1) {
                        String name = cateogory.get("name").getAsString();
                        list.add(new Categories(id, name));
                    }
                }
                ArrayList<String> listString = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    listString.add(list.get(i).toString());
                }
                Log.e("list2", list.size() + "");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, listString);
                spnQuanhuyenDpt.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                dialog.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Không tải được");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                t.printStackTrace();
            }
        });
    }

    public void uploadImage() {
        String auth = Credentials.basic(username, password);
        File file = new File(pathFile);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse(pathFile), file);
        Log.e("path",pathFile);
        MultipartBody.Part uploadPart =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Call<JsonElement> callUploadImage = RestClient2.getApiInterface().uploadImage(auth, uploadPart);
        callUploadImage.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonObject image = response.body().getAsJsonObject();
                link = image.get("guid").getAsJsonObject().get("rendered").getAsString();
                String content = "{\"price\":" + price + ",\"people\":" + people + ",\"image\":\"" + link + "\",\"phone\":\"" + phone + "\"}";
                DoingWithAPI.uploadPost(getContext(), address, content, username, password, idCategory);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
