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

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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
    private TextView styleButton;

    private String mTitle;

    private ListView mDrawerList;
    private DrawerAdapter adapter;
    private String[] arrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setEvent();
        mTitle = (String) getTitle();
    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        imageView = (ImageView) findViewById(R.id.drawer_indicator);
        titleTextView = (TextView) findViewById(R.id.drawer_text);
        resources = getResources();
        styleButton = (TextView) findViewById(R.id.indicator_style);

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

        styleButton.setOnClickListener(new View.OnClickListener() {
            boolean rounded = false;

            @Override
            public void onClick(View v) {
                styleButton.setText(rounded //
                        ? resources.getString(R.string.rounded) //
                        : resources.getString(R.string.squared));

                rounded = !rounded;

                drawerArrowDrawable = new DrawerArrowDrawable(resources,
                        rounded);
                drawerArrowDrawable.setParameter(offset);
                drawerArrowDrawable.setFlip(flipped);
                drawerArrowDrawable.setStrokeColor(resources
                        .getColor(R.color.light_gray));

                imageView.setImageDrawable(drawerArrowDrawable);
            }
        });

        adapter = new DrawerAdapter(this);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {
        switch (position) {

            case 9: //退出登陆
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    AppManager.AppExit(MainActivity.this);
                }
                break;
            default:
                titleTextView.setText(adapter.getTitle(position));
                Fragment contentFragment = new ContentFragment();
                Bundle args = new Bundle();
                args.putString("text", adapter.getTitle(position));
                contentFragment.setArguments(args);

                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, contentFragment)
                        .commit();

                drawer.closeDrawer(mDrawerList);
                break;
        }


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

}
