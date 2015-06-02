package com.cdhxqh.inventorymovement.api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json数据解析类
 */
public class JsonUtils {
    private static final String TAG="JsonUtils";

    /**
     * 解析登陆信息*
     */
    static int parsingAuthStr(String data) {
        int isSuccess = 0;
        try {
            JSONObject json = new JSONObject(data);
            JSONObject jsonObject=json.getJSONObject("auth");
            isSuccess = Integer.valueOf(jsonObject.getString("isSuccess"));
            String useruid = jsonObject.getString("useruid");
            String location = jsonObject.getString("location");
            String locationsite = jsonObject.getString("locationsite");
            String locationorg = jsonObject.getString("locationorg");
            String loginid = jsonObject.getString("loginid");
            Log.i(TAG, "isSuccess=" + isSuccess +"useruid=" + useruid+ ",location=" + location + ",locationsite=" + locationsite + ",locationorg=" + locationorg + ",loginid=" + loginid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
