package com.cdhxqh.inventorymovement.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Locations;

public class LocationsDetailActivity extends BaseActivity {

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回按钮


    /**界面信息显示**/
    private TextView locationText; //位置

    private TextView desctionText; //描述

    private TextView siteidText;  //站点


    private Button removedBtn; //移出
    private Button moveBtn; //移入


    /**Locations**/
    private Locations locations;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_detail);

        initData();

        findViewById();

        initView();
    }


    /**初始化界面数据**/
    private void initData() {
        locations= (Locations) getIntent().getSerializableExtra("locations");
    }


    /**初始化界面组件**/
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);


        locationText=(TextView)findViewById(R.id.locations_location_text);
        desctionText=(TextView)findViewById(R.id.locations_description_text);
        siteidText=(TextView)findViewById(R.id.location_siteid_text);


        removedBtn=(Button)findViewById(R.id.locations_removed_btn_id);
        moveBtn=(Button)findViewById(R.id.locations_move_btn_id);
    }

    /**设置事件监听**/
    private void initView() {
        titleTextView.setText(getString(R.string.locations_title_text));
        backImage.setOnClickListener(backOnClickListener);

        if(locations!=null){
            locationText.setText(locations.getLocation()==null?"":locations.getLocation());
            desctionText.setText(locations.getDescription()==null?"":locations.getDescription());
            siteidText.setText(locations.getSiteid()==null?"":locations.getSiteid());
        }
    }



    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
