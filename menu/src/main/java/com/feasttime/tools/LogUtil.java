package com.feasttime.tools;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;



public class LogUtil {
    public static boolean DEBUG = true;

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void bigLog(String TAG, String msg) {
        if (DEBUG) {
            msg = msg.trim();
            int index = 0;
            int maxLength = 4000;
            String sub;
            try {
                while (index < msg.length()) {
                    // java的字符不允许指定超过总的长度end
//				if (msg.length() <= index + maxLength) {
                    sub = msg.substring(index);
//				} else {
//					sub = msg.substring(index, maxLength);
//				}
                    index += maxLength;
                    Log.i(TAG, sub.trim());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    //	* 写文件到sd卡上
//	*
//			* @param context
//	*/
    public static void writeFileToSD(String context) {
        // 先判断是否有SDCard
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String content = context;
            try {
                // 获取SDcard路径
                File sdCardDir = Environment.getExternalStorageDirectory();

                File file = new File(sdCardDir, "log.txt");
                // File file = new File(sdcardPath + File.separator + FILE_NAME);

                // 以指定文件创建RandomAccessFile对象
                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                // 将文件记录指针移动最后
                raf.seek(file.length());
                // 输出文件内容
                raf.write(content.getBytes());
                raf.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}