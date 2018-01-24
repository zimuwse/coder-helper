package org.lr.helper.model;

import org.apache.commons.lang.StringUtils;
import org.lr.helper.util.CodeUtil;

import java.util.*;

/**
 * @author: zimuwse
 * @time: 2018-01-03 17:58
 * @description:
 */
public class JavaEntity implements IJavaFile {
    /**
     * file author
     */
    private String author;
    /**
     * table name of the java object
     */
    private String tableName;

    /**
     * table comment in database
     */
    private String tableComment;
    /**
     * simple name of the java object.
     */
    private String name;
    /**
     * the real name of the java object is name+suffix.
     */
    private String suffix;
    /**
     * package name of the java object.
     */
    private String pack;
    /**
     * classes to be imported of the java object.
     */
    private Set<Class> imports;

    /**
     * fields of the java object.
     */
    private List<JavaField> fields;

    /**
     * indices of the table
     */
    private List<JavaIndex> indices;

    /**
     * unique index columns
     */
    private Set<String> uniqueIndexColumns;

    /**
     * index columns
     */
    private Set<String> indexColumns;


    public JavaEntity(DBTable table, String author, String suffix, String pack, List<String> prefix, List<KV> mappings) {
        this.author = author;
        this.tableName = table.getName();
        this.tableComment = table.getComment();
        this.name = CodeUtil.upperFirst(CodeUtil.humpField(CodeUtil.removePrefix(table.getName(), prefix)));
        this.suffix = suffix;
        this.pack = pack;
        this.fields = new ArrayList<>();
        this.imports = new HashSet<>();
        for (DBField field : table.getFields()) {
            JavaField javaField = new JavaField(field, mappings);
            this.fields.add(javaField);
            if (!CodeUtil.isBasicJavaType(javaField.getJavaType()))
                this.imports.add(javaField.getJavaType());
        }
        this.uniqueIndexColumns = new HashSet<>();
        this.indexColumns = new HashSet<>();
        Map<String, JavaIndex> map = new HashMap<>();
        for (DBIndex index : table.getIndices()) {

            indexColumns.add(index.getColumn());

            if (index.isUnique() && 1 == index.getSeq())
                this.uniqueIndexColumns.add(index.getColumn());

            if (!map.containsKey(index.getName())) {
                map.put(index.getName(), new JavaIndex(index));
            } else {
                map.get(index.getName()).getColumns().add(index.getColumn());
            }
        }
        this.indices = new ArrayList<>();
        for (Map.Entry<String, JavaIndex> entry : map.entrySet()) {
            this.indices.add(entry.getValue());
        }
    }

    public String getSimpleName() {
        return name + suffix;
    }

