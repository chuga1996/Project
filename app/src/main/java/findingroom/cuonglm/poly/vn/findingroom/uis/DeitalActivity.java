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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_deital);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
