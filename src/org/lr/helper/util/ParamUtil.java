package org.lr.helper.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author: zimuwse
 * @time: 2018-01-19 19:17
 * @description:
 */
public class ParamUtil {

    /**
     * True returned when obj meets the conditions as follows:
     * 1.null obj
     * 2.blank string
     * 3.empty collection or map
     * 4.zero length array
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((String) obj).trim().length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else {
            return false;
        }
    }

    /**
     * A string is null or blank
     *
     * @param str
     * @return
     */
    public static boolean isNullOrBlank(String str) {
        return StringUtils.isEmpty(str) || StringUtils.isBlank(str);
    }

    /**
     * trim a string object
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (StringUtils.isEmpty(str))
            return str;
        return str.trim();
    }

    /**
     * decide whether or not a string object contains blank
     *
     * @param str
     * @return
     */
    public static boolean containsBlank(String str) {
        return !StringUtils.isEmpty(str) && str.matches("\\s");
    }

    /**
     * Object src is one of expects.
     * Expects's equals methods are called.
     *
     * @param src
     * @param expects
     * @param <T>
     * @return
     */
    public static <T> boolean isInExpects(T src, T... expects) {
        for (T obj : expects) {
            if (obj.equals(src))
                return true;
        }
        return false;
    }

    /**
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * null or empty array
     *
     * @param obj
     * @return
     */
    public static boolean isEmptyArr(Object obj) {
        return null == obj || Array.getLength(obj) == 0;
    }


    /**
     * split a string to set by regex sep
     *
     * @param str
     * @param sep
     * @return
     */
    public static Set<String> splitAsSet(String str, String sep) {
        if (isNullOrBlank(str))
            return null;
        String[] arr = str.split(sep);
        Set<String> set = new HashSet<>();
        for (String s : arr) {
            String trim = trim(s);
            if (!isNullOrBlank(trim))
                set.add(trim);
        }
        return set;
    }

    /**
     * join
     *
     * @param collection
     * @param sep
     * @return
     */
    public static String join(Collection<String> collection, String sep) {
        if (isEmpty(collection))
            return null;
        StringBuilder builder = new StringBuilder();
        for (String s : collection) {
            builder.append(s).append(sep);
        }
        builder.delete(builder.length() - sep.length(), builder.length());
        return builder.toString();
    }
}