    public String getFullName() {
        return pack + "." + getSimpleName();
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

    public Set<Class> getImports() {
        return imports;
    }

    public List<JavaField> getFields() {
        return fields;
    }

    public List<JavaIndex> getIndices() {
        return indices;
    }

    public String getTableName() {
        return tableName;
    }

    public String getAuthor() {
        return author;
    }

    public String getTableComment() {
        return tableComment;
    }

    public String getImportsAsStr() {
        StringBuilder builder = new StringBuilder();
        for (Class clz : imports) {
            builder.append("import ").append(clz.getName()).append(";\n");
        }
        return builder.toString();
    }

    /**
     * xml of all fields resultMap
     *
     * @return
     */
    public String getResultMapXml() {
        StringBuilder builder = new StringBuilder(CodeUtil.getTab());
        builder.append("<resultMap id=\"")
                .append(name)
                .append("Map")
                .append("\" type=\"")
                .append(getFullName())
                .append("\">")
                .append("\n");
        for (JavaField field : fields) {
            builder.append(field.getXmlResultMap());
        }
        builder.append(CodeUtil.getTab()).append("</resultMap>").append("\n");
        return builder.toString();
    }


    /**
     * xml of all fields
     *
     * @return
     */
    public String getFieldsXml() {
        StringBuilder builder = new StringBuilder(CodeUtil.getTab());
        builder.append("<sql id=\"fields\">").append("\n");
        builder.append(CodeUtil.get2Tab());
        for (JavaField field : fields) {
            builder.append(field.getName()).append(',');
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("\n").append(CodeUtil.getTab()).append("</sql>").append("\n");
        return builder.toString();
    }

    /**
     * xml of all fields to insert
     *
     * @return
     */
    public String getInsertFieldsXml() {
        StringBuilder builder = new StringBuilder(CodeUtil.getTab());
        builder.append("<sql id=\"insertFields\">")
                .append("\n")
                .append(CodeUtil.get2Tab())
                .append("<trim suffixOverrides=\",\">")
                .append("\n");
        for (JavaField field : fields) {
            if (!"id".equals(field.getName()))
                builder.append(CodeUtil.getTab()).append(field.getXmlInsertField());
        }
        builder.append(CodeUtil.get2Tab()).append("</trim>").append("\n");
        builder.append(CodeUtil.getTab()).append("</sql>").append("\n");
        return builder.toString();
    }


    /**
     * xml of all values to insert
     *
     * @return
     */
    public String getInsertValuesXml() {
        StringBuilder builder = new StringBuilder(CodeUtil.getTab());
        builder.append("<sql id=\"insertValues\">")
                .append("\n")
                .append(CodeUtil.get2Tab())
                .append("<trim suffixOverrides=\",\">")
                .append("\n");
        for (JavaField field : fields) {
            if (!"id".equals(field.getName()))
                builder.append(CodeUtil.getTab()).append(field.getXmlInsertValue());
        }
        builder.append(CodeUtil.get2Tab()).append("</trim>").append("\n");
        builder.append(CodeUtil.getTab()).append("</sql>").append("\n");
        return builder.toString();
    }

    /**
     * xml for fields that can be updated
     *
     * @return
     */
    public String getUpdateFieldsXml() {
        StringBuilder builder = new StringBuilder(CodeUtil.getTab());
        builder.append("<sql id=\"updateFields\">")
                .append("\n")
                .append(CodeUtil.get2Tab())
                .append("<set>")
                .append("\n");
        for (JavaField field : fields) {
            if (!isUnique(field.getName()))
                builder.append(CodeUtil.getTab()).append(field.getXmlUpdateValue());
        }
        builder.append(CodeUtil.get2Tab()).append("</set>").append("\n").append(CodeUtil.getTab()).append("</sql>").append("\n");
        return builder.toString();
    }

    private boolean isUnique(String name) {
        return null != uniqueIndexColumns && uniqueIndexColumns.contains(name);
    }

    /**
     * xml for fields that can be searched
     *
     * @return
     */
    public String getSearchFieldsXml() {
        StringBuilder builder = new StringBuilder(CodeUtil.getTab());
        builder.append("<sql id=\"searchFields\">")
                .append("\n")
                .append(CodeUtil.get2Tab())
                .append("<trim prefixOverrides=\"AND\">")
                .append("\n");
        for (JavaField field : fields) {
            if (indexColumns.contains(field.getName()))
                builder.append(CodeUtil.getTab()).append(field.getXmlCondition());
        }
        builder.append(CodeUtil.get2Tab()).append("</trim>").append("\n").append(CodeUtil.getTab()).append("</sql>").append("\n");
        return builder.toString();
    }

    /**
     * xml for insert sql
     *
     * @return
     */
    public String getInsertSqlXml() {
        StringBuilder builder = new StringBuilder(CodeUtil.getTab());
        builder.append("<insert id=\"insert\" parameterType=\"Object\" keyProperty=\"id\" useGeneratedKeys=\"true\">")
                .append("\n")
                .append(CodeUtil.get2Tab())
                .append("INSERT INTO ")
                .append(tableName)
                .append("(<include refid=\"insertFields\"/>) ")
                .append("values(<include refid=\"insertValues\"/>)")
                .append("\n").append(CodeUtil.getTab()).append("</insert>");
        return builder.toString();
    }

    /**
     * file content
     *
     * @return
     */
    @Override
    public String fileContent() {
        StringBuilder builder = new StringBuilder("package ");
        builder.append(pack).append(";\n\n");
        builder.append(getImportsAsStr()).append("\n");
        StringBuilder fieldsBuilder = new StringBuilder();
        StringBuilder methodsBuilder = new StringBuilder();
        for (JavaField field : fields) {
            fieldsBuilder.append(field.getBeanFieldComment()).append(field.getBeanField()).append("\n");
            methodsBuilder.append(field.getBeanSetter()).append("\n").append(field.getBeanGetter()).append("\n");
        }
        builder.append("\n")
                .append(CodeUtil.getFileComment(getAuthor(), getTableName() + '(' + getTableComment() + ')'))
                .append("public class ")
                .append(name)
                .append(suffix)
                .append(" {")
                .append("\n")
                .append(fieldsBuilder)
                .append(methodsBuilder)
                .append("}")
                .append("\n");
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

    public JavaField getFieldByName(String column) {
        for (JavaField field : fields) {
            if (field.getName().equals(column))
                return field;
        }
        return null;
    }
}
