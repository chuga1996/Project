package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.app.ProgressDialog;
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
    private EditText edtUsername;
    private EditText edtFullName;
    private EditText edtPassword;
    private EditText edtRePassword;
    private EditText edtEmail;
    private EditText edtPhone;
    private Button btnSignUp;
    String username, fullname, password, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtFullName = (EditText) findViewById(R.id.edtFullName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtRePassword = (EditText) findViewById(R.id.edtRePassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                username = edtUsername.getText().toString();
                fullname = edtFullName.getText().toString();
                password = edtPassword.getText().toString();
                email = edtEmail.getText().toString();
                phone = edtPhone.getText().toString();
                if (edtUsername.getText().toString().isEmpty() && edtEmail.getText().toString().isEmpty()
                        && edtPassword.getText().toString().isEmpty() && edtRePassword.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Không để trống ", Toast.LENGTH_SHORT).show();
                } else {
                    if (edtRePassword.getText().toString().equals(edtPassword.getText().toString())) {
                        DoingWithAPI.registerUser(SignupActivity.this, username, fullname, password, email, phone);
                    }else{
                        Toast.makeText(SignupActivity.this, "Password và RePassword phải giống nhau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

}
