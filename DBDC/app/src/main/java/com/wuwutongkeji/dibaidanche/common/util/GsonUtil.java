package com.wuwutongkeji.dibaidanche.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

/**
 * Created by Mr.Bai on 17/9/15.
 */

public class GsonUtil {

    private static Gson gson;


    static {
        gson = new GsonBuilder()
                .setDateFormat(DateUtil.yyyy_MM_dd_HH__mm__ss)
                .create();
    }


    public static Gson getGson(){
        return gson;
    }

    public static String xml2JSON(String xml) {
        try {
            XmlToJson xmlToJson = new XmlToJson.Builder(xml).build();
            return xmlToJson.toJson().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static Object xml2Bean(String xml,Class clazz){
        return getGson().fromJson(xml2JSON(xml),clazz);
    }
}
