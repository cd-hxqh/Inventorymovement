package com.cdhxqh.inventorymovement.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.LocationsAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.api.ig_json.Ig_Json_Model;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.model.Locations;
import com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 条码打印*
 */
public class TypoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final String TAG = "TypoFragment";
    private static final int RESULT_ADD_TOPIC = 100;
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

    private Button button;

    LocationsAdapter locationsAdapter;

    private int page = 1;

    private static final int mark=0; //库存转移标识

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_typo, container,
                false);

        findByIdView(view);


        return view;
    }

    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_topics);
        button = (Button) view.findViewById(R.id.button_id);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        locationsAdapter = new LocationsAdapter(getActivity(),mark);
        mRecyclerView.setAdapter(locationsAdapter);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);


        notLinearLayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"待开发",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        mSwipeLayout.setRefreshing(true);
//        getItemList();
    }



    /**
     * 获取库存转移位置信息*
     */

    private void getItemList() {
        ImManager.getDataPagingInfo(getActivity(), ImManager.serLocationsUrl("",page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Locations> items = null;
                try {
                    items = Ig_Json_Model.parseLocationsFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        notLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (page == 1) {
                            locationsAdapter = new LocationsAdapter(getActivity(),mark);
                            mRecyclerView.setAdapter(locationsAdapter);
                        }
                        if (totalPages == page) {
                            locationsAdapter.adddate(items);
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
//        getItemList();
    }

    //上拉加载
    @Override
    public void onLoad() {
        page++;
//        getItemList();
    }
}
