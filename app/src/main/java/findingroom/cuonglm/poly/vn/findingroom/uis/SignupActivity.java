package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.rest.DoingWithAPI;
import findingroom.cuonglm.poly.vn.findingroom.rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout edtUsername;
    private TextInputLayout edtFullName;
    private TextInputLayout edtPassword;
    private TextInputLayout edtRePassword;
    private TextInputLayout edtEmail;
    private TextInputLayout edtPhone;
    private Button btnSignUp;
    String username, fullname, password, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUsername = (TextInputLayout) findViewById(R.id.edtUsername);
        edtFullName = (TextInputLayout) findViewById(R.id.edtFullName);
        edtPassword = (TextInputLayout) findViewById(R.id.edtPassword);
        edtRePassword = (TextInputLayout) findViewById(R.id.edtRePassword);
        edtEmail = (TextInputLayout) findViewById(R.id.edtEmail);
        edtPhone = (TextInputLayout) findViewById(R.id.edtPhone);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                username = edtUsername.getEditText().getText().toString();
                fullname = edtFullName.getEditText().getText().toString();
                password = edtPassword.getEditText().getText().toString();
                email = edtEmail.getEditText().getText().toString();
                phone = edtPhone.getEditText().getText().toString();
                if (username.isEmpty()) {
                    edtUsername.setError("Không để trống tài khoản");
                } else if(fullname.isEmpty()){
                    edtFullName.setError("Không để trống tên");
                } else if(password.isEmpty()){
                    edtPassword.setError("Không để trống mật khẩu");
                }else if(edtRePassword.getEditText().getText().toString().isEmpty()){
                    edtRePassword.setError("Nhập lại mật khẩu");
                } else if(email.isEmpty()){
                    edtEmail.setError("Không để trống email");
                } else {
                    if (edtRePassword.getEditText().getText().toString().equals(edtPassword.getEditText().getText().toString())) {
                        edtUsername.setErrorEnabled(false);
                        edtFullName.setErrorEnabled(false);
                        edtPassword.setErrorEnabled(false);
                        edtRePassword.setErrorEnabled(false);
                        edtEmail.setErrorEnabled(false);
                        DoingWithAPI.registerUser(SignupActivity.this, username, fullname, password, email, phone);

                    }else{
                       edtRePassword.setError("Mật khẩu nhập lại khác mậu khẩu trên!");
                    }
                }
            }
        });


    }

}
