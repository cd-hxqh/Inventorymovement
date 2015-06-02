package com.cdhxqh.inventorymovement;

import android.content.Context;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * Created by apple on 15/5/27.
 */
public class Application extends android.app.Application {

    private static Application mContext;

    public static Application getInstance(){
        return mContext;
    }


    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //MobclickAgent.openActivityDurationTrack(false);

        SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());

        mContext = this;

    }

    /**
     * 是否Https登录
     * @return
     */
    public boolean isHttps()
    {
        String perf_https = getProperty(AppConfig.CONF_USE_HTTPS);
        if(perf_https == null || perf_https.isEmpty())
            return true;
        else
            return Boolean.parseBoolean(perf_https);
    }


    public String getProperty(String key){
        return AppConfig.getAppConfig(this).get(key);
    }
}
