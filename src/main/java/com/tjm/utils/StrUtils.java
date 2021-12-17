package com.tjm.utils;

public class StrUtils {

    /**
     * 判断字符串是否为空,只有空格也算空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null && !str.replace(" ", "").equals("")) {
            return false;
        } else {
            return true;
        }
    }

}
