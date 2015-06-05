package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/3.
 */
public class Item extends Entity implements Parcelable {
    private static final String TAG = "Item";
    private static final long serialVersionUID = 2015050105L;

    public String itemid; //id
    public String itemnum; //项目编号
    public String description; //描述
    public String in20; //规格型号
    public String in24; //材质
    public String orderunit; //订购单位
    public String issueunit; //发放单位
    public String enterby; //录入人
    public String enterdate; //录入时间


    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        Log.i(TAG, "jsonObject=" + jsonObject.toString());
        itemid = jsonObject.getString("itemid");
        itemnum = jsonObject.getString("itemnum");
        description = jsonObject.getString("description");
        in20 = jsonObject.getString("in20");
        in24 = jsonObject.getString("in24");
        orderunit = jsonObject.getString("orderunit");
        issueunit = jsonObject.getString("issueunit");
        enterby = jsonObject.getString("enterby");
        enterdate = jsonObject.getString("enterdate");
        Log.i(TAG, "itemid=" + itemid + ",itemnum=" + itemnum + ",description=" + description + ",in20=" + in20 + ",in24=" + in24 + ",orderunit=" + orderunit + ",issueunit=" + issueunit + ",enterby=" + enterby + ",enterdate=" + enterdate);
    }

    public Item() {
    }


    private Item(Parcel in) {
        String[] strings = new String[8];
        in.readStringArray(strings);
        itemid = strings[0];
        itemnum = strings[1];
        description = strings[2];
        in20 = strings[3];
        in24 = strings[4];
        orderunit = strings[5];
        issueunit = strings[6];
        enterby = strings[7];
        enterdate = strings[8];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                itemid,
                itemnum,
                description,
                in20,
                in24,
                orderunit,
                issueunit,
                enterby,
                enterdate

        });
    }


    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
