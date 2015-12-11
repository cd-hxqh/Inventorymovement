package com.cdhxqh.inventorymovement.ui.detailsUi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.ui.BaseActivity;
import com.cdhxqh.inventorymovement.ui.pictureui.PictureActivity;
import com.cdhxqh.inventorymovement.utils.InputUtils;
import com.cdhxqh.inventorymovement.webserviceclient.AndroidClientService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 主项目详情
 */
public class ItemDetailsActivity extends BaseActivity {

    private static final String TAG = "ItemDetailsActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回按钮

    /**
     * --界面显示的textView--**
     */

    private TextView numTextView; //项目编号

    private EditText descTextView; //项目描述

    private EditText in20TextView; //规格型号

    private TextView orderunitTextView; //订购单位

    private TextView issueunitTextView; //发放单位

    private TextView enterbyTextView; //录入人

    private TextView enterdateTextView; //录入时间

    private Button submitButton; //提交按钮

    private RecyclerView mRecyclerView; //图片显示按钮

    private ImageView cameraImageView; //照相按钮

    private ImageView fileImageView; //图片浏览按钮

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        geiIntentData();
        initView();
        setEvent();
    }

    /**
     * 获取上个界面的数据*
     */
    private void geiIntentData() {
        item = (Item) getIntent().getSerializableExtra("item");
        Log.i(TAG, "item=" + item);

    }


    /**
     * 初始化界面组件
     */
    private void initView() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);
        numTextView = (TextView) findViewById(R.id.item_num_text_id);
        descTextView = (EditText) findViewById(R.id.item_desc_text);
        in20TextView = (EditText) findViewById(R.id.item_in20_text);
        orderunitTextView = (TextView) findViewById(R.id.item_orderunit_text);
        issueunitTextView = (TextView) findViewById(R.id.item_issueunit_text);
        enterbyTextView = (TextView) findViewById(R.id.item_enterby_text);
        enterdateTextView = (TextView) findViewById(R.id.item_enterdate_text);

        submitButton = (Button) findViewById(R.id.submit_button_id);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);


        cameraImageView=(ImageView)findViewById(R.id.image_camera_id);
        fileImageView=(ImageView)findViewById(R.id.image_create_id);


    }


    public void updateSendButtonStyle() {
        if (sendButtonEnable()) {
            submitButton.setVisibility(View.VISIBLE);
        } else {
            submitButton.setVisibility(View.GONE);
        }
    }

    private boolean sendButtonEnable() {
        if (descTextView.getText().toString().equals(item.description) && in20TextView.getText().toString().equals(item.in20)) {
            return false;
        }
        return true;
    }

    /**
     * 设置事件监听
     */
    private void setEvent() {
        titleTextView.setText(getResources().getText(R.string.title_activity_item_details));
        backImage.setOnClickListener(backOnClickListener);

        if (item != null) {
            numTextView.setText(item.itemnum);
            descTextView.setText(item.description);
            in20TextView.setText(item.in20);
            orderunitTextView.setText(item.orderunit);
            issueunitTextView.setText(item.issueunit);
            enterbyTextView.setText(item.enterby);
            enterdateTextView.setText(item.enterdate);
        }

        descTextView.setSelection(descTextView.length());
        in20TextView.setSelection(in20TextView.length());


        submitButton.setOnClickListener(submitButtonOnClickListenr);


        updateSendButtonStyle();

        descTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateSendButtonStyle();
            }
        });

        in20TextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateSendButtonStyle();
            }
        });


        fileImageView.setOnClickListener(fileImageViewOnClickListener);

    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener submitButtonOnClickListenr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            InputUtils.popSoftkeyboard(ItemDetailsActivity.this, descTextView, false);

            InputUtils.popSoftkeyboard(ItemDetailsActivity.this, in20TextView, false);
            showProgressBar(R.string.submit_process_ing);
            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    String result = null;
                    String data = getBaseApplication().getWsService().UpdateItem(getBaseApplication().getUsername(),
                            item.itemnum,descTextView.getText().toString(),in20TextView.getText().toString());
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        result = jsonObject.getString("msg");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    colseProgressBar();
                    if(s.equals("操作成功！")){
                        Toast.makeText(ItemDetailsActivity.this,s,Toast.LENGTH_SHORT).show();
                    }else if (s!=null||s.equals("")){
                        Toast.makeText(ItemDetailsActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();

        }
    };


    /**图片选择器**/
    private View.OnClickListener fileImageViewOnClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(ItemDetailsActivity.this, PictureActivity.class);
            startActivity(intent);
//            Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            intent.setType("image/*");
//            intent.putExtra("return-data", true);

//            startActivityForResult(intent, 0);
        }
    };


}
