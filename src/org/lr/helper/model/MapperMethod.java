package org.lr.helper.model;

import java.util.Set;

/**
 * @author: zimuwse
 * @time: 2018-01-04 14:35
 * @description: no-use?
 */
public class MapperMethod {
    /**
     * insert,update,queryOne,queryList
     */
    private String type;
    /**
     * updateById,getListByUid
     */
    private String name;
    /**
     * columns included to update or insert
     */
    private Set<String> columns;
    /**
     * columns included to be selected as condition
     */
    private Set<String> conditionColumns;
}
