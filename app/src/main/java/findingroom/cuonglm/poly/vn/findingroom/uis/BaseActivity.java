package findingroom.cuonglm.poly.vn.findingroom.uis;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(injectLayout());
        initialViews();
        initialVariables();
    }

    /**
     * Setup layout for activity
     * @return
     */
    public abstract int injectLayout();

    /**
     * initial views
     */
    public abstract void initialViews();

    /**
     * initial variables
     */
    public abstract void initialVariables();

    /**
     * Show progress dialog
     */

    public void showProgressDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(BaseActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

    public void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.setCancelable(false);
            progressDialog.dismiss();
        }
    }

    public void loadImages(){

    }

    public void openGallery(){

    }
}
