package com.cdhxqh.inventorymovement.ui.poui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.model.Po;
import com.cdhxqh.inventorymovement.ui.BaseActivity;

/**
 * 物质接收详情
 */

public class PodetailsActivity extends BaseActivity {
    private static final String TAG="PodetailsActivity";
    /**标题**/
    private TextView titleText;
    /**返回按钮**/
    private ImageView backImageView;


    private TextView poNumText; //采购单

    private TextView poDescText; //描述

    private TextView poStateText; //状态

    private Po po;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podetails);
        getIntentData();
        initView();
        setEvent();
    }

    private void getIntentData() {
        po = (Po) getIntent().getParcelableExtra("po");

    }


    /**初始化界面组件**/
    private void initView() {
        titleText=(TextView)findViewById(R.id.drawer_text);
        backImageView=(ImageView)findViewById(R.id.drawer_indicator);
        poNumText=(TextView)findViewById(R.id.po_num_text);
        poDescText=(TextView)findViewById(R.id.po_desc_text);
        poStateText=(TextView)findViewById(R.id.po_status_text);

    }

    /**设置事件监听**/
    private void setEvent() {
        titleText.setText(getString(R.string.material_receiving));
        backImageView.setOnClickListener(backImageViewOnClickListener);
        if (po!=null){
            poNumText.setText(po.ponum);
            poDescText.setText(po.description);
            poStateText.setText(po.status);
        }

    }


    private View.OnClickListener backImageViewOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


}
