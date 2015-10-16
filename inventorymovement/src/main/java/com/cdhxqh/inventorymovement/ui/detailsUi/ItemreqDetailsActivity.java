package com.cdhxqh.inventorymovement.ui.detailsUi;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.ItemreqAdapter;
import com.cdhxqh.inventorymovement.adapter.ItemreqLineAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.api.JsonUtils;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Itemreq;
import com.cdhxqh.inventorymovement.model.Itemreqline;
import com.cdhxqh.inventorymovement.ui.BaseActivity;

import java.util.ArrayList;

/**
 * 物资编码申请详情
 */
public class ItemreqDetailsActivity extends BaseActivity {

    private static final String TAG = "ItemreqDetailsActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回按钮

    /**
     * --界面显示的textView--**
     */

    private TextView itemreqnumTextView; //申请编号

    private TextView descriptionTextView; //申请描述

    private TextView recorderdescTextView; //录入人名称

    private TextView recorderdateTextView; //录入时间


    private Itemreq itemreq;


    /**
     * RecyclerView*
     */
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout mSwipeLayout;


    /**
     * 暂无数据*
     */
    LinearLayout notLinearLayout;


    private ItemreqLineAdapter itemreqLineAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemreq_details_items);
        geiIntentData();
        initView();
        setEvent();
    }

    /**
     * 获取上个界面的数据*
     */
    private void geiIntentData() {
        itemreq = (Itemreq) getIntent().getParcelableExtra("itemreq");

    }


    /**
     * 初始化界面组件
     */
    private void initView() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);


        itemreqnumTextView = (TextView) findViewById(R.id.itemreq_itemreqnum_text_id);
        descriptionTextView = (TextView) findViewById(R.id.itemreq_description_text);
        recorderdescTextView = (TextView) findViewById(R.id.itemreq_recorder_text);
        recorderdateTextView = (TextView) findViewById(R.id.itemreq_recorderdate_text);


        mRecyclerView = (RecyclerView)findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        itemreqLineAdapter=new ItemreqLineAdapter(ItemreqDetailsActivity.this);
        mRecyclerView.setAdapter(itemreqLineAdapter);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getItemList(itemreq.itemreqnum);
            }
        });
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));


        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);

        getItemList(itemreq.itemreqnum);
    }


    /**
     * 设置事件监听
     */
    private void setEvent() {
        titleTextView.setText(getString(R.string.itemreq_title_text));
        backImage.setOnClickListener(backOnClickListener);

        if (itemreq != null) {
            itemreqnumTextView.setText(itemreq.itemreqnum);
            descriptionTextView.setText(itemreq.description);
            recorderdescTextView.setText(itemreq.recorderdesc);
            recorderdateTextView.setText(itemreq.recorderdate);
        }


    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };



    /**
     * 获取库存项目信息*
     */

    private void getItemList(String itemreqnum) {
        Log.i(TAG,"itemreqnum="+itemreqnum);
        ImManager.getDataPagingInfo(this, ImManager.serItemreqLineUrl(1, 20, itemreqnum), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                Log.i(TAG, "results data=" + results.getResultlist());
                ArrayList<Itemreqline> items = JsonUtils.parsingItemreqline(ItemreqDetailsActivity.this, results.getResultlist());
                mSwipeLayout.setRefreshing(false);
                if (items == null || items.isEmpty()) {
                    notLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    itemreqLineAdapter.update(items, true);
                }
            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
                notLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }


}
