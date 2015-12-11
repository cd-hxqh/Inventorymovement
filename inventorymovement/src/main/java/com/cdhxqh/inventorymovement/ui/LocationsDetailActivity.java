package com.cdhxqh.inventorymovement.ui;

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
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Locations;

public class LocationsDetailActivity extends BaseActivity {


    private static final int REMOVED_MARK=1000; //移出
    private static final int MOVED_MARK=1001; //移入

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回


    /**������Ϣ��ʾ**/
    private TextView locationText; //λ��

    private TextView desctionText; //����

    private TextView siteidText;  //վ��


    private Button removedBtn; //�Ƴ�
    private Button moveBtn; //����


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


    /**��ʼ���������**/
    private void initData() {
        locations= (Locations) getIntent().getSerializableExtra("locations");
    }


    /**��ʼ���������**/
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);


        locationText=(TextView)findViewById(R.id.locations_location_text);
        desctionText=(TextView)findViewById(R.id.locations_description_text);
        siteidText=(TextView)findViewById(R.id.location_siteid_text);


        removedBtn=(Button)findViewById(R.id.locations_removed_btn_id);
        moveBtn=(Button)findViewById(R.id.locations_move_btn_id);
    }

    /**�����¼�����**/
    private void initView() {
        titleTextView.setText(getString(R.string.locations_title_text));
        backImage.setOnClickListener(backOnClickListener);

        if(locations!=null){
            locationText.setText(locations.getLocation()==null?"":locations.getLocation());
            desctionText.setText(locations.getDescription()==null?"":locations.getDescription());
            siteidText.setText(locations.getSiteid()==null?"":locations.getSiteid());
        }


        removedBtn.setOnClickListener(onClickListener);
    }



    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LocationsDetailActivity.this,InvbalancesActivity.class);
            intent.putExtra("location",locations.getLocation());
            intent.putExtra("mark",REMOVED_MARK);
            startActivityForResult(intent,0);
        }
    };
}
