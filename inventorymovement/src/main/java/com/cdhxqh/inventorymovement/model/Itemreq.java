package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/3.
 * 物资编码申请
 */
public class Itemreq extends Entity implements Parcelable {
    private static final String TAG = "Itemreq";

    private static final long serialVersionUID = 2015050105L;

    public String itemreqnum; //申请编号

    public String description; //申请描述

    public String recorder; //录入人编号

    public String recorderdesc; //录入人名称

    public String recorderdate; //录入时间

    public String itemreqid; //唯一ID

    public String status; //状态

    public String statusdesc; //状态描述

    public String isfinish; //是否完成


    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        itemreqnum = jsonObject.getString("itemreqnum");
        description = jsonObject.getString("description");
        recorder = jsonObject.getString("recorder");
        recorderdesc = jsonObject.getString("recorderdesc");
        recorderdate = jsonObject.getString("recorderdate");
        itemreqid = jsonObject.getString("itemreqid");
        status = jsonObject.getString("status");
        statusdesc = jsonObject.getString("statusdesc");
        isfinish = jsonObject.getString("isfinish");
    }

    public Itemreq() {
    }


    private Itemreq(Parcel in) {
        itemreqnum = in.readString();
        description = in.readString();
        recorder = in.readString();
        recorderdesc = in.readString();
        recorderdate = in.readString();
        itemreqid = in.readString();
        status = in.readString();
        statusdesc = in.readString();
        isfinish = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemreqnum);
        dest.writeString(description);
        dest.writeString(recorder);
        dest.writeString(recorderdesc);
        dest.writeString(recorderdate);
        dest.writeString(itemreqid);
        dest.writeString(status);
        dest.writeString(statusdesc);
        dest.writeString(isfinish);

    }


    public static final Creator<Itemreq> CREATOR = new Creator<Itemreq>() {
        @Override
        public Itemreq createFromParcel(Parcel source) {
            return new Itemreq(source);
        }

        @Override
        public Itemreq[] newArray(int size) {
            return new Itemreq[size];
        }
    };


}
