package com.cdhxqh.inventorymovement.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Invbalances;
import com.cdhxqh.inventorymovement.model.Invreserve;
import com.cdhxqh.inventorymovement.utils.MessageUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 出库
 */
public class InvreserveDetailActivity extends BaseActivity {

    private static final String TAG = "InvreserveDetailActivity";
    private TextView titleTextView; // 标题

    private ImageView backImage; //返回

    private Button issueBtn; //发放

    private Button withdrawingBtn; //退货

    /**
     * 界面说明*
     */

    private TextView itemnumText; //物资编号
    private TextView desctionText; //描述
    private EditText qtyText; //当前余量
    private TextView locationText; //库房
    private TextView binnumText; //货柜

    /**
     * Invreserve*
     */
    private Invreserve invreserve;

    /**
     * 工单
     */
    private String wonum;


    /**
     * 进度条*
     */
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invreserve_detail);

        initData();
        findViewById();
        initView();
    }

    private void initData() {
        invreserve = (Invreserve) getIntent().getSerializableExtra("invreserve");
        wonum = getIntent().getStringExtra("wonum");

        Log.i(TAG, "wonum=" + wonum);

    }


    /**
     * 初始化界面控件*
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);

        itemnumText = (TextView) findViewById(R.id.invreserve_itemnum_text);
        desctionText = (TextView) findViewById(R.id.inbalance_desction_text);
        qtyText = (EditText) findViewById(R.id.invreserve_qty_text);
        locationText = (TextView) findViewById(R.id.invreserve_location_text);
        binnumText = (EditText) findViewById(R.id.invreserve_binnum_text);


        issueBtn = (Button) findViewById(R.id.invreserve_issue_btn_id);
        withdrawingBtn = (Button) findViewById(R.id.invreserve_withdrawing_btn_id);

    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        titleTextView.setText("发放/退库");
        backImage.setOnClickListener(backOnClickListener);

        if (invreserve != null) {
            itemnumText.setText(invreserve.getItemnum() == null ? "" : invreserve.getItemnum());
            desctionText.setText(invreserve.getDescription() == null ? "" : invreserve.getDescription());
            qtyText.setText(invreserve.getReservedqty() == null ? "" : invreserve.getReservedqty());
            locationText.setText(invreserve.getLocation() == null ? "" : invreserve.getLocation());
//            binnumText.setText(invreserve.getBaseApplication == null ? "" : invreserve.getCurbal());
        }
        issueBtn.setOnClickListener(confirmBtnOnClickListener);
        withdrawingBtn.setOnClickListener(confirmBtnOnClickListener);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener confirmBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mProgressDialog = ProgressDialog.show(InvreserveDetailActivity.this, null,
                    "正在提交中...", true, true);
            confirmData();
        }
    };


    /**
     * 提交数据方法*
     */
    private void confirmData() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String result = null;
                String data = getBaseApplication().getWsService().INV03Issue(getBaseApplication().getUsername(), wonum,
                        invreserve.itemnum, qtyText.getText().toString(), invreserve.location, binnumText.getText().toString());

                Log.i(TAG, "data=" + data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    result = jsonObject.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mProgressDialog.cancel();

                Log.i(TAG, "s=" + s);
                MessageUtils.showMiddleToast(InvreserveDetailActivity.this, s);
                finish();
            }
        }.execute();
    }
}
