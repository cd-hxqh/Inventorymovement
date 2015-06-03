package com.cdhxqh.inventorymovement.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cdhxqh.inventorymovement.AppManager;
import com.cdhxqh.inventorymovement.R;

public class BaseActivity extends Activity {
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    public void showProgressBar(boolean show) {
        showProgressBar(show, "");
    }

    public void showProgressBar(boolean show, String message) {
        initProgressBar();
        if (show) {
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        } else {
            mProgressDialog.hide();
        }
    }

    private void initProgressBar() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
    }

    public void showProgressBar(int messageId) {
        String message = getString(messageId);
        showProgressBar(true, message);
    }
}
