package com.generator;

import java.util.Map;

public class Table {
    private Column primaryKey;
    private String tableName;
    private String className;
    private String classFullName;
    private String daoFullName;
    private String daoName;
    private Map<String, Column> columnMap;

    public Column getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Column primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        TableNameToClassName tableNameToClassName = new TableNameToClassName(Generator.config.getTablePrefix(), tableName, Generator.config.getTableSuffix());
        String className = tableNameToClassName.toClassName();
        this.className = className;

        this.classFullName = Generator.config.getPackageModel()+"."+className;
        this.daoFullName = Generator.config.getPackageDao()+"."+className+"Mapper";
        this.daoName = className + "Mapper";
    }

    public Map<String, Column> getColumnMap() {
        return columnMap;
    }

    public void setColumnMap(Map<String, Column> columnMap) {
        this.columnMap = columnMap;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    public String getDaoFullName() {
        return daoFullName;
    }

    public void setDaoFullName(String daoFullName) {
        this.daoFullName = daoFullName;
    }

    public String getDaoName() {
        return daoName;
    }

    public void setDaoName(String daoName) {
        this.daoName = daoName;
    }
}
