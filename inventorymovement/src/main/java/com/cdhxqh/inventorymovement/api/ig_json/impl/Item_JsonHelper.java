package com.cdhxqh.inventorymovement.api.ig_json.impl;

import android.util.Log;

import com.cdhxqh.inventorymovement.api.ig_json.JsonHelper;
import com.cdhxqh.inventorymovement.model.Item;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Item解析*
 */
public final class Item_JsonHelper
        implements JsonHelper<Item> {
    private static final String TAG = "Item_JsonHelper";

    /**
     * 解析List*
     */
    public static ArrayList<Item> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<Item> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<Item>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Item parsed = parseFromJson(jp);
                Log.i(TAG, "parsed=" + parsed);
                if (parsed != null) {
                    results.add(parsed);
                }
            }
        }


        return results;
    }


    /**
     * 解析Item
     */
    public static Item parseFromJson(JsonParser jp)
            throws IOException {
        Item instance = new Item();

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

    public static boolean processSingleField(Item instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("ITEMID".equals(fieldName)) {
            instance.itemid = jp.getValueAsString();
            return true;
        } else if ("ITEMNUM".equals(fieldName)) {
            instance.itemnum = jp.getValueAsString();
            return true;
        } else if ("DESCRIPTION".equals(fieldName)) {
            instance.description = jp.getValueAsString();
        } else if ("IN20".equals(fieldName)) {
            instance.in20 = jp.getValueAsString();
            return true;
        } else if ("ORDERUNIT".equals(fieldName)) {
            instance.orderunit = jp.getValueAsString();
            return true;
        } else if ("ISSUEUNIT".equals(fieldName)) {
            instance.issueunit = jp.getValueAsString();
            return true;
        } else if ("ENTERBY".equals(fieldName)) {
            instance.enterby = jp.getValueAsString();
            return true;
        } else if ("ENTERDATE".equals(fieldName)) {
            instance.enterdate = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Item*
     */
    public static ArrayList<Item> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析Item*
     */
    public static Item parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
