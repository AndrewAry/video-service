package com.mwtutu.utils;


import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class Debug {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Debug.class);
    public static final String DEBUG_INFO = "INFO";
    public static final String DEBUG_ERROR = "ERROR";
    public static final String DEBUG_DEBUG = "DEBUG";

    public static void printlnInfo(Object... ob) {
        Debug.base(Debug.DEBUG_INFO, StringUtil.getObjValue(ob));
    }

    public static void printlnDebug(Object... ob) {
        Debug.base(Debug.DEBUG_DEBUG, StringUtil.getObjValue(ob));
    }

    public static void printlnErr(Object... ob) {
        Debug.base(Debug.DEBUG_ERROR, StringUtil.getObjValue(ob));
    }

    public static void printlnArray(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            Object obj = array[i];
            Debug.base(Debug.DEBUG_INFO, i + ":" + obj.toString());
        }
    }

    public static void printlnListArray(List list) {
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            Debug.base(Debug.DEBUG_INFO, i + ":" + StringUtil.getObjValue((Object[]) obj));
        }
    }

    public static void printlnList(List list) {
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            Debug.base(Debug.DEBUG_INFO, i + ":" + obj.toString());
        }
    }

    public static void printlnMap(Map map) {
        for (Object obj : map.keySet()) {
            Debug.base(Debug.DEBUG_INFO, obj.toString() + "->" + map.get(obj));
        }
    }

    public static void printlnMap(Map map, String info) {
        Debug.printlnInfo(info);
        Debug.printlnMap(map);
    }

    public static void printlnThrowable(Throwable e, Object... info) {
        Debug.base(Debug.DEBUG_ERROR, e.toString() + " " + StringUtil.getObjValue(info), e);
    }

    private static void base(String type, String str) {
        base(type, str, null);
    }

    private static void base(String type, String res, Throwable e) {
        if ((type == Debug.DEBUG_DEBUG)) {
            log.debug(res);
        } else if ((type == Debug.DEBUG_INFO)) {
            log.info(res);
        } else if ((type == Debug.DEBUG_ERROR)) {
            if (e == null) {
                log.error(res);
            } else {
                log.error(res, e);
            }
        }
    }
}
