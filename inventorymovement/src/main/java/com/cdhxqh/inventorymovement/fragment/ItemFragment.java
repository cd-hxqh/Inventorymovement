package com.cdhxqh.inventorymovement.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.ItemAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.ui.BaseActivity;
import com.cdhxqh.inventorymovement.utils.MessageUtils;

import java.util.ArrayList;

public class ItemFragment extends Fragment implements HttpRequestHandler<ArrayList<Item>>{
    private static final String TAG="ItemFragment";
    public static final int RESULT_ADD_TOPIC = 100;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipeLayout;

    ItemAdapter itemAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container,
                false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        itemAdapter=new ItemAdapter(getActivity());
        mRecyclerView.setAdapter(itemAdapter);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestItems(true);
            }
        });
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        mSwipeLayout.setRefreshing(true);
        requestItemById(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_ADD_TOPIC) {
            if (resultCode == Activity.RESULT_OK || data != null) {
                final Item item = (Item) data.getParcelableExtra("create_result");
                itemAdapter.update(new ArrayList<Item>() {{
                    add(item);
                }}, true);
            }
        }
    }



    @Override
    public void onSuccess(ArrayList<Item> data) {



        mSwipeLayout.setRefreshing(false);
//        mIsLoading = false;
        if (data.size() == 0) return;


        itemAdapter.update(data, true);
    }

    @Override
    public void onSuccess(ArrayList<Item> data, int totalPages, int currentPage) {
        Log.i(TAG,"data size="+data.size());
        onSuccess(data);
    }

    @Override
    public void onFailure(String error) {
        MessageUtils.showErrorMessage(getActivity(), error);

    }

    private void requestItemById(boolean refresh) {
        ImManager.getLatestItem(getActivity(), refresh, this);
    }

    private void requestItems(boolean refresh) {
        requestItemById(refresh);

    }

}
