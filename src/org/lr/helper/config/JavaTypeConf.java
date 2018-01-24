package org.lr.helper.config;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-22 11:40
 * @description:
 */
public class JavaTypeConf extends ListConf<String> {
    @Override
    protected String key() {
        return "java.type.set";
    }

    @Override
    boolean checkItem(String item) {
        try {
            Class.forName(item);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected List<String> def() {
        return Arrays.asList(
                Date.class.getName(),
                Date.class.getName(),
                Timestamp.class.getName(),
                Integer.class.getName(),
                Long.class.getName(),
                Short.class.getName(),
                Float.class.getName(),
                Double.class.getName(),
                BigDecimal.class.getName(),
                String.class.getName(),
                Character.class.getName()
        );
    }
}
