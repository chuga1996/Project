package findingroom.cuonglm.poly.vn.findingroom.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.adapter.PostedRoomAdapter;
import findingroom.cuonglm.poly.vn.findingroom.model.Categories;
import findingroom.cuonglm.poly.vn.findingroom.model.Room;
import findingroom.cuonglm.poly.vn.findingroom.rest.RestClient2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostedRoomFragment extends Fragment {
    RecyclerView recyclerView;
    PostedRoomAdapter adapter;
    List<Room> phongTroList;
    int idAuthor;
    ArrayList<Categories> listCategory;
    ProgressDialog dialog2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posted_room,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_cacphongdadang);
        phongTroList = new ArrayList<>();
        adapter = new PostedRoomAdapter(phongTroList);
        listCategory = new ArrayList<>();
        idAuthor = getArguments().getInt("id");
        Log.e("id", idAuthor+"");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        dialog2= new ProgressDialog(getContext());
        dialog2.setMessage("Loading...");
        dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.show();
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
                Call<JsonElement> callGetPostByAuthor = RestClient2.getApiInterface().getPostsByAuthor(idAuthor);
                callGetPostByAuthor.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response2) {
                        phongTroList.clear();
                        JsonArray listPost = response2.body().getAsJsonArray();
                        if(listPost.size()>0){
                            for (int i = 0; i < listPost.size(); i++) {
                                try {
                                    JsonObject post = listPost.get(i).getAsJsonObject();
                                    int id  = post.get("id").getAsInt();
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
                                    Room room = new Room(address, district, price, people, phone, image,id);
                                    phongTroList.add(room);
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
                            }).setMessage("Bạn chưa đăng bài nào").show();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


        return view;
    }


}
