package com.wuwutongkeji.dibaidanche.common.util;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Bai on 17/9/14.
 */

public class FieldUtil {

    public static Map<String,Object> Model2Map(Object obj) {

        Field[] fields = obj.getClass().getDeclaredFields();

        Map<String,Object> map = new HashMap<>();

        for (Field field : fields) {

            field.setAccessible(true);

            try {
                Object objValue = field.get(obj);
                if(null != objValue){
                    map.put(field.getName(),objValue);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Logger.d(e);
            }
        }
        return map;
    }
}
