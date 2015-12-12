package com.cdhxqh.inventorymovement.api.ig_json.impl;

import android.util.Log;

import com.cdhxqh.inventorymovement.api.ig_json.JsonHelper;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.model.Itemreq;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.instagram.common.json.JsonFactoryHolder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Itemreq解析*
 */
public final class Itemreq_JsonHelper
        implements JsonHelper<Item> {
    private static final String TAG = "Itemreq_JsonHelper";

    /**
     * 解析List*
     */
    public static ArrayList<Itemreq> parseFromJsonList(JsonParser jp)
            throws IOException {

        ArrayList<Itemreq> results = null;

        // validate that we're on the right token
        if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
            results = new ArrayList<Itemreq>();
            while (jp.nextToken() != JsonToken.END_ARRAY) {
                Itemreq parsed = parseFromJson(jp);
                Log.i(TAG, "parsed=" + parsed);
                if (parsed != null) {
                    results.add(parsed);
                }
            }
        }


        return results;
    }


    /**
     * 解析Itemreq
     */
    public static Itemreq parseFromJson(JsonParser jp)
            throws IOException {
        Itemreq instance = new Itemreq();

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

    public static boolean processSingleField(Itemreq instance, String fieldName, JsonParser jp)
            throws IOException {
        if ("ITEMREQNUM".equals(fieldName)) {
            instance.itemreqnum = jp.getValueAsString();
            return true;
        } else if ("DESCRIPTION".equals(fieldName)) {
            instance.description = jp.getValueAsString();
            return true;
        } else if ("RECORDER".equals(fieldName)) {
            instance.recorder = jp.getValueAsString();
        } else if ("RECORDERDESC".equals(fieldName)) {
            instance.recorderdesc = jp.getValueAsString();
            return true;
        } else if ("RECORDERDATE".equals(fieldName)) {
            instance.recorderdate = jp.getValueAsString();
            return true;
        } else if ("ITEMREQID".equals(fieldName)) {
            instance.itemreqid = jp.getValueAsString();
            return true;
        } else if ("STATUS".equals(fieldName)) {
            instance.status = jp.getValueAsString();
            return true;
        } else if ("STATUSDESC".equals(fieldName)) {
            instance.statusdesc = jp.getValueAsString();
            return true;
        }else if ("ISFINISH".equals(fieldName)) {
            instance.isfinish = jp.getValueAsString();
            return true;
        }

        return false;
    }

    /**
     * 解析Itemreq*
     */
    public static ArrayList<Itemreq> parseFromJsonList(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJsonList(jp);
    }

    /**
     * 解析Itemreq*
     */
    public static Itemreq parseFromJson(String inputString)
            throws IOException {
        JsonParser jp = JsonFactoryHolder.APP_FACTORY.createParser(inputString);
        jp.nextToken();
        return parseFromJson(jp);
    }


}
