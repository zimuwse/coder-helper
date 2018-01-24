package org.lr.helper.ui;

/**
 * @author: zimuwse
 * @time: 2018-01-22 10:35
 * @description:
 */
public interface IShowFrame {

    /**
     * init UI
     */
    void initUI();

    /**
     * initBind action
     */
    void initBind();

    /**
     * change size to show frame
     *
     * @param width
     * @param height
     */
    void show(int width, int height);


    /**
     * close
     */
    void close();
}
