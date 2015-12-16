package com.cdhxqh.inventorymovement.ui.poui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.model.Po;
import com.cdhxqh.inventorymovement.ui.BaseActivity;
import com.cdhxqh.inventorymovement.ui.PoLineActivity;

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

    private TextView poRecorderText; //接收人

    private TextView poVendorText;//供应商

    private Button recordeBtn;//接收按钮

    private Button returnBtn;//返回按钮

    private final int RECORDE_MARK = 1000;//接收标记
    private final int RETURN_MARK = 1001;//退货标记

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
        po = (Po) getIntent().getSerializableExtra("po");

    }


    /**初始化界面组件**/
    private void initView() {
        titleText=(TextView)findViewById(R.id.drawer_text);
        backImageView=(ImageView)findViewById(R.id.drawer_indicator);
        poNumText=(TextView)findViewById(R.id.po_num_text);
        poDescText=(TextView)findViewById(R.id.po_desc_text);
        poRecorderText=(TextView)findViewById(R.id.po_recorder_text);
        poVendorText=(TextView)findViewById(R.id.po_vendor_text);

        recordeBtn = (Button) findViewById(R.id.po_recorde_text);
        returnBtn = (Button) findViewById(R.id.po_return_text);
    }

    /**设置事件监听**/
    private void setEvent() {
        titleText.setText(getString(R.string.material_receiving));
        backImageView.setOnClickListener(backImageViewOnClickListener);
        if (po!=null){
            poNumText.setText(po.ponum);
            poDescText.setText(po.description);
            poRecorderText.setText(po.recorder);
            poVendorText.setText(po.vendor);
        }

        recordeBtn.setOnClickListener(intentOnClicListener);
        returnBtn.setOnClickListener(intentOnClicListener);
    }


    private View.OnClickListener backImageViewOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener intentOnClicListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(PodetailsActivity.this, PoLineActivity.class);
            intent.putExtra("ponum",po.ponum);
            if(v.getId()==recordeBtn.getId()){
                intent.putExtra("mark",RECORDE_MARK);
            }else if(v.getId()==returnBtn.getId()){
                intent.putExtra("mark",RETURN_MARK);
            }
            startActivity(intent);
        }
    };

}
