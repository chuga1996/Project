package findingroom.cuonglm.poly.vn.findingroom.rest;

import com.google.gson.JsonElement;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface2 {
    @POST("posts/")
    Call<JsonElement> uploadPost(@Query("title") String title,
                                 @Query("content") String content,
                                 @Query("status") String status,
                                 @Query("categories") int categories,
                                 @Header("Authorization") String auth);
    @GET("categories/")
    Call<JsonElement> getCategories(@Query("per_page") int perpage);

    @GET("posts/")
    Call<JsonElement> getPosts();

    @GET("posts/")
    Call<JsonElement> getPostsByCategory(@Query("categories") int idCategory);

}
