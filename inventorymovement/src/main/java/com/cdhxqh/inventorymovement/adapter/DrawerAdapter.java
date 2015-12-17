package com.cdhxqh.inventorymovement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cdhxqh.inventorymovement.R;


/**
 * Created by yugy on 14-3-15.
 */
public class DrawerAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mTitles;
    private final int mIcons[] = new int[]{
            R.drawable.ic_project,
            R.drawable.ic_in_store,
            R.drawable.ic_out_store,
            R.drawable.ic_c_store,
            R.drawable.ic_t_store,
            R.drawable.ic_code_print,
            R.drawable.ic_usage,
            R.drawable.ic_encoding,
            R.drawable.ic_exit
    };

    public DrawerAdapter(Context context) {
        mContext = context;
        mTitles = context.getResources().getStringArray(R.array.item_text);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public String getItem(int position) {
        return mTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getIconId(int position) {
        return mIcons[position];
    }

    public String getTitle(int position) {
        return mTitles[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView item = (TextView) convertView;
        if (item == null) {
            item = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_item_text, null);
        }
        item.setText(getItem(position));
        item.setCompoundDrawablesWithIntrinsicBounds(getIconId(position), 0, 0, 0);
        return item;
    }
}
