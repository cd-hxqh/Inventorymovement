package com.cdhxqh.inventorymovement.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.CInvbalancesAdapter;
import com.cdhxqh.inventorymovement.adapter.InvAdapter;
import com.cdhxqh.inventorymovement.adapter.InvbalancesAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.api.ig_json.Ig_Json_Model;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.model.Invbalances;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Locations;
import com.cdhxqh.inventorymovement.model.Matrectrans;
import com.cdhxqh.inventorymovement.utils.MessageUtils;
import com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 库存余量*
 */
public class CInvbalancesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "CInvbalancesActivity";

    private TextView titleTextView; // 标题

    private LinearLayout titlelayout;//标题布局
    private RelativeLayout title_searchlayout;//标题搜索布局

    private ImageView backImage; //返回

    private ImageView searchimg; //搜索

    private EditText editText; // 搜索

    /**
     * 搜索值*
     */
    private String search;

    private Button chooseBtn; //选择
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

    CInvbalancesAdapter cInvbalancesAdapter;

    private int page = 1;

    /**
     * Locations*
     */
    private Locations locations;

    private int mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinvbalances);

        initData();

        findViewById();

        initView();
    }


    /**
     * 获取上个界面的数据*
     */
    private void initData() {

        locations = (Locations) getIntent().getSerializableExtra("locations");
    }


    /**
     * 初始化界面组件*
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        titlelayout = (LinearLayout) findViewById(R.id.title_contains);
        title_searchlayout = (RelativeLayout) findViewById(R.id.title_search_layout);
        editText = (EditText) findViewById(R.id.search_edittext_id);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);
        searchimg = (ImageView) findViewById(R.id.menu_imageview_id);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {

        titleTextView.setText(locations.location);
        backImage.setOnClickListener(backImageOnClickListener);
        searchimg.setBackgroundResource(R.drawable.ic_search);
        searchimg.setVisibility(View.VISIBLE);
        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_searchlayout.setVisibility(View.VISIBLE);
            }
        });
        editText.setOnEditorActionListener(editTextOnEditorActionListener);


        mLayoutManager = new LinearLayoutManager(CInvbalancesActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        cInvbalancesAdapter = new CInvbalancesAdapter(CInvbalancesActivity.this);
        mRecyclerView.setAdapter(cInvbalancesAdapter);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setLoading(false);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);


        getItemList(locations.location, "");
    }


    /**
     * 软键盘*
     */
    private TextView.OnEditorActionListener editTextOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                // 先隐藏键盘
                ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(
                                CInvbalancesActivity.this.getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                search = editText.getText().toString();
                mSwipeLayout.setRefreshing(true);
                mSwipeLayout.setLoading(true);
                notLinearLayout.setVisibility(View.GONE);
                getItemList(locations.location, search);
                return true;

            }
            return false;
        }
    };

    private View.OnClickListener backImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (title_searchlayout.getVisibility() == View.VISIBLE) {
                title_searchlayout.setVisibility(View.GONE);
            } else {
                finish();
            }
        }
    };


    /**
     * 获取库存项目信息*
     */

    private void getItemList(String location, String seach) {
        ImManager.getDataPagingInfo(CInvbalancesActivity.this, ImManager.sercInvbalancesUrl(locations.location, seach, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {

                Log.i(TAG,"results="+results.getResultlist());
                ArrayList<Invbalances> items = null;
                try {
                    items = Ig_Json_Model.parseInvbalancesFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        if (cInvbalancesAdapter.getItemCount() != 0) {
                            MessageUtils.showMiddleToast(CInvbalancesActivity.this, getString(R.string.loading_data_fail));
                        } else {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        }


                    } else {
                        if (page == 1) {
                            cInvbalancesAdapter = new CInvbalancesAdapter(CInvbalancesActivity.this);
                            mRecyclerView.setAdapter(cInvbalancesAdapter);
                        }
                        if (totalPages == page) {
                            cInvbalancesAdapter.adddate(items);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
                mSwipeLayout.setLoading(false);
                if (cInvbalancesAdapter.getItemCount() != 0) {
                    MessageUtils.showMiddleToast(CInvbalancesActivity.this, getString(R.string.loading_data_fail));
                } else {
                    notLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onLoad() {
        page++;
        getItemList(locations.location, search);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getItemList(locations.location, search);
        mSwipeLayout.setRefreshing(false);
    }
}
