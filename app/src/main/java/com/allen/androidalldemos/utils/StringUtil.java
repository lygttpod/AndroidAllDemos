package com.allen.androidalldemos.utils;


public class StringUtil {
    /**
     * 非空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && !"".equals(s.trim());
    }

    /**
     * 空判断
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim());
    }

    /**
     * 格式化
     *
     * @param src
     * @param objects
     * @return
     */
    public static String format(String src, Object... objects) {
        int k = 0;
        for (Object obj : objects) {
            src = src.replace("{" + k + "}", obj.toString());
            k++;
        }
        return src;
    }

    public static String Substring(String string) {
        string = string.substring(string.length() - 4, string.length() - 1);
        return string;
    }

    public static String Substring1(String string) {
        string = string.substring(string.length() - 3, string.length() );
        return string;
    }
}
