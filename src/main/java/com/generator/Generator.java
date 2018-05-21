package com.generator;

import sun.dc.pr.PRError;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class Generator {
    public static Config config;

    public static Connection connection = null;
    public static void main(String[] args) throws SQLException, IOException {
        Generator generator = new Generator();
        generator.generator();
    }

    public void generator() throws IOException, SQLException {
        URL resource = Generator.class.getClassLoader().getResource("generator.properties");
        System.out.println(resource);
        Properties properties = new Properties();
        properties.load(Generator.class.getClassLoader().getResourceAsStream("generator.properties"));
        config = new Config(properties);
        connInit();
        Table table = tableInfo(config.getTableName());
        GeneratorModel generator = new GeneratorModel(table);
        generator.generator();

        GeneratorMapper generatorMapper = new GeneratorMapper(table);
        generatorMapper.generator();

        GeneratorDao generatorDao = new GeneratorDao(table);
        generatorDao.generator();

        connection.close();
    }


    private void connInit() {
        try {
            Class.forName(config.getDataSourceDriver());
            connection = DriverManager.getConnection(config.getDataSourceUrl(), config.getDataSourceUsername(), config.getDataSourcePassword());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询所有表
     * @return
     */
    private List<String> tables() throws SQLException {
        List<String> tables = new LinkedList<>();
        String sql = "select table_name from user_tables";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String string = resultSet.getString(1);
            System.out.println(string);
            tables.add(string);
        }

        if (config.getTableName() != null && config.getTableName().length() > 0 && tables.contains(config.getTableName())) {

        }else {
            //throw new RuntimeException("制定表明错误，未找到制定的表");
        }
        statement.close();
        return tables;
    }

    private Table tableInfo(String tableName) {
        String sql = "";
        System.out.println(sql);
        Map<String, Column> map = new LinkedHashMap<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Table table = new Table();
        try {
            sql = "select cu.COLUMN_NAME,cu.COLUMN_NAME from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name and au.constraint_type = 'P' and au.table_name = '"+tableName.toUpperCase()+"'";
            System.out.println(sql);
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String colName = resultSet.getString(1);
                String colType = resultSet.getString(2);
                System.out.println(colName + "\t" + colType + "\t");
                Column column = new Column(colName, colType, null);
                map.put(colName, column);
            }

            sql = "select cname,coltype,width from col where tname='"+tableName.toUpperCase()+"'";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String colName = resultSet.getString(1);
                String colType = resultSet.getString(2);
                String length = resultSet.getString(3);
                System.out.println(colName + "\t" + colType + "\t" + length);
                Column column = new Column(colName, colType, length);
                if (map.containsKey(colName)) {
                    column.setPrimaryKey(true);
                    table.setPrimaryKey(column);
                }
                map.put(column.getColName(), column);
            }
            resultSet.close();
            statement.close();
            table.setTableName(tableName);
            table.setColumnMap(map);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                resultSet.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            try {
                statement.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return table;
    }


}
