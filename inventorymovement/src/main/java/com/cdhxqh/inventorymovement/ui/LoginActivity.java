package com.cdhxqh.inventorymovement.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cdhxqh.inventorymovement.R;

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
//                    login();
                    Intent intent=new Intent();
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }

//    private void login() {
//        mProgressDialog = ProgressDialog.show(LoginActivity.this, null,
//                getString(R.string.login_loging), true, true);
//
//        V2EXManager.loginWithUsername(this,
//                mUsername.getText().toString(),
//                mPassword.getText().toString(),
//                new HttpRequestHandler<Integer>() {
//                    @Override
//                    public void onSuccess(Integer data) {
//                        getProfile();
//                    }
//
//                    @Override
//                    public void onSuccess(Integer data, int totalPages, int currentPage) {
//                        getProfile();
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//                        MessageUtils.showErrorMessage(LoginActivity.this, error);
//                        mProgressDialog.dismiss();
//                    }
//                });
//    }
//
//    private HttpRequestHandler<ArrayList<MemberModel>> profileHandler =
//            new HttpRequestHandler<ArrayList<MemberModel>>() {
//                @Override
//                public void onSuccess(ArrayList<MemberModel> data) {
//                    mProgressDialog.dismiss();
//                    if (data.size() == 0) {
//                        onFailure("");
//                        return;
//                    }
//                    mProfile = data.get(0);
//                    AccountUtils.writeLoginMember(LoginActivity.this, mProfile);
//                    mProgressDialog.dismiss();
//                    Intent intent = new Intent();
//                    intent.putExtra("profile", (Parcelable) mProfile);
//                    setResult(RESULT_OK, intent);
//                    finish();
//                }
//
//                @Override
//                public void onSuccess(ArrayList<MemberModel> data, int totalPages, int currentPage) {
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    mProgressDialog.dismiss();
//                    MessageUtils.showErrorMessage(LoginActivity.this, error);
//                }
//            };
//
//    private void getProfile() {
//        mProgressDialog.setMessage(getString(R.string.login_obtain_profile));
//        V2EXManager.getProfile(this, profileHandler);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                Intent upIntent = NavUtils.getParentActivityIntent(this);
//                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
//                    TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
//                } else {
//                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    NavUtils.navigateUpTo(this, upIntent);
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}