/*
 * Copyright (C) 2014 Chris Renke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cdhxqh.inventorymovement.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cdhxqh.inventorymovement.AppManager;
import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.adapter.DrawerAdapter;
import com.cdhxqh.inventorymovement.fragment.ContentFragment;
import com.cdhxqh.inventorymovement.fragment.InVFragment;
import com.cdhxqh.inventorymovement.fragment.ItemFragment;
import com.cdhxqh.inventorymovement.fragment.ItemreqFragment;
import com.cdhxqh.inventorymovement.fragment.LocationFragment;
import com.cdhxqh.inventorymovement.fragment.PoFragment;
import com.cdhxqh.inventorymovement.wight.CustomDialog;
import com.cdhxqh.inventorymovement.wight.DrawerArrowDrawable;

import static android.view.Gravity.START;

public class MainActivity extends BaseActivity implements OnItemClickListener {
    private static final String TAG = "DrawerArrowSample";
    private DrawerArrowDrawable drawerArrowDrawable;
    private float offset;
    private boolean flipped;

    private DrawerLayout drawer;
    private ImageView imageView;
    /**
     * 标题TextView*
     */
    private TextView titleTextView;
    private Resources resources;

    /**
     * 搜索按钮*
     */
    private ImageView searchButton;

    private String mTitle;

    private ListView mDrawerList;
    private DrawerAdapter adapter;
    private String[] arrays;

    /**
     * 主项目的fragment*
     */
    private Fragment newItemFragment;
    /**
     * 库存使用情况*
     */
    private InVFragment newInVFragment;

    /**
     * 物资编码申请*
     */
    private ItemreqFragment newItemreqFragment;
    /**入库管理**/
    private PoFragment newPoFragemnt;
    /**库存转移**/
    private LocationFragment newLocationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setEvent();
        mTitle = (String) getTitle();
        defaultShowItem();
    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        imageView = (ImageView) findViewById(R.id.drawer_indicator);
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        resources = getResources();
        searchButton = (ImageView) findViewById(R.id.indicator_style);

    }

    private void setEvent() {
        drawerArrowDrawable = new DrawerArrowDrawable(resources);
        drawerArrowDrawable.setStrokeColor(resources
                .getColor(R.color.light_gray));
        imageView.setImageDrawable(drawerArrowDrawable);

        drawer.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                offset = slideOffset;

                // Sometimes slideOffset ends up so close to but not quite 1 or
                // 0.
                if (slideOffset >= .995) {
                    flipped = true;
                    drawerArrowDrawable.setFlip(flipped);
                } else if (slideOffset <= .005) {
                    flipped = false;
                    drawerArrowDrawable.setFlip(flipped);
                }

                drawerArrowDrawable.setParameter(offset);
            }

        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(START)) {
                    drawer.closeDrawer(START);
                } else {
                    drawer.openDrawer(START);
                }
            }
        });


        adapter = new DrawerAdapter(this);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);


        searchButton.setOnClickListener(searchButtonOnClickListener);
    }

    private View.OnClickListener searchButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setSearchButton(mSelectPos);
        }
    };


    int mSelectPos = 0;

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {
        mSelectPos = position;

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        switch (position) {
            case 0://主项目
                titleTextView.setText(adapter.getTitle(position));
                if (newItemFragment == null) {
                    newItemFragment = new ItemFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("text", adapter.getTitle(position));
                    newItemFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.content_frame, newItemFragment).commit();
                drawer.closeDrawer(mDrawerList);
                break;
            case 1://入库管理
                titleTextView.setText(adapter.getTitle(position));
                if (newPoFragemnt == null) {
                    newPoFragemnt = new PoFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("text", adapter.getTitle(position));
                    newPoFragemnt.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.content_frame, newPoFragemnt).commit();
                drawer.closeDrawer(mDrawerList);
                break;
            case 4://库存转移
                titleTextView.setText(adapter.getTitle(position));
                if (newPoFragemnt == null) {
                    newLocationFragment = new LocationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("text", adapter.getTitle(position));
                    newLocationFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.content_frame, newLocationFragment).commit();
                drawer.closeDrawer(mDrawerList);
                break;
            case 6://库存使用情况
                titleTextView.setText(adapter.getTitle(position));
                if (newInVFragment == null) {
                    newInVFragment = new InVFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("text", adapter.getTitle(position));
                    newInVFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.content_frame, newInVFragment).commit();
                drawer.closeDrawer(mDrawerList);
                break;
            case 7://物资编码申请
                titleTextView.setText(adapter.getTitle(position));
                if (newItemreqFragment == null) {
                    newItemreqFragment = new ItemreqFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("text", adapter.getTitle(position));
                    newItemreqFragment.setArguments(bundle);
                }
                fragmentTransaction.replace(R.id.content_frame, newItemreqFragment).commit();
                drawer.closeDrawer(mDrawerList);
                break;

            case 9: //退出登陆
                showAlertDialog();
                drawer.closeDrawer(mDrawerList);
                break;
            default:
                titleTextView.setText(adapter.getTitle(position));
                Fragment contentFragment = new ContentFragment();
                Bundle args = new Bundle();
                args.putString("text", adapter.getTitle(position));
                contentFragment.setArguments(args);

                fragmentTransaction.replace(R.id.content_frame, contentFragment)
                        .commit();

                drawer.closeDrawer(mDrawerList);
                break;
        }


    }


    /**
     * 退出程序
     */
    public void showAlertDialog() {

        CustomDialog.Builder builder = new CustomDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.exit_dialog_hint));
        builder.setTitle(getString(R.string.exit_dialog_title));
        builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AppManager.AppExit(MainActivity.this);
            }
        });

        builder.setNegativeButton(getString(R.string.canel),
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();

    }






    /**
     * 默认显示主项目的*
     */
    private void defaultShowItem() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (newItemFragment == null) {
            newItemFragment = new ItemFragment();
            Bundle bundle = new Bundle();
            bundle.putString("text", adapter.getTitle(0));
            newItemFragment.setArguments(bundle);
        }
        fragmentTransaction.replace(R.id.content_frame, newItemFragment).commit();
        drawer.closeDrawer(mDrawerList);
    }


    private long exitTime = 0;

    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(MainActivity.this);
        }
    }

    /**
     * 跳转至搜索界面*
     */
    private void setSearchButton(int mark) {

        if (mark == 0) { //跳转至主项目界面
            Intent intent = new Intent();
            intent.putExtra("search_mark", mark);
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 0);
        }else if(mark==4){ //跳转至库存转移界面
            Intent intent = new Intent();
            intent.putExtra("search_mark", mark);
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 0);
        }

    }

}
