package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 15/6/3.
 * 物资编码申请行
 */
public class Itemreqline extends Entity implements Parcelable {
    private static final String TAG = "Itemreqline";

    private static final long serialVersionUID = 2015050105L;

    public String itemnum; //申请编号

    public String itemreqnum; //编码ID

    public String matername; //物资名称

    public String xh; //型号



    @Override
    public void parse(JSONObject jsonObject) throws JSONException {
        itemnum = jsonObject.getString("itemnum");
        itemreqnum = jsonObject.getString("itemreqnum");
        matername = jsonObject.getString("matername");
        xh = jsonObject.getString("xh");
    }

    public Itemreqline() {
    }


    private Itemreqline(Parcel in) {
        itemnum = in.readString();
        itemreqnum = in.readString();
        matername = in.readString();
        xh = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemnum);
        dest.writeString(itemreqnum);
        dest.writeString(matername);
        dest.writeString(xh);

    }


    public static final Creator<Itemreqline> CREATOR = new Creator<Itemreqline>() {
        @Override
        public Itemreqline createFromParcel(Parcel source) {
            return new Itemreqline(source);
        }

        @Override
        public Itemreqline[] newArray(int size) {
            return new Itemreqline[size];
        }
    };


}
