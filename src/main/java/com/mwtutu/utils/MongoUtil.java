package com.mwtutu.utils;

import com.mongodb.client.MongoCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MongoUtil {

    public static List<Map> getListFromDBCursor(MongoCursor cursor) {

        List<Map> list = new ArrayList<Map>();
        if (StringUtil.isBlank(cursor)) {
            return list;
        }

        try {
            while (cursor.hasNext()) {
                Map map = (Map) cursor.next();
                if (StringUtil.isNotBlank(map)) {
                    list.add(map);
                }
            }
        } catch (Exception e) {
            Debug.printlnThrowable(e, cursor);
        }
        return list;
    }
}
