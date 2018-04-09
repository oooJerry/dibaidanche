package com.wuwutongkeji.dibaidanche;

import com.wuwutongkeji.dibaidanche.common.util.TextUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String url = "http://api.dibaibike.com/Service/App/Get_plate_number/idcard/702374.shtml";
        String code = TextUtil.getScanCode(url);
        System.out.println(code);
    }
}