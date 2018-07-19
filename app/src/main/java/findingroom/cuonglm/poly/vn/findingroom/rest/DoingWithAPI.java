package findingroom.cuonglm.poly.vn.findingroom.rest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
import retrofit2.Response;

public class DoingWithAPI {

    public static void registerUser(final Context context, final String user_name, final String name, final String pass, final String email, final String phone){
        String nonce;

        Call<JsonElement> callGetNonce = RestClient.getApiInterface().getNonce("user","register");

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        callGetNonce.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonObject getNonce = jsonElement.getAsJsonObject();
                String nonce = getNonce.get("nonce").getAsString();
                Call<JsonElement> callRegister = RestClient.getApiInterface().registerUser(user_name,email, nonce,pass,name,phone);
                callRegister.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        JsonElement jsonElement2 = response.body();
                        JsonObject register = jsonElement2.getAsJsonObject();
                        String status = register.get("status").getAsString();
                        if(status.equalsIgnoreCase("ok")){
                            dialog.dismiss();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Đăng ký thành công");
                            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            builder.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        Toast.makeText(context,"Khong load dc 1",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(context,"Khong load dc 2 ",Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

}
