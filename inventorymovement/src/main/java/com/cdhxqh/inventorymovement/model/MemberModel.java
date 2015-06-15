package com.cdhxqh.inventorymovement.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用户表
 */
public class MemberModel extends Entity implements Parcelable {
    public static final Creator<MemberModel> CREATOR = new Creator<MemberModel>() {
        @Override
        public MemberModel createFromParcel(Parcel source) {
            return new MemberModel(source);
        }

        @Override
        public MemberModel[] newArray(int size) {
            return new MemberModel[size];
        }
    };
    private static final long serialVersionUID = 2015050102L;
    public int useruid;
    public String location;
    public String locationsite;
    public String locationorg;
    public String loginid;

    public MemberModel() {
    }

    private MemberModel(Parcel in) {
        useruid = in.readInt();
        location=in.readString();
        locationsite=in.readString();
        locationorg=in.readString();
        loginid=in.readString();
    }

    public void parse(JSONObject jsonObject) throws JSONException {
        useruid = Integer.valueOf(jsonObject.getString("useruid"));
        location = jsonObject.getString("location");
        locationsite = jsonObject.getString("locationsite");
        locationorg = jsonObject.optString("locationorg");
        loginid = jsonObject.optString("loginid");

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(useruid);
        dest.writeString(location);
        dest.writeString(locationsite);
        dest.writeString(locationorg);
        dest.writeString(loginid);
    }


    public int getUseruid() {
        return useruid;
    }

    public void setUseruid(int useruid) {
        this.useruid = useruid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationsite() {
        return locationsite;
    }

    public void setLocationsite(String locationsite) {
        this.locationsite = locationsite;
    }

    public String getLocationorg() {
        return locationorg;
    }

    public void setLocationorg(String locationorg) {
        this.locationorg = locationorg;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }
}
