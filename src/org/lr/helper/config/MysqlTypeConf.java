package org.lr.helper.config;

import java.util.Arrays;
import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-22 11:38
 * @description:
 */
public class MysqlTypeConf extends ListConf<String> {
    @Override
    protected String key() {
        return "mysql.type.set";
    }

    @Override
    boolean checkItem(String item) {
        return true;
    }

    @Override
    protected List<String> def() {
        return Arrays.asList("date", "datetime", "timestamp",
                "tinyint", "smallint", "mediumint", "int", "bigint",
                "float", "double", "decimal",
                "char", "varchar", "tinytext", "text", "mediumtext", "longtext");
    }
}
