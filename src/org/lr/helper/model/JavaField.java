package org.lr.helper.model;

import org.lr.helper.config.Mysql2JavaTypeMappingConf;
import org.lr.helper.util.CodeUtil;

import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-03 09:49
 * @description:
 */
public class JavaField extends DBField {
    private String javaName;
    private Class javaType;

    public JavaField(String name, String type, boolean notNull, String def, String comment, List<KV> mappings) {
        super(name, type, notNull, def, comment);
        this.javaName = CodeUtil.humpField(name.toLowerCase());
        this.javaType = Mysql2JavaTypeMappingConf.getMapping(mappings, type);
    }

    public JavaField(DBField dbField, List<KV> mappings) {
        this(dbField.getName(), dbField.getType(), dbField.isNotNull(), dbField.getDef(), dbField.getComment(), mappings);

    }

    public String getJavaName() {
        return javaName;
    }

    public Class getJavaType() {
        return javaType;
    }

    public String getBeanFieldComment() {
        return CodeUtil.getTab() + "/**\n" + CodeUtil.getTab() + " * " + getComment() + "\n"
                + CodeUtil.getTab() + " * not-null:" + isNotNull() + "\n"
                + CodeUtil.getTab() + " * default:'" + getDef() + "'\n"
                + CodeUtil.getTab() + " */\n";
    }

    public String getBeanField() {
        return CodeUtil.getTab() + "private " + javaType.getSimpleName() + " " + javaName + ";" + "\n";
    }

    public String getBeanGetter() {
        return CodeUtil.getTab() + "public " + javaType.getSimpleName() + " get" + CodeUtil.upperFirst(javaName) + "() {" + "\n"
                + CodeUtil.get2Tab() + "return " + javaName + ";" + "\n"
                + CodeUtil.getTab() + "}" + "\n";
    }

    public String getBeanSetter() {
        return CodeUtil.getTab() + "public void set" + CodeUtil.upperFirst(javaName) + "(" + javaType.getSimpleName() + " " + javaName + ") {" + "\n"
                + CodeUtil.get2Tab() + "this." + javaName + " = " + javaName + ";" + "\n"
                + CodeUtil.getTab() + "}" + "\n";
    }

    public String getXmlResultMap() {
        return CodeUtil.get2Tab() + "<result column=\"" + getName() + "\" property=\"" + javaName + "\"/>" + "\n";
    }

    public String getXmlInsertField() {
        return CodeUtil.get2Tab() + "<if test=\"" + javaName + " != null\">" + getName() + ",</if>" + "\n";
    }

    public String getXmlInsertValue() {
        return CodeUtil.get2Tab() + "<if test=\"" + javaName + " != null\">#{" + javaName + "},</if>" + "\n";
    }

    public String getXmlUpdateValue() {
        return CodeUtil.get2Tab() + "<if test=\"" + javaName + " != null\">" + getName() + "=#{" + javaName + "},</if>" + "\n";
    }

    public String getXmlCondition() {
        return CodeUtil.get2Tab() + "<if test=\"" + javaName + " != null\">AND " + getName() + "=#{" + javaName + "}</if>" + "\n";
    }
}
