package com.cdhxqh.inventorymovement.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.cdhxqh.inventorymovement.AppManager;
import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.utils.AccountUtils;
import com.cdhxqh.inventorymovement.utils.MessageUtils;
import com.umeng.update.UmengUpdateAgent;

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
    private CheckBox checkBox; //记住密码

    private boolean isRemember; //是否记住密码


    String userName; //用户名
    String userPassWorld; //密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UmengUpdateAgent.setDefault();
        UmengUpdateAgent.update(this);
        initView();
        setEvent();
        mLogin.setOnClickListener(this);
    }


    private void initView() {
        mUsername = (EditText) findViewById(R.id.login_username_edit);
        mPassword = (EditText) findViewById(R.id.login_password_edit);
        mLogin = (Button) findViewById(R.id.login_login_btn);
        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        if (isChecked) {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
        }


        checkBox = (CheckBox) findViewById(R.id.checkBox);

    }

    /**
     * 设置事件监听*
     */
    private void setEvent() {
        checkBox.setOnCheckedChangeListener(cheBoxOnCheckedChangListener);
    }


    private CompoundButton.OnCheckedChangeListener cheBoxOnCheckedChangListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isRemember = isChecked;
        }
    };

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
                }
                break;

        }
    }

    /**
     * 登陆*
     */
    private void login() {
        mProgressDialog = ProgressDialog.show(LoginActivity.this, null,
                getString(R.string.login_loging), true, true);

        ImManager.loginWithUsername(LoginActivity.this,
                mUsername.getText().toString(),
                mPassword.getText().toString(),
                new HttpRequestHandler<Integer>() {
                    @Override
                    public void onSuccess(Integer data) {

                        MessageUtils.showMiddleToast(LoginActivity.this, "登陆成功");
                        mProgressDialog.dismiss();
                        if (isRemember) {
                            AccountUtils.setChecked(LoginActivity.this, isRemember);
                            //记住密码
                            AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                        }
                        startIntent();

                    }

                    @Override
                    public void onSuccess(Integer data, int totalPages, int currentPage) {
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


    private void startIntent() {
        Intent inetnt = new Intent();
        inetnt.setClass(this, MainActivity.class);
        startActivity(inetnt);
    }


    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(LoginActivity.this);
        }
    }

}
