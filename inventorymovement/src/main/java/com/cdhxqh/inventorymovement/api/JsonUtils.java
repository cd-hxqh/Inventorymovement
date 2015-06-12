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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

    /**解析主项目返回的信息**/
    static int parsingUpdateString(String data){
        int isSuccess=0;
        try {
            JSONObject json=new JSONObject(data);
            isSuccess = Integer.valueOf(json.getString("success"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
