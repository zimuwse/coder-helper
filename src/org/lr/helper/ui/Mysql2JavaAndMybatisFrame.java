package org.lr.helper.ui;

import org.lr.helper.config.DB2JavaMybatisVOConf;
import org.lr.helper.model.DB2JavaMybatisVO;
import org.lr.helper.model.DBTable;
import org.lr.helper.model.GenFileTypeOptions;
import org.lr.helper.model.KV;
import org.lr.helper.service.Mysql2JavaJob;
import org.lr.helper.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author: zimuwse
 * @time: 2018-01-22 19:09
 * @description:
 */
public class Mysql2JavaAndMybatisFrame extends BaseFrame implements IMultiSelect {
    private static final long serialVersionUID = -9010504342668742389L;
    private Mysql2JavaAndMybatisFrame _this;
    private String projectPath;
    private JPanel contentPanel;
    private JTextField txtHost;
    private JTextField txtPort;
    private JTextField txtUser;
    private JTextField txtPwd;
    private JTextField txtAuthor;
    private JTextField txtPrefix;
    private JTextField txtBeanSuffix;
    private JTextField txtMapperSuffix;
    private JTextField txtBeanLocation;
    private JTextField txtMapperLocation;
    private JTextField txtXmlLocation;
    private JButton btnTestConnection;
    private JTextArea txtTables;
    private JTextField txtPreview;
    private JButton btnPreview;
    private JButton btnConfMapping;
    private JButton btnGenerate;
    private JTextField txtDB;
    private JButton btnCancel;
    private JCheckBox cbJavaBean;
    private JCheckBox cbMybatisMapper;
    private JCheckBox cbMybatisXml;
    private JButton btnSavePreference;

    public Mysql2JavaAndMybatisFrame(String projectPath) {
        setTitle("Mysql To Java And Mybatis");
        _this = this;
        this.projectPath = projectPath;
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
        loadPreference();
    }

