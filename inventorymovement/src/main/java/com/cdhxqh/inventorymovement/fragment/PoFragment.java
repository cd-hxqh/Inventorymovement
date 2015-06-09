package com.cdhxqh.inventorymovement.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.PoAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.model.Po;
import com.cdhxqh.inventorymovement.utils.MessageUtils;

import java.util.ArrayList;

/**
 * Po的Fragemnt
 */
public class PoFragment extends Fragment implements HttpRequestHandler<ArrayList<Po>> {
    private static final String TAG = "PoFragment";
    public static final int RESULT_ADD_TOPIC = 100;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipeLayout;

    PoAdapter poAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_po, container,
                false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        poAdapter = new PoAdapter(getActivity());
        mRecyclerView.setAdapter(poAdapter);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestPoId(true);
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
        requestPoById(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }


    @Override
    public void onSuccess(ArrayList<Po> data) {
        Log.i(TAG,"data11="+data);

        mSwipeLayout.setRefreshing(false);
//        mIsLoading = false;
        if (data.size() == 0) return;

//        if (mNode == null)
//            mNode = data.get(0).node;
//
//        if (!mAttachMain && mNodeName.isEmpty())
//            mNodeName = data.get(0).node.name;

        poAdapter.update(data, true);
    }

    @Override
    public void onSuccess(ArrayList<Po> data, int totalPages, int currentPage) {
        Log.i(TAG, "data size=" + data.size());
//        for (int i=0;i<data.size();i++);
        onSuccess(data);
    }

    @Override
    public void onFailure(String error) {
        MessageUtils.showErrorMessage(getActivity(), error);

    }

    private void requestPoById(boolean refresh) {
//        if (mNodeId == LatestTopics)
        ImManager.getLatestPo(getActivity(), refresh, this);
//        else if (mNodeId == HotTopics)
//            V2EXManager.getHotTopics(getActivity(), refresh, this);
//        else if (mNodeId > 0)
//            V2EXManager.getTopicsByNodeId(getActivity(), mNodeId, refresh, this);
    }

    private void requestPoId(boolean refresh) {
//        if (mIsLoading)
//            return;
//
//        mIsLoading = true;
//        if (mNodeName != null && !mNodeName.isEmpty())
//            requestTopicsByName(refresh);
//        else if (mTabName != null && !mTabName.isEmpty())
//            requestTopicsByTab(refresh);
//        else
        requestPoById(refresh);

    }

}
