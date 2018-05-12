package com.generator;

import com.alibaba.fastjson.JSON;

import static com.generator.GeneratorUtils.*;
import static com.generator.GeneratorUtils.firstLetterUp;

public class Column {
    public Column(String colName, String jdbcType, String length) {
        this.colName = colName;
        this.jdbcType = jdbcType;
        this.length = length;
        String javaType = jdbcTypeToJavaType(jdbcType);
        this.fieldName = firstLetterLow(nameFix(colName));
        this.javaType = javaType;
        this.javaTypeName = nameFix(javaType.substring(javaType.lastIndexOf(".") + 1, javaType.length()));
        this.getMethodName = "get" + firstLetterUp(getFieldName());
        this.setMethodName = "set" + firstLetterUp(getFieldName());
    }

    private String colName;
    private String fieldName;
    private String jdbcType;
    private String length;

    private String javaType;
    private String javaTypeName;
    private String getMethodName;
    private String setMethodName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
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

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
