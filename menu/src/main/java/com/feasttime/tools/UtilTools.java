package com.feasttime.tools;

import java.net.URLDecoder;

/**
 * Created by Administrator on 2017/7/16.
 */

public class UtilTools {
    public static String decodeStr(String str) {
        try {
            String result = URLDecoder.decode(str,"UTF-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
