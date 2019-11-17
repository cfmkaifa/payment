package org.forbes.comm.util;

/***
 * StrUtil概要说明：
 * @author Huanghy
 */
public class StrUtil {

    public static String toString(Object obj) {
        return obj == null?"":obj.toString();
    }

    public static String toString(Object obj, String nullStr) {
        return obj == null?nullStr:obj.toString();
    }

}
