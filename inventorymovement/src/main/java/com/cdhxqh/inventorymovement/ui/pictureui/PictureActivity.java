package com.cdhxqh.inventorymovement.ui.pictureui;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.ImageGridAdapter;
import com.cdhxqh.inventorymovement.adapter.MyAdapter;
import com.cdhxqh.inventorymovement.bean.ImageFloder;
import com.cdhxqh.inventorymovement.ui.BaseActivity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PictureActivity extends BaseActivity {
    private static final String TAG = "PictureActivity";


    private TextView titleText;  //标题

    private ImageView backImageView; //返回按钮

    private GridView gridView; //gridView;


    private MyAdapter adapter;




    Button bt; //完成




    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;



    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();



    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);


        initView();
        getImages();
        setEvent();
    }


    /**
     * 为View绑定数据
     */
    private void data2View()
    {
        if (mImgDir == null)
        {
            Toast.makeText(getApplicationContext(), "擦，一张图片没扫描到",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i(TAG, "path="+mImgDir.getAbsolutePath());
        mImgs = Arrays.asList(mImgDir.list());
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        adapter = new MyAdapter(getApplicationContext(), mImgs,
                R.layout.grid_item, mImgDir.getAbsolutePath());
        gridView.setAdapter(adapter);
    };

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

//        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        imageadapter = new ImageGridAdapter(PictureActivity.this, dataImageList,
//                mHandler);
//        gridView.setAdapter(imageadapter);
//        imageadapter.setTextCallback(new ImageGridAdapter.TextCallback() {
//            public void onListen(int count) {
//                bt.setText("完成" + "(" + count + ")");
//            }
//        });
//
//        gridView.setOnItemClickListener(gridViewOnItemClickListenr);
//        bt.setOnClickListener(btOnClickListener);
    }

//    private AdapterView.OnItemClickListener gridViewOnItemClickListenr = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            imageadapter.notifyDataSetChanged();
//        }
//    };


    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


//    private View.OnClickListener btOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//            ArrayList<String> list = new ArrayList<String>();
//            Collection<String> c = imageadapter.map.values();
//            Iterator<String> it = c.iterator();
//            for (; it.hasNext(); ) {
//                list.add(it.next());
//            }
//
//            if (Bimp.act_bool) {
//                finish();
//                Bimp.act_bool = false;
//            }
//            for (int i = 0; i < list.size(); i++) {
//                if (Bimp.drr.size() < 9) {
//                    Bimp.drr.add(list.get(i));
//                }
//            }
//            finish();
//
//        }
//    };





    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages()
    {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))
        {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = PictureActivity.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" },
                        MediaStore.Images.Media.DATE_MODIFIED);

                Log.i(TAG, "file="+mCursor.getCount() + "");
                while (mCursor.moveToNext())
                {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    Log.i(TAG, "filepath="+path);
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath))
                    {
                        continue;
                    } else
                    {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    int picSize = parentFile.list(new FilenameFilter()
                    {
                        @Override
                        public boolean accept(File dir, String filename)
                        {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize)
                    {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }


}
