package com.cdhxqh.inventorymovement.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.InvAdapter;
import com.cdhxqh.inventorymovement.adapter.ItemAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.api.JsonUtils;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Item;

import java.util.ArrayList;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    private static final int ITEM_MARK = 0; //主项目标识
    private static final int INV_MARK = 6; //库存使用情况标识

    private EditText editText; // 搜索

    private ImageView backImage; //返回按钮


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

    /**
     * 主项目*
     */
    ItemAdapter itemAdapter;

    /**
     * 库存使用情况*
     */
    InvAdapter invAdapter;


    /**
     * 搜索标识*
     */
    private int search_mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getData();
        initView();
        setEvent();
    }



    /**
     * 初始化界面控件*
     */
    private void initView() {
        editText = (EditText) findViewById(R.id.search_edittext_id);
        backImage = (ImageView) findViewById(R.id.back_image_id);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(SearchActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (search_mark == ITEM_MARK) {
            itemAdapter = new ItemAdapter(SearchActivity.this);
            mRecyclerView.setAdapter(itemAdapter);
        }else if(search_mark==INV_MARK){
            invAdapter=new InvAdapter(SearchActivity.this);
            mRecyclerView.setAdapter(invAdapter);
        }
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));


        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);

    }

    /**
     * 设置事件监听*
     */
    private void setEvent() {
        backImage.setOnClickListener(backOnClickListener);
        editText.setOnEditorActionListener(editTextOnEditorActionListener);
    }


    /**
     * 获取初始化数据*
     */
    private void getData() {
        search_mark = getIntent().getExtras().getInt("search_mark");
        Log.i(TAG, "search_mark=" + search_mark);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    /**
     * 软键盘*
     */
    private TextView.OnEditorActionListener editTextOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                if (search_mark == ITEM_MARK) {
                    getItemList(editText.getText().toString());
                } else if (search_mark == INV_MARK) {
                    getInvList(editText.getText().toString());
                }
                return true;

            }
            return false;
        }
    };


    /**
     * 获取主项目信息*
     */

    private void getItemList(String search) {
        Log.i(TAG, "search=" + search);
        ImManager.getData(SearchActivity.this, ImManager.searchItem(search), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Item> items = JsonUtils.parsingItem(SearchActivity.this, results.getResultlist());
                mSwipeLayout.setRefreshing(false);
                if (items == null || items.isEmpty()) {
                    mRecyclerView.setVisibility(View.GONE);
                    notLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    itemAdapter = new ItemAdapter(SearchActivity.this);
                    mRecyclerView.setAdapter(itemAdapter);

                    itemAdapter.update(items, true);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    notLinearLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
                mRecyclerView.setVisibility(View.GONE);
                notLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * 库存使用情况*
     */
    private void getInvList(String search) {
        ImManager.getData(SearchActivity.this, ImManager.searchInventoryUrl(search), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Inventory> items = JsonUtils.parsingInventory(SearchActivity.this, results.getResultlist());
                mSwipeLayout.setRefreshing(false);
                if (items == null || items.isEmpty()) {
                    notLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    invAdapter.update(items, true);
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
