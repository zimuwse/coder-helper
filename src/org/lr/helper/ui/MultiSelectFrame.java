package org.lr.helper.ui;

import org.lr.helper.model.KV;
import org.lr.helper.util.MsgUtil;
import org.lr.helper.util.ParamUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-23 12:42
 * @description:
 */
public class MultiSelectFrame extends BaseFrame {
    private static final long serialVersionUID = 8999427603617959335L;
    private JPanel contentPanel;
    private JTextField txtFilter;
    private JButton btnAll;
    private JButton btnInverse;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JPanel wrapper;
    private JScrollPane scrollPanel;
    private IMultiSelect multiSelect;
    private List<KV> kvList;

    public MultiSelectFrame(String title, IMultiSelect multiSelect) {
        this.multiSelect = multiSelect;
        initUI();
        initBind();
    }

    @Override
    JPanel getContentPanel() {
        return contentPanel;
    }

    @Override
    public void initUI() {
        super.initUI();
        List<KV> kvList = multiSelect.getOptions();
        if (null == kvList)
            return;
        this.kvList = kvList;
        paint(this.kvList);
    }

    @Override
    public void initBind() {
        super.initBind();
        txtFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    doFilter();
            }
        });
        btnAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAll();
            }
        });
        btnInverse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doInverse();
            }
        });
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doConfirm();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doCancel();
            }
        });
    }

    private void paint(List<KV> list) {
        if (null == list || list.isEmpty())
            return;
        wrapper.removeAll();
        int size = list.size();
        GridLayout layout = new GridLayout(size, 1);
        wrapper.setLayout(layout);
        for (KV kv : list) {
            JCheckBox checkBox = new JCheckBox(kv.getKey());
            checkBox.setToolTipText(kv.getValue());
            wrapper.add(checkBox);
        }
        scrollPanel.getVerticalScrollBar().setBlockIncrement(10);
        scrollPanel.getVerticalScrollBar().setUnitIncrement(10);
        scrollPanel.getVerticalScrollBar().setValue(0);
        scrollPanel.updateUI();
    }

    private List<KV> filter(String filter) {
        if (ParamUtil.isNullOrBlank(filter))
            return kvList;
        List<KV> filterList = new LinkedList<>();
        for (KV kv : kvList) {
            if (kv.getKey().contains(filter))
                filterList.add(kv);
        }
        return filterList;
    }

    private void doFilter() {
        paint(filter(txtFilter.getText()));
    }

    private void doAll() {
        if (null == kvList)
            return;
        Component[] components = wrapper.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                ((JCheckBox) component).setSelected(true);
            }
        }
    }

    private void doInverse() {
        if (null == kvList)
            return;
        Component[] components = wrapper.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    checkBox.setSelected(false);
                } else {
                    checkBox.setSelected(true);
                }
            }
        }
    }

    private void doConfirm() {
        if (null == kvList) {
            return;
        }
        List<KV> list = new LinkedList<>();
        Component[] components = wrapper.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    list.add(new KV(checkBox.getText(), checkBox.getToolTipText()));
                }
            }
        }
        if (list.isEmpty()) {
            MsgUtil.popErrOnAC("no item selected");
        } else {
            multiSelect.onFinishMultiSelect(list);
            close();
        }
    }

    private void doCancel() {
        cancel();
    }

}
