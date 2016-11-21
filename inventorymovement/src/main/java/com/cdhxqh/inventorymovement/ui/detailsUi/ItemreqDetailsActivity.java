package com.cdhxqh.inventorymovement.ui.detailsUi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.ItemreqLineAdapter;
import com.cdhxqh.inventorymovement.api.HttpRequestHandler;
import com.cdhxqh.inventorymovement.api.ImManager;
import com.cdhxqh.inventorymovement.api.ig_json.Ig_Json_Model;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.model.Itemreq;
import com.cdhxqh.inventorymovement.model.Itemreqline;
import com.cdhxqh.inventorymovement.ui.BaseActivity;
import com.cdhxqh.inventorymovement.utils.MessageUtils;
import com.cdhxqh.inventorymovement.wight.SwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 物资编码申请详情
 */
public class ItemreqDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    private static final String TAG = "ItemreqDetailsActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回按钮

    private ImageView menuImage; //菜单按钮

    /**
     * --界面显示的textView--**
     */

    private TextView itemreqnumTextView; //申请编号

    private TextView descriptionTextView; //申请描述

    private TextView recorderdescTextView; //录入人名称

    private TextView recorderdateTextView; //录入时间

    private CheckBox isfinishCheckBox; //已经生产编码

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

    /**
     * PopupWindow*
     */
    private PopupWindow menuPopup;

    /**
     * 生成物资编码*
     */
    private TextView genTextView;
    /**
     * 工作流*
     */
    private TextView flowTextView;

    /**
     * 进度条*
     */
    private ProgressDialog mProgressDialog;

    private int page = 1;


    private PopupWindow popupWindow;

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
        itemreq = (Itemreq) getIntent().getSerializableExtra("itemreq");

    }


    /**
     * 初始化界面组件
     */
    private void initView() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);
        menuImage = (ImageView) findViewById(R.id.menu_imageview_id);

        itemreqnumTextView = (TextView) findViewById(R.id.itemreq_itemreqnum_text_id);
        descriptionTextView = (TextView) findViewById(R.id.itemreq_description_text);
        recorderdescTextView = (TextView) findViewById(R.id.itemreq_recorder_text);
        recorderdateTextView = (TextView) findViewById(R.id.itemreq_recorderdate_text);
        isfinishCheckBox = (CheckBox) findViewById(R.id.isfinish_checkbox_id);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        itemreqLineAdapter = new ItemreqLineAdapter(ItemreqDetailsActivity.this);
        mRecyclerView.setAdapter(itemreqLineAdapter);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);

        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);

        getItemList(itemreq.itemreqnum);
    }


    /**
     * 设置事件监听
     */
    private void setEvent() {
        titleTextView.setText(getString(R.string.itemreq_title_text));
        backImage.setOnClickListener(backOnClickListener);
        menuImage.setVisibility(View.VISIBLE);
        menuImage.setOnClickListener(menuImageOnClickListener);

        if (itemreq != null) {
            itemreqnumTextView.setText(itemreq.itemreqnum);
            descriptionTextView.setText(itemreq.description);
            recorderdescTextView.setText(itemreq.recorderdesc);
            recorderdateTextView.setText(itemreq.recorderdate);

            if (itemreq.isfinish.equals("0")) {
                isfinishCheckBox.setChecked(false);
            } else {
                isfinishCheckBox.setChecked(true);
            }
        }


    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener menuImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupWindow(menuImage);
        }
    };

    /**
     * 获取库存项目信息*
     */

    private void getItemList(String itemreqnum) {
        ImManager.getDataPagingInfo(this, ImManager.serItemreqLineUrl(page, 20, itemreqnum), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Itemreqline> items = null;
                try {
                    items = Ig_Json_Model.parseItemreqlineFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        notLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (page == 1) {
                            itemreqLineAdapter = new ItemreqLineAdapter(ItemreqDetailsActivity.this);
                            mRecyclerView.setAdapter(itemreqLineAdapter);
                        }
                        if (totalPages == page) {
                            itemreqLineAdapter.adddate(items);
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


    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(ItemreqDetailsActivity.this).inflate(
                R.layout.pop_window, null);


        popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        genTextView = (TextView) contentView.findViewById(R.id.item_generate_id);
        genTextView.setOnClickListener(genTextViewOnClickListener);
        popupWindow.setTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
            }
        });

        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.popup_background_mtrl_mult));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

    }

    /**
     * 生成物资编码*
     */
    private View.OnClickListener genTextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mProgressDialog = ProgressDialog.show(ItemreqDetailsActivity.this, null,
                    "正在生成物资编码...", true, true);
            genNumber();
        }
    };


    /**
     * 生成物资编码*
     */
    private void genNumber() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String data = getBaseApplication().getWsService().INV08CreateItem(getBaseApplication().getUsername(),
                        itemreq.itemreqnum);


                return data;
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);
                mProgressDialog.cancel();
                popupWindow.dismiss();

                try {
                    if (!data.equals("")) {
                        JSONObject jsonObject = new JSONObject(data);
                        String result = jsonObject.getString("msg");
                        MessageUtils.showMiddleToast(ItemreqDetailsActivity.this, result);
                    } else {
                        MessageUtils.showMiddleToast(ItemreqDetailsActivity.this, "失败");
                    }
                } catch (JSONException e) {
                    MessageUtils.showMiddleToast(ItemreqDetailsActivity.this, "失败");
                }


            }
        }.execute();
    }


    @Override
    public void onLoad() {
        page = 1;
        getItemList(itemreq.itemreqnum);
    }

    @Override
    public void onRefresh() {
        page++;
        getItemList(itemreq.itemreqnum);
    }
}
