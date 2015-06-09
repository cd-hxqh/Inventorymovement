package com.cdhxqh.inventorymovement.api;

import android.content.Context;
import android.util.Log;

import com.cdhxqh.inventorymovement.model.Entity;
import com.cdhxqh.inventorymovement.model.PersistenceHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yw on 2015/5/2.
 */
class WrappedJsonHttpResponseHandler<T extends Entity> extends JsonHttpResponseHandler {
    private static final String TAG="WrappedJsonHttpResponseHandler";
    HttpRequestHandler<ArrayList<T>> handler;
    Class c;
    Context context;
    String key;

    public WrappedJsonHttpResponseHandler(Context cxt, Class c, String key,
                                          HttpRequestHandler<ArrayList<T>> handler) {
        this.handler = handler;
        this.c = c;
        this.context = cxt;
        this.key = key;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        ArrayList<T> models = new ArrayList<T>();
        try {
            T obj = (T) Class.forName(c.getName()).newInstance();
            obj.parse(response);
            if (obj != null)
                models.add(obj);
        } catch (Exception e) {
        }
        PersistenceHelper.saveModelList(context, models, key);
        try {
            handler.onSuccess(models);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        Log.i(TAG,"response="+response);
        ArrayList<T> models = new ArrayList<T>();
        for (int i = 0; i < response.length(); i++) {

            try {
                JSONObject jsonObj = response.getJSONObject(i);
                String str=jsonObj.getString("cboset");
                JSONArray jsona=new JSONArray(str);
            for (int j = 0; j < jsona.length(); j++){
                JSONObject jsonObjitem = jsona.getJSONObject(j);
                T obj = (T) Class.forName(c.getName()).newInstance();
                obj.parse(jsonObjitem);
                if (obj != null)
                    models.add(obj);
            }
                T obj = (T) Class.forName(c.getName()).newInstance();
                obj.parse(jsonObj);
                if (obj != null)
                    models.add(obj);
            } catch (Exception e) {
            }
        }


        PersistenceHelper.saveModelList(context, models, key);
        SafeHandler.onSuccess(handler, models);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable e) {
        handleFailure(statusCode, e.getMessage());
    }

    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
        handleFailure(statusCode, e.getMessage());
    }

    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONArray errorResponse) {
        handleFailure(statusCode, e.getMessage());
    }

    private void handleFailure(int statusCode, String error) {
        error = IMErrorType.errorMessage(context, IMErrorType.ErrorApiForbidden);
        SafeHandler.onFailure(handler, error);
    }
}

