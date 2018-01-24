package org.lr.helper.ui;

import com.intellij.openapi.project.Project;
import org.lr.helper.util.CmdUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author: zimuwse
 * @time: 2018-01-22 10:34
 * @description:
 */
public class MainFrame extends BaseFrame {
    private static final long serialVersionUID = -4595844141800322677L;
    private JPanel contentPanel;
    private JButton btnMysql2Java;
    private JLabel labGit;
    private Project project;

    public MainFrame(Project project) {
        setTitle("Coder Helper");
        this.project = project;
        initBind();
    }

    @Override
    public JPanel getContentPanel() {
        return contentPanel;
    }

    @Override
    public void initBind() {
        super.initBind();
        btnMysql2Java.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Mysql2JavaAndMybatisFrame(project.getBasePath()).show(460, 600);
            }
        });
        labGit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CmdUtil.openBrowser("https://github.com/zimuwse/coder-helper");
            }
        });
    }
}
