package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.instagram.common.json.annotation.JsonField;
import com.instagram.common.json.annotation.JsonType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/3.
 * 物资编码申请行
 */
@JsonType
public class Itemreqline extends Entity  {
    @JsonField(fieldName = "itemnum")
    public String itemnum; //申请编号
    @JsonField(fieldName = "itemreqnum")
    public String itemreqnum; //编码ID
    @JsonField(fieldName = "matername")
    public String matername; //物资名称
    @JsonField(fieldName = "xh")
    public String xh; //型号


    public String getItemnum() {
        return itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    public String getItemreqnum() {
        return itemreqnum;
    }

    public void setItemreqnum(String itemreqnum) {
        this.itemreqnum = itemreqnum;
    }

    public String getMatername() {
        return matername;
    }

    public void setMatername(String matername) {
        this.matername = matername;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }
}
