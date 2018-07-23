package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import findingroom.cuonglm.poly.vn.findingroom.R;

public class ACLogin extends AppCompatActivity {
    private EditText edtUser;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView tvSignup;
    private CheckBox cbcheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUser = (EditText) findViewById(R.id.edt_user);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        cbcheck=(CheckBox) findViewById(R.id.cbcheck);
        tvSignup = (TextView) findViewById(R.id.tv_signup);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ACLogin.this, SignupActivity.class));
            }
        });
    }
}
