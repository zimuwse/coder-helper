package org.lr.helper.util;

import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.util.PopupUtil;
import org.lr.helper.config.Conf;

import javax.swing.*;
import java.awt.*;

import static com.intellij.openapi.ui.MessageType.ERROR;

/**
 * @author: zimuwse
 * @time: 2018-01-22 14:19
 * @description:
 */
public class MsgUtil {

    public static void popErrOnAC(String msg) {
        if (Conf.isDebug())
            System.out.println(msg);
        else
            PopupUtil.showBalloonForActiveComponent(msg, ERROR);
    }

    public static void popInfoOnAC(String msg) {
        if (Conf.isDebug())
            System.out.println(msg);
        else
            PopupUtil.showBalloonForActiveComponent(msg, MessageType.INFO);
    }

    public static void popErrOnComp(Component component, String msg) {
        pop(component, msg, "error");
    }

    public static void popInfoOnComp(Component component, String msg) {
        pop(component, msg, "info");
    }

    private static void pop(Component component, String msg, String type) {
        MessageType messageType = MessageType.ERROR;
        if ("info".equals(type)) {
            messageType = MessageType.INFO;
        } else if ("warning".equals(type)) {
            messageType = MessageType.WARNING;
        }
        if (Conf.isDebug()) {
            if (component instanceof JButton) {
                System.out.println("[clz:" + JButton.class.getSimpleName() + ",text:" + ((JButton) component).getText() + ",type:" + type + "]:" + msg);
            } else if (component instanceof JTextField) {
                System.out.println("[clz:" + JTextField.class.getSimpleName() + ",text:" + ((JTextField) component).getText() + ",type:" + type + "]:" + msg);
            } else if (component instanceof JTextArea) {
                System.out.println("[clz:" + JTextArea.class.getSimpleName() + ",text:" + ((JTextArea) component).getText() + ",type:" + type + "]:" + msg);
            } else {
                System.out.println("[clz:" + component.getClass().getSimpleName() + ",type:" + type + "]:" + msg);
            }
        } else {
            PopupUtil.showBalloonForComponent(component, msg, messageType, true, null);

        }
    }
}
