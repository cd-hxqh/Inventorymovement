package com.cdhxqh.inventorymovement.model;

/**
 * Created by think on 2015/12/12.
 * 库存转移临时类
 */
public class Matrectrans extends Entity {

    public String itemnum;//项目
    public String description;//描述
    public String type;//型号
    public String receiptquantity;//数量
    public String curbaltotal;//仓库当前余量
    public String linecost;//行成本
    public String frombin;//原库位号
    public String tostoreloc;//目标位置
    public String tobin;//目标库位号
}
