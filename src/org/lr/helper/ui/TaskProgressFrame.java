package org.lr.helper.ui;

import javax.swing.*;

/**
 * @author: zimuwse
 * @time: 2018-01-23 14:19
 * @description:
 */
public class TaskProgressFrame extends BaseFrame {
    private static final long serialVersionUID = 285073203412492283L;
    private JPanel contentPanel;
    private JProgressBar progressBar;
    private JTextArea output;
    private JScrollPane scroll;

    public TaskProgressFrame(String title) {
        setTitle(title);
        initUI();
        initBind();
    }

    @Override
    public void initUI() {
        super.initUI();
    }

    @Override
    public void initBind() {
        super.initBind();
    }

    @Override
    JPanel getContentPanel() {
        return contentPanel;
    }

    public void append(String s) {
        output.append(s + "\n");
        JScrollBar bar = scroll.getVerticalScrollBar();
        bar.setValue(bar.getMaximum());
    }

    public void updateProgress(int val, String text) {
        progressBar.setString(text);
        progressBar.setValue(val);
    }

    public void finish(long time) {
        append("[TASK FINISHED]" + time + " ms");

    }
}
