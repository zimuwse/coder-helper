package org.lr.helper.model;

/**
 * @author: zimuwse
 * @time: 2018-01-03 17:47
 * @description:
 */
public class DBField {
    private String name;
    private String type;
    private boolean notNull;
    private String def;
    private String comment;

    public DBField() {
    }

    public DBField(String name, String type, boolean notNull, String def, String comment) {
        this.name = name;
        this.type = type;
        this.notNull = notNull;
        this.def = def;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
