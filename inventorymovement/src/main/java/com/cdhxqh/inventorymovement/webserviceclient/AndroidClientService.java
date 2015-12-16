package com.cdhxqh.inventorymovement.webserviceclient;


import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by think on 2015/8/11.
 * webservice方法
 */
public class AndroidClientService {
    private static final String TAG = "AndroidClientService";
    public String NAMESPACE = "http://www.ibm.com/maximo";
    public String url = "http://182.92.8.94:7002/meaweb/services/MOBILESERVICE";
    public int timeOut = 1200000;

    public AndroidClientService() {
    }

    public AndroidClientService(String url) {
        this.url = url;
    }

    public void setTimeOut(int seconds) {
        this.timeOut = seconds * 1000;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * ��ӹ�������
     * @param username
     * @param itemnum
     * @param desc
     * @param model
     * @return
     */
    public String UpdateItem(String username,String itemnum,String desc,String model){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceINV07UpdateItem");
        soapReq.addProperty("userid", username);
        soapReq.addProperty("itemnum",itemnum);
        soapReq.addProperty("desc",desc);
        soapReq.addProperty("model",model);
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }




    /**
     * 库存盘点
     */
    public String INV04Invadj(String userid,String storeroom,String itemnum,String qty){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceINV04Invadj");
        soapReq.addProperty("userid", userid);//用户名
        soapReq.addProperty("storeroom", storeroom);//库房
        soapReq.addProperty("itemnum",itemnum);//物资编号
        soapReq.addProperty("qty", qty);//数量
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            String s = obj;
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }















    /**
     * 库存移出
     */
    public String INV05Invtrans1(String userid,String itemnum,String qty,String storeroom1,String binnum1,
            String storeroom2,String binnum2){
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "mobileserviceINV05Invtrans");
        soapReq.addProperty("userid", userid);//用户名
        soapReq.addProperty("itemnum", itemnum);//物质编号
        soapReq.addProperty("qty",qty);//数量
        soapReq.addProperty("storeroom1", storeroom1);//出库房
        soapReq.addProperty("binnum1", binnum1);//出货柜号
        soapReq.addProperty("storeroom2",storeroom2);//入库房
        soapReq.addProperty("binnum2", binnum2);//入货柜号
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
            String s = obj;
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

//    /**
//     * 库存移入
//     */
//    public String INV05Invtrans2(String userid,String itemnum,String qty,String storeroom2,String binnum2){
//        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//        soapEnvelope.implicitTypes = true;
//        soapEnvelope.dotNet = true;
//        SoapObject soapReq = new SoapObject(NAMESPACE, "INV05Invtrans");
//        soapReq.addProperty("userid", userid);//用户名
//        soapReq.addProperty("itemnum", itemnum);//物质编号
//        soapReq.addProperty("qty", qty);//数量
//        soapReq.addProperty("storeroom2", storeroom2);//库房
//        soapReq.addProperty("binnum2", binnum2);//货柜号
//        soapEnvelope.setOutputSoapObject(soapReq);
//        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
//        try {
//            httpTransport.call("urn:action", soapEnvelope);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
//        String obj = null;
//        try {
//            obj = soapEnvelope.getResponse().toString();
//        } catch (SoapFault soapFault) {
//            soapFault.printStackTrace();
//        }
//        return obj;
//    }



    /**
     * 生成物资编码
     */
    public String INV08CreateItem(String userid,String itemnum){

        Log.i(TAG,"userid="+userid+",itemnum="+itemnum);
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;
        SoapObject soapReq = new SoapObject(NAMESPACE, "INV08CreateItem");
        soapReq.addProperty("userid", userid);//用户名
        soapReq.addProperty("itemreqid", itemnum);//物质编号
        soapEnvelope.setOutputSoapObject(soapReq);
        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
        try {
            httpTransport.call("urn:action", soapEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        String obj = null;
        try {
            obj = soapEnvelope.getResponse().toString();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        return obj;
    }

}
