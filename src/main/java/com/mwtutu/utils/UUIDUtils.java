package com.mwtutu.utils;

import java.util.UUID;

/**
 * @时间 2018年8月2日 15:29:45
 * @TODO： 生成随机字符串的工具类 uuid
 */
public class UUIDUtils {
    public static  String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
