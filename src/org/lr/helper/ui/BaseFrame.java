package org.lr.helper.ui;

import javax.swing.*;

/**
 * @author: zimuwse
 * @time: 2018-01-22 10:56
 * @description:
 */
public abstract class BaseFrame extends JFrame implements IShowFrame {

    private static final long serialVersionUID = 8262742911829403016L;

    abstract JPanel getContentPanel();

    @Override
    public void initUI() {

    }

    @Override
    public void initBind() {

    }

    @Override
    public void show(int width, int height) {
        setContentPane(getContentPanel());
        setResizable(false);
        setAlwaysOnTop(true);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void close() {
        dispose();
    }

    protected void confirm() {

    }

    protected void cancel() {
        close();
    }
}
