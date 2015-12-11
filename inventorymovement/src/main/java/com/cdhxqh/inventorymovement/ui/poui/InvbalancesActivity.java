package com.cdhxqh.inventorymovement.ui.poui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.ui.BaseActivity;
import com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout;

import java.io.IOException;

/**
 * Created by think on 2015/12/11.
 * 要转移的库存项目列表
 */
public class InvbalancesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG = "InvbalancesActivity";

    private TextView titleTextView; // 标题


    private ImageView backImage; //返回按钮
    /**
     * RecyclerView*
     */
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout mSwipeLayout;

    private Button confirm;//确定

    private String location;//源仓库

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invbalances_list);

        geiIntentData();
        findViewById();
        initView();
    }

    /**
     * 获取数据*
     */
    private void geiIntentData() {
        location = getIntent().getStringExtra("location");
    }

    private void findViewById(){
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        confirm = (Button) findViewById(R.id.confirm);
    }

    private void initView(){
        titleTextView.setText(R.string.itemreqDetails_list);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);

        mSwipeLayout.setOnRefreshListener(this);
        getInvbalancesList();
    }

    /**
     * 获取要转移的库存项目
     */
    private void getInvbalancesList(){
        ImManager.getData(this, ImManager.getInvbalances(), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
//                ArrayList<Item> items = null;
//                try {
//                    items = Ig_Json_Model.parseItemFromString(results.getResultlist());
                mSwipeLayout.setRefreshing(false);
//                    mSwipeLayout.setLoading(false);
//                    if (items == null || items.isEmpty()) {
//                        notLinearLayout.setVisibility(View.VISIBLE);
//                    } else {
//                        if (page == 1) {
//                            itemAdapter = new ItemAdapter(getActivity());
//                            mRecyclerView.setAdapter(itemAdapter);
//                        }
//                        if (totalPages == page) {
//                            itemAdapter.adddate(items);
//                        }
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
//                notLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    //下拉刷新触发事件
    @Override
    public void onRefresh() {
        getInvbalancesList();
    }

}
