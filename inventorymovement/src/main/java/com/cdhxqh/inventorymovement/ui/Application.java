package com.cdhxqh.inventorymovement.ui;

/**
 * Created by apple on 15/5/27.
 */
public class Application extends android.app.Application {

    private static Application mContext;

    public static Application getInstance(){
        return mContext;
    }
}
