package com.xiaoxin.notes.utils;

import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on 2021/1/22. 热门文章
 *
 * @author XiaoXinZai
 */
public class IdUtils {

    public static String getTimeStamp(Date time){
        long l = System.currentTimeMillis() - time.getTime();
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
            return sb.toString();
        }
        if (hour > 0) {
            sb.append(hour + "小时");
            return sb.toString();
        }
        if (min > 0) {
            sb.append(min + "分");
            return sb.toString();
        }
        sb.append(s + "秒");
        return sb.toString();
    }
}
