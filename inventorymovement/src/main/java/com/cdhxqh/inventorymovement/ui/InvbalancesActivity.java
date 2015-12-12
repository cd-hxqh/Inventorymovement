package com.cdhxqh.inventorymovement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.MatrectransAdapter;
import com.cdhxqh.inventorymovement.model.Matrectrans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2015/12/11.
 */
public class InvbalancesActivity extends BaseActivity{
    private static final String TAG = "InvbalancesActivity";

    private TextView titleTextView; // 标题


    private ImageView backImage; //返回


    private Button chooseBtn; //选择
    /**
     * RecyclerView*
     */
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    MatrectransAdapter matrectransAdapter;

    private Button confirm;//确定

    private String location;//源仓库

    private int mark; //移出，移入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invbalances_list);

        geiIntentData();
        findViewById();
        initView();
    }

    /**
     *获取界面数据
     */
    private void geiIntentData() {
        location = getIntent().getStringExtra("location");
        mark = getIntent().getIntExtra("mark",0);
        Log.i(TAG, "location=" + location + "mark=" + mark);
    }

    /**
     * 初始化界面控件
     */
    private void findViewById(){
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);
        chooseBtn=(Button)findViewById(R.id.btn_transfer_btn);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        confirm = (Button) findViewById(R.id.confirm);
    }

    private void initView(){
        if(mark == 1000) {
            titleTextView.setText(R.string.invbalances_remove);
        }else if(mark == 1001){
            titleTextView.setText(R.string.invbalances_move);
        }
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chooseBtn.setVisibility(View.VISIBLE);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        matrectransAdapter = new MatrectransAdapter(this);
        mRecyclerView.setAdapter(matrectransAdapter);
        addData();

        chooseBtn.setOnClickListener(chooseBtnOnClickListener);

    }

    private void addData(){
        Matrectrans matrectrans = new Matrectrans();
        matrectrans.itemnum = "101002";
        matrectrans.description = "双金属温度计";
        matrectrans.type = "0－100℃，Ф10，L=120";
        matrectrans.receiptquantity = "1.00";
        matrectrans.curbaltotal = "3";
        matrectrans.linecost = "129231";
        matrectrans.frombin = "B210D0107";
        matrectrans.tostoreloc = "";
        matrectrans.tobin = "";
        ArrayList<Matrectrans> matrectranses = new ArrayList<>();
        matrectranses.add(matrectrans);
        matrectransAdapter.adddate(matrectranses);
    }


    /**
     * 选择按钮*
     */
    private View.OnClickListener chooseBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();

            intent.setClass(InvbalancesActivity.this, InvbalancesListActivity.class);

            startActivityForResult(intent, 0);
        }
    };




}
