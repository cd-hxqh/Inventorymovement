package com.cdhxqh.inventorymovement.api;


import android.content.Context;
import android.util.Log;

import com.cdhxqh.inventorymovement.Application;
import com.cdhxqh.inventorymovement.R;
import com.cdhxqh.inventorymovement.utils.MessageUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by apple on 15/5/27.
 */
public class ImManager {

    private static Application mApp = Application.getInstance();
    private static AsyncHttpClient sClient = null;
    private static final String TAG = "ImManager";


    private static final String HTTP_API_URL = "http://192.168.1.110:8080/hhw/services/";

    public static final String HTTP_BASE_URL = "http://192.168.1.110:8080/hhw";
    public static final String HTTPS_BASE_URL = "https://192.168.1.110:8080/hhw";
    //登陆URL
    private static final String SIGN_IN_URL = HTTP_API_URL + "user/auth";


    public static String getBaseUrl() {
        Log.i(TAG, "mapp=" + mApp);
        return mApp.isHttps() ? HTTPS_BASE_URL : HTTP_BASE_URL;
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
                } else if(code==0){
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
                SafeHandler.onFailure(handler, IMErrorType.errorMessage(cxt, IMErrorType.ErrorLoginFailure));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (statusCode == 200) {
                    SafeHandler.onSuccess(handler, responseString);
                }
            }
        });
    }


    private static String getOnceStringFromHtmlResponseObject(String content) {
        Pattern pattern = Pattern.compile("<input type=\"hidden\" value=\"([0-9]+)\" name=\"once\" />");
        final Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return matcher.group(1);
        return null;
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
}
