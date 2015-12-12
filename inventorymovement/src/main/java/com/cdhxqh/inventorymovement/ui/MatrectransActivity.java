package com.cdhxqh.inventorymovement.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Matrectrans;

/**
 * Created by think on 2015/12/12.
 * 仓库转移列表详情
 */
public class MatrectransActivity extends BaseActivity {
    private static final String TAG = "InvbalancesActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回

    public TextView itemnum;//项目
    public TextView description;//描述
    public TextView type;//型号
    public TextView receiptquantity;//数量
    public TextView curbaltotal;//仓库当前余量
    public TextView linecost;//行成本
    public TextView frombin;//原库位号
    public TextView tostoreloc;//目标位置
    public TextView tobin;//目标库位号

    private Matrectrans matrectrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrectrans_details);

        geiIntentData();
        findViewById();
        initView();
    }

    /**
     *获取界面数据
     */
    private void geiIntentData() {
        matrectrans = (Matrectrans) getIntent().getSerializableExtra("matrectrans");
    }

    /**
     * 初始化界面控件
     */
    private void findViewById(){
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        backImage = (ImageView) findViewById(R.id.drawer_indicator);

        itemnum = (TextView) findViewById(R.id.matrectrans_itemnum);
        description = (TextView) findViewById(R.id.matrectrans_description);
        type = (TextView) findViewById(R.id.matrectrans_type);
        receiptquantity = (TextView) findViewById(R.id.matrectrans_receiptquantity);
        curbaltotal = (TextView) findViewById(R.id.matrectrans_curbaltotal);
        linecost = (TextView) findViewById(R.id.matrectrans_linecost);
        frombin = (TextView) findViewById(R.id.matrectrans_frombin);
        tostoreloc = (TextView) findViewById(R.id.matrectrans_tostoreloc);
        tobin = (TextView) findViewById(R.id.matrectrans_tobin);


    }

    private void initView() {
        titleTextView.setText(R.string.matrectrans_details);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        itemnum.setText(matrectrans.itemnum);
        description.setText(matrectrans.description);
        type.setText(matrectrans.type);
        receiptquantity.setText(matrectrans.receiptquantity);
        curbaltotal.setText(matrectrans.curbaltotal);
        linecost.setText(matrectrans.linecost);
        frombin.setText(matrectrans.frombin);
        tostoreloc.setText(matrectrans.tostoreloc);
        tobin.setText(matrectrans.tobin);
    }
}
