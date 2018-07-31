package findingroom.cuonglm.poly.vn.findingroom.rest;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface2 {
    @POST("posts/")
    Call<JsonElement> uploadPost(@Query("title") String title,
                                 @Query("content") String content,
                                 @Query("status") String status,
                                 @Header("Authorization") String auth);
}
