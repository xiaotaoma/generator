package com.generator;

import java.util.List;

public class Table {
    private String tableName;
    private String className;
    private List<Column> columns;

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
        TableNameToClassName tableNameToClassName = new TableNameToClassName("", tableName, "");
        String className = tableNameToClassName.toClassName();
        this.className = className;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