    @Override
    public void initBind() {
        super.initBind();
        btnTestConnection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testConnection();
            }
        });
        btnConfMapping.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configMapping();
            }
        });
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generate();
            }
        });
        btnPreview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                preview();
            }
        });
        txtTables.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2)
                    chooseTables();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        txtBeanLocation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooseLocation(txtBeanLocation, "Choose Java Bean Location");
            }
        });

        txtMapperLocation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooseLocation(txtMapperLocation, "Choose Mybatis Mapper Location");
            }
        });

        txtXmlLocation.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooseLocation(txtXmlLocation, "Choose Mybatis Xml Location");
            }
        });

        btnSavePreference.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePreference();
            }
        });
    }

    private void configMapping() {
        new Mysql2JavaTypeMappingFrame().show(420, 500);
    }

    private void testConnection() {
        DB2JavaMybatisVO vo = getParams(Operation.TEST_CONNECTION);
        if (null != vo) {
            try {
                Connection connection = DBUtils.getConnection(vo);
                connection.close();
            } catch (Exception e) {
                MsgUtil.popErrOnAC(e.getMessage());
            }
        }
    }

    private void chooseTables() {
        new MultiSelectFrame("Choose Tables", _this).show(360, 400);
    }

    private void preview() {
        DB2JavaMybatisVO vo = getParams(Operation.PREVIEW);
        if (null == vo)
            return;
        new Mysql2JavaPreview(vo).show(1200, 750);
    }

    private void generate() {
        DB2JavaMybatisVO vo = getParams(Operation.GENERATE);
        if (null == vo)
            return;
        new Thread(new Mysql2JavaJob(vo, 800, 400, "Task On Database[" + vo.getDb() + "]")).start();
    }

    private void savePreference() {
        DB2JavaMybatisVO vo = new DB2JavaMybatisVO();
        vo.setHost(txtHost.getText());
        vo.setPort(txtPort.getText());
        vo.setUser(txtUser.getText());
        vo.setPwd(txtPwd.getText());
        vo.setDb(txtDB.getText());
        vo.setAuthor(txtAuthor.getText());
        vo.setBeanSuffix(ParamUtil.trim(txtBeanSuffix.getText()));
        vo.setBeanLocation(txtBeanLocation.getText());
        vo.setBeanPkg(CodeUtil.getPackageFromLocation(vo.getBeanLocation()));
        vo.setMapperSuffix(ParamUtil.trim(txtMapperSuffix.getText()));
        vo.setMapperLocation(txtMapperLocation.getText());
        vo.setMapperPkg(CodeUtil.getPackageFromLocation(vo.getMapperLocation()));
        vo.setXmlLocation(txtXmlLocation.getText());
        Set<String> prefix = ParamUtil.splitAsSet(txtPrefix.getText(), ",");
        if (!ParamUtil.isEmpty(prefix))
            vo.setPrefixes(new ArrayList<>(prefix));
        vo.setPreview(txtPreview.getText());
        int option = 0;
        if (cbJavaBean.isSelected())
            option += 1;
        if (cbMybatisMapper.isSelected())
            option += 2;
        if (cbMybatisXml.isSelected())
            option += 4;
        vo.setOption(option);
        new DB2JavaMybatisVOConf().save(vo);
    }


    private void loadPreference() {
        DB2JavaMybatisVO vo = new DB2JavaMybatisVOConf().get();
        if (null == vo) {
            txtAuthor.setText(CmdUtil.getUserName());
            return;
        }
        txtHost.setText(vo.getHost());
        txtPort.setText(vo.getPort());
        txtUser.setText(vo.getUser());
        txtPwd.setText(vo.getPwd());
        txtDB.setText(vo.getDb());
        txtAuthor.setText(vo.getAuthor());
        txtBeanSuffix.setText(vo.getBeanSuffix());
        txtBeanLocation.setText(vo.getBeanLocation());
        txtMapperSuffix.setText(vo.getMapperSuffix());
        txtMapperLocation.setText(vo.getMapperLocation());
        txtXmlLocation.setText(vo.getXmlLocation());
        txtPrefix.setText(ParamUtil.join(vo.getPrefixes(), ","));
        txtPreview.setText(vo.getPreview());
        if (GenFileTypeOptions.isJavaBean(vo.getOption()))
            cbJavaBean.setSelected(true);
        if (GenFileTypeOptions.isMybatisMapper(vo.getOption()))
            cbMybatisMapper.setSelected(true);
        if (GenFileTypeOptions.isMybatisXml(vo.getOption()))
            cbMybatisXml.setSelected(true);
    }

    private DB2JavaMybatisVO getConnectionParams() {
        DB2JavaMybatisVO vo = new DB2JavaMybatisVO();
        vo.setHost(txtHost.getText());
        if (ParamUtil.isNullOrBlank(vo.getHost())) {
            MsgUtil.popErrOnComp(txtHost, "Host cannot be null or empty");
            return null;
        }
        vo.setPort(txtPort.getText());
        if (!ParamUtil.isNumber(vo.getPort())) {
            MsgUtil.popErrOnComp(txtPort, "Port must be number");
            return null;
        }
        vo.setUser(txtUser.getText());
        if (ParamUtil.isNullOrBlank(vo.getUser())) {
            MsgUtil.popErrOnComp(txtUser, "User cannot be null or empty");
            return null;
        }
        vo.setPwd(txtPwd.getText());
        if (ParamUtil.isNullOrBlank(vo.getPwd())) {
            MsgUtil.popErrOnComp(txtPwd, "Password cannot be null or empty");
            return null;
        }
        vo.setDb(txtDB.getText());
        if (ParamUtil.isNullOrBlank(vo.getDb())) {
            MsgUtil.popErrOnComp(txtDB, "Database cannot be null or empty");
            return null;
        }
        return vo;
    }

    private DB2JavaMybatisVO getParams(int operation) {
        boolean isSavePreference = Operation.isSavePreference(operation);
        boolean isPreview = Operation.isPreview(operation);
        boolean isGenerate = Operation.isGenerate(operation);
        boolean isTestConnection = Operation.isTestConnection(operation);
        DB2JavaMybatisVO vo = new DB2JavaMybatisVO();
        vo.setHost(txtHost.getText());
        if (!isSavePreference)
            if (ParamUtil.isNullOrBlank(vo.getHost())) {
                MsgUtil.popErrOnComp(txtHost, "Host cannot be null or empty");
                return null;
            }
        vo.setPort(txtPort.getText());
        if (!isSavePreference)
            if (!ParamUtil.isNumber(vo.getPort())) {
                MsgUtil.popErrOnComp(txtPort, "Port must be number");
                return null;
            }
        vo.setUser(txtUser.getText());
        if (!isSavePreference)
            if (ParamUtil.isNullOrBlank(vo.getUser())) {
                MsgUtil.popErrOnComp(txtUser, "User cannot be null or empty");
                return null;
            }
        vo.setPwd(txtPwd.getText());
        if (!isSavePreference)
            if (ParamUtil.isNullOrBlank(vo.getPwd())) {
                MsgUtil.popErrOnComp(txtPwd, "Password cannot be null or empty");
                return null;
            }
        vo.setDb(txtDB.getText());
        if (!isSavePreference)
            if (ParamUtil.isNullOrBlank(vo.getDb())) {
                MsgUtil.popErrOnComp(txtDB, "Database cannot be null or empty");
                return null;
            }
        vo.setAuthor(txtAuthor.getText());
        vo.setBeanSuffix(ParamUtil.trim(txtBeanSuffix.getText()));
        if (!isSavePreference && !isTestConnection)
            if (ParamUtil.containsBlank(vo.getBeanSuffix())) {
                MsgUtil.popErrOnComp(txtBeanSuffix, "Java Bean Suffix cannot contains any blanks");
                return null;
            }
        vo.setBeanLocation(txtBeanLocation.getText());
        if (!isSavePreference && !isTestConnection)
            if (ParamUtil.isNullOrBlank(vo.getBeanLocation())) {
                MsgUtil.popErrOnComp(txtBeanLocation, "Java Bean Location cannot be null oe empty");
                return null;
            }
        vo.setBeanPkg(CodeUtil.getPackageFromLocation(vo.getBeanLocation()));
        if (!isSavePreference && !isTestConnection)
            if (ParamUtil.isNullOrBlank(vo.getBeanPkg())) {
                MsgUtil.popErrOnComp(txtBeanLocation, "Java Bean Location must contain a java package");
                return null;
            }
        vo.setMapperSuffix(ParamUtil.trim(txtMapperSuffix.getText()));
        if (!isSavePreference && !isTestConnection)
            if (ParamUtil.containsBlank(vo.getMapperSuffix())) {
                MsgUtil.popErrOnComp(txtMapperSuffix, "Mybatis Mapper Suffix cannot contains any blanks");
                return null;
            }
        vo.setMapperLocation(txtMapperLocation.getText());
        if (!isSavePreference && !isTestConnection)
            if (ParamUtil.isNullOrBlank(vo.getBeanLocation())) {
                MsgUtil.popErrOnComp(txtMapperLocation, "Mybatis Mapper Location cannot be null oe empty");
                return null;
            }
        vo.setMapperPkg(CodeUtil.getPackageFromLocation(vo.getMapperLocation()));
        if (!isSavePreference && !isTestConnection)
            if (ParamUtil.isNullOrBlank(vo.getMapperPkg())) {
                MsgUtil.popErrOnComp(txtMapperLocation, "Mybatis Mapper Location must contain a java package");
                return null;
            }
        vo.setXmlLocation(txtXmlLocation.getText());
        if (!isSavePreference && !isTestConnection)
            if (ParamUtil.isNullOrBlank(vo.getXmlLocation())) {
                MsgUtil.popErrOnComp(txtXmlLocation, "Mybatis Xml Location cannot be null oe empty");
                return null;
            }
        Set<String> prefix = ParamUtil.splitAsSet(txtPrefix.getText(), ",");
        if (!ParamUtil.isEmpty(prefix))
            vo.setPrefixes(new ArrayList<>(prefix));

        if (isPreview) {
            vo.setPreview(txtPreview.getText());
            if (ParamUtil.isNullOrBlank(vo.getPreview())) {
                MsgUtil.popErrOnComp(txtTables, "Previewed Table cannot be null oe empty");
                return null;
            }
        } else if (isGenerate) {
            Set<String> tables = ParamUtil.splitAsSet(txtTables.getText(), ",");
            if (ParamUtil.isEmpty(tables)) {
                MsgUtil.popErrOnComp(txtTables, "Tables cannot be null oe empty");
                return null;
            }
            vo.setTables(new LinkedList<>(tables));
            int option = 0;
            if (cbJavaBean.isSelected())
                option += 1;
            if (cbMybatisMapper.isSelected())
                option += 2;
            if (cbMybatisXml.isSelected())
                option += 4;
            if (option == 0) {
                MsgUtil.popErrOnComp(btnGenerate, "Must Choose one of Java Bean, Mybatis Mapper or Mybatis Xml as least.");
                return null;
            }
            vo.setOption(option);
        }

        return vo;
    }


    private void chooseLocation(JTextField textField, String title) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileHidingEnabled(true);
        jfc.setAcceptAllFileFilterUsed(true);
        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "directory";
            }
        });
        jfc.setBounds(0, 0, 100, 100);
        jfc.setCurrentDirectory(new File(projectPath));
        jfc.setDialogTitle(title);
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //jfc.showDialog(_this, "Choose");
        jfc.showOpenDialog(_this);
        File file = jfc.getSelectedFile();
        if (null != file)
            textField.setText(file.getAbsolutePath());
    }

    @Override
    public List<KV> getOptions() {
        DB2JavaMybatisVO vo = getParams(Operation.TEST_CONNECTION);
        if (null == vo)
            return null;
        try {
            Connection connection = DBUtils.getConnection(vo);
            List<DBTable> list = DBUtils.getTables(connection);
            List<KV> kvList = new LinkedList<>();
            for (DBTable table : list) {
                kvList.add(new KV(table.getName(), table.getComment()));
            }
            return kvList;
        } catch (Exception e) {
            e.printStackTrace();
            MsgUtil.popErrOnAC(e.getMessage());
            return null;
        }
    }

    @Override
    public void onFinishMultiSelect(List<KV> kvs) {
        List<String> list = new LinkedList<>();
        for (KV kv : kvs) {
            list.add(kv.getKey());
        }
        txtTables.setText(ParamUtil.join(list, ","));
    }

    private static final class Operation {
        private final static int TEST_CONNECTION = 0x1;
        private final static int PREVIEW = 0x2;
        private final static int SAVE_PREFERENCE = 0x4;
        private final static int GENERATE = 0x8;

        private static boolean isTestConnection(int operation) {
            return (operation & TEST_CONNECTION) == TEST_CONNECTION;
        }

        private static boolean isPreview(int operation) {
            return (operation & PREVIEW) == PREVIEW;
        }

        private static boolean isSavePreference(int operation) {
            return (operation & SAVE_PREFERENCE) == SAVE_PREFERENCE;
        }

        private static boolean isGenerate(int operation) {
            return (operation & GENERATE) == GENERATE;
        }
    }
}
