package com.cdhxqh.inventorymovement.ui.detailsUi;

import android.content.Intent;
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

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.ui.BaseActivity;
import com.cdhxqh.inventorymovement.ui.pictureui.PictureActivity;
import com.cdhxqh.inventorymovement.utils.InputUtils;

/**
 * 库存使用情况详情
 */
public class InvDetailsActivity extends BaseActivity {

    private static final String TAG = "InvDetailsActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回按钮

    /**
     * --界面显示的textView--**
     */

    private TextView numTextView; //项目编号

    private TextView descTextView; //项目描述

    private TextView binnumTextView; //库位号

    private TextView curbaltotalTextView; //当前余量

    private TextView issueunitTextView; //发放单位

    private TextView locationTextView; //仓库编号

    private TextView locationdescTextView; //仓库描述

    private TextView lotnumTextView; //批次


    private Inventory inventory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inv_details);
        geiIntentData();
        initView();
        setEvent();
    }

    /**
     * 获取上个界面的数据*
     */
    private void geiIntentData() {
        inventory = (Inventory) getIntent().getSerializableExtra("inventory");

    }


    /**
     * 初始化界面组件
     */
    private void initView() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);


        numTextView = (TextView) findViewById(R.id.item_num_text_id);
        descTextView = (TextView) findViewById(R.id.item_desc_text);
        binnumTextView = (TextView) findViewById(R.id.item_binnum_text);
        curbaltotalTextView = (TextView) findViewById(R.id.inv_curbaltotal_text);
        issueunitTextView = (TextView) findViewById(R.id.inv_issueunit_text);
        locationTextView = (TextView) findViewById(R.id.inv_location_text);
        locationdescTextView = (TextView) findViewById(R.id.inv_locationdesc_text);
        lotnumTextView = (TextView) findViewById(R.id.inv_lotnum_text);


    }


    /**
     * 设置事件监听
     */
    private void setEvent() {
        titleTextView.setText(getString(R.string.inv_text_title));
        backImage.setOnClickListener(backOnClickListener);

        if (inventory != null) {
            numTextView.setText(inventory.itemnum);
            descTextView.setText(inventory.itemdesc);
            binnumTextView.setText(inventory.binnum);
            curbaltotalTextView.setText(inventory.curbaltotal);
            issueunitTextView.setText(inventory.issueunit);
            locationTextView.setText(inventory.location);
            locationdescTextView.setText(inventory.locationdesc);
            lotnumTextView.setText(inventory.lotnum);
        }


    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


}
