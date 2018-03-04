package com.feasttime.dishmap.utils;

/**
 * Created by chen on 2018/2/11.
 */

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YLBF
 *
 * @category 类说明 Activity集合
 * @version 修改时间 2015年3月5日下午4:04:05
 * @version 版本号 1.0.0.0
 */
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<Activity>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 移除Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 移除所有Activity
     *
     *
     */
    public static void finishAllActivity() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
