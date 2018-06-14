package com.generator;

import com.alibaba.fastjson.JSON;

import static com.generator.GeneratorUtils.*;
import static com.generator.GeneratorUtils.firstLetterUp;

public class Column {
    public Column(String colName, String coltype, String length) {
        this.colName = colName;
        this.coltype = coltype;
        this.length = length;

        String jdbcType = "VARCHAR";
        if (Generator.config.getDbType().equals("oracle")) {
            jdbcType = GeneratorUtils.oracleColTypeToJdbcType(coltype.toUpperCase());
        }else if (Generator.config.getDbType().equals("mysql")) {
            jdbcType = GeneratorUtils.mysqlColTypeToJdbcType(coltype.toUpperCase());
        }
        this.jdbcType = jdbcType;

        this.fieldName = firstLetterLow(nameFix(colName));//java model字段名
        this.javaType = GeneratorUtils.jdbcTypeToJavaType(jdbcType);//model字段类型，全类名 java.lang.String
        this.javaTypeName = javaType.substring(javaType.lastIndexOf(".") + 1, javaType.length());
        this.getMethodName = "get" + firstLetterUp(getFieldName());
        this.setMethodName = "set" + firstLetterUp(getFieldName());

        this.primaryKey = false;
    }

    private String coltype;//数据库字段类型
    private String colName;//数据库字段名
    private String length;//数据库字段长度

    private String jdbcType;//model字段对应mybatis类型

    private String fieldName;//model字段名
    private String javaType;//model字段类型，全类名 java.lang.String
    private String javaTypeName;//model字段类型 String
    private String getMethodName;//get方法名
    private String setMethodName;//set方法名

    private Boolean primaryKey;//是否主键

    public Boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getColtype() {
        return coltype;
    }

    public void setColtype(String coltype) {
        this.coltype = coltype;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }

    public String getGetMethodName() {
        return getMethodName;
    }

    public void setGetMethodName(String getMethodName) {
        this.getMethodName = getMethodName;
    }

    public String getSetMethodName() {
        return setMethodName;
    }

    public void setSetMethodName(String setMethodName) {
        this.setMethodName = setMethodName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
