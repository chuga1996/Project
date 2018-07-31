package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import findingroom.cuonglm.poly.vn.findingroom.R;

public class DeitalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deital);


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
