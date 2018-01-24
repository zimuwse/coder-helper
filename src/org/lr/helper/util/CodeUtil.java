package org.lr.helper.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-23 13:54
 * @description:
 */
public class CodeUtil {

    /**
     * parse package from location
     *
     * @param location
     * @return
     */
    public static String getPackageFromLocation(String location) {
        if (ParamUtil.isNullOrBlank(location))
            return null;
        int index = location.indexOf("java");
        if (index < 0 || index + 5 >= location.length())
            return null;
        return location.substring(index + 5).replaceAll("[\\\\/]", ".");
    }


    public static boolean isBasicJavaType(Class clz) {
        return clz.getName().startsWith("java.lang");
    }

    public static String upperFirst(String str) {
        if (ParamUtil.isNullOrBlank(str))
            return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String humpField(String field) {
        String[] arr = field.split("_");
        if (arr.length == 1)
            return field;
        if (arr.length == 2)
            return arr[0] + upperFirst(arr[1]);
        if (arr.length == 3)
            return arr[0] + upperFirst(arr[1]) + upperFirst(arr[2]);
        if (arr.length == 4)
            return arr[0] + upperFirst(arr[1]) + upperFirst(arr[2]) + upperFirst(arr[3]);
        StringBuilder builder = new StringBuilder(arr[1]);
        for (int i = 1; i < arr.length; i++) {
            builder.append(upperFirst(arr[i]));
        }
        return builder.toString();
    }

    public static String removePrefix(String original, List<String> prefixes) {
        if (null == prefixes || prefixes.isEmpty())
            return original;
        for (String prefix : prefixes) {
            if (prefix.length() < original.length()) {
                if (prefix.equals(original.substring(0, prefix.length()))) {
                    return original.substring(prefix.length());
                }
            }
        }
        return original;
    }


    public static String getTab() {
        return "    ";
    }

    public static String get2Tab() {
        return "        ";
    }

    public static String get3Tab() {
        return "            ";
    }


    public static String getFileComment(String author, String desc) {
        return "/**\n" +
                " * @author: " + author + "\n" +
                " * @time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n" +
                " * @description: " + desc + "\n" +
                " */\n";
    }
}
