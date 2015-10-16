package com.cdhxqh.inventorymovement.api;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cdhxqh.inventorymovement.Application;
import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.constants.Constants;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.model.MemberModel;
import com.cdhxqh.inventorymovement.model.PersistenceHelper;
import com.cdhxqh.inventorymovement.model.Po;
import com.cdhxqh.inventorymovement.utils.AccountUtils;
import com.cdhxqh.inventorymovement.utils.MessageUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 15/5/27.
 */
public class ImManager {

    private static Application mApp = Application.getInstance();
    private static AsyncHttpClient sClient = null;
    private static final String TAG = "ImManager";


    /**设置主项目接口**/
    public static String serItemUrl(int curpage,int showcount){
        return "{'appid':'ITEM','objectname':'ITEM','curpage':"+curpage+",'showcount':"+showcount+",'option':'read'}";
    }

    /**设置库存使用情况接口**/
    public static String serInventoryUrl(int curpage,int showcount){
        return "{'appid':'"+Constants.INV_APPID+"','objectname':'"+Constants.INVENTORY_NAME+"','curpage':"+curpage+",'showcount':"+showcount+",'option':'read'}";
    }

    /**设置物资编码申请接口**/
    public static String serItemreqUrl(int curpage,int showcount){
        return "{'appid':'"+Constants.ITEMREQ_APPID+"','objectname':'"+Constants.ITEMREQ_NAME+"','curpage':"+curpage+",'showcount':"+showcount+",'option':'read'}";
    }


    /**设置物资编码申请行接口**/
    public static String serItemreqLineUrl(int curpage,int showcount,String itemreqnum){
        return "{'appid':'"+Constants.ITEMREQ_APPID+"','objectname':'"+Constants.ITEMREQLINE_NAME+"','curpage':"+curpage+",'showcount':"+showcount+",'option':'read','condition':{'itemreqnum':'"+itemreqnum+"'}}";
    }

    /**
     * 使用用户名密码登录
     *
     * @param cxt
     * @param username 用户名
     * @param password 密码
     * @param imei     密码
     * @param handler  返回结果处理
     */
    public static void loginWithUsername(final Context cxt, final String username, final String password, String imei,
                                         final HttpRequestHandler<String> handler) {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("loginid", username);
        params.put("password", password);
        params.put("imei", imei);
        client.post(Constants.SIGN_IN_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, "SstatusCode=" + statusCode + "responseString=" + responseString);
                if (statusCode == 200) {
                    String errmsg = JsonUtils.parsingAuthStr(cxt, responseString);
                    SafeHandler.onSuccess(handler, errmsg);
                }
            }
        });


    }


    /**
     * 获取信息方法*
     */
    public static void getData(final Context cxt, String data, final HttpRequestHandler<String> handler) {
        Log.i(TAG,"itemdata="+data);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.get(Constants.BASE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                SafeHandler.onSuccess(handler, responseString);

            }
        });
    }


    /**
     * 解析返回的结果--分页*
     */
    public static void getDataPagingInfo(final Context cxt, String data, final HttpRequestHandler<Results> handler) {
        Log.i(TAG,"data="+data);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("data", data);
        client.get(Constants.BASE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                SafeHandler.onFailure(handler, cxt.getString(R.string.get_data_info_fail));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Results result = JsonUtils.parsingResults(cxt, responseString);

                SafeHandler.onSuccess(handler, result, result.getCurpage(), result.getShowcount());
            }
        });
    }




}
