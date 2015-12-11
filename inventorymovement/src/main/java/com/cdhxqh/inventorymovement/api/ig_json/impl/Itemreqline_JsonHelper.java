package com.cdhxqh.inventorymovement.api.ig_json.impl;

import android.util.Log;

import com.cdhxqh.inventorymovement.api.ig_json.JsonHelper;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Itemreqline;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Itemreqline解析*
 */
public final class Itemreqline_JsonHelper
        implements JsonHelper<Itemreqline> {
    private static final String TAG = "Itemreqline_JsonHelper";

    /**
     * 解析List*
     */
    public static ArrayList<Itemreqline> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<Itemreqline> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<Itemreqline>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Itemreqline parsed = parseFromJson(jp);
                Log.i(TAG, "parsed=" + parsed);
                if (parsed != null) {
                    results.add(parsed);
                }
            }
        }


        return results;
    }


    /**
     * 解析Inventory
     */
    public static Itemreqline parseFromJson(JsonParser jp)
            throws IOException {
        Itemreqline instance = new Itemreqline();

        // validate that we're on the right token
        if (jp.getCurrentToken() != JsonToken.START_OBJECT) {
            jp.skipChildren();
            return null;
        }

        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jp.getCurrentName();
            jp.nextToken();
            processSingleField(instance, fieldName, jp);
            jp.skipChildren();
        }

        return instance;
    }

    public static boolean processSingleField(Itemreqline instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("ITEMNUM".equals(fieldName)) {
            instance.itemnum = jp.getValueAsString();
            return true;
        } else if ("ITEMREQNUM".equals(fieldName)) {
            instance.itemreqnum = jp.getValueAsString();
            return true;
        } else if ("MATERNAME".equals(fieldName)) {
            instance.matername = jp.getValueAsString();
        } else if ("XH".equals(fieldName)) {
            instance.xh = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Item*
     */
    public static ArrayList<Itemreqline> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析Itemreqline*
     */
    public static Itemreqline parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
