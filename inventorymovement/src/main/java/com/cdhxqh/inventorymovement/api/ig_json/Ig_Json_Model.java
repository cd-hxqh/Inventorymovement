// Copyright 2004-present Facebook. All Rights Reserved.

package com.cdhxqh.inventorymovement.api.ig_json;

import android.util.Log;


import com.cdhxqh.inventorymovement.api.ig_json.impl.Invbalances_JsonHelper;
import com.cdhxqh.inventorymovement.api.ig_json.impl.Inventory_JsonHelper;
import com.cdhxqh.inventorymovement.api.ig_json.impl.Item_JsonHelper;
import com.cdhxqh.inventorymovement.api.ig_json.impl.Itemreqline_JsonHelper;
import com.cdhxqh.inventorymovement.api.ig_json.impl.Locations_JsonHelper;
import com.cdhxqh.inventorymovement.model.Invbalances;
import com.cdhxqh.inventorymovement.model.Inventory;
import com.cdhxqh.inventorymovement.model.Item;
import com.cdhxqh.inventorymovement.model.Itemreqline;
import com.cdhxqh.inventorymovement.model.Locations;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Helper class to parse the model.
 */
public class Ig_Json_Model {

    private static final String TAG = "Ig_Json_Model";

    /**
     * 主项目*
     */
    public static ArrayList<Item> parseItemFromString(String input) throws IOException {
        return Item_JsonHelper.parseFromJsonList(input);
    }
    /**
     * 库存使用情况
     */
    public static ArrayList<Inventory> parseInventoryFromString(String input) throws IOException {
        return Inventory_JsonHelper.parseFromJsonList(input);
    }
    /**
     * 物资编码申请行
     */
    public static ArrayList<Itemreqline> parseItemreqlineFromString(String input) throws IOException {
        return Itemreqline_JsonHelper.parseFromJsonList(input);
    }
    /**
     * 解析库存转移
     */
    public static ArrayList<Locations> parseLocationsFromString(String input) throws IOException {
        return Locations_JsonHelper.parseFromJsonList(input);
    }
    /**
     * 解析库存项目
     */
    public static ArrayList<Invbalances> parseInvbalancesFromString(String input) throws IOException {
        return Invbalances_JsonHelper.parseFromJsonList(input);
    }



}
