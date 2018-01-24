package org.lr.helper.model;

/**
 * @author: zimuwse
 * @time: 2018-01-03 17:56
 * @description:
 */
public class DBIndex {
    private String name;
    private boolean unique;
    private String column;
    private int seq;

    public DBIndex() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
