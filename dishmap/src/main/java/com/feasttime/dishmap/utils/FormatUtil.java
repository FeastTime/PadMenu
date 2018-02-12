package com.feasttime.dishmap.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * Created by LiXiaoQing on 2018/2/11.
 */

public class FormatUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());;


    public static String formatDate(long input){

        return simpleDateFormat.format(input);
    }

}
