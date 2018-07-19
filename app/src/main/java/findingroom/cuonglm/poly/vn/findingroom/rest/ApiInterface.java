package findingroom.cuonglm.poly.vn.findingroom.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("get_nonce/")
    Call<JsonElement> getNonce(@Query("controller") String controller,
                              @Query("method") String method);
    @GET("user/register/")
    Call<JsonElement> registerUser(@Query("username") String username,
                      @Query("email") String email,
                      @Query("nonce") String nonce,
                      @Query("user_pass") String user_pass,
                      @Query("display_name")String display_name,
                      @Query("description")String user_phone);
}
