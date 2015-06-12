package com.cdhxqh.inventorymovement.api;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cdhxqh.inventorymovement.Application;
import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.model.PersistenceHelper;
import com.cdhxqh.inventorymovement.model.Po;
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


    //    private static final String HTTP_API_URL = "http://192.168.1.125:8080/hhw/services/";
    private static final String HTTP_API_URL = "http://192.168.1.103:8080/hhw/services/";



    //登陆URL
    private static final String SIGN_IN_URL = HTTP_API_URL + "user/auth";

    //获取数据URL
    private static final String BASE_URL = HTTP_API_URL + "cmd/getcboset";

    //主项目详情修改
    private static final String ITEM_UPDATE_URL = HTTP_API_URL + "item/update";


    /**
     * 主项目表名*
     */
    private static final String ITEM_TABLE_NAME = "Item";
    /**
     * 主项目的表的字段*
     */
    private static final String ITEM_TABLE_FILED = "itemid,itemnum,description,in20,in24,orderunit,issueunit,enterby,enterdate";

    /**
     * 入库管理的PO表视图*
     */
    private static final String PO_TABLE_NAME = "CDHXQH_V_PO";
    /**
     * 入库管理Po表的字段*
     */
    private static final String PO_TABLE_FILED = "poid,ponum,description,vendordesc,pretaxtotal,status,siteid,orderdate,shiptoattn";


    //获取主项目
    public static void getLatestItem(Context ctx, boolean refresh,
                                     HttpRequestHandler<ArrayList<Item>> handler) {
        getItems(ctx, BASE_URL, refresh, handler);
    }


    //获取入库管理
    public static void getLatestPo(Context ctx, boolean refresh,
                                   HttpRequestHandler<ArrayList<Po>> handler) {
        getPos(ctx, BASE_URL, refresh, handler);
    }



    private static String getProblemFromHtmlResponse(Context cxt, String response) {
        Pattern errorPattern = Pattern.compile("<div class=\"problem\">(.*)</div>");
        Matcher errorMatcher = errorPattern.matcher(response);
        String errorContent;
        if (errorMatcher.find()) {
            errorContent = errorMatcher.group(1).replaceAll("<[^>]+>", "");
        } else {
            errorContent = cxt.getString(R.string.error_unknown);
        }
        return errorContent;
    }

    /**
     * 使用用户名密码登录
     *
     * @param cxt
     * @param username 用户名
     * @param password 密码
     * @param handler  返回结果处理
     */
    public static void loginWithUsername(final Context cxt, final String username, final String password,
                                         final HttpRequestHandler<Integer> handler) {
        requestOnceWithURLString(cxt, username, password, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {

            }

            @Override
            public void onSuccess(String data) {
                Log.i(TAG, "data=" + data);


                //解析返回的Json数据
                int code = JsonUtils.parsingAuthStr(data);
                if (code == 1) {
                    SafeHandler.onSuccess(handler, 200);
                } else if (code == 0) {
                    SafeHandler.onFailure(handler, "用户名和密码不匹配");
                }
            }


            @Override
            public void onFailure(String error) {
                SafeHandler.onFailure(handler, error);
            }
        });
    }


    private static void requestOnceWithURLString(final Context cxt, final String username, final String password,
                                                 final HttpRequestHandler<String> handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);
        client.post(SIGN_IN_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG,"FstatusCode="+statusCode);
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG,"SstatusCode="+statusCode);
                if (statusCode == 200) {
                    SafeHandler.onSuccess(handler, responseString);
                }
            }
        });
    }


    private static AsyncHttpClient getClient(Context context) {
        return getClient(context, true);
    }

    private static AsyncHttpClient getClient(Context context, boolean mobile) {
        if (sClient == null) {
            sClient = new AsyncHttpClient();
            sClient.setEnableRedirects(false);
            sClient.setCookieStore(new PersistentCookieStore(context));
            sClient.addHeader("Cache-Control", "max-age=0");
            sClient.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            sClient.addHeader("Accept-Charset", "utf-8, iso-8859-1, utf-16, *;q=0.7");
            sClient.addHeader("Accept-Language", "zh-CN, en-US");
            sClient.addHeader("X-Requested-With", "com.android.browser");
//            sClient.addHeader("Host", "www.v2ex.com");
        }

        if (mobile)
            sClient.setUserAgent("Mozilla/5.0 (Linux; U; Android 4.2.1; en-us; M040 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        else
            sClient.setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");

        return sClient;
    }


    /**
     * 获取主项目列表
     *
     * @param ctx
     * @param urlString URL地址
     * @param refresh   是否从缓存中读取
     * @param handler   结果处理
     */
    public static void getItems(Context ctx, String urlString, boolean refresh,
                                final HttpRequestHandler<ArrayList<Item>> handler) {
        Uri uri = Uri.parse(urlString);
        String path = uri.getLastPathSegment();
        String param = uri.getEncodedQuery();
        String key = path + ITEM_TABLE_NAME;
        if (param != null)
            key += param;

        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<Item> topics = PersistenceHelper.loadModelList(ctx, key);
            if (topics != null && topics.size() > 0) {
                SafeHandler.onSuccess(handler, topics);
                return;
            }
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tableName", ITEM_TABLE_NAME);
        params.put("fields", ITEM_TABLE_FILED);
        params.put("params", "");
        params.put("orderby", "");
        params.put("sorttype", "");
        params.put("haspage", "true");
        params.put("currentpage", "1");
        params.put("pagesize", "20");
        params.put("useruid", "1");
        client.post(ctx, urlString, params,
                new WrappedJsonHttpResponseHandler<Item>(ctx, Item.class, key, handler));
    }


    /**
     * 获取PO列表
     *
     * @param ctx
     * @param urlString URL地址
     * @param refresh   是否从缓存中读取
     * @param handler   结果处理
     */
    public static void getPos(Context ctx, String urlString, boolean refresh,
                              final HttpRequestHandler<ArrayList<Po>> handler) {
        Uri uri = Uri.parse(urlString);
        String path = uri.getLastPathSegment();
        String param = uri.getEncodedQuery();
        String key = path + PO_TABLE_NAME;
        if (param != null)
            key += param;

        if (!refresh) {
            //尝试从缓存中加载
            ArrayList<Po> topics = PersistenceHelper.loadModelList(ctx, key);
            if (topics != null && topics.size() > 0) {
                SafeHandler.onSuccess(handler, topics);
                return;
            }
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("tableName", PO_TABLE_NAME);
        params.put("fields", PO_TABLE_FILED);
        params.put("params", "");
        params.put("orderby", "");
        params.put("sorttype", "");
        params.put("haspage", "true");
        params.put("currentpage", "1");
        params.put("pagesize", "20");
        params.put("useruid", "1");
        client.post(ctx, urlString, params,
                new WrappedJsonHttpResponseHandler<Po>(ctx, Po.class, key, handler));
    }


    /**
     * 主项目修改
     *
     * @param cxt
     * @param itemid 项目ID
     * @param desc   项目描述
     * @param in20   规格型号
     */
    public static void updateItemInfo(final Context cxt, final String itemid, final String desc, final String in20,
                                      final HttpRequestHandler<Integer> handler) {
        requestOnceWithURLItem(cxt, itemid, desc, in20, new HttpRequestHandler<String>() {
            @Override
            public void onSuccess(String data) {
                Log.i(TAG, "data=" + data);


                //解析返回的Json数据
                int code = JsonUtils.parsingUpdateString(data);
                Log.i(TAG,"code="+code);
                if (code == 1) {
                    SafeHandler.onSuccess(handler, 200);
                } else if (code == 0) {
                    SafeHandler.onFailure(handler, "提交失败");
                }
            }

            @Override
            public void onSuccess(String data, int totalPages, int currentPage) {

            }

            @Override
            public void onFailure(String error) {
                SafeHandler.onFailure(handler, error);
            }
        });
    }


    /**
     * 主项目修改
     * @param cxt
     * @param itemid
     * @param desc
     * @param in20
     * @param handler
     */
    private static void requestOnceWithURLItem(final Context cxt, final String itemid, final String desc, final String in20,
                                               final HttpRequestHandler<String> handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("itemid", itemid);
        params.put("desc", desc);
        params.put("in20", in20);
        params.put("useruid", "1");
        client.post(ITEM_UPDATE_URL, params, new TextHttpResponseHandler() {


            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i(TAG,"FstatusCode="+statusCode);
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorCommentFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG,"SstatusCode="+statusCode);
                if (statusCode == 200) {
                    SafeHandler.onSuccess(handler, responseString);
                }
            }
        });
    }


}
