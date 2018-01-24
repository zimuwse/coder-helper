package org.lr.helper.model;

/**
 * @author: zimuwse
 * @time: 2018-01-03 18:54
 * @description:
 */
public class JavaXmlMapper implements IJavaFile {
    private String path;
    private JavaMapper javaMapper;

    public JavaXmlMapper(String path, JavaMapper javaMapper) {
        this.path = path;
        this.javaMapper = javaMapper;
    }

    /**
     * file content
     *
     * @return
     */
    @Override
    public String fileContent() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        xml.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        xml.append("<mapper namespace=\"").append(javaMapper.getFullName()).append("\">\n");
        xml.append(javaMapper.getEntity().getResultMapXml());
        xml.append(javaMapper.getEntity().getFieldsXml());
        xml.append(javaMapper.getEntity().getInsertValuesXml());
        xml.append(javaMapper.getEntity().getInsertFieldsXml());
        xml.append(javaMapper.getEntity().getUpdateFieldsXml());
        xml.append(javaMapper.getEntity().getSearchFieldsXml());
        xml.append(javaMapper.getEntity().getInsertSqlXml());
        xml.append("\n</mapper>");
        return xml.toString();
    }

    /**
     * file name
     *
     * @return
     */
    @Override
    public String fileName() {
        return javaMapper.getName() + javaMapper.getSuffix() + ".xml";
    }

    /**
     * file path
     *
     * @return
     */
    @Override
    public String filePath() {
        return path;
    }
}
