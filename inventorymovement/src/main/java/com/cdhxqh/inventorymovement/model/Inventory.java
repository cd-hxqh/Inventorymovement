package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/3.
 * 库存使用情况信息
 */
public class Inventory extends Entity implements Parcelable {
    private static final String TAG = "Inventory";
    private static final long serialVersionUID = 2015050105L;

    public String binnum; //货柜编号

    public String curbaltotal; //当前余量

    public String issueunit; //发放单位

    public String itemdesc; //项目描述

    public String itemnum; //项目编号

    public String location; //仓库

    public String locationdesc; //仓库描述

    public String lotnum; //批次


    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        binnum = jsonObject.getString("binnum");
        curbaltotal = jsonObject.getString("curbaltotal");
        issueunit = jsonObject.getString("issueunit");
        itemdesc = jsonObject.getString("itemdesc");
        itemnum = jsonObject.getString("itemnum");
        location = jsonObject.getString("location");
        locationdesc = jsonObject.getString("locationdesc");
        lotnum = jsonObject.getString("lotnum");
    }

    public Inventory() {
    }


    private Inventory(Parcel in) {
        binnum = in.readString();
        curbaltotal = in.readString();
        issueunit = in.readString();
        itemdesc = in.readString();
        itemnum = in.readString();
        issueunit = in.readString();
        location = in.readString();
        locationdesc = in.readString();
        lotnum = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(binnum);
        dest.writeString(curbaltotal);
        dest.writeString(issueunit);
        dest.writeString(itemdesc);
        dest.writeString(itemnum);
        dest.writeString(issueunit);
        dest.writeString(location);
        dest.writeString(locationdesc);
        dest.writeString(lotnum);

    }


    public static final Creator<Inventory> CREATOR = new Creator<Inventory>() {
        @Override
        public Inventory createFromParcel(Parcel source) {
            return new Inventory(source);
        }

        @Override
        public Inventory[] newArray(int size) {
            return new Inventory[size];
        }
    };


}
