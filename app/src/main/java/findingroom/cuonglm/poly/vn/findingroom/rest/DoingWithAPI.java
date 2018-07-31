package findingroom.cuonglm.poly.vn.findingroom.rest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import findingroom.cuonglm.poly.vn.findingroom.model.Categories;
import findingroom.cuonglm.poly.vn.findingroom.uis.MainActivity;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoingWithAPI {

    public static void registerUser(final Context context, final String user_name, final String name, final String pass, final String email, final String phone) {


        Call<JsonElement> callGetNonce = RestClient.getApiInterface().getNonce("user", "register");

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        callGetNonce.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, final Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonObject getNonce = jsonElement.getAsJsonObject();
                String nonce = getNonce.get("nonce").getAsString();
                Call<JsonElement> callRegister = RestClient.getApiInterface().registerUser(user_name, email, nonce, pass, name, phone);
                callRegister.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response2) {
                        if (response2.isSuccessful()) {
                            JsonElement jsonElement2 = response2.body();
                            JsonObject register = jsonElement2.getAsJsonObject();
                            String status = register.get("status").getAsString();
                            if (status.equalsIgnoreCase("ok")) {
                                dialog.dismiss();
                                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setTitle("Đăng ký thành công");
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        ((Activity) context).finish();
                                    }
                                });
                                builder.show();
                            }
                        } else {
                            dialog.dismiss();
                            try {
                                JSONObject errorObject = new JSONObject(response2.errorBody().string());
                                String status = errorObject.getString("status");
                                if (status.equalsIgnoreCase("error")) {
                                    String error = errorObject.getString("error");
                                    dialog.dismiss();
                                    if (error.equalsIgnoreCase("Username already exists.")) {
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("Tên tài khoản đã tồn tại");
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        builder.show();
                                    } else if (error.equalsIgnoreCase("E-mail address is already in use.")) {
                                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("Email đã tồn tại");
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                            }
                                        });
                                        builder.show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }

                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Toast.makeText(context, "Không tải được", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context, "Không tải được", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public static void login(final Context context, final String username, final String password) {

        Call<JsonElement> callGetNonce = RestClient.getApiInterface().getNonce("auth", "generate_auth_cookie");
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();

        callGetNonce.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, final Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonObject getNonce = jsonElement.getAsJsonObject();
                String nonce = getNonce.get("nonce").getAsString();
                Call<JsonElement> callLogin = RestClient.getApiInterface().getCookieUser(nonce, username, password);
                callLogin.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response2) {
                        dialog.dismiss();

                        if (response2.isSuccessful()) {
                            JsonElement jsonElement2 = response2.body();
                            JsonObject login = jsonElement2.getAsJsonObject();
                            String status = login.get("status").getAsString();

                            if (status.equalsIgnoreCase("ok")) {

                                String cookie = login.get("cookie").getAsString();
                                JsonObject user = login.get("user").getAsJsonObject();
                                String displaynName = user.get("displayname").getAsString();
                                String email = user.get("email").getAsString();
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("displayname", displaynName);
                                intent.putExtra("email", email);
                                intent.putExtra("username", username);
                                intent.putExtra("password", password);
                                context.startActivity(intent);
                            } else {
                                String error = login.get("error").getAsString();
                                if (error.equalsIgnoreCase("Invalid username and/or password.")) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Sai tên tài khoản hoặc mật khẩu");
                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    builder.show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context, "Không tải được", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }


    public static void uploadPost(final Context context, String title, String content, String username, String password) {
        String auth = Credentials.basic(username, password);
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        Call<JsonElement> callUploadPost = RestClient2.getApiInterface().uploadPost(title, content, "publish", auth);
        callUploadPost.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()){
                    dialog.dismiss();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng thành công");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            ((Activity)context).finish();
                        }
                    });
                    builder.show();
                }else{
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        Log.e("respone", errorObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    public  static ArrayList<Categories> getCategories(Context context){
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        final ArrayList<Categories> list = new ArrayList<>();
        Call<JsonElement> callGetCategories=RestClient2.getApiInterface().getCategories(40);
        callGetCategories.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonArray listCategories = jsonElement.getAsJsonArray();
                for (int i = 0; i<listCategories.size();i++){
                    JsonObject cateogory = listCategories.get(i).getAsJsonObject();

                    int id  = cateogory.get("id").getAsInt();
                    if (id != 1){
                        String name = cateogory.get("name").getAsString();
                        list.add(new Categories(id,name));

                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
            }
        });
        return list;
    }
}
