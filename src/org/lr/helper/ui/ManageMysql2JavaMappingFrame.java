package org.lr.helper.ui;

import org.lr.helper.config.JavaTypeConf;
import org.lr.helper.config.Mysql2JavaTypeMappingConf;
import org.lr.helper.config.MysqlTypeConf;
import org.lr.helper.model.KV;
import org.lr.helper.util.MsgUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: zimuwse
 * @time: 2018-01-22 13:49
 * @description:
 */
public class ManageMysql2JavaMappingFrame extends BaseFrame {
    private static final long serialVersionUID = -8016563445941925461L;
    private JPanel contentPanel;
    private JTextField txtMysql;
    private JTextField txtJava;
    private JComboBox cbMysql;
    private JComboBox cbJava;
    private JButton btnAdd;
    private JButton btnDel;
    private JButton btnConfirm;
    private JButton btnCancel;
    private Set<String> mysqlTypes;
    private Set<String> javaTypes;
    private Set<KV> mappings;
    private Mysql2JavaTypeMappingFrame mysql2JavaTypeMappingFrame;
    private boolean hasBeenModified = false;

    public ManageMysql2JavaMappingFrame(Mysql2JavaTypeMappingFrame mysql2JavaTypeMappingFrame) {
        setTitle("Manage Mysql To Java Type Mappings");
        this.mysql2JavaTypeMappingFrame = mysql2JavaTypeMappingFrame;
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
        mysqlTypes = new HashSet<>(new MysqlTypeConf().get());
        javaTypes = new HashSet<>(new JavaTypeConf().get());
        mappings = new HashSet<>(new Mysql2JavaTypeMappingConf().get());
        for (String s : mysqlTypes) {
            cbMysql.addItem(s);
        }
        for (String s : javaTypes) {
            cbJava.addItem(s);
        }
    }

    @Override
    public void initBind() {
        super.initBind();
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation(0);
            }
        });

        btnDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation(1);
            }
        });

        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirm();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        cbMysql.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                txtMysql.setText(e.getItem().toString());
            }
        });

        cbJava.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                txtJava.setText(e.getItem().toString());
            }
        });
    }

    private void operation(int type) {
        String mysql = txtMysql.getText();
        String java = txtJava.getText();
        Mysql2JavaTypeMappingConf conf = new Mysql2JavaTypeMappingConf();
        if (!conf.checkKey(mysql)) {
            MsgUtil.popErrOnComp(txtMysql, "mysql type cannot be null or blank or wrong format");
            return;
        }
        if (!conf.checkVal(java)) {
            MsgUtil.popErrOnComp(txtJava, "java type cannot be null or blank or not exist");
            return;
        }
        switch (type) {
            case 0:
                boolean b1 = mysqlTypes.add(mysql);
                boolean b2 = javaTypes.add(java);
                boolean b3 = mappings.add(new KV(mysql, java));
                if (b1 || b2 || b3) {
                    MsgUtil.popInfoOnComp(btnAdd, "added");
                    hasBeenModified = true;
                }
                break;
            case 1:
                //javaTypes.remove(java);
                boolean b4 = mysqlTypes.remove(mysql);
                boolean b5 = mappings.remove(new KV(mysql, java));
                if (b4 || b5) {
                    hasBeenModified = true;
                    MsgUtil.popInfoOnComp(btnDel, "deleted");
                }
                break;
        }
    }

    @Override
    protected void confirm() {
        super.confirm();
        if (hasBeenModified) {
            new MysqlTypeConf().save(new ArrayList<>(mysqlTypes));
            new JavaTypeConf().save(new ArrayList<>(javaTypes));
            new Mysql2JavaTypeMappingConf().save(new ArrayList<>(mappings));
            mysql2JavaTypeMappingFrame.repaintListPanel();
        }
        cancel();
    }
}
