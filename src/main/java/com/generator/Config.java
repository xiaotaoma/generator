package com.generator;

import java.util.Properties;

public class Config {

    public Config (Properties properties) {
        this.dataSourceUrl = properties.getProperty("generator.datasource.url");
        this.dataSourceDriver = properties.getProperty("generator.datasource.driverClassName");
        this.dataSourceUsername = properties.getProperty("generator.datasource.username");
        this.dataSourcePassword = properties.getProperty("generator.datasource.password");
        this.packageModel = "com.model";
        if (properties.getProperty("generator.package.model") != null && !properties.getProperty("generator.package.model").equals("")) {
            this.packageModel = properties.getProperty("generator.package.model");
        }
        this.packageDao = "com.dao";
        if (properties.getProperty("generator.package.dao") != null && !properties.getProperty("generator.package.dao").equals("")) {
            this.packageDao = properties.getProperty("generator.package.dao");
        }
        this.packageXml = null;
        if (properties.getProperty("generator.package.xml") != null && !properties.getProperty("generator.package.xml").equals("")) {
            this.packageXml = properties.getProperty("generator.package.xml");
        }
        this.tablePrefix = properties.getProperty("generator.table.prefix");
        this.tableSuffix = properties.getProperty("generator.table.suffix");
        this.tableName = properties.getProperty("generator.table.name");

        if (dataSourceDriver.indexOf("oracle") >= 0) {
            dbType = "oracle";
        }else if (dataSourceDriver.indexOf("mysql") >= 0) {
            dbType = "mysql";
        }
    }

    private String dataSourceUrl;
    private String dataSourceDriver;
    private String dataSourceUsername;
    private String dataSourcePassword;
    private String packageModel;
    private String packageDao;
    private String packageXml;

    private String tablePrefix;
    private String tableSuffix;
    private String tableName;
    private String dbType;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataSourceUrl() {
        return dataSourceUrl;
    }

    public void setDataSourceUrl(String dataSourceUrl) {
        this.dataSourceUrl = dataSourceUrl;
    }

    public String getDataSourceDriver() {
        return dataSourceDriver;
    }

    public void setDataSourceDriver(String dataSourceDriver) {
        this.dataSourceDriver = dataSourceDriver;
    }

    public String getDataSourceUsername() {
        return dataSourceUsername;
    }

    public void setDataSourceUsername(String dataSourceUsername) {
        this.dataSourceUsername = dataSourceUsername;
    }

    public String getDataSourcePassword() {
        return dataSourcePassword;
    }

    public void setDataSourcePassword(String dataSourcePassword) {
        this.dataSourcePassword = dataSourcePassword;
    }

    public String getPackageModel() {
        return packageModel;
    }

    public void setPackageModel(String packageModel) {
        this.packageModel = packageModel;
    }

    public String getPackageDao() {
        return packageDao;
    }

    public void setPackageDao(String packageDao) {
        this.packageDao = packageDao;
    }

    public String getPackageXml() {
        return packageXml;
    }

    public void setPackageXml(String packageXml) {
        this.packageXml = packageXml;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }
}
