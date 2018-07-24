package findingroom.cuonglm.poly.vn.findingroom.rest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class DoingWithAPI {

    public static void registerUser(final Context context, final String user_name, final String name, final String pass, final String email, final String phone) {
        String nonce;

        Call<JsonElement> callGetNonce = RestClient.getApiInterface().getNonce("user", "register");

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        callGetNonce.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, final Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                Log.e("respone",response.body().getAsString());
                JsonObject getNonce = jsonElement.getAsJsonObject();
                String nonce = getNonce.get("nonce").getAsString();
                Call<JsonElement> callRegister = RestClient.getApiInterface().registerUser(user_name, email, nonce, pass, name, phone);
                callRegister.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response2) {
                        if (response2.isSuccessful()){
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
                        }else{
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

}
