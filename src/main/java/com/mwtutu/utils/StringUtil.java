package com.mwtutu.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;

public class StringUtil {

    private String[] mapValueArray;



    public static String appendString(String... strArray) {

        StringBuffer sb = new StringBuffer();
        for (String str : strArray) {
            sb.append(str);
        }
        return sb.toString();
    }



    public static byte[] interceptByte(byte[] b, int beginIndex, int length) {

        if (b == null || (beginIndex + length) > b.length) {

            return new byte[0];
        }
        byte[] buf = new byte[length];
        System.arraycopy(b, beginIndex, buf, 0, length);
        return buf;
    }



    // public static Map setMap(Map map, Object key, Object value) {
    // map.put(key, value);
    // return map;
    // }
    public static Map popMap(Object... array) {

        Map map = new LinkedHashMap();
        int size = array.length / 2;
        for (int i = 0; i < size; i++) {
            map.put(array[i], array[i + size]);
        }
        return map;
    }



    private static SimpleDateFormat getSimpleDateFormat(String str) {

        SimpleDateFormat sf = new SimpleDateFormat(str);
        sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sf;
    }



    public static String checkListAttrSame(List<Map> list, String key, String primarykeys) {

        if (StringUtil.isBlank(list)) {
            return "";
        }
        String old = StringUtil.getMapValue(list.get(0), key);
        for (Map map : list) {
            String value = StringUtil.getMapValue(map, key);
            if (!old.equals(value)) {
                String pk = StringUtil.getMapValue(map, primarykeys);
                if (StringUtil.isNotBlank(pk)) {
                    return pk;
                } else {
                    return old + "-" + value;
                }
            }
        }
        return "";
    }



    public static String checkListAttrHave(List<Map> list, String key, String primarykeys, String[] havaArray) {

        if (StringUtil.isBlank(list)) {
            return "";
        }
        for (Map map : list) {
            String value = StringUtil.getMapValue(map, key);
            if (StringUtil.haveStringLikeWith(havaArray, value)) {
                String pk = StringUtil.getMapValue(map, primarykeys);
                return pk;
            }
        }
        return "";
    }



