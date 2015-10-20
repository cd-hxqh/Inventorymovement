package com.cdhxqh.inventorymovement.api;

import android.content.Context;
import android.util.Log;

import com.cdhxqh.inventorymovement.bean.Results;
import com.cdhxqh.inventorymovement.constants.Constants;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.model.Itemreq;
import com.cdhxqh.inventorymovement.model.Itemreqline;
import com.cdhxqh.inventorymovement.model.MemberModel;
import com.cdhxqh.inventorymovement.utils.AccountUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Json数据解析类
 */
public class JsonUtils {
    private static final String TAG = "JsonUtils";


    /**
     * 解析登录信息*
     */
    public static String parsingAuthStr(final Context cxt, String data) {
        String isSuccess = null;
        String errmsg = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.LOGINSUCCESS) || jsonString.equals(Constants.CHANGEIMEI)) {
                errmsg = json.getString("errmsg");
            }

            return errmsg;


        } catch (JSONException e) {
            e.printStackTrace();
            return isSuccess;
        }
    }


    /**
     * 分页解析返回的结果*
     */
    public static Results parsingResults(Context ctx, String data) {

        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                JSONObject rJson = new JSONObject(result);
                String curpage = rJson.getString("curpage");
                String totalresult = rJson.getString("totalresult");
                String resultlist = rJson.getString("resultlist");
                String totalpage = rJson.getString("totalpage");
                String showcount = rJson.getString("showcount");
                results = new Results();
                results.setCurpage(Integer.valueOf(curpage));
                results.setTotalresult(totalresult);
                results.setResultlist(resultlist);
                results.setTotalpage(totalpage);
                results.setShowcount(Integer.valueOf(showcount));
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }

    /**
     * 不分页解析返回的结果*
     */
    public static Results parsingResults1(Context ctx, String data) {

        String result = null;
        Results results = null;
        try {
            JSONObject json = new JSONObject(data);
            String jsonString = json.getString("errcode");
            if (jsonString.equals(Constants.GETDATASUCCESS)) {
                result = json.getString("result");
                Log.i(TAG,"result="+result);
                results = new Results();
                results.setResultlist(result);
            }

            return results;


        } catch (JSONException e) {
            e.printStackTrace();
            return results;
        }

    }





    /**
     * 解析主项目信息*
     */
    public static ArrayList<Item> parsingItem(Context ctx, String data) {
        Log.i(TAG, "data=" + data);
        ArrayList<Item> list = null;
        Item item = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Item>();
            for (int i = 0; i < jsonArray.length(); i++) {
                item = new Item();
                jsonObject = jsonArray.getJSONObject(i);
                item.itemnum = jsonObject.getString("ITEMNUM"); //项目编号
                item.description = jsonObject.getString("DESCRIPTION"); //项目描述
                item.in20 = jsonObject.getString("IN20"); //规格型号
                item.orderunit = jsonObject.getString("ORDERUNIT"); //订购单位
                item.issueunit = jsonObject.getString("ISSUEUNIT"); //发放单位
                item.enterby = jsonObject.getString("ENTERBY"); //录入人

                String enterdate = jsonObject.getString("ENTERDATE");//录入时间
                if (!enterdate.equals("null")) {
                    item.enterdate = enterdate;
                }
                list.add(item);
            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 解析库存使用情况信息
     */
    public static ArrayList<Inventory> parsingInventory(Context ctx, String data) {
        Log.i(TAG, "data=" + data);
        ArrayList<Inventory> list = null;
        Inventory inventory = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Inventory>();
            for (int i = 0; i < jsonArray.length(); i++) {
                inventory = new Inventory();
                jsonObject = jsonArray.getJSONObject(i);
                inventory.binnum = jsonObject.getString("BINNUM"); //箱柜号
                inventory.curbaltotal = jsonObject.getString("CURBALTOTAL"); //数量
                inventory.issueunit = jsonObject.getString("ISSUEUNIT"); //单位
                inventory.itemdesc = jsonObject.getString("ITEMDESC"); //项目描述
                inventory.itemnum = jsonObject.getString("ITEMNUM"); //项目
                inventory.location = jsonObject.getString("LOCATION"); //仓库
                inventory.locationdesc = jsonObject.getString("LOCATIONDESC"); //仓库描述
                inventory.lotnum = jsonObject.getString("LOTNUM"); //批次

                list.add(inventory);
            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 解析物资编码主表信息
     */
    public static ArrayList<Itemreq> parsingItemreq(Context ctx, String data) {
        Log.i(TAG, "data=" + data);
        ArrayList<Itemreq> list = null;
        Itemreq itemreq = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Itemreq>();
            for (int i = 0; i < jsonArray.length(); i++) {
                itemreq = new Itemreq();
                jsonObject = jsonArray.getJSONObject(i);
                itemreq.itemreqnum = jsonObject.getString("ITEMREQNUM"); //编号
                itemreq.description = jsonObject.getString("DESCRIPTION"); //描述
                itemreq.recorder = jsonObject.getString("RECORDER"); //录入人编号
                itemreq.recorderdesc = jsonObject.getString("RECORDERDESC"); //录入人名称
                itemreq.recorderdate = jsonObject.getString("RECORDERDATE"); //录入时间
                itemreq.itemreqid = jsonObject.getString("ITEMREQID"); //唯一ID
                itemreq.status = jsonObject.getString("STATUS"); //状态
                itemreq.statusdesc = jsonObject.getString("STATUSDESC"); //状态描述
                itemreq.isfinish = jsonObject.getString("ISFINISH"); //是否完成


                list.add(itemreq);
            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }



    /**
     * 解析物资编码从表信息
     */
    public static ArrayList<Itemreqline> parsingItemreqline(Context ctx, String data) {
        Log.i(TAG, "data=" + data);
        ArrayList<Itemreqline> list = null;
        Itemreqline itemreqline = null;
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject;
            list = new ArrayList<Itemreqline>();
            for (int i = 0; i < jsonArray.length(); i++) {
                itemreqline = new Itemreqline();
                jsonObject = jsonArray.getJSONObject(i);
                itemreqline.itemnum = jsonObject.getString("ITEMNUM"); //编号
                itemreqline.itemreqnum = jsonObject.getString("ITEMREQNUM"); //编码ID
                itemreqline.matername = jsonObject.getString("MATERNAME"); //物资名称
                itemreqline.xh = jsonObject.getString("XH"); //型号


                list.add(itemreqline);
            }

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }



}