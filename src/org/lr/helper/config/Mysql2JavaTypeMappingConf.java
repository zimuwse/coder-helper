package org.lr.helper.config;

import org.lr.helper.model.KV;
import org.lr.helper.util.ParamUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-22 10:14
 * @description:
 */
public class Mysql2JavaTypeMappingConf extends ListConf<KV> {

    @Override
    public boolean checkItem(KV kv) {
        return checkKey(kv.getKey()) && checkVal(kv.getValue());
    }

    @Override
    public String key() {
        return "mysql2java.type.mapping";
    }

    @Override
    protected List<KV> def() {
        List<KV> kvs = new ArrayList<>(16);

        kvs.add(new KV("date", Date.class.getName()));
        kvs.add(new KV("datetime", Date.class.getName()));
        kvs.add(new KV("timestamp", Timestamp.class.getName()));

        kvs.add(new KV("tinyint", Integer.class.getName()));
        kvs.add(new KV("smallint", Integer.class.getName()));
        kvs.add(new KV("mediumint", Integer.class.getName()));
        kvs.add(new KV("int", Integer.class.getName()));
        kvs.add(new KV("bigint", Long.class.getName()));

        kvs.add(new KV("float", Float.class.getName()));
        kvs.add(new KV("double", Double.class.getName()));
        kvs.add(new KV("decimal", BigDecimal.class.getName()));

        kvs.add(new KV("char", String.class.getName()));
        kvs.add(new KV("varchar", String.class.getName()));
        kvs.add(new KV("tinytext", String.class.getName()));
        kvs.add(new KV("text", String.class.getName()));
        kvs.add(new KV("mediumtext", String.class.getName()));
        kvs.add(new KV("longtext", String.class.getName()));
        return kvs;
    }

    public boolean checkKey(String key) {
        return !ParamUtil.isNullOrBlank(key) && key.matches("^[a-zA-Z]+$");
    }

    public boolean checkVal(String val) {
        if (ParamUtil.isNullOrBlank(val))
            return false;
        try {
            Class.forName(val);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Class getMapping(List<KV> list, String mysql) {
        if (null == list || list.isEmpty())
            return String.class;
        for (KV kv : list) {
            if (kv.getKey().equals(mysql)) {
                try {
                    return Class.forName(kv.getValue());
                } catch (ClassNotFoundException e) {
                    return String.class;
                }
            }
        }
        return String.class;
    }
}
