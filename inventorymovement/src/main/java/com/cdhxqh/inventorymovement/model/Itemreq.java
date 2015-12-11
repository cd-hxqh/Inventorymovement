package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/3.
 * 物资编码申请
 */

@JsonType
public class Itemreq extends Entity {

    @JsonField(fieldName = "itemreqnum")
    public String itemreqnum; //申请编号
    @JsonField(fieldName = "description")
    public String description; //申请描述
    @JsonField(fieldName = "recorder")
    public String recorder; //录入人编号
    @JsonField(fieldName = "recorderdesc")
    public String recorderdesc; //录入人名称
    @JsonField(fieldName = "recorderdate")
    public String recorderdate; //录入时间
    @JsonField(fieldName = "itemreqid")
    public String itemreqid; //唯一ID
    @JsonField(fieldName = "status")
    public String status; //状态
    @JsonField(fieldName = "statusdesc")
    public String statusdesc; //状态描述
    @JsonField(fieldName = "isfinish")
    public String isfinish; //是否完成


    public String getItemreqnum() {
        return itemreqnum;
    }

    public void setItemreqnum(String itemreqnum) {
        this.itemreqnum = itemreqnum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getRecorderdesc() {
        return recorderdesc;
    }

    public void setRecorderdesc(String recorderdesc) {
        this.recorderdesc = recorderdesc;
    }

    public String getRecorderdate() {
        return recorderdate;
    }

    public void setRecorderdate(String recorderdate) {
        this.recorderdate = recorderdate;
    }

    public String getItemreqid() {
        return itemreqid;
    }

    public void setItemreqid(String itemreqid) {
        this.itemreqid = itemreqid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusdesc() {
        return statusdesc;
    }

    public void setStatusdesc(String statusdesc) {
        this.statusdesc = statusdesc;
    }

    public String getIsfinish() {
        return isfinish;
    }

    public void setIsfinish(String isfinish) {
        this.isfinish = isfinish;
    }
}
