package com.cdhxqh.inventorymovement.ui.itemui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.ui.BaseActivity;

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
        item = (Item) getIntent().getParcelableExtra("item");
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

    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


}
