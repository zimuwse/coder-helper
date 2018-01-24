package org.lr.helper.ui;

import org.lr.helper.config.JavaTypeConf;
import org.lr.helper.config.Mysql2JavaTypeMappingConf;
import org.lr.helper.model.KV;
import org.lr.helper.util.MsgUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author: zimuwse
 * @time: 2018-01-22 10:45
 * @description:
 */
public class Mysql2JavaTypeMappingFrame extends BaseFrame {
    private static final long serialVersionUID = -4588539391610695755L;
    private Mysql2JavaTypeMappingFrame _this;
    private JPanel contentPanel;
    private JButton btnSave;
    private JButton btnCancel;
    private JScrollPane scrollPanel;
    private JPanel listPanel;
    private JButton btnAddMapping;
    private Map<JLabel, JComboBox<String>> mappings;

    public Mysql2JavaTypeMappingFrame() {
        setTitle("Config Mysql To Java Type Mappings");
        _this = this;
        initUI();
        initBind();
    }

    @Override
    public void initUI() {
        super.initUI();
        paint();
    }

    @Override
    public void initBind() {
        super.initBind();
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        btnAddMapping.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ManageMysql2JavaMappingFrame(_this).show(440, 112);
            }
        });
    }

    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }

    private void save() {
        java.util.List<KV> list = new ArrayList<>();
        for (Map.Entry<JLabel, JComboBox<String>> entry : mappings.entrySet()) {
            list.add(new KV(entry.getKey().getText(), entry.getValue().getSelectedItem().toString()));
        }
        new Mysql2JavaTypeMappingConf().save(list);
        MsgUtil.popInfoOnComp(btnSave, "config saved");
        close();
    }

    private void paint() {
        java.util.List<KV> mappings = new Mysql2JavaTypeMappingConf().get();
        java.util.List<String> javaTypes = new JavaTypeConf().get();
        listPanel.setLayout(new GridLayout(mappings.size(), 2));
        this.mappings = new HashMap<>();
        for (KV kv : mappings) {
            JLabel label = new JLabel(kv.getKey());
            listPanel.add(label);
            JComboBox<String> comboBox = new JComboBox<>(new Vector<>(javaTypes));
            comboBox.setSelectedItem(kv.getValue());
            listPanel.add(comboBox);
            this.mappings.put(label, comboBox);
        }
    }

    public void repaintListPanel() {
        listPanel.removeAll();
        listPanel.repaint();
        listPanel.updateUI();
        paint();
    }
}
