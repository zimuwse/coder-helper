package org.lr.helper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-03 18:03
 * @description:
 */
public class JavaIndex {
    private String name;
    private boolean unique;
    private List<String> columns;

    public JavaIndex(DBIndex index) {
        this(index.getName(), index.isUnique(), index.getColumn());
    }

    public JavaIndex(String name, boolean unique, String column) {
        List<String> list = new ArrayList<>();
        list.add(column);
        this.name = name;
        this.unique = unique;
        this.columns = list;
    }

    public String getName() {
        return name;
    }

    public boolean isUnique() {
        return unique;
    }

    public List<String> getColumns() {
        return columns;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((JavaIndex) obj).getName());
    }
}
