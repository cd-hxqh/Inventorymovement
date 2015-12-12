package com.cdhxqh.inventorymovement.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Invbalances;

/**
 * ���ת������*
 */
public class InvbalanceDetailActivity extends BaseActivity {
    private TextView titleTextView; // ����

    private ImageView backImage; //����

    /**
     * ����˵��*
     */

    private TextView itemnumText; //��Ŀ
    private TextView descriptionText; //����
    private TextView in20Text; //����ͺ�
    private TextView orderunitText; //������λ
    private TextView curbalText; //��ǰ����
    private TextView locationText; //�ⷿ
    private TextView siteidText; //�ص�
    private TextView binnumText; //����

    /**Invbalances**/
    private Invbalances invbalances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invbalance_detail);

        initData();
        findViewById();
        initView();
    }

    private void initData() {
        invbalances= (Invbalances) getIntent().getSerializableExtra("invbalances");


    }


    /**
     * ��ʼ������ؼ�*
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);

        itemnumText=(TextView)findViewById(R.id.invbalance_itemnum_text);
        descriptionText=(TextView)findViewById(R.id.invbalance_description_text);
        in20Text=(TextView)findViewById(R.id.invbalance_in20_text);
        orderunitText=(TextView)findViewById(R.id.invbalance_orderunit_text);
        curbalText=(TextView)findViewById(R.id.invbalance_curbal_text);
        locationText=(TextView)findViewById(R.id.invbalance_location_text);
        siteidText=(TextView)findViewById(R.id.invbalance_siteid_text);
        binnumText=(TextView)findViewById(R.id.invbalance_binnum_text);

    }


    /**
     * �����¼�����*
     */
    private void initView() {
        titleTextView.setText(getString(R.string.title_activity_invbalance_detail));
        backImage.setOnClickListener(backOnClickListener);

        if(invbalances!=null){
            itemnumText.setText(invbalances.getItemnum()==null?"":invbalances.getItemnum());
            descriptionText.setText(invbalances.getItemdesc()==null?"":invbalances.getItemdesc());
            in20Text.setText(invbalances.getItemin20()==null?"":invbalances.getItemin20());
            orderunitText.setText(invbalances.getItemorderunit()==null?"":invbalances.getItemorderunit());
            curbalText.setText(invbalances.getCurbal()==null?"":invbalances.getCurbal());
            locationText.setText(invbalances.getLocation()==null?"":invbalances.getLocation());
            siteidText.setText(invbalances.getSiteid()==null?"":invbalances.getSiteid());
            binnumText.setText(invbalances.getBinnum()==null?"":invbalances.getBinnum());
        }

    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
