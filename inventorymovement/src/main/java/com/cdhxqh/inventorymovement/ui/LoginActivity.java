package com.cdhxqh.inventorymovement.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.utils.MessageUtils;

/**
 * Created by yugy on 14-2-26.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;
    private ProgressDialog mProgressDialog;
//    private MemberModel mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        mLogin.setOnClickListener(this);
    }

    private void initView() {
        mUsername = (EditText) findViewById(R.id.login_username_edit);
        mPassword = (EditText) findViewById(R.id.login_password_edit);
        mLogin = (Button) findViewById(R.id.login_login_btn);
        mUsername.setText("maxadmin");
        mPassword.setText("maxmax");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_btn:
                if (mUsername.getText().length() == 0) {
                    mUsername.setError(getString(R.string.login_error_empty_user));
                    mUsername.requestFocus();
                } else if (mPassword.getText().length() == 0) {
                    mPassword.setError(getString(R.string.login_error_empty_passwd));
                    mPassword.requestFocus();
                } else {
                    login();
//                    Intent intent=new Intent();
//                    intent.setClass(this,MainActivity.class);
//                    startActivity(intent);
                }
                break;

        }
    }

    /**登陆**/
    private void login() {
        mProgressDialog = ProgressDialog.show(LoginActivity.this, null,
                getString(R.string.login_loging), true, true);

        ImManager.loginWithUsername(LoginActivity.this,
                mUsername.getText().toString(),
                mPassword.getText().toString(),
                new HttpRequestHandler<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {

//                        getProfile();
                        MessageUtils.showMiddleToast(LoginActivity.this, "登陆成功");
                        mProgressDialog.dismiss();
                        startIntent();

                    }

                    @Override
                    public void onSuccess(Integer data, int totalPages, int currentPage) {
//                        getProfile();
                        MessageUtils.showMiddleToast(LoginActivity.this, "登陆成功");

                        startIntent();
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(LoginActivity.this, error);
                        mProgressDialog.dismiss();
                    }
                });
    }


        private void startIntent(){
            Intent inetnt=new Intent();
            inetnt.setClass(this,MainActivity.class);
            startActivity(inetnt);
        }


}
