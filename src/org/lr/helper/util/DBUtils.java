package org.lr.helper.util;

import org.lr.helper.model.DBField;
import org.lr.helper.model.DBIndex;
import org.lr.helper.model.DBTable;
import org.lr.helper.model.DBVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-17 17:49
 * @description:
 */
public class DBUtils {

    private static void loadDriver() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
    }

    public static Connection getConnection(DBVO vo) throws Exception {
        loadDriver();
        String url = "jdbc:mysql://" + vo.getHost() + ":" + vo.getPort() + "/" + vo.getDb() + "?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
        return DriverManager.getConnection(url, vo.getUser(), vo.getPwd());
    }

    public static List<DBTable> getTables(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("show table status");
        List<DBTable> list = new LinkedList<>();
        while (resultSet.next()) {
            String comment = resultSet.getString("Comment");
            if (!ParamUtil.isNullOrBlank(comment))
                comment = comment.replaceAll("\r|\n", "");
            list.add(new DBTable(resultSet.getString("Name"), comment, null, null));
        }
        resultSet.close();
        statement.close();
        return list;
    }

    public static DBTable getTableDetail(Connection connection, String tableName) throws Exception {
        Statement tabStat = null;
        ResultSet tabRes = null;
        Statement fieldStat = null;
        ResultSet fieldRes = null;
        Statement indexStat = null;
        ResultSet indexRes = null;
        try {
            tabStat = connection.createStatement();
            tabRes = tabStat.executeQuery("show table status like '" + tableName + "'");
            fieldStat = connection.createStatement();
            fieldRes = fieldStat.executeQuery("show full fields from " + tableName);
            indexStat = connection.createStatement();
            indexRes = indexStat.executeQuery("show index from " + tableName);
            List<DBField> fields = new ArrayList<>();
            String tabComment = "";
            while (tabRes.next()) {
                tabComment = tabRes.getString("Comment");
                if (!ParamUtil.isNullOrBlank(tabComment))
                    tabComment = tabComment.replaceAll("\r|\n", "");
            }
            while (fieldRes.next()) {
                DBField field = new DBField();
                field.setName(fieldRes.getString("Field"));
                String mysqlType = getMySqlType(fieldRes.getString("Type"));
                field.setType(mysqlType);
                field.setComment(fieldRes.getString("Comment"));
                field.setDef(fieldRes.getString("Default"));
                field.setNotNull("NO".equals(fieldRes.getString("Null")));
                fields.add(field);
            }
            List<DBIndex> indices = new ArrayList<>();
            while (indexRes.next()) {
                DBIndex index = new DBIndex();
                index.setName(indexRes.getString("Key_name"));
                index.setUnique("0".equals(indexRes.getString("Non_unique")));
                index.setColumn(indexRes.getString("Column_name"));
                index.setSeq(indexRes.getInt("Seq_in_index"));
                indices.add(index);
            }
            return new DBTable(tableName, tabComment, fields, indices);
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != tabRes) tabRes.close();
            if (null != fieldRes) fieldRes.close();
            if (null != indexRes) indexRes.close();
            if (null != tabStat) tabStat.close();
            if (null != fieldStat) fieldStat.close();
            if (null != indexStat) indexStat.close();
        }
    }

    public static String getMySqlType(String origin) {
        int index = origin.indexOf('(');
        if (index > 0)
            return origin.substring(0, index);
        return origin;
    }

}
