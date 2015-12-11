package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/3.
 * 主项目信息
 */
@JsonType
public class Item extends Entity {

    @JsonField(fieldName = "itemid")
    public String itemid; //id
    @JsonField(fieldName = "itemnum")
    public String itemnum; //项目编号
    @JsonField(fieldName = "description")
    public String description; //描述
    @JsonField(fieldName = "in20")
    public String in20; //规格型号
    @JsonField(fieldName = "orderunit")
    public String orderunit; //订购单位
    @JsonField(fieldName = "issueunit")
    public String issueunit; //发放单位
    @JsonField(fieldName = "enterby")
    public String enterby; //录入人
    @JsonField(fieldName = "enterdate")
    public String enterdate; //录入时间

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIn20() {
        return in20;
    }

    public void setIn20(String in20) {
        this.in20 = in20;
    }

    public String getOrderunit() {
        return orderunit;
    }

    public void setOrderunit(String orderunit) {
        this.orderunit = orderunit;
    }

    public String getIssueunit() {
        return issueunit;
    }

    public void setIssueunit(String issueunit) {
        this.issueunit = issueunit;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getEnterdate() {
        return enterdate;
    }

    public void setEnterdate(String enterdate) {
        this.enterdate = enterdate;
    }
}