    public static String listToAppendString(List<Map> list, String key, String cut) {

        StringBuffer sb = new StringBuffer();
        if (StringUtil.isBlank(list)) {
            return sb.toString();
        }
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            String value = StringUtil.getMapValue(map, key);
            if (i > 0) {
                sb.append(cut);
            }
            sb.append(value);
        }
        return sb.toString();
    }



    /**
     * 验证是否不是yyyy-MM-dd HH:mm:ss格式的时间
     *
     * @param sDate
     * @return 不是时间 TRUE 是时间 FALSE
     */
    public static boolean isNotDateyMdHms(String sDate) {

        if (isBlank(sDate)) {
            return true;
        }
        try {
            String formater = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format = getSimpleDateFormat(formater);
            format.setLenient(false);
            format.parse(sDate);
            return false;
        } catch (Exception ex) {
            return true;
        }
    }



    public static Timestamp getTimestampFromString(String str) {

        return getTimestampFromString(str, "");
    }



    public static Timestamp getTimestampFromString(String str, String formaterStr) {

        if (StringUtil.isBlank(str)) {
            str = StringUtil.getCurrentAllDate();
        }
        try {
            String formater = StringUtil.isBlank(formaterStr) ? "yyyy-MM-dd HH:mm:ss" : formaterStr;
            SimpleDateFormat format = new SimpleDateFormat(formater);
            Date date = format.parse(str);
            Timestamp t = new Timestamp(date.getTime());
            return t;
        } catch (Exception e) {
            return null;
        }
    }



    public static String getReplaceStr(char replace, String str, Object[] value) {

        if (value == null || value.length == 0)
            return str;
        StringBuffer sb = new StringBuffer();
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == replace) {
                if (index < value.length) {
                    sb.append(value[index]);
                    index++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }



    public static String checkNull(String strIn) {

        if (StringUtil.isBlank(strIn)) {
            return "";
        } else {
            return strIn;
        }
    }



    /**
     * 判断输入参数(String类型)是否为空,如果str为NULL，则输出Null,否则输出str<br>
     * 例如：通常在页面中传递参数时使用<br>
     */
    public static String checkNull(String strIn, String strDefault) {

        if (StringUtil.isBlank(strIn)) {
            return strDefault;
        } else {
            return strIn;
        }
    }



    /**
     * 将源字符串中的目标字符串内容替换<br>
     * 例如：replace("中国红红","红","龙"),返回"中国龙龙"<br>
     * 很少使用<br>
     */
    public static String replace(String strSource, String strFrom, String strTo) {
        // 如果要替换的子串为空，则直接返回源串
        if (strFrom == null || strFrom.equals(""))
            return strSource;
        String strDest = "";
        // 要替换的子串长度
        int intFromLen = strFrom.length();
        int intPos;
        // 循环替换字符串
        while ((intPos = strSource.indexOf(strFrom)) != -1) {
            // 获取匹配字符串的左边子串
            strDest = strDest + strSource.substring(0, intPos);
            // 加上替换后的子串
            strDest = strDest + strTo;
            // 修改源串为匹配子串后的子串
            strSource = strSource.substring(intPos + intFromLen);
        }
        // 加上没有匹配的子串
        strDest = strDest + strSource;
        // 返回
        return strDest;
    }



    /**
     * 判断输入参数(Object类型)是否为空,如果strIn为NULL，则输出strDefault,否则输出strIn.toString<br>
     * 例如：通常在页面中传递参数时使用<br>
     */
    public static String checkObjectNull(Object strIn, String strDefault) {

        if (strIn == null) {
            return strDefault;
        } else {
            return strIn.toString();
        }
    }



    /**
     * 把超过指定长度的字符串截断并加上"..."<br>
     * --不区分中英文<br>
     * 例如：StringUtil.formatTitle("我爱中国龙",3); return "我爱中..."<br>
     */
    public static String formatTitle(String title, int length) {

        String str = title;
        str = html2Text(str);
        if (str.length() > length) {
            str = str.substring(0, length) + "...";
        }
        return str;
    }



    /**
     * 把超过指定长度的字符串截断并加上"..."(只过滤换行)<br>
     * --不区分中英文<br>
     * 例如：StringUtil.formatTitle("我爱中国龙",3); return "我爱中..."<br>
     */
    public static String formatTitle2(String title, int length) {

        String str = title;
        str = html2Textp(str);
        if (str.length() > length) {
            str = str.substring(0, length) + "...";
        }
        return str;
    }



    /**
     * 把超过指定长度的字符串截断并加上"..."<br>
     * --区分中英文<br>
     * 例如：StringUtil.formatTitle("我爱中国龙",3); return "我..."<br>
     * 例如：StringUtil.formatTitle("我爱中国龙",4); return "我爱..."<br>
     */
    public static String formatTitleIndex(String originalString, int strLength) {

        float currentLength = 0;
        String resultStr = "";
        for (int i = 0; i <= originalString.length(); i++) {
            if (currentLength > strLength) {
                resultStr = originalString.substring(0, i - 1) + "...";
                break;
            }
            if (originalString.length() == i + 1) {
                resultStr = originalString;
                break;
            }
            char ch = originalString.charAt(i);
            if (ch < 256) {
                currentLength += 1.3;
            } else {
                currentLength += 2;
            }
        }
        return resultStr;
    }



    /**
     * 得到yyyy-MM-dd HH:mm:ss<br>
     * 例如：return 2007-05:03 25:03:19<br>
     */
    public static String getCurrentAllDate() {

        SimpleDateFormat df = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 小写的hh取得12小时，大写的HH取的是24小时
        Date date = new Date();
        return df.format(date);
    }



    public static String getSftDate(String name) {

        SimpleDateFormat df = getSimpleDateFormat("MM月dd日 " + name);
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到yyyyMMdd<br>
     * 例如：return 20070503<br>
     */
    public static String getCurrentIntDate() {

        SimpleDateFormat df = getSimpleDateFormat("yyyyMMdd"); // 小写的hh取得12小时，大写的HH取的是24小时
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 时间比较 fromData - toData
     *
     * @param fromData
     * @param toData
     * @return 毫秒
     */
    public static long ssDiff(String fromData, String toData) {

        long diff = 0;
        DateFormat df = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(fromData);
            Date d2 = df.parse(toData);
            diff = d1.getTime() - d2.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diff;
    }



    /**
     * 功能说明 toData - fromData 例如： ssDiff_Cn_MM_dd("2013-04-12 02:02:00","2013-04-17 22:02:00") 5天20小时
     *
     * @param fromData
     * @param toData
     * @return
     */
    public static String ssDiff_Cn_MM_dd(String fromData, String toData) {

        String ret = "";
        SimpleDateFormat dfs = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day1 = 0L;
        long hour1 = 0L;
        try {
            Date begin = dfs.parse(fromData);
            Date end = dfs.parse(toData);
            long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
            day1 = between / (24 * 3600);
            hour1 = between % (24 * 3600) / 3600;
            if (day1 < 0) {
                day1 = 0;
            }
            if (hour1 < 0) {
                hour1 = 0;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ret = day1 + "天" + hour1 + "小时";
        return ret;
    }



    /**
     * 例如： ssDiff_Cn_MM_dd(504801L) 5天20小时
     *
     * @param m
     * @return
     */
    public static String ssDiff_Cn_MM_dd(Long m) {

        String ret = "";
        long day1 = 0L;
        long hour1 = 0L;
        try {
            long between = m; // 换成秒
            day1 = between / (24 * 3600);
            hour1 = between % (24 * 3600) / 3600;
            if (day1 < 0) {
                day1 = 0;
            }
            if (hour1 < 0) {
                hour1 = 0;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ret = day1 + "天" + hour1 + "小时";
        return ret;
    }



    public static String ssDiff_Cn(String fromData, String toData) {

        String ret = "";
        SimpleDateFormat dfs = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day1 = 0L;
        long hour1 = 0L;
        long minute1 = 0L;
        long second1 = 0L;
        try {
            Date begin = dfs.parse(fromData);
            Date end = dfs.parse(toData);
            long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
            day1 = between / (24 * 3600);
            hour1 = between % (24 * 3600) / 3600;
            minute1 = between % 3600 / 60;
            second1 = between % 60 / 60;
            if (day1 < 0) {
                day1 = 0;
            }
            if (hour1 < 0) {
                hour1 = 0;
            }
            if (minute1 < 0) {
                minute1 = 0;
            }
            if (second1 < 0) {
                second1 = 0;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ret = day1 + "天" + hour1 + "小时" + minute1 + "分" + second1 + "秒";
        return ret;
    }



    /**
     * 功能说明 toData - fromData 天数的整数 例如： ssDiff_Cn_MM_dd("2013-04-12 02:02:00","2013-04-17 22:02:00") 5
     *
     * @param fromData
     * @param toData
     * @return
     */
    public static String ssDiff_Cn_day(String fromData, String toData) {

        String ret = "";
        SimpleDateFormat dfs = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day1 = 0L;
        try {
            Date begin = dfs.parse(fromData);
            Date end = dfs.parse(toData);
            long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
            day1 = between / (24 * 3600);
            if (day1 < 0) {
                day1 = 0;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ret = day1 + "";
        return ret;
    }



    /**
     * 功能说明 toData - fromData 例如： ssDiff_Cn_MM_dd("2013-04-12 02:02:00","2013-04-17 22:02:00") 5天
     *
     * @param fromData
     * @param toData
     * @return
     */
    public static String ssDiff_Cn_dd(String fromData, String toData) {

        String ret = "";
        SimpleDateFormat dfs = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day1 = 0L;
        try {
            Date begin = dfs.parse(fromData);
            Date end = dfs.parse(toData);
            long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
            day1 = between / (24 * 3600);
            if (day1 < 0) {
                day1 = 0;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ret = day1 + "天";
        return ret;
    }



    /**
     * 得到yyyyMMHHMM<br>
     * 例如：return 2007-05-03 08:03<br>
     */
    public static String getYYYYMMHHMM() {

        SimpleDateFormat df = getSimpleDateFormat("yyyy-MM-dd HH:mm"); // 小写的hh取得12小时，大写的HH取的是24小时
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到yyyyMM<br>
     * 例如：return 20070503<br>
     */
    public static String getYYYYMM() {

        SimpleDateFormat df = getSimpleDateFormat("yyyyMM"); // 小写的hh取得12小时，大写的HH取的是24小时
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到yyyy-MM<br>
     * 例如：return 2007-05<br>
     */
    public static String getyyyyMM() {

        SimpleDateFormat df = getSimpleDateFormat("yyyy-MM");
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到yyyy<br>
     * 例如：return 2007<br>
     */
    public static String getYYYY() {

        SimpleDateFormat df = getSimpleDateFormat("yyyy");
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到HHmm<br>
     * 例如：return 08:00<br>
     */
    public static String getHHmm() {

        SimpleDateFormat df = getSimpleDateFormat("HH:mm"); // 小写的hh取得12小时，大写的HH取的是24小时
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到HHmmss<br>
     * 例如：return 20070503<br>
     */
    public static String getHHmmss() {

        SimpleDateFormat df = getSimpleDateFormat("HHmmss"); // 小写的hh取得12小时，大写的HH取的是24小时
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到HHmmss<br>
     * 例如：return 20070503<br>
     */
    public static String getHHmmss_cn() {

        SimpleDateFormat df = getSimpleDateFormat("HH：mm：ss"); // 小写的hh取得12小时，大写的HH取的是24小时
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到hh<br>
     * 例如：15--(2007-11-02 15:03:50)<br>
     */
    public static String gethhDate() {

        SimpleDateFormat df = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String strTemp = df.format(date);
        return strTemp.substring(11, 13);
    }



    /**
     * 得到当前时间-分钟 mm<br>
     * 例如 03--(2007-11-02 15:03:50)<br>
     */
    public static String getmmDate() {

        SimpleDateFormat df = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String strTemp = df.format(date);
        return strTemp.substring(14, 16);
    }



    /**
     * "2006-01-12 16:30"
     *
     * @param sDate
     * @return 2006年1月12 星期四 16:30:20
     */
    public static String getFullDateWeekTime(String sDate) {

        try {
            String formater = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format = getSimpleDateFormat(formater);
            Date date = format.parse(sDate);
            // format.applyPattern("yyyy年M月d日 E HH:mm:ss");
            format.applyPattern("yyyy-MM-dd日 E");
            return format.format(date);
        } catch (Exception ex) {
            System.out.println("TimeUtil  getFullDateWeekTime" + ex.getMessage());
            return "";
        }
    }



    /**
     * "2006-01-12 16:30"
     *
     * @return 2006年1月12 星期四 16:30:20
     */
    public static String getFullDateWeekTimeNow() {

        String sDate = getCurrentAllDate();
        try {
            String formater = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat format = getSimpleDateFormat(formater);
            Date date = format.parse(sDate);
            // format.applyPattern("yyyy年M月d日 E HH:mm:ss");
            format.applyPattern("yyyy-MM-dd E");
            return format.format(date);
        } catch (Exception ex) {
            System.out.println("TimeUtil  getFullDateWeekTime" + ex.getMessage());
            return "";
        }
    }



    /**
     * 得到当前时间-数字<br>
     * 20071102150350
     *
     * @return String
     */
    public static String getCurrentAllIntDate() {

        SimpleDateFormat df = getSimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return df.format(date);
    }



    public static String getCurrentAllIntDateMs() {

        SimpleDateFormat df = getSimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 得到当前时间-秒 ss<br>
     * 例如 50--(2007-11-02 15:03:50)<br>
     */
    public static String getssDate() {

        SimpleDateFormat df = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String strTemp = df.format(date);
        return strTemp.substring(17, 19);
    }



    /**
     * 功能：得到当前时间yyyy-MM-dd<br>
     * 例如 2007-11-02<br>
     *
     * @return String
     */
    public static String getCurrentShotDate() {

        SimpleDateFormat df = getSimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 功能：得到当前时间yyyy-MM-dd<br>
     * 例如 2007-11-02<br>
     *
     * @return String
     */
    public static String getCurrentShotTime() {

        SimpleDateFormat df = getSimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 功能：得到30天前时间yyyy-MM-dd<br>
     * 例如 2007-11-02<br>
     *
     * @return String
     */
    public static String getThirtyDaysAgoDate() {

        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -30);
        return sdf.format(cal.getTime());
    }



    /**
     * 功能：得到昨天时间yyyy-MM-dd<br>
     * 例如 2007-11-02<br>
     *
     * @return String
     */
    public static String getYesterdayDate() {

        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }



    /**
     * 功能：得到昨天时间yyyyMMdd<br>
     * 例如 20071102<br>
     *
     * @return String
     */
    public static String getYesDate() {

        SimpleDateFormat sdf = getSimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return sdf.format(cal.getTime());
    }



    public static long fromDateStringToLong(String inVal) { // 此方法计算时间毫秒
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = inputFormat.parse(inVal); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime(); // 返回毫秒数
    }



    /**
     * 功能：文件操作--【返回文件后缀名(如果没有.则原string返回)】<br>
     * 例如：StringUtil.getFileLastdot("test.doc") 返回 ".doc"<br>
     * 例如：StringUtil.getFileLastdot("test") 返回 "test"<br>
     * 例如：StringUtil.getFileLastdot("test.") 返回 "."<br>
     */
    public static String getFileLastdot(String temp) {

        int begin = temp.lastIndexOf(".");
        begin = begin == -1 ? 0 : begin;
        return temp.substring(begin);
    }



    /**
     * String前截取(从第一位开始,是否匹配)<br>
     * 例如 StringUtil.getIndexName("avttt","av") 返回 "ttt"<br>
     * 例如 StringUtil.getIndexName("bvttt","av") 返回 "bvttt"<br>
     * 例如 StringUtil.getIndexName("bvttt","t") 返回 "bvttt"<br>
     * 例如 StringUtil.getIndexName("bvttt","b") 返回 "vttt"<br>
     */
    public static String getIndexName(String str, String temp) {

        int begin = str.indexOf(temp);
        if (begin != 0) {
            return str;
        } else {
            begin = temp.length();
        }
        return str.substring(begin);
    }



    /**
     * String前截取(从匹配位开始)<br>
     * 例如 StringUtil.getIndexName1("avttt","av") 返回 "ttt"<br>
     * 例如 StringUtil.getIndexName1("bvttt","vt") 返回 "tt"<br>
     * 例如 StringUtil.getIndexName1("bvttt","ab") 返回 "bvttt"<br>
     */
    public static String getIndexName1(String str, String temp) {

        int begin = str.indexOf(temp);
        if (begin == -1) {
            return str;
        } else {
            return str.substring(begin + temp.length());
        }
    }



    // 取得系统当前时间前n个月的相对应的一天
    public static String getNMonthBeforeCurrentDay(int n) {

        Calendar c = Calendar.getInstance();
        c.add(c.MONTH, -n);
        return c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DATE);
    }



    /**
     * String后截取(从最后一位开始,是否匹配)<br>
     * 例如 StringUtil.getLastOne("begtto","to") 返回 "begt"<br>
     * 例如 StringUtil.getLastOne("begtto","vt") 返回 "begtto"<br>
     * 例如 StringUtil.getLastOne("begtto","o") 返回 "begtt"<br>
     * 例如 StringUtil.getLastOne("begtto","t") 返回 "begtto"<br>
     */
    public static String getLastOne(String str, String temp) {

        int end = str.lastIndexOf(temp);
        if (end == -1) { // 没有找到
            return str;
        } else if (str.length() == end + temp.length()) {
            return str.substring(0, end);
        } else {
            return str;
        }
    }



    /**
     * String后截取(从否匹配位开始)<br>
     * 例如 StringUtil.getLastOne1("begtto","to") 返回 "begt"<br>
     * 例如 StringUtil.getLastOne1("begtto","t") 返回 "begt"<br>
     * 例如 StringUtil.getLastOne1("begtto","tt") 返回 "beg"<br>
     * 例如 StringUtil.getLastOne1("begtto","bb") 返回 "begtto"<br>
     */
    public static String getLastOne1(String str, String temp) {

        int end = str.lastIndexOf(temp);
        if (end == -1) { // 没有找到
            return str;
        } else {
            return str.substring(0, end);
        }
    }



    /**
     * 判断文件格式
     */
    public static boolean isRightFileName(String temp) {

        if (temp.indexOf(".rar") >= 0 || temp.indexOf(".zip") >= 0 || temp.indexOf(".mp3") >= 0 || temp.indexOf(".jpg") >= 0 || temp.indexOf(".jpeg") >= 0 || temp.indexOf(".doc") >= 0 || temp.indexOf(".xls") >= 0 || temp.indexOf(".png") >= 0 || temp.indexOf(".gif") >= 0 || temp.indexOf(".swf") >= 0 || temp.indexOf(".asf") >= 0 || temp.indexOf(".wmv") >= 0 || temp.indexOf(".rm") >= 0 || temp.indexOf(".rmvb") >= 0 || temp.indexOf(".mepg") >= 0 || temp.indexOf(".avi") >= 0 || temp.indexOf(".mpg") >= 0 || temp.indexOf(".mov") >= 0 || temp.indexOf(".rmvb") >= 0) {
            return true;
        }
        return false;
    }



    /**
     * 判断文件格式
     */
    public static boolean isRightImageEx(String temp) {

        if (temp.indexOf(".jpg") >= 0 || temp.indexOf(".jpeg") >= 0 || temp.indexOf(".png") >= 0 || temp.indexOf(".gif") >= 0 || temp.indexOf(".bmp") >= 0) {
            return true;
        }
        return false;
    }



    public static String encodeHTML(String s) {

        return encodeHTML(s, false);
    }



    public static boolean isNullOrEmpty(String s) {

        if (s == null || s.equalsIgnoreCase("null") || s == "" || s.trim().length() == 0)
            return true;
        else
            return false;
    }



    public static boolean isBlank(Map map) {

        if (map == null || map.size() == 0) {
            return true;
        } else {
            return false;
        }
    }



    public static boolean isNotBlank(Map map) {

        return !isBlank(map);
    }



    public static boolean isBlank(List list) {

        if (list == null || list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }



    public static boolean isNotBlank(List list) {

        return !isBlank(list);
    }



    public static boolean isBlank(String strIn) {

        if ((strIn == null) || (strIn.trim().equals("")) || (strIn.equalsIgnoreCase("null")) || (strIn.equalsIgnoreCase("*")) || (strIn.equalsIgnoreCase("undefined")) || (strIn.equalsIgnoreCase("N/A")) || strIn.length() == 0) {
            return true;
        } else {
            return false;
        }
    }



    public static boolean isNotBlank(String strIn) {

        return !isBlank(strIn);
    }



    public static String[] split(String source, String delim) {

        if (StringUtil.isBlank(source)) {
            return new String[0];
        } else if (StringUtil.isBlank(delim)) {
            return new String[]{source};
        } else {
            return source.split(delim);
        }
    }



    public static String objToString(Object[] obj) {

        String strTemp = "";
        for (int i = 0; i < obj.length; i++) {
            strTemp = strTemp + obj[i] + ";";
        }
        return strTemp;
    }



    public static String encodeHTML(String s, boolean encodeAmpersands) {

        if (isNullOrEmpty(s)) {
            return "";
        }
        StringBuffer sb = new StringBuffer(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("&quot;");
                    break;
                case '&':
                    if (encodeAmpersands) {
                        sb.append("&amp;");
                    } else {
                        sb.append(c);
                    }
                    break;
                case '\'':
                    sb.append("&#39;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }



    /**
     * 去掉html所有标签
     *
     * @param htmlStr
     * @return String
     */
    public static String htmlToStr(String htmlStr) {

        String result = "";
        boolean flag = true;
        if (htmlStr == null) {
            return null;
        }
        char[] a = htmlStr.toCharArray();
        int length = a.length;
        for (int i = 0; i < length; i++) {
            if (a[i] == '<') {
                flag = false;
                continue;
            }
            if (a[i] == '>') {
                flag = true;
                continue;
            }
            if (flag == true) {
                result += a[i];
            }
        }
        return result.toString();
    }



    public static String html2Textp(String html) {

        html.replace("\"", "\'");
        html = replacefromto(html, "<p", ">");
        html = replacefromto(html, "</p", ">");
        html = replacefromto(html, "<P", ">");
        html = replacefromto(html, "</P", ">");
        return html;
    }



    public static String html2Text(String html) {

        html.replace("\"", "\'");
        html = replacefromto(html, "<TABLE", ">");
        html = replacefromto(html, "</TABLE", ">");
        html = replacefromto(html, "<table", ">");
        html = replacefromto(html, "</table", ">");
        html = replacefromto(html, "<TBODY", ">");
        html = replacefromto(html, "</TBODY", ">");
        html = replacefromto(html, "<tbody", ">");
        html = replacefromto(html, "</tbody", ">");
        // html = replacefromto(html,"<DIV",">");
        // html = replacefromto(html,"</DIV",">");
        // html = replacefromto(html,"<div",">");
        // html = replacefromto(html,"</div",">");
        html = replacefromto(html, "<b", ">");
        html = replacefromto(html, "</b", ">");
        html = replacefromto(html, "<p", ">");
        html = replacefromto(html, "</p", ">");
        html = replacefromto(html, "<P", ">");
        html = replacefromto(html, "</P", ">");
        html = replacefromto(html, "<img", ">");
        html = replacefromto(html, "<input", ">");
        html = replacefromto(html, "<span", ">");
        html = replacefromto(html, "</span", ">");
        html = replacefromto(html, "<SPAN", ">");
        html = replacefromto(html, "</SPAN", ">");
        html = replacefromto(html, "<TR", ">");
        html = replacefromto(html, "</TR", ">");
        html = replacefromto(html, "<tr", ">");
        html = replacefromto(html, "</tr", ">");
        html = replacefromto(html, "<TD", ">");
        html = replacefromto(html, "</TD", ">");
        html = replacefromto(html, "<td", ">");
        html = replacefromto(html, "</td", ">");
        html = replacefromto(html, "<A", ">");
        html = replacefromto(html, "</A", ">");
        html = replacefromto(html, "<a", ">");
        html = replacefromto(html, "</a", ">");
        html = replacefromto(html, "<SCRIPT", "SCRIPT>");
        html = replacefromto(html, "</script", "script>");
        html = replacefromto(html, "<FONT", ">");
        html = replacefromto(html, "</FONT", ">");
        html = replacefromto(html, "<font", ">");
        html = replacefromto(html, "</font", ">");
        return html;
    }



    public static String replacefromto(String strHtml, String fromStr, String toStr) {

        while (strHtml.indexOf(fromStr) >= 0) {
            int beginInt = strHtml.indexOf(fromStr);
            String str1 = "";
            String str2 = "";
            str1 = strHtml.substring(0, strHtml.indexOf(fromStr));
            // System.out.println(beginInt);
            // System.out.println(strHtml.indexOf(toStr,beginInt));
            if (strHtml.indexOf(toStr, beginInt) >= 0) { // 能够找到结束标签
                str2 = strHtml.substring(strHtml.indexOf(toStr, beginInt) + toStr.length());
                strHtml = str1 + str2;
            } else { // 能够找到结束标签
                strHtml = strHtml.replace(fromStr, "");
            }
        }
        return strHtml;
    }



    /**
     * findfromto("vvv1234fffgg", "vv", "g") 返回v1234fff
     *
     * @param strHtml
     * @param fromStr
     * @param toStr
     * @return
     */
    public static String findfromto(String strHtml, String fromStr, String toStr) {

        int beginInt = strHtml.indexOf(fromStr);
        if (beginInt < 0)
            return ""; // 没有找到直接返回
        int endInt = strHtml.indexOf(toStr, beginInt + fromStr.length());
        if (endInt < 0)
            return ""; // 没有找到直接返回
        strHtml = strHtml.substring(beginInt + fromStr.length(), endInt);
        return strHtml;
    }



    public static long resetTimeHHmm(String timeHHmm) throws Exception {

        SimpleDateFormat ftime = getSimpleDateFormat("HH:mm");
        Date time = ftime.parse(timeHHmm);
        Calendar timeCl = Calendar.getInstance();
        timeCl.setTime(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.set(Calendar.HOUR_OF_DAY, timeCl.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, timeCl.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }



    // 判断是否是整数 >=0
    public static boolean isNumeric(String s) {

        if (isNotBlank(s))
            return s.matches("^[0-9]*$");
        else
            return false;
    }



    // 判断是否是整数 >=0
    public static boolean isNotNumeric(String s) {

        return !isNumeric(s);
    }



    // 判断小数，与判断整型的区别在与d后面的小数点（红色）
    public static boolean isNumber(String s) {

        if (isNotBlank(s))
            return s.matches("[\\d.]+");
        else
            return false;
    }



    // 判断传递来的是否是数字
    public static int checkID(String s) {

        if ((s == null) || (s.length() == 0) || !s.matches("^[0-9]*$")) {
            return 0;
        } else {
            if (s.length() < 10) {
                return Integer.parseInt(s);
            } else {
                return 0;
            }
        }
    }



    // 判断传递来的是否是字符串
    public static String checkString(String s) {

        if ((s == null) || (s.length() == 0) || s.matches("^[0-9]*$")) {
            return "";
        } else {
            return s;
        }
    }



    public static String getObjValue(Object[] obj) {

        return getObjValue(obj, ",", null);
    }



    // 根据obj的长度返回("name,password")
    public static String getObjValue(Object[] obj, String cut, String round) {

        StringBuffer sb = new StringBuffer();
        round = StringUtil.checkString(round);
        if (obj != null && obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                String str = String.valueOf(obj[i]).trim();
                if (StringUtil.isBlank(str)) {
                    continue;
                }
                if (sb.length() > 0) {
                    sb.append(cut);
                }
                sb.append(round);
                sb.append(str);
                sb.append(round);
            }
        }
        return sb.toString();
    }



    // 根据obj的长度返回?,?
    public static String getObjParam(Object[] obj) {

        String strTmp = "";
        if (obj != null && obj.length > 0) {
            for (int i = 0; i < obj.length; i++) {
                if (i == 0)
                    strTmp = "?";
                else
                    strTmp = strTmp + ",?";
            }
        }
        return strTmp;
    }



    public static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }



    /**
     * isChinese("zhongguo"); false isChinese("中国2"); true isChinese("中国"); true
     *
     * @param strName
     */
    public static boolean isChinese(String strName) {

        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c) == true) {
                // System.out.println(isChinese(c));
                return true;
            } else {
                // System.out.println(isChinese(c));
                return false;
            }
        }
        return false;
    }



    /**
     * 比较2个数组内容是否一致
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean compareObject(Object[] a, Object[] b) {

        Object[] aa = new Object[a.length];
        Object[] bb = new Object[b.length];
        System.arraycopy(a, 0, aa, 0, a.length);
        System.arraycopy(b, 0, bb, 0, b.length);
        Arrays.sort(aa);
        Arrays.sort(bb);
        return Arrays.equals(aa, bb);
    }



    /**
     * 数组组合 a+b
     *
     * @param a
     * @param b
     * @return
     */
    public static String[] appendString(String[] a, String[] b) {

        if (StringUtil.isBlank(a))
            a = new String[0];
        if (StringUtil.isBlank(b))
            b = new String[0];
        String[] obj = new String[a.length + b.length];
        System.arraycopy(a, 0, obj, 0, a.length);
        System.arraycopy(b, 0, obj, a.length, b.length);
        return obj;
    }



    /**
     * 数组组合 a+b
     *
     * @param a
     * @param b
     * @return
     */
    public static String[] appendString(String[] a, String b) {

        if (StringUtil.isBlank(a))
            a = new String[0];
        String[] obj = new String[a.length + 1];
        System.arraycopy(a, 0, obj, 0, a.length);
        obj[a.length] = b;
        return obj;
    }



    /**
     * 数组组合 a+b
     *
     * @param a
     * @param b
     * @return
     */
    public static String[] appendString(String a, String[] b) {

        String[] obj = new String[b.length + 1];
        obj[0] = a;
        System.arraycopy(b, 0, obj, 1, b.length);
        return obj;
    }



    /**
     * 数组组合 a+b
     *
     * @param a
     * @param b
     * @return
     */
    public static Object[] appendObject(Object[] a, Object b) {

        if (StringUtil.isBlank(a))
            a = new Object[0];
        Object[] obj = new Object[a.length + 1];
        System.arraycopy(a, 0, obj, 0, a.length);
        obj[a.length] = b;
        return obj;
    }



    /**
     * 数组组合 a+b
     *
     * @param a
     * @param b
     * @return
     */
    public static Object[] appendObject(Object[] a, Object[] b) {

        if (StringUtil.isBlank(a))
            a = new Object[0];
        if (StringUtil.isBlank(b))
            b = new Object[0];
        Object[] obj = new Object[a.length + b.length];
        System.arraycopy(a, 0, obj, 0, a.length);
        System.arraycopy(b, 0, obj, a.length, b.length);
        // //Debug.printlnArray(obj);
        return obj;
    }



    /**
     * sunsummer15
     * <p>
     * return srcArray-removeArray
     */
    public static String[] removeArray(String[] srcArray, String[] removeArray) {

        List<String> list = new ArrayList<String>();
        if (StringUtil.isBlank(srcArray) || StringUtil.isBlank(removeArray)) {
            return srcArray;
        }
        for (String str : srcArray) {
            list.add(str);
        }
        for (String str : removeArray) {
            // System.out.println(str+" "+list.size());
            list.remove(str);
        }
        return (String[]) list.toArray(new String[list.size()]);
    }



    /**
     * sunsummer15
     *
     * @param a
     * @param b
     * @return
     */
    public static void appendListToA(List a, List b) {

        if (StringUtil.isBlank(a))
            a = new ArrayList();
        if (StringUtil.isBlank(b))
            b = new ArrayList();
        for (Object obj : b) {
            a.add(obj);
        }
    }



    public static byte[] appendByteArray(byte[]... array) {

        int count = 0;
        for (byte[] bb : array) {
            count += bb.length;
        }
        int index = 0;
        byte[] res = new byte[count];
        for (byte[] bb : array) {
            for (byte b : bb) {
                res[index] = b;
                index++;
            }
        }
        return res;
    }



    /**
     * 求两个时间之间的天数查
     *
     * @param data1
     * @param data2
     * @return
     */
    public static long dayNum(String data1, String data2) {

        DateFormat df = getSimpleDateFormat("yyyy-MM-dd");
        long days = 0;
        try {
            Date d1 = df.parse(data1);
            Date d2 = df.parse(data2);
            long diff = d1.getTime() - d2.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (Exception e) {
        }
        return days;
    }



    /**
     * 求两个时间之间的天数查
     *
     * @param data1
     * @param data2
     * @return
     */
    public static int dayNumMin(String data1, String data2) {

        DateFormat df = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int min = 0;
        try {
            Date d1 = df.parse(data1);
            Date d2 = df.parse(data2);
            long diff = d1.getTime() - d2.getTime();
            min = (int) (diff / (1000 * 60));
        } catch (Exception e) {
        }
        return min;
    }



    /**
     * 天数+-
     *
     * @return
     */
    public static String dayDiff2(String datetime, int days) {

        SimpleDateFormat format = getSimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(datetime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        date = cal.getTime();
        // System.out.println("3 days after(or before) is
        // "+format.format(date));
        cal = null;
        return format.format(date);
    }



    /**
     * 验证是否formater格式的Date sunsummer15
     *
     * @param sDate
     * @param formater
     * @return
     */
    public static boolean isDate(String sDate, String formater, boolean lenient) {

        if (sDate.endsWith(".0")) {
            sDate = sDate.substring(0, sDate.length() - 2);
        }
        if (isBlank(sDate)) {
            return false;
        }
        if (isBlank(formater)) {
            formater = "yyyy-MM-dd HH:mm:ss";
        }
        if (!lenient && formater.length() != sDate.length()) {
            return false;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(formater);
            format.setLenient(lenient);
            format.parse(sDate);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }



    /**
     * 验证是否formater格式的Date sunsummer15
     *
     * @param sDate
     * @param formater
     * @return
     */
    public static boolean isDate(String sDate, String formater) {

        return StringUtil.isDate(sDate, formater, false);
    }



    public static List changeDate(List<Map> list, String field, String formaterIn, String formaterOut, boolean lenient) {

        if (isBlank(list)) {
            return list;
        }
        for (Map map : list) {
            map.put(field, changeDate(getMapValue(map, field), formaterIn, formaterOut, lenient));
        }
        return list;
    }



    public static String changeDate(String sDate, String formaterIn, String formaterOut, boolean lenient) {

        String res = "";
        if (StringUtil.isBlank(sDate)) {
            return res;
        }
        if (isBlank(sDate)) {
            return res;
        }
        if (isBlank(formaterIn)) {
            formaterIn = "yyyy-MM-dd HH:mm:ss";
        }

        if (isBlank(formaterOut)) {
            formaterOut = "yyyy-MM-dd HH:mm:ss";
        }
        String[] array = sDate.split("[.]");
        if (array.length == 2 && array[1].length() <= 3) {
            sDate = sDate.substring(0, sDate.length() - (array[1].length() + 1));
        }
        if (isBlank(formaterOut)) {
            formaterOut = "yyyy-MM-dd HH:mm:ss";
        }
        if (!StringUtil.isDate(sDate, formaterIn)) {
            //Debug.printlnErr("changeDate err 1:" + sDate + " " + formaterIn + " " + formaterOut);
            return res;
        }
        try {
            SimpleDateFormat in = new SimpleDateFormat(formaterIn);
            SimpleDateFormat out = new SimpleDateFormat(formaterOut);
            in.setLenient(lenient);
            Date date = in.parse(sDate);
            res = out.format(date);
        } catch (Exception ex) {
            //Debug.printlnErr("changeDate err 2:" + sDate + " " + formaterIn + " " + formaterOut);
        }
        return res;
    }



    /**
     * 天数+-
     *
     * @return
     */
    public static String dayDiff(String datetime, int days) {

        SimpleDateFormat format = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(datetime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, days);
        date = cal.getTime();
        // System.out.println("3 days after(or before) is
        // "+format.format(date));
        cal = null;
        return format.format(date);
    }



    /**
     * start <= time < end
     *
     * @param starttime
     * @param endtime
     * @param time
     * @return
     */
    public static boolean ssBetween(String starttime, String endtime, String time) {

        int d1 = starttime.compareTo(time);
        int d2 = endtime.compareTo(time);
        boolean res = (d1 == 0) || (d1 < 0 && d2 > 0);
        // System.out.println(d1+"|"+d2+"|"+res+"|"+starttime+"|"+endtime+"|"+time);
        return res;
    }



    /**
     * 功能说明:秒+-
     *
     * @return
     */
    public static String ssDiff(String datetime, long nums) {

        Date date = new Date(stringToDate(datetime).getTime() + 1000 * nums);
        return dateToString(date);
    }



    /**
     * @param dateString 代表日期的字符串
     * @return 格式化成yyyy-MM-dd HH:mm:ss 字符串
     */
    public static String getformatDateString(String dateString) {

        SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = null;
        try {
            s = sdf.format((sdf.parse(dateString)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return s;
    }



    /**
     * URL检查<br>
     * <br>
     *
     * @param pInput 要检查的字符串<br>
     * @return boolean 返回检查结果<br>
     */
    public static boolean isUrl(String pInput) {

        if (pInput.indexOf("http://", 0) >= 0)
            return true;
        if (pInput.indexOf("https://", 0) >= 0)
            return true;
        return false;
    }



    /**
     * String转Date
     *
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString) {
        // String dateString = "2012-12-06 01-01-09";
        Date date = null;
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.getMessage();
        }
        return date;
    }



    public static String dateToString(Date date) {

        String str = null;
        try {
            str = (getSimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
        } catch (Exception e) {
            e.getMessage();
        }
        return str;
    }



    /**
     * url处理
     *
     * @param qStr
     * @param pagenum 例子： pagenum=222 -> "" pagenum=222&grade=11&haha=444 -> grade=11&haha=444 grade=11&pagenum=222&haha=444 -> grade=11&haha=444 grade=11&haha=444&pagenum=22 -> grade=11&haha=444
     * @return
     */
    public static String doQuertString(String qStr, String pagenum) {

        String strCon = qStr;
        if (StringUtil.isNotBlank(strCon)) {
            int begin = strCon.indexOf(pagenum);
            int end = 0;
            if (begin == 0) { // 开头找到
                end = strCon.indexOf("&", begin + pagenum.length());
                if (end < 0) { // 唯一
                    strCon = "";
                } else { // 有结尾
                    strCon = strCon.substring(end + 1, strCon.length());
                }
            } else if (begin > 0) { // 中间或结尾找到
                end = strCon.indexOf("&", begin + pagenum.length());
                if (end < 0) { // 结尾
                    if (String.valueOf(strCon.charAt(begin - 1)).equalsIgnoreCase("&")) {
                        strCon = strCon.substring(0, begin - 1);
                    } else {
                        strCon = strCon.substring(0, begin);
                    }
                } else { // 中间
                    strCon = strCon.substring(0, begin) + strCon.substring(end + 1, strCon.length());
                }
            }
        } else {
            strCon = "";
        }
        if (StringUtil.isNotBlank(strCon)) {
            if (!String.valueOf(strCon.charAt(0)).equalsIgnoreCase("&")) {
                strCon = "&" + strCon;
            }
        }
        return strCon;
    }



    // 80.07999877929689 - > 80.08
    public static String floatFix2(String floatTmp) {

        DecimalFormat df = new DecimalFormat("#.##");
        double d = 0.00;
        try {
            d = Double.parseDouble(floatTmp);
        } catch (Exception e) {
            //Debug.printlnErr("floatTmp:" + floatTmp + "转换错误");
        }
        return df.format(d);
    }



    /**
     * 功能：输入0.999 --> 转换成百分比 --> 99.9%
     *
     * @param d : double
     * @author sunqingbiao
     */
    public static String change2percent(String d) {

        double dd = (double) Double.parseDouble(d);
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(3);// 这个3的意识是保存结果到小数点后几位，但是特别声明：这个结果已经先＊100了。
        return nf.format(dd);
    }



    public static String format(Date date, String format) {

        if (date == null) {
            return "";
        }
        DateFormat df = getSimpleDateFormat(format);
        return df.format(date);
    }



    public static long format(String time, String format) {

        if (isBlank(time) || isBlank(format)) {
            return 0;
        }
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = getSimpleDateFormat(format);
        try {
            date = inputFormat.parse(time); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime(); // 返回毫秒数
    }



    /**
     * 怎么使float保留两位小数或多位小数
     *
     * @param value
     * @param length 保留位数
     * @return
     */
    public static Float formatFloat(float value, int length) {

        BigDecimal b = new BigDecimal(value);
        return b.setScale(length, BigDecimal.ROUND_HALF_UP).floatValue();
    }



    public static String convertStreamToString(InputStream is) throws IOException {

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }



    public static String[] getMapKeyArray(Map map) {

        return (String[]) map.keySet().toArray(new String[0]);
    }



    public static Object[] getMapKeyValue(Map map, Object[] field) {

        Object[] value = new Object[field.length];
        for (int i = 0; i < field.length; i++) {
            value[i] = map.get(field[i]);
        }
        return value;
    }



    public static String getMapValue(Map map, Object key) {

        String res = "";
        if (map == null || map.size() == 0 || StringUtil.isBlank(key))
            return res;
        res = StringUtil.checkNull(String.valueOf(map.get(key))).trim();
        if (StringUtil.isBlank(res)) {
            res = StringUtil.checkNull(String.valueOf(map.get(key.toString().toUpperCase()))).trim();
        }
        if (StringUtil.isBlank(res)) {
            res = StringUtil.checkNull(String.valueOf(map.get(key.toString().toLowerCase()))).trim();
        }
        return res;
    }



    public static String[] getMapValueArray(Map map, Object key) {

        return getMapValueArray(map, key, ",");
    }



    public static String[] getMapValueArray(Map map, Object key, String arrayCut) {

        List<String> list = new ArrayList<String>();
        String[] array = split(StringUtil.getMapValue(map, key), arrayCut);
        for (String value : array) {
            if (StringUtil.isNotBlank(value) && !list.contains(value)) {
                list.add(value);
            }
        }
        String[] res = (String[]) list.toArray(new String[list.size()]);
        if (StringUtil.isBlank(res)) {
            res = new String[0];
        }
        return res;
    }



    public static double getMapValueDouble(Map map, Object key) {

        if (map == null || map.size() == 0 || StringUtil.isBlank(key))
            return 0d;
        try {
            return StringUtil.getDouble(String.valueOf(map.get(key)));
        } catch (Exception e) {
            return 0d;
        }
    }



    public static long getMapValueLong(Map map, Object key) {

        if (map == null || map.size() == 0 || StringUtil.isBlank(key))
            return 0;
        try {
            return StringUtil.getLong(String.valueOf(map.get(key)));
        } catch (Exception e) {
            return 0;
        }
    }



    public static int getMapValueInt(Map map, Object key) {

        if (map == null || map.size() == 0 || StringUtil.isBlank(key))
            return 0;
        try {
            return StringUtil.getInteger(String.valueOf(map.get(key)));
        } catch (Exception e) {
            return 0;
        }
    }



    /**
     * 将String转为int
     *
     * @param str
     * @return
     */
    public static int getInteger(String str) {

        int i = 0;
        try {
            i = Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return i;
        }
        return i;
    }



    /**
     * 将String转为int
     *
     * @param str
     * @return
     */
    public static int getInteger(Object str) {

        int i = 0;
        try {
            i = Integer.valueOf(str.toString());
        } catch (NumberFormatException e) {
            return i;
        }
        return i;
    }



    /**
     * 将String转为int
     *
     * @param str
     * @return
     */
    public static long getLong(String str) {

        long i = 0;
        try {
            i = Long.valueOf(str);
        } catch (NumberFormatException e) {
            return i;
        }
        return i;
    }



    public static double getDouble(String str) {

        double d = 0d;
        if (StringUtil.isBlank(str))
            return d;
        try {
            d = Double.valueOf(str);
        } catch (Throwable e) {
            return d;
        }
        return d;
    }



    /**
     * @param text
     * @param c    "asfsda;das", ";"
     * @return asfsda
     */
    public static String getInTextBefore(String text, String c) {

        return getInTextBefore(text, c, "");
    }



    /**
     * @param text
     * @param c    "asfsda;das", ";"
     * @return das
     */
    public static String getInTextAfter(String text, String c) {

        return getInTextAfter(text, c, "");
    }



    public static String getInTextBetween(String text, String startStr, String endStr) {

        return StringUtil.getInTextAfter(StringUtil.getInTextBefore(text, endStr), startStr);
    }



    public static String getInTextAfterLast(String text, String c) {

        return getInTextAfterLast(text, c, "");
    }



    /**
     * @param text
     * @param c    "asfsda;das", ";"
     * @return das
     */
    public static String getInTextAfterLast(String text, String c, String nullStr) {

        text = StringUtil.checkNull(text);
        int i = text.lastIndexOf(c);
        if (i < 0) {
            return nullStr;
        } else {
            return text.substring(i + c.length(), text.length());
        }
    }



    /**
     * @param text
     * @param c    "asfsda;das", ";"
     * @return asfsda
     */
    public static String getInTextBefore(String text, String c, String nullStr) {

        text = StringUtil.checkNull(text);
        int i = text.indexOf(c);
        if (i < 0) {
            return nullStr;
        } else {
            return text.substring(0, i);
        }
    }



    /**
     * @param text
     * @param c    "asfsda;das", ";"
     * @return das
     */
    public static String getInTextAfter(String text, String c, String nullStr) {

        text = StringUtil.checkNull(text);
        int i = text.indexOf(c);
        if (i < 0) {
            return nullStr;
        } else {
            return text.substring(i + c.length(), text.length());
        }
    }



    /**
     * OBJ内是否存在某元素
     *
     * @param objArray
     * @param obj
     * @param res
     * @return 如果obj内不存在str则返回res 存在 返回""
     */
    public static Object haveObject(Object[] objArray, Object obj, Object res) {

        Object ok = "";
        if (StringUtil.isBlank(objArray) || StringUtil.isBlank(obj))
            return ok;
        for (Object o : objArray) {
            if (o.equals(obj)) {
                ok = obj;
                break;
            }
        }
        return ok;
    }



    public static String removeBlanket(String str) {

        return StringUtil.checkNull(str).replace(" ", "");
    }



    public static boolean haveStringIs(String[] strArray, String str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        str = StringUtil.removeBlanket(str);
        for (String s : strArray) {
            if (str.equalsIgnoreCase(StringUtil.removeBlanket(s))) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringIs(String[] strArray, String[] str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        for (String s : str) {
            if (StringUtil.haveStringIs(strArray, s)) {
                return true;
            }
        }
        return false;
    }



    public static int haveStringIsR(String[] strArray, String str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return -1;
        str = StringUtil.removeBlanket(str);
        for (int i = 0; i < strArray.length; i++) {
            String s = StringUtil.removeBlanket(strArray[i]);
            if (str.equalsIgnoreCase(s)) {
                return i;
            }
        }
        return -1;
    }



    public static boolean haveStringStartWith(String[] strArray, String[] str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        for (String s : str) {
            if (StringUtil.haveStringStartWith(strArray, s)) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringStartWith(String[] strArray, String str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        str = StringUtil.removeBlanket(str);
        for (String s : strArray) {
            if (str.toLowerCase().startsWith(StringUtil.removeBlanket(s).toLowerCase())) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringEndWith(String[] strArray, String[] str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        for (String s : str) {
            if (StringUtil.haveStringEndWith(strArray, s)) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringEndWith(String[] strArray, String str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        str = StringUtil.removeBlanket(str);
        for (String s : strArray) {
            if (str.toLowerCase().endsWith(StringUtil.removeBlanket(s).toLowerCase())) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringLikeWith_NoTrim(String[] strArray, String[] str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        for (String s : str) {
            if (StringUtil.haveStringLikeWith_NoTrim(strArray, s)) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringLikeWith(String[] strArray, String[] str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        for (String s : str) {
            if (StringUtil.haveStringLikeWith(strArray, s)) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringLikeWith_NoTrim(String[] strArray, String str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        for (String s : strArray) {
            if (str.toLowerCase().indexOf(s.toLowerCase()) > -1) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringLikeWith(String[] strArray, String str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        str = StringUtil.removeBlanket(str);
        for (String s : strArray) {
            if (str.toLowerCase().indexOf(StringUtil.removeBlanket(s).toLowerCase()) > -1) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringLikeWith_Desc(String[] strArray, String[] str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        for (String s : str) {
            if (StringUtil.haveStringLikeWith_Desc(strArray, s)) {
                return true;
            }
        }
        return false;
    }



    public static boolean haveStringLikeWith_Desc(String[] strArray, String str) {

        if (StringUtil.isBlank(strArray) || StringUtil.isBlank(str))
            return false;
        str = StringUtil.removeBlanket(str);
        for (String s : strArray) {
            if (StringUtil.removeBlanket(s).toLowerCase().indexOf(str.toLowerCase()) > -1) {
                return true;
            }
        }
        return false;
    }



    /**
     * OBJ内是否不存在某元素
     *
     * @param objArray
     * @param obj
     * @return 不存在 TRUE
     */
    public static boolean haveObjectNot(Object[] objArray, Object obj) {

        return !haveObjectIs(objArray, obj);
    }



    // 取得系统当日时间+N天后时间
    public static String getCurrentIntDate(int day) {

        Calendar c = Calendar.getInstance();
        c.add(c.DATE, day);
        SimpleDateFormat df = getSimpleDateFormat("yyyyMMdd");
        Date date = c.getTime();
        return df.format(date);
    }



    public static String getAddDate(String startTime, int addMinute) {

        return getAddDate(startTime, Calendar.MINUTE, addMinute);
    }



    public static String getAddDate(String startTime, int calenderType, int addMinute) {

        if (StringUtil.isBlank(startTime)) {
            startTime = StringUtil.getCurrentAllDate();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 小写的hh取得12小时，大写的HH取的是24小时
        Date start = null;
        try {
            start = df.parse(startTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.add(calenderType, addMinute);
            start = cal.getTime();
        } catch (Exception e) {
            start = new Date();
        }
        return df.format(start);
    }



    /**
     * 获取上个月的今天
     *
     * @return String : prev_month 例如20130223
     * @author sunqingbiao
     */
    public static String getPrevmonth() {

        String currentdate = new StringUtil().getCurrentIntDate();// 当前日期
        int month = Integer.valueOf(currentdate.substring(4, 6));// 月
        int year = Integer.valueOf(currentdate.substring(0, 4));// 年
        String day = currentdate.substring(6, 8);// 日
        if (month < 2) {
            month = 12;
            year--;
        } else {
            month--;
        }
        String str_i = String.valueOf(month);
        if (str_i.length() < 2) {
            str_i = "0" + str_i;
        }
        String prev_month = String.valueOf(year) + str_i;
        return prev_month;
    }



    /**
     * 获取上个月的日期 例如：201402
     */
    public static String getLastMonth() {

        SimpleDateFormat sdf = getSimpleDateFormat("yyyyMM");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);
        return sdf.format(cal.getTime());
    }



    public static double decimalFormatDouble(String dStr, int totalDigit, int fractionalDigit) {

        return decimalFormatDouble(StringUtil.getDouble(dStr), totalDigit, fractionalDigit);
    }



    public static double decimalFormatDouble(double d, int totalDigit, int fractionalDigit) {

        String doubleStr = decimalFormat(d, totalDigit, fractionalDigit);
        return getDouble(doubleStr);
    }



    // public static String decimalFormat(double d, int totalDigit, int
    // fractionalDigit) {
    //
    // String str = "0";
    // if (String.valueOf(d).equalsIgnoreCase("NaN")) {
    // return str;
    // }
    // DecimalFormat decimalFormat = new DecimalFormat();
    // decimalFormat.setMinimumFractionDigits(fractionalDigit);
    // decimalFormat.setMaximumFractionDigits(fractionalDigit);
    // decimalFormat.setGroupingUsed(false);
    // decimalFormat.setMaximumIntegerDigits(totalDigit - fractionalDigit - 1);
    // decimalFormat.setMinimumIntegerDigits(totalDigit - fractionalDigit - 1);
    // str = decimalFormat.format(d);
    // boolean isMinus = str.startsWith("-");
    // if (isMinus) {
    // str = str.substring(1);
    // }
    // while (str.startsWith("0")) {
    // if (str.startsWith("0.")) {
    // break;
    // }
    // str = str.substring(1);
    // }
    // if (StringUtil.isBlank(str)) {
    // return "0";
    // } else if (isMinus) {
    // return "-" + str;
    // }
    // return str;
    // }
    public static String decimalFormat(double d, int totalDigit, int fractionalDigit) {

        String str = "0";
        if (String.valueOf(d).equalsIgnoreCase("NaN")) {
            return str;
        }
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(fractionalDigit);
        decimalFormat.setMaximumFractionDigits(fractionalDigit);
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setMaximumIntegerDigits(totalDigit - fractionalDigit - 1);
        decimalFormat.setMinimumIntegerDigits(totalDigit - fractionalDigit - 1);
        str = decimalFormat.format(d);
        boolean isMinus = str.startsWith("-");
        if (isMinus) {
            str = str.substring(1);
        }
        while (str.startsWith("0")) {
            if (str.startsWith("0.")) {
                break;
            }
            str = str.substring(1);
        }
        if (StringUtil.isBlank(str)) {
            return "0";
        } else if (isMinus) {
            return "-" + str;
        }
        return str;
    }



    public static String decimalFormat(String dStr, int totalDigit, int fractionalDigit) {

        double d = StringUtil.getDouble(dStr);
        return StringUtil.decimalFormat(d, totalDigit, fractionalDigit);
    }



    /**
     * OBJ内是否存在某元素 fuser-role
     *
     * @param objArray
     * @param obj
     * @return 存在 TRUE
     */
    public static boolean haveObjectIs(Object[] objArray, Object obj) {

        if (StringUtil.isBlank(objArray) || StringUtil.isBlank(obj))
            return false;
        for (Object o : objArray) {
            if (o.equals(obj)) {
                return true;
            }
        }
        return false;
    }



    public static boolean isBlank(Object... obj) {

        if (obj == null || obj.length == 0 || (obj.length == 1 && StringUtil.isBlank(String.valueOf(obj[0])))) {
            return true;
        }
        return false;
    }



    public static boolean isNotBlank(Object... obj) {

        return !isBlank(obj);
    }



    /**
     * 得到两个日期相差的天数
     */
    public static int getBetweenDay(Date date1, Date date2) {

        Calendar d1 = new GregorianCalendar();
        d1.setTime(date1);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(date2);
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        System.out.println("days=" + days);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }



    public static String[] listfield2array(List list, String field) {

        List<String> resList = new ArrayList<String>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                String value = StringUtil.getMapValue(map, field);
                resList.add(value);
            }
        }
        return (String[]) resList.toArray(new String[0]);
    }



    public static String[] initDay(String foramtStr, int count, int dx) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = getSimpleDateFormat(foramtStr);
        String[] days = new String[count];
        c.add(Calendar.MINUTE, -count * dx + 1);
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                c.add(Calendar.MINUTE, dx);
            }
            Date date = c.getTime();
            String timeStr = df.format(date);
            days[i] = timeStr;
        }
        return days;
    }



    public static String unescape(String src) {

        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                // 最后是一个%
                if (pos == src.length() - 1)
                    break;
                // 将%这中后四位的字符以16进制转化成汉字
                if (src.charAt(pos + 1) == 'u') {
                    // 如果是%u执行以下
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    if (src.substring(pos + 1, pos + 3).equals("%u")) {
                        unescape(src.substring(pos + 1));
                        break;
                    }
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }



    public static boolean strIn(String str, String key) {

        return StringUtil.checkNull(str).indexOf(key) >= 0;
    }



    public static String trim(Object obj) {

        return obj.toString().trim();
    }



    public static String[] getArrayByMap(Map map, String... keyArray) {

        List list = new ArrayList();
        for (String key : keyArray) {
            String value = StringUtil.getMapValue(map, key);
            list.add(value);
        }
        return (String[]) list.toArray(new String[keyArray.length]);
    }



    public static Map getAppendMapByList(List<Map> list, String... keyArray) {

        Map map = new LinkedHashMap();
        if (StringUtil.isBlank(list)) {
            return map;
        }
        if (StringUtil.isBlank(keyArray)) {
            keyArray = StringUtil.getMapKeyArray(list.get(0));
        }
        for (String key : keyArray) {
            map.put(key, StringUtil.getObjValue(StringUtil.getAllArrayByList(list, key)));
        }
        return map;
    }



    public static String[] getAllArrayByList(List<Map> list, String key) {

        String[] resArray = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String value = StringUtil.getMapValue(list.get(i), key);
            resArray[i] = value;
        }
        return resArray;
    }



    public static String[] getArrayByList(List<Map> list, String key) {

        LinkedHashMap<String, String> tmpMap = new LinkedHashMap<String, String>();
        if (isNotBlank(list)) {
            for (Map map : list) {
                String value = StringUtil.getMapValue(map, key);
                if (StringUtil.isNotBlank(value)) {
                    tmpMap.put(value, "");
                }
            }
        }
        return (String[]) tmpMap.keySet().toArray(new String[tmpMap.size()]);
    }



    public static String[] getSameArray(String[] a1, String[] a2) {

        Map<String, String> map = new HashMap<String, String>();
        for (String str : a1) {
            map.put(str, "");
        }
        List<String> list = new ArrayList<String>();
        for (String str : a2) {
            if (map.containsKey(str)) {
                list.add(str);
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }



    public static Map<String, Map> getMapFromList(List<Map> list, String key) {

        Map<String, Map> res = new LinkedHashMap<String, Map>();
        for (Map map : list) {
            String keyV = StringUtil.getMapValue(map, key);
            if (StringUtil.isNotBlank(keyV)) {
                res.put(keyV, map);
            }
        }
        return res;
    }



    public static Map<String, String[]> getMapArrayFromList(List<Map> list, String keyField, String arrayField) {

        Map<String, List> map = getMapListFromList(list, keyField);
        Map<String, String[]> res = new LinkedHashMap<String, String[]>();
        for (String str : map.keySet()) {
            res.put(str, StringUtil.getArrayByList(map.get(str), arrayField));
        }
        return res;
    }



    public static Map<String, List> getMapListFromList(List<Map> list, String key) {

        Map<String, List> res = new LinkedHashMap<String, List>();
        for (Map map : list) {
            String keyV = StringUtil.getMapValue(map, key);
            if (!res.containsKey(keyV)) {
                res.put(keyV, new ArrayList());
            }
            res.get(keyV).add(map);
        }
        return res;
    }



    public static Map<String, String> getMapFromListToUpperCase(List<Map> list, String key, String value) {

        Map<String, String> res = new LinkedHashMap<String, String>();
        for (Map map : list) {
            String keyStr = StringUtil.getMapValue(map, key).toUpperCase();
            String valueStr = StringUtil.getMapValue(map, value);
            res.put(keyStr, valueStr);
        }
        return res;
    }



    //
    public static Map<String, String> getMapFromList(List<Map> list, String key, String value) {

        Map<String, String> res = new LinkedHashMap<String, String>();
        for (Map map : list) {
            String keyStr = StringUtil.getMapValue(map, key);
            String valueStr = StringUtil.getMapValue(map, value);
            res.put(keyStr, valueStr);
        }
        return res;
    }



    public static Map<String, String> getMapFromArray(String[] array) {

        Map<String, String> res = new LinkedHashMap<String, String>();
        for (String str : array) {
            if (StringUtil.isNotBlank(str)) {
                res.put(str, "");
            }
        }
        return res;
    }



    public static List<Map> getListFromMap(Map map) {

        List list = new ArrayList();
        for (Object key : map.keySet()) {
            list.add(map.get(key));
        }
        return list;
    }



    public static void addAttrMapByList(Map<String, Map> srcMap, List<Map> list, String key, String... attrArray) {

        for (Map map : list) {
            String keyV = StringUtil.getMapValue(map, key);
            if (StringUtil.isNotBlank(keyV)) {
                Map m = srcMap.get(keyV);
                if (m != null) {
                    for (String attr : attrArray) {
                        String attrValue = StringUtil.getMapValue(map, attr);
                        m.put(attr, attrValue);
                    }
                }
            }
        }
    }



    public static void deleteAttrMapByList(List<Map> list, String... attrArray) {

        if (StringUtil.isBlank(list)) {
            return;
        }
        for (Map map : list) {
            for (String attr : attrArray) {
                map.remove(attr);
            }
        }
    }



    public static void clearAttrList(List<Map> list, String... attrArray) {

        if (StringUtil.isBlank(list) || StringUtil.isBlank(attrArray)) {
            return;
        }
        String[] keyArray = StringUtil.getMapKeyArray(list.get(0));

        for (String key : keyArray) {
            if (!StringUtil.haveStringIs(attrArray, key)) {
                StringUtil.deleteAttrMapByList(list, key);
            }
        }
    }



    public static void addAttrList(List<Map> list, String... attrArray) {

        if (StringUtil.isBlank(list)) {
            return;
        }
        for (Map map : list) {
            for (String attr : attrArray) {
                if (!map.containsKey(attr)) {
                    map.put(attr, "");
                }
            }
        }
    }



    public static void addAttrValueList(List<Map> list, String field, String value) {

        if (StringUtil.isBlank(list)) {
            return;
        }
        for (Map map : list) {
            map.put(field, value);
        }
    }



    public static void renameAttrList(List<Map> list, String oldname, String newname) {

        if (StringUtil.isBlank(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            int oldSize = map.size();
            Object obj = map.get(oldname);
            if (obj != null) {
                map.put(newname, map.get(oldname));
                if (map.size() != oldSize) {
                    map.remove(oldname);
                }
            }
        }
    }



    // public static void renameAttrMap(Map<String, Object> map, String name1,
    // String name2) {
    // Object obj = map.get(name1);
    // if (obj != null) {
    // map.put(name2, obj);
    // if (!name1.equals(name2)) {
    // map.remove(name1);
    // }
    // }
    // }
    public static void addAttrMap(Map<String, Object> map, String key, String value) {

        String mapValue = StringUtil.getMapValue(map, key);
        if (StringUtil.isBlank(mapValue) || StringUtil.isNotBlank(value)) {
            map.put(key, value);
        }
    }



    public static List<Map> map2List(Map<String, Map> map) {

        List<Map> list = new ArrayList<Map>();
        for (String key : map.keySet()) {
            list.add(map.get(key));
        }
        return list;
    }



    public static String[] getStringArrayCutCount(String strAll, int count) {

        List list = new ArrayList();
        List listTmp = new ArrayList();
        int ic = 0;
        String[] strArray = strAll.split(",");
        for (int i = 0; i < strArray.length; i++) {
            listTmp.add(strArray[i]);
            ic++;
            if (ic == count || i == strArray.length - 1) {
                list.add(StringUtil.getObjValue(listTmp.toArray()));
                listTmp.clear();
                ic = 0;
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }



    public static void replaceList(List<Map> list, String field, String oldStr, String newStr) {

        for (Map map : list) {
            String value = StringUtil.getMapValue(map, field);
            map.put(field, value.replace(oldStr, newStr));
        }
    }



    public static void replaceArray(String[] array, String oldStr, String newStr) {

        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].replace(oldStr, newStr);
        }
    }



    public static void resetMapValue(Map map, String field, String oldStr, String newStr) {

        String str = StringUtil.getMapValue(map, field);
        if (str.equals(oldStr)) {
            map.put(field, newStr);
        }
    }



    public static int getNumberYuShuMin(int number, int start, int end) {

        number--;
        int ys = start;
        int res = start;
        for (int i = start; i <= end; i++) {
            int yt = number % i;
            if (yt < ys) {
                ys = yt;
                res = i;
            }
        }
        return res;
    }



    public static String[] initDayStr(String starttime, String endtime, int calendarType, int diff) {

        List<String> list = new ArrayList<String>();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            c.setTime(df.parse(starttime));
            if (!starttime.endsWith("00")) {
                c.set(Calendar.SECOND, 0);
                c.add(Calendar.MINUTE, 1);
            }
            while (true) {
                String timeStr = df.format(c.getTime());
                if (timeStr.compareTo(endtime) > 0) {
                    break;
                }
                list.add(timeStr);
                c.add(calendarType, diff);
            }
        } catch (ParseException e) {
        }
        return (String[]) list.toArray(new String[list.size()]);
    }



    public static String getTable(List<Map<String, Object>> datas, String[] titles, String[] fields) {

        StringBuffer sb = new StringBuffer("<table class='table table-striped table-bordered table-hover'><thead><tr>");
        for (String title : titles) {
            sb.append("<th class='center'>" + title + "</th>");
        }
        sb.append("</tr></thead><tbody>");
        for (Map<String, Object> data : datas) {
            sb.append("<tr>");
            for (String field : fields) {
                sb.append("<td>" + StringUtil.getMapValue(data, field) + "</td>");
            }
            sb.append("</tr>");
        }
        sb.append("</tbody></table>");
        return sb.toString();
    }



    public static String getInStrParam(String key, Object[] array) {

        StringBuffer sb = new StringBuffer();
        if (StringUtil.isBlank(key) || StringUtil.isBlank(array)) {
            return "2 != 2";
        }
        sb.append(" ( ");
        int size = 950;
        List list = new ArrayList();
        for (int i = 1; i <= array.length; i++) {
            list.add(array[i - 1]);
            if (i % size == 0 || i == array.length) {
                if (i > size) {
                    sb.append(" or ");
                }
                sb.append(key);
                sb.append(" in ( ");
                sb.append(StringUtil.getObjParam(list.toArray()));
                sb.append(" ) ");
                list = new ArrayList();
            }
        }
        sb.append(" ) ");
        return sb.toString();
    }



    public static String getInStrParamValue(String key, Object[] array) {

        StringBuffer sb = new StringBuffer();
        if (StringUtil.isBlank(key) || StringUtil.isBlank(array)) {
            return "2 != 2";
        }
        sb.append(" ( ");
        int size = 950;
        List list = new ArrayList();
        for (int i = 1; i <= array.length; i++) {
            list.add(array[i - 1]);
            if (i % size == 0 || i == array.length) {
                if (i > size) {
                    sb.append(" or ");
                }
                sb.append(key);
                sb.append(" in ( ");
                sb.append(StringUtil.getObjValue(list.toArray(), ",", "'"));
                sb.append(" ) ");
                list = new ArrayList();
            }
        }
        sb.append(" ) ");
        return sb.toString();
    }



    public static String getStringJoinList(List list, String joinStr) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String str = StringUtil.checkNull(list.get(i).toString());
            if (StringUtil.isBlank(str)) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append(joinStr);
            }
            sb.append(str);
        }
        return sb.toString();
    }



    public static String checkInputString(String input) {

        input = StringUtil.checkString(input);
        input = input.replace(" ", "");
        input = input.replace("=", "");
        input = input.replace("+", "");
        input = input.replace("\"", "");
        return input;
    }



    public static Date getDate(String dateString) {

        Date date = new Date();
        try {
            SimpleDateFormat sdf = getSimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.getMessage();
        }
        return date;
    }



    /**
     * 功能：得到当前时间yyyy-MM-dd<br>
     * 例如 2007-11-02<br>
     *
     * @return String
     */
    public static String getCurrentShotIntDate() {

        SimpleDateFormat df = getSimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return df.format(date);
    }



    public static String getUUID() {

        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }



    public static String getUUID(int size) {

        return getUUID().substring(0, size);
    }



    public static String clearWebParam(String str) {

        String res = str.replace(";", "");
        res = str.replace("[", "");
        res = str.replace("]", "");
        res = str.replace("*", "");
        res = str.replace("?", "");
        res = str.replace("\"", "");
        res = str.replace("'", "");
        return res;
    }

}
