package com.cdhxqh.inventorymovement.ui.pictureui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.ImageBucketAdapter;
import com.cdhxqh.inventorymovement.adapter.ImageGridAdapter;
import com.cdhxqh.inventorymovement.bean.ImageBucket;
import com.cdhxqh.inventorymovement.bean.ImageItem;
import com.cdhxqh.inventorymovement.ui.BaseActivity;
import com.cdhxqh.inventorymovement.utils.AlbumHelper;
import com.cdhxqh.inventorymovement.utils.Bimp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PictureActivity extends BaseActivity {
    private static final String TAG = "PictureActivity";

    public static Bitmap bimap;

    private TextView titleText;  //标题

    private ImageView backImageView; //返回按钮

    private GridView gridView; //gridView;

    List<ImageBucket> dataList;

    AlbumHelper helper;


    List<ImageItem> dataImageList;

    ImageGridAdapter imageadapter;// 自定义的适配器


    Button bt; //完成


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(PictureActivity.this, "最多选择9张图片", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        initData();
        initView();
        setEvent();
    }

    /**
     * 初始化数据*
     */
    private void initData() {
        dataList = helper.getImagesBucketList(true);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
        dataImageList = dataList.get(0).imageList;

        Log.i(TAG,"dataList size="+dataList.size()+",dataImageList size="+dataImageList.size());
    }


    /**
     * 初始化界面组件*
     */
    private void initView() {
        titleText = (TextView) findViewById(R.id.title_text_id);
        backImageView = (ImageView) findViewById(R.id.back_image_id);
        gridView = (GridView) findViewById(R.id.id_gridView);
        bt = (Button) findViewById(R.id.bt);
    }

    /**
     * 设置事件监听*
     */
    private void setEvent() {
        titleText.setText(getString(R.string.choose_picture_text));
        backImageView.setOnClickListener(backImageViewOnClickListener);

        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        imageadapter = new ImageGridAdapter(PictureActivity.this, dataImageList,
                mHandler);
        gridView.setAdapter(imageadapter);
        imageadapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(gridViewOnItemClickListenr);
        bt.setOnClickListener(btOnClickListener);
    }

    private AdapterView.OnItemClickListener gridViewOnItemClickListenr = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            imageadapter.notifyDataSetChanged();
        }
    };


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener btOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ArrayList<String> list = new ArrayList<String>();
            Collection<String> c = imageadapter.map.values();
            Iterator<String> it = c.iterator();
            for (; it.hasNext(); ) {
                list.add(it.next());
            }

            if (Bimp.act_bool) {
                finish();
                Bimp.act_bool = false;
            }
            for (int i = 0; i < list.size(); i++) {
                if (Bimp.drr.size() < 9) {
                    Bimp.drr.add(list.get(i));
                }
            }
            finish();

        }
    };

}
