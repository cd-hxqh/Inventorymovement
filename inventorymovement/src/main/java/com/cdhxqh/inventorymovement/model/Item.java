package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/3.
 */
public class Item extends Entity implements Parcelable {

    private static final long serialVersionUID = 2015050105L;

    public int itemid; //id
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

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
