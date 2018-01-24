package org.lr.helper.config;

import org.lr.helper.model.DB2JavaMybatisVO;

/**
 * @author: zimuwse
 * @time: 2018-01-23 19:14
 * @description:
 */
public class DB2JavaMybatisVOConf extends BaseConf<DB2JavaMybatisVO> {
    @Override
    protected String key() {
        return "db.to.java.mybatis.prefer";
    }

    @Override
    protected boolean check(DB2JavaMybatisVO config) {
        return true;
    }

    @Override
    protected DB2JavaMybatisVO def() {
        return null;
    }
}
