package com.cdhxqh.inventorymovement.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.InvAdapter;
import com.cdhxqh.inventorymovement.adapter.InvbalancesAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.api.ig_json.Ig_Json_Model;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.model.Invbalances;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Matrectrans;
import com.cdhxqh.inventorymovement.utils.MessageUtils;
import com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 库存项目*
 */
public class InvbalancesListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "InvbalancesActivity";

    private TextView titleTextView; // 标题


    private ImageView backImage; //返回


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

    InvbalancesAdapter invbalancesAdapter;

    private int page = 1;

    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invbalances);

        initData();

        findViewById();

        initView();
    }


    /**
     * 获取上个界面的数据*
     */
    private void initData() {
        location = getIntent().getStringExtra("location");
    }


    /**
     * 初始化界面组件*
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);
        chooseBtn = (Button) findViewById(R.id.invbalances_btn_id);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {

        titleTextView.setText(getResources().getString(R.string.title_activity_invbalances_list));
        backImage.setOnClickListener(backImageOnClickListener);
        chooseBtn.setText(getString(R.string.confirm_text));
        chooseBtn.setVisibility(View.VISIBLE);


        chooseBtn.setOnClickListener(chooseBtnOnClickListener);

        mLayoutManager = new LinearLayoutManager(InvbalancesListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        invbalancesAdapter = new InvbalancesAdapter(InvbalancesListActivity.this);
        mRecyclerView.setAdapter(invbalancesAdapter);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setLoading(false);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);


        getItemList(location, "");
    }


    private View.OnClickListener backImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };


    private View.OnClickListener chooseBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            HashMap<Integer, Invbalances> list = invbalancesAdapter.checkedlist;


            ArrayList<Matrectrans> mList = new ArrayList<Matrectrans>();
            Matrectrans matrectrans = null;
            for (int i = 0; i < list.size(); i++) {
                matrectrans = new Matrectrans();
                matrectrans.setItemnum(list.get(i).itemnum);
                matrectrans.setDescription(list.get(i).itemdesc);
                matrectrans.setType(list.get(i).itemin20);
                matrectrans.setCurbaltotal(list.get(i).curbal);
                matrectrans.setFrombin(list.get(i).binnum);
                mList.add(matrectrans);
            }
            Intent intent = getIntent();
            intent.putExtra("matrectrans", mList);
            setResult(1000, intent);
            finish();
        }
    };


    /**
     * 获取库存项目信息*
     */

    private void getItemList(String location, String seach) {
        ImManager.getDataPagingInfo(InvbalancesListActivity.this, ImManager.serInvbalancesUrl(location, seach, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Invbalances> items = null;
                try {
                    items = Ig_Json_Model.parseInvbalancesFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        if (invbalancesAdapter.getItemCount() != 0) {
                            MessageUtils.showMiddleToast(InvbalancesListActivity.this, getString(R.string.loading_data_fail));
                        } else {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        }


                    } else {
                        Log.i(TAG, "page=" + page + ",totalPages=" + totalPages + ",currentPage=" + currentPage);
                        if (page == 1) {
                            invbalancesAdapter = new InvbalancesAdapter(InvbalancesListActivity.this);
                            mRecyclerView.setAdapter(invbalancesAdapter);
                        }
                        if (totalPages == page) {
                            invbalancesAdapter.adddate(items);
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
                if (invbalancesAdapter.getItemCount() != 0) {
                    MessageUtils.showMiddleToast(InvbalancesListActivity.this, getString(R.string.loading_data_fail));
                } else {
                    notLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onLoad() {
        page++;
        getItemList(location, "");
    }

    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(false);
    }
}
