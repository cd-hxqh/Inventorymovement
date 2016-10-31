package com.cdhxqh.inventorymovement.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.PolineAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.api.ig_json.Ig_Json_Model;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.model.Poline;
import com.cdhxqh.inventorymovement.utils.MessageUtils;
import com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by think on 2015/12/16.
 * 入库接收物料页面
 */
public class PoLineActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "PoLineActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回

    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout mSwipeLayout;

    PolineAdapter polineAdapter;

    private Button inputall;//全部提交
    /**
     * 暂无数据*
     */
    LinearLayout notLinearLayout;

    public String ponum;

    public int mark; //1000接收，1001退货

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poline_list);

        geiIntentData();
        findViewById();
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        page = 1;
        polineAdapter = new PolineAdapter(this);
        mRecyclerView.setAdapter(polineAdapter);
        getPoLineList();
    }

    /**
     * 获取界面数据
     */
    private void geiIntentData() {
        ponum = getIntent().getStringExtra("ponum");
        mark = getIntent().getIntExtra("mark", 0);
    }

    /**
     * 初始化界面控件
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        inputall = (Button) findViewById(R.id.input_all);

        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);
    }

    private void initView() {
        if (mark == 1000) {
            titleTextView.setText(R.string.po_recorde_text);
        } else if (mark == 1001) {
            titleTextView.setText(R.string.po_return_text);
            inputall.setVisibility(View.GONE);
        }
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        inputall.setOnClickListener(inputOnClickListener);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setLoading(false);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);

        getPoLineList();
    }

    /**
     * 获取库存项目信息*
     */

    private void getPoLineList() {
        ImManager.getDataPagingInfo(PoLineActivity.this, ImManager.setPolineUrl(ponum, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Poline> items = null;
                try {
                    items = Ig_Json_Model.parsePolineFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        if (polineAdapter.getItemCount() != 0) {
                            MessageUtils.showMiddleToast(PoLineActivity.this, getString(R.string.loading_data_fail));
                        } else {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        if (page == 1) {
                            polineAdapter = new PolineAdapter(PoLineActivity.this);
                            mRecyclerView.setAdapter(polineAdapter);
                        }
                        if (totalPages == page) {
                            polineAdapter.adddate(items);
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
                if (polineAdapter.getItemCount() != 0) {
                    MessageUtils.showMiddleToast(PoLineActivity.this, getString(R.string.loading_data_fail));
                } else {
                    notLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 入库管理
     **/
    private View.OnClickListener inputOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressBar(R.string.submit_process_ing);
            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    String data = getBaseApplication().getWsService().INV01RecByPO(getBaseApplication().getUsername(),
                            ponum);

                    return data;
                }

                @Override
                protected void onPostExecute(String data) {
                    super.onPostExecute(data);
                    Log.i(TAG, "data=" + data);
                    colseProgressBar();
                    try {
                        if (!data.equals("")) {
                            JSONObject jsonObject = new JSONObject(data);
                            String result = jsonObject.getString("msg");
                            if (result.equals("操作成功！")) {
                                MessageUtils.showMiddleToast(PoLineActivity.this, result);
                                polineAdapter = new PolineAdapter(PoLineActivity.this);
                                mRecyclerView.setAdapter(polineAdapter);
                            } else {
                                MessageUtils.showMiddleToast(PoLineActivity.this, result);
                            }
                        }
                    } catch (JSONException e) {
                    }


                }
            }.execute();
        }
    };

    @Override
    public void onLoad() {
        page++;
        getPoLineList();
    }

    @Override
    public void onRefresh() {
        page = 1;
        getPoLineList();
        mSwipeLayout.setRefreshing(false);
    }
}
