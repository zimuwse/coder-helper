package org.lr.helper.ui;

import org.lr.helper.config.Mysql2JavaTypeMappingConf;
import org.lr.helper.model.*;
import org.lr.helper.util.DBUtils;
import org.lr.helper.util.MsgUtil;
import org.lr.helper.util.ParamUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author: zimuwse
 * @time: 2018-01-23 17:39
 * @description:
 */
public class Mysql2JavaPreview extends BaseFrame {
    private static final long serialVersionUID = -7429967318525611310L;
    private JPanel contentPanel;
    private JTextArea txtBean;
    private JTextArea txtMapper;
    private JTextArea txtXml;
    private JButton btnCpBean;
    private JButton btnCpMapper;
    private JButton btnCpXml;
    private JLabel labBeanName;
    private JLabel labMapperName;
    private JLabel labXmlName;
    private JScrollPane jspBean;
    private JScrollPane jspMapper;
    private JScrollPane jspXml;
    private DB2JavaMybatisVO vo;

    public Mysql2JavaPreview(DB2JavaMybatisVO vo) {
        this.vo = vo;
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
        reset(jspBean);
        reset(jspMapper);
        reset(jspXml);
        Connection connection = null;
        java.util.List<KV> mappings = new Mysql2JavaTypeMappingConf().get();
        try {
            connection = DBUtils.getConnection(vo);
            DBTable dbTable = DBUtils.getTableDetail(connection, vo.getPreview());
            JavaEntity javaEntity = new JavaEntity(dbTable, vo.getAuthor(), vo.getBeanSuffix(), vo.getBeanPkg(), vo.getPrefixes(), mappings);
            JavaMapper javaMapper = new JavaMapper(javaEntity.getName(), vo.getMapperSuffix(), vo.getMapperPkg(), javaEntity);
            JavaXmlMapper javaXmlMapper = new JavaXmlMapper(vo.getMapperLocation(), javaMapper);
            labBeanName.setText(javaEntity.fileName());
            labBeanName.setToolTipText(javaEntity.filePath() + "/" + javaEntity.fileName());
            labMapperName.setText(javaMapper.fileName());
            labMapperName.setToolTipText(javaMapper.filePath() + "/" + javaMapper.fileName());
            labXmlName.setText(javaXmlMapper.fileName());
            labXmlName.setToolTipText(javaXmlMapper.filePath() + "/" + javaXmlMapper.fileName());
            txtBean.setText(javaEntity.fileContent());
            txtMapper.setText(javaMapper.fileContent());
            txtXml.setText(javaXmlMapper.fileContent());
            btnCpBean.setText("copy " + javaEntity.fileName());
            btnCpMapper.setText("copy " + javaMapper.fileName());
            btnCpXml.setText("copy " + javaXmlMapper.fileName());

        } catch (Exception e) {
            MsgUtil.popErrOnAC(e.getMessage());
        } finally {
            if (null != connection) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initBind() {
        super.initBind();
        copyBind(btnCpBean, txtBean);
        copyBind(btnCpMapper, txtMapper);
        copyBind(btnCpXml, txtXml);
    }

    private void copyBind(JButton button, JTextArea textArea) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ParamUtil.isNullOrBlank(textArea.getText())) {
                    MsgUtil.popErrOnComp(button, "empty content");
                } else {
                    copy(textArea.getText());
                    MsgUtil.popInfoOnComp(button, "copied");
                }
            }
        });

    }

    private void reset(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setValue(0);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setValue(0);
        scrollPane.updateUI();
    }

    private void copy(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }
}
