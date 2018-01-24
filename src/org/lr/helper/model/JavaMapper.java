package org.lr.helper.model;

import org.apache.commons.lang.StringUtils;
import org.lr.helper.util.CodeUtil;
import org.lr.helper.util.ParamUtil;


import java.util.List;

/**
 * @author: zimuwse
 * @time: 2018-01-03 18:52
 * @description:
 */
public class JavaMapper implements IJavaFile {
    private String name;
    private String suffix;
    private String pack;
    private JavaEntity entity;


    public JavaMapper(String name, String suffix, String pack, JavaEntity entity) {
        this.name = name;
        this.suffix = suffix;
        this.pack = pack;
        this.entity = entity;
    }

    public JavaMapper(String name, String pack, JavaEntity entity) {
        this.name = name;
        this.suffix = "Mapper";
        this.pack = pack;
        this.entity = entity;
    }

    public String getSimpleName() {
        return name + suffix;
    }

    public String getFullName() {
        return pack + "." + name + suffix;
    }

    public String getName() {
        return name;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getPack() {
        return pack;
    }

    public JavaEntity getEntity() {
        return entity;
    }


    private String getUpdateMethodName(List<String> list) {
        return "updateBy" + getMethodAndParams(list);
    }

    private String getQueryMethodName(List<String> list) {
        return "getBy" + getMethodAndParams(list);
    }

    private String getMethodAndParams(List<String> list) {
        StringBuilder method = new StringBuilder();
        StringBuilder param = new StringBuilder("(");
        for (String column : list) {
            JavaField field = entity.getFieldByName(column);
            method.append(field.getJavaName()).append("And");
            param.append("@Param(\"")
                    .append(field.getJavaName())
                    .append("\") ")
                    .append(field.getJavaType().getSimpleName())
                    .append(" ")
                    .append(field.getJavaName())
                    .append(",");
        }
        if (method.length() > 3)
            method.delete(method.length() - 3, method.length());
        method.append(param.deleteCharAt(param.length() - 1)).append(");\n");
        return CodeUtil.upperFirst(method.toString());
    }

    /**
     * file content
     *
     * @return
     */
    @Override
    public String fileContent() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ")
                .append(pack)
                .append(";").append("\n\n");
        builder.append("import ").append(entity.getFullName()).append(";\n");
        builder.append("import org.apache.ibatis.annotations.Param;\n\n");
        builder.append("import java.util.List;\n");
        builder.append(entity.getImportsAsStr());
        StringBuilder methodsBuilder = new StringBuilder();
        //insert
        methodsBuilder.append(CodeUtil.getTab()).append("int insert(").append(entity.getSimpleName()).append(" entity);\n");

        for (JavaIndex index : entity.getIndices()) {
            if (index.isUnique()) {
                //update & query
                methodsBuilder.append(CodeUtil.getTab()).append("int ").append(getUpdateMethodName(index.getColumns()));
                methodsBuilder.append(CodeUtil.getTab()).append(entity.getSimpleName()).append(" ").append(getQueryMethodName(index.getColumns()));
            } else {
                methodsBuilder.append(CodeUtil.getTab()).append("List<").append(entity.getSimpleName()).append("> ").append(getQueryMethodName(index.getColumns()));
            }
        }
        builder.append("\n")
                .append(CodeUtil.getFileComment(entity.getAuthor(), entity.getTableName() + '(' + entity.getTableComment() + ')'))
                .append("public interface ")
                .append(name)
                .append(suffix)
                .append(" {\n")
                .append(methodsBuilder)
                .append("}\n");
        return builder.toString();
    }

    /**
     * file name
     *
     * @return
     */
    @Override
    public String fileName() {
        return name + suffix + ".java";
    }

    /**
     * file path
     *
     * @return
     */
    @Override
    public String filePath() {
        return StringUtils.join(pack.split("\\."), '/');
    }
}
