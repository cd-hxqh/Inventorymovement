package com.cdhxqh.inventorymovement.ui;

import android.os.Bundle;
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
import com.cdhxqh.inventorymovement.api.ig_json.Ig_Json_Model;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout.OnRefreshListener, com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout.OnLoadListener {
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
    /**
     * 搜索值*
     */
    private String search;

    private int page = 1;

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
        } else if (search_mark == INV_MARK) {
            invAdapter = new InvAdapter(SearchActivity.this);
            mRecyclerView.setAdapter(invAdapter);
        }
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);


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
                search = editText.getText().toString();
                if (search_mark == ITEM_MARK) {
                    getItemList(search);
                } else if (search_mark == INV_MARK) {
                    getInvList(search);
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
                ArrayList<Inventory> items = null;
                try {
                    items = Ig_Json_Model.parseInventoryFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        notLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (page == 1) {
                            invAdapter = new InvAdapter(SearchActivity.this);
                            mRecyclerView.setAdapter(invAdapter);
                        }
                        if (totalPages == page) {
                            invAdapter.adddate(items);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
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
                ArrayList<Inventory> items = null;
                try {
                    items = Ig_Json_Model.parseInventoryFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        notLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (page == 1) {
                            invAdapter = new InvAdapter(SearchActivity.this);
                            mRecyclerView.setAdapter(invAdapter);
                        }
                        if (totalPages == page) {
                            invAdapter.adddate(items);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
                notLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onLoad() {
        page = 1;
        getItemList(search);
    }

    @Override
    public void onRefresh() {
        page++;
        getItemList(search);
    }
}
