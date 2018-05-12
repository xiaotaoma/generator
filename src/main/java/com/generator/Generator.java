package com.generator;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class Generator {
    private static String driverName = "oracle.jdbc.driver.OracleDriver";
    private static String url = "jdbc:oracle:thin:@47.95.237.183:1521:orcl";
    private static String user = "NUMYSQL-JZ";
    private static String password = "Zkzn2017";

    private static String model_package = "com.test.test.model";//model文件路径
    private static String mapper_package = "com.test.test.mapper";//dao文件路径
    private static String xml_package = "com.test.test.xml";//xml文件路径
    private static String table = "";
    private static Connection connection = null;
    public static void main(String[] args) throws SQLException {
        /*String rootPath = getRootPath();
        packageInit(rootPath, mapper_package);
        packageInit(rootPath, model_package);
        packageInit(rootPath, xml_package);*/
        connInit();
        //List<String> tables = tables();
        Table table = tableInfo("SYS_APP_USER");
        GeneratorModel generator = new GeneratorModel(table, model_package);
        generator.generator();


        connection.close();
    }

    public static Method getMethod(String fieldName, Class clazz){
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return clazz.getMethod(sb.toString());
        } catch (Exception e) {

        }
        return null;
    }

    public static Set<Method> get_getDeclared_methods(Class T) {
        Method[] methods = T.getMethods();
        Set<Method> methodSet = new HashSet<Method>();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                methodSet.add(method);
            }
        }
        return methodSet;
    }

    private static String getRootPath() {
        File file = new File("");
        try {
            return file.getCanonicalPath();
        }catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    private static void packageInit(String rootPath, String packagePath){
        String javaPath = rootPath + "\\src\\main\\java";
        String[] split = packagePath.split("\\.");
        for (int i = 0; i < split.length; i++) {
            javaPath += "\\"+split[i];
            File file = new File(javaPath);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    private static void connInit() {
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询所有表
     * @return
     */
    private static List<String> tables() throws SQLException {
        List<String> tables = new LinkedList<>();
        String sql = "select table_name from user_tables";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String string = resultSet.getString(1);
            System.out.println(string);
            tables.add(string);
        }

        if (table != null && table.length() > 0 && tables.contains(table)) {

        }else {
            //throw new RuntimeException("制定表明错误，未找到制定的表");
        }
        statement.close();
        return tables;
    }

    private static Table tableInfo(String tableName) throws SQLException {
        String sql = "select cname,coltype,width from col where tname='"+tableName+"'";
        System.out.println(sql);
        List<Column> list = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String colName = resultSet.getString(1);
            String jdbcType = resultSet.getString(2);
            String length = resultSet.getString(3);
            System.out.println(colName + "\t" + jdbcType + "\t" + length);
            Column column = new Column(colName, jdbcType, length);
            System.out.println(column);
            list.add(column);
        }
        Table table = new Table();
        table.setTableName(tableName);
        table.setColumns(list);
        return table;
    }


}
