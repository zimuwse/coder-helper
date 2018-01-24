package org.lr.helper.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-03 17:53
 * @description:
 */
public class DBTable implements Comparable {
    private String name;
    private String comment;
    private List<DBField> fields;
    private List<DBIndex> indices;

    public DBTable(String name, String comment, List<DBField> fields, List<DBIndex> indices) {
        this.name = name;
        this.comment = comment;
        this.fields = fields;
        this.indices = indices;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public List<DBField> getFields() {
        return fields;
    }

    public List<DBIndex> getIndices() {
        return indices;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return getName().compareTo(((DBTable) o).getName());
    }
}
