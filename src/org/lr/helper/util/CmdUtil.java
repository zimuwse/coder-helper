package org.lr.helper.util;

import java.io.IOException;

/**
 * @author: zimuwse
 * @time: 2018-01-24 15:53
 * @description:
 */
public class CmdUtil {

    public static String getUserName() {
        return System.getProperty("user.name");
    }

    public static void openBrowser(String url) {
        int type = getSystemType();
        try {
            if (SystemType.isWindows(type)) {
                Runtime.getRuntime().exec("cmd.exe /c start " + url);
            } else if (SystemType.isLinux(type)) {
                Runtime.getRuntime().exec("cx-www-browser " + url);
            } else if (SystemType.isMac(type)) {
                Runtime.getRuntime().exec("open " + url);
            }
        } catch (IOException e) {

        }
    }

    public static int getSystemType() {
        String os = System.getProperty("os.name");
        if (!ParamUtil.isNullOrBlank(os)) {
            if (os.startsWith("Windows")) {
                return SystemType.WINDOWS;
            } else if (os.startsWith("Linux")) {
                return SystemType.LINUX;
            } else if (os.startsWith("Mac")) {
                return SystemType.MAC;
            }
        }
        return SystemType.UNKNOW;
    }


    static final class SystemType {
        private final static int UNKNOW = -1;
        private final static int WINDOWS = 0x1;
        private final static int LINUX = 0x2;
        private final static int MAC = 0x4;

        public static boolean isWindows(int type) {
            return (type & WINDOWS) == WINDOWS;
        }

        public static boolean isLinux(int type) {
            return (type & LINUX) == LINUX;
        }

        public static boolean isMac(int type) {
            return (type & MAC) == MAC;
        }
    }
}

