package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.adapter.ListRoomAdapter;
import findingroom.cuonglm.poly.vn.findingroom.model.Categories;
import findingroom.cuonglm.poly.vn.findingroom.model.Room;
import findingroom.cuonglm.poly.vn.findingroom.rest.RestClient2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindListRoomFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListRoomAdapter adapter;
    private Spinner filterDistrict;

    ArrayList<Room> listRoom;
    String districtSelect;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_room, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_danhsach);
        listRoom = new ArrayList<>();
        adapter = new ListRoomAdapter(listRoom) {

        };
        filterDistrict = view.findViewById(R.id.filterDistrict);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        getCategories();

        filterDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if (position!=0){
                    final ProgressDialog dialog2 = new ProgressDialog(getContext());
                    dialog2.setMessage("Loading...");
                    dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    dialog2.setCanceledOnTouchOutside(false);
                    dialog2.show();
                    int idCategory;
                    districtSelect = filterDistrict.getSelectedItem().toString();
                    for (int i = 0; i < listCategory.size(); i++) {
                        if (listCategory.get(i).getName().equalsIgnoreCase(districtSelect)) {

                            idCategory = listCategory.get(i).getId();
                            Call<JsonElement> callGetPostsByCategory = RestClient2.getApiInterface().getPostsByCategory(idCategory);
                            callGetPostsByCategory.enqueue(new Callback<JsonElement>() {
                                @Override
                                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                                    listRoom.clear();
                                    JsonArray listPost = response.body().getAsJsonArray();
                                    if(listPost.size()>0){
                                        for (int i = 0; i < listPost.size(); i++) {
                                            try {
                                                JsonObject post = listPost.get(i).getAsJsonObject();
                                                String address = post.get("title").getAsJsonObject().get("rendered").getAsString();
                                                String district = "";
                                                int idDistrict = post.get("categories").getAsJsonArray().get(0).getAsInt();
                                                for (int j = 0; j < listCategory.size(); j++) {
                                                    if (idDistrict == listCategory.get(j).getId()) {
                                                        district = listCategory.get(j).getName();

                                                    }
                                                }
                                                String content = post.get("content").getAsJsonObject().get("rendered").getAsString();
                                                content = content.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                                                String news = Html.fromHtml(content).toString();
                                                news = news.replace('“', '"');
                                                news = news.replace('”', '"');
                                                news = news.replace('″', '"');
                                                news = news.trim();
                                                JSONObject jsonObject = new JSONObject(news);

                                                int price = jsonObject.getInt("price");
                                                int people = jsonObject.getInt("people");
                                                String image = jsonObject.getString("image");
                                                String phone = jsonObject.getString("phone");
                                                Room room = new Room(address, district, price, people, phone, image);
                                                listRoom.add(room);
                                                adapter.notifyDataSetChanged();
                                                dialog2.dismiss();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }else{
                                        dialog2.dismiss();
                                        AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setMessage("Không có phòng nào thuộc quận "+filterDistrict.getItemAtPosition(position).toString()).show();
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonElement> call, Throwable t) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    ArrayList<Categories> listCategory;
    ProgressDialog dialog;

    public void getCategories() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        listCategory = new ArrayList<>();
        Call<JsonElement> callGetCategories = RestClient2.getApiInterface().getCategories(40);
        callGetCategories.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonArray listCategories = jsonElement.getAsJsonArray();
                for (int i = 0; i < listCategories.size(); i++) {
                    JsonObject cateogory = listCategories.get(i).getAsJsonObject();

                    int id = cateogory.get("id").getAsInt();
                    if (id != 1) {
                        String name = cateogory.get("name").getAsString();
                        listCategory.add(new Categories(id, name));
                    }
                }
                ArrayList<String> listString = new ArrayList<>();
                listString.add("Chọn quận để tìm...");
                for (int i = 0; i < listCategory.size(); i++) {
                    listString.add(listCategory.get(i).toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listString) {
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, View convertView,
                                                ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv = (TextView) view;
                        if (position == 0) {
                            // Set the hint text color gray
                            tv.setTextColor(Color.GRAY);
                        } else {
                            tv.setTextColor(Color.BLACK);
                        }
                        return view;
                    }
                };
                filterDistrict.setAdapter(adapter);
                getRoomList();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
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


    private void getRoomList() {
        Call<JsonElement> callGetAllRoom = RestClient2.getApiInterface().getPosts();
        callGetAllRoom.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonArray listPost = response.body().getAsJsonArray();
                for (int i = 0; i < listPost.size(); i++) {
                    try {
                        JsonObject post = listPost.get(i).getAsJsonObject();
                        String address = post.get("title").getAsJsonObject().get("rendered").getAsString();
                        String district = "";
                        int idDistrict = post.get("categories").getAsJsonArray().get(0).getAsInt();
                        for (int j = 0; j < listCategory.size(); j++) {
                            if (idDistrict == listCategory.get(j).getId()) {
                                district = listCategory.get(j).getName();

                            }
                        }
                        String content = post.get("content").getAsJsonObject().get("rendered").getAsString();
                        content = content.replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                        String news = Html.fromHtml(content).toString();
                        news = news.replace('“', '"');
                        news = news.replace('”', '"');
                        news = news.replace('″', '"');
                        news = news.trim();
                        JSONObject jsonObject = new JSONObject(news);

                        int price = jsonObject.getInt("price");
                        int people = jsonObject.getInt("people");
                        String image = jsonObject.getString("image");
                        String phone = jsonObject.getString("phone");
                        Room room = new Room(address, district, price, people, phone, image);
                        listRoom.add(room);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }
}
