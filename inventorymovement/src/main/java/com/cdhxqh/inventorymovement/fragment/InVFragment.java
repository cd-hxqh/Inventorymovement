package com.cdhxqh.inventorymovement.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

/**
 * 库存使用情况列表*
 */
public class InVFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final String TAG = "ItemFragment";
    private static final int RESULT_ADD_TOPIC = 100;
    /**
     * RecyclerView*
     */
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout mSwipeLayout;

    private int page = 1;

    /**
     * 暂无数据*
     */
    LinearLayout notLinearLayout;

    InvAdapter invAdapter;

    private static final int mark =1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container,
                false);

        findByIdView(view);


        return view;
    }

    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        invAdapter = new InvAdapter(getActivity(),mark);
        mRecyclerView.setAdapter(invAdapter);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);

        notLinearLayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        mSwipeLayout.setRefreshing(true);
        getItemList();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == RESULT_ADD_TOPIC) {
//            if (resultCode == Activity.RESULT_OK || data != null) {
//                final Item item = (Item) data.getParcelableExtra("create_result");
//                invAdapter.update(new ArrayList<Inventory>() {{
//                    add(item);
//                }}, true);
//            }
//        }
//    }


    /**
     * 获取库存项目信息*
     */

    private void getItemList() {
        ImManager.getDataPagingInfo(getActivity(), ImManager.serInventoryUrl(page, 20), new HttpRequestHandler<Results>() {
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
                            invAdapter = new InvAdapter(getActivity(),mark);
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

    //下拉刷新触发事件
    @Override
    public void onRefresh() {
        page = 1;
        getItemList();
    }

    //上拉加载
    @Override
    public void onLoad() {
        page++;
        getItemList();
    }
}
