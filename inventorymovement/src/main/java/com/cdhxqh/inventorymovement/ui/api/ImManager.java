package com.cdhxqh.inventorymovement.ui.api;




import com.cdhxqh.inventorymovement.ui.Application;
import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by apple on 15/5/27.
 */
public class ImManager {

    private static Application mApp = Application.getInstance();
    private static AsyncHttpClient sClient = null;
    private static final String TAG = "ImManager";


    private static final String HTTP_API_URL = "http://192.168.1.110:8080/hhw/services/";



}
