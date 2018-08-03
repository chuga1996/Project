package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import findingroom.cuonglm.poly.vn.findingroom.R;
import findingroom.cuonglm.poly.vn.findingroom.rest.DoingWithAPI;

public class ACLogin extends AppCompatActivity {
    private EditText edtUser;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvSignup;
    private CheckBox cbcheck;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser = (EditText) findViewById(R.id.edt_user);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        cbcheck=(CheckBox) findViewById(R.id.cbcheck);
        tvSignup = (TextView) findViewById(R.id.tv_signup);
        context = ACLogin.this;
        restoringPreferent();

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ACLogin.this, SignupActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingPreferent();
                if (TextUtils.isEmpty(edtUser.getText().toString())){
                    edtUser.setError("Vui lòng nhập thông tin tài khoản");
                }else if (TextUtils.isEmpty(edtPassword.getText().toString())){
                    edtPassword.setError("Vui lòng nhập mật khẩu");
                }else {
                    DoingWithAPI.login(ACLogin.this,edtUser.getText().toString() ,edtPassword.getText().toString());
                }
            }
        });

    }
    public void savingPreferent(){
        SharedPreferences preferences=getSharedPreferences("lam",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        String user=edtUser.getText().toString();
        String password=edtPassword.getText().toString();
        boolean check = cbcheck.isChecked();
        if (!check){
            editor.clear();
        }else {
            editor.putString("username",user);
            editor.putString("password",password);
            editor.putBoolean("savestatus",check);
        }
        editor.commit();

    }
    public void restoringPreferent(){
        SharedPreferences preferences=getSharedPreferences("lam",MODE_PRIVATE);
        boolean check=preferences.getBoolean("savestatus",false);
        if (check){
            String user=preferences.getString("username","");
            String password=preferences.getString("password","");
            edtUser.setText(user);
            edtPassword.setText(password);
        }
        cbcheck.setChecked(check);
    }

}
