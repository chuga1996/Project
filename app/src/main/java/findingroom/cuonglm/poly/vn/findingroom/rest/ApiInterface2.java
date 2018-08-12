package findingroom.cuonglm.poly.vn.findingroom.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @Multipart
    @POST("media/")
    Call<JsonElement> uploadImage(@Header("Authorization") String auth, @Part MultipartBody.Part file);

    @GET("posts/")
    Call<JsonElement> getPostsByAuthor(@Query("author") int idAuthor);


    @DELETE("posts/{id}/")
    Call<JsonElement> deletePostbyID(@Path(value = "id",encoded = true) int id, @Query("force") String force,@Header("Authorization") String auth );

}
