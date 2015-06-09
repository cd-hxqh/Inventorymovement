package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/9.
 */
public class Po extends Entity implements Parcelable {
    private static final String TAG = "PO";

    private static final long serialVersionUID = 2015050105L;

    public String poid; //唯一表识

    public String ponum; //采购编号

    public String description; //描述

    public String vendordesc; //公司名称

    public String status; //状态

    public String siteid; //站点

    public String pretaxtotal; //税前总计


    public String orderdate; //订购日期

    public String shiptoattn; //接收人

    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        Log.i(TAG, "jsonObject=" + jsonObject.toString());
        poid = jsonObject.getString("poid");
        ponum = jsonObject.getString("ponum");
        description = jsonObject.getString("description");
        vendordesc = jsonObject.getString("vendordesc");
        status = jsonObject.getString("status");
        siteid = jsonObject.getString("siteid");
        pretaxtotal = jsonObject.getString("pretaxtotal");
        orderdate = jsonObject.getString("orderdate");
        shiptoattn = jsonObject.getString("shiptoattn");
        Log.i(TAG, "poid=" + poid + ",ponum=" + ponum + ",description=" + description + ",status=" + status + ",siteid=" + siteid + ",orderdate=" + orderdate + ",shiptoattn=" + shiptoattn);
    }

    public Po() {
    }

    private Po(Parcel in) {
        poid = in.readString();
        ponum = in.readString();
        description = in.readString();
        vendordesc = in.readString();
        status = in.readString();
        siteid = in.readString();
        pretaxtotal = in.readString();
        orderdate = in.readString();
        shiptoattn = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(poid);
        dest.writeString(ponum);
        dest.writeString(description);
        dest.writeString(vendordesc);
        dest.writeString(status);
        dest.writeString(siteid);
        dest.writeString(pretaxtotal);
        dest.writeString(orderdate);
        dest.writeString(shiptoattn);
    }


    public static final Creator<Po> CREATOR = new Creator<Po>() {
        @Override
        public Po createFromParcel(Parcel source) {
            return new Po(source);
        }

        @Override
        public Po[] newArray(int size) {
            return new Po[size];
        }
    };
}
