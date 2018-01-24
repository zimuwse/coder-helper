package org.lr.helper.model;

import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-22 21:00
 * @description:
 */
public class DB2JavaMybatisVO extends DBVO {
    private List<String> prefixes;
    private String author;
    private String beanSuffix;
    private String beanLocation;
    private String beanPkg;
    private String mapperSuffix;
    private String mapperLocation;
    private String mapperPkg;
    private String xmlLocation;
    private List<String> tables;
    private String preview;
    private int option;

    public List<String> getPrefixes() {
        return prefixes;
    }

    public void setPrefixes(List<String> prefixes) {
        this.prefixes = prefixes;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBeanSuffix() {
        return beanSuffix;
    }

    public void setBeanSuffix(String beanSuffix) {
        this.beanSuffix = beanSuffix;
    }

    public String getBeanLocation() {
        return beanLocation;
    }

    public void setBeanLocation(String beanLocation) {
        this.beanLocation = beanLocation;
    }

    public String getBeanPkg() {
        return beanPkg;
    }

    public void setBeanPkg(String beanPkg) {
        this.beanPkg = beanPkg;
    }

    public String getMapperSuffix() {
        return mapperSuffix;
    }

    public void setMapperSuffix(String mapperSuffix) {
        this.mapperSuffix = mapperSuffix;
    }

    public String getMapperLocation() {
        return mapperLocation;
    }

    public void setMapperLocation(String mapperLocation) {
        this.mapperLocation = mapperLocation;
    }

    public String getMapperPkg() {
        return mapperPkg;
    }

    public void setMapperPkg(String mapperPkg) {
        this.mapperPkg = mapperPkg;
    }

    public String getXmlLocation() {
        return xmlLocation;
    }

    public void setXmlLocation(String xmlLocation) {
        this.xmlLocation = xmlLocation;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }
}
