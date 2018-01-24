package org.lr.helper.model;

/**
 * @author: zimuwse
 * @time: 2018-01-23 15:58
 * @description:
 */
public final class GenFileTypeOptions {
    public final static int JAVA_BEAN = 0x1;
    public final static int MYBATIS_MAPPER = 0x2;
    public final static int MYBATIS_XML = 0x4;


    public final static boolean isJavaBean(int i) {
        return (i & JAVA_BEAN) == JAVA_BEAN;
    }

    public final static boolean isMybatisMapper(int i) {
        return (i & MYBATIS_MAPPER) == MYBATIS_MAPPER;
    }

    public final static boolean isMybatisXml(int i) {
        return (i & MYBATIS_XML) == MYBATIS_XML;
    }
}
