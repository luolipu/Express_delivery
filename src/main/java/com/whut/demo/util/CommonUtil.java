package com.whut.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    public static String createDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
}
