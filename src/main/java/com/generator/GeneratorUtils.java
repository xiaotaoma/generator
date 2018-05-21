package com.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class GeneratorUtils {

    public static String firstLetterUp(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
    }

    public static String firstLetterLow(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        return s.substring(0, 1).toLowerCase() + s.substring(1, s.length());
    }

    public static int space(int space) {
        space += 4;
        return space;
    }

    public static int backSpace(int space) {
        space = space - 4;
        return space;
    }

    /**
     * 数据库字段转化java字段名
     * 忽略下划线，已下划线分割，首字母大写
     * @param name
     * @return
     */
    public static String nameFix(String name) {
        name = name.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder();
        StringTokenizer stringTokenizer = new StringTokenizer(name, "_");
        while (stringTokenizer.hasMoreTokens()) {
            String s = stringTokenizer.nextToken();
            s = GeneratorUtils.firstLetterUp(s);
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }

    private static void writeField(StringBuilder stringBuilder, Column column, StringBuilder importMsg) {
        stringBuilder.append("\n    ").append("private ").append(column.getJavaTypeName()).append(" ").append(column.getFieldName()).append(";");
        if (column.getJavaType().indexOf("Date") > 0 && importMsg.indexOf("Date") <= 0) {
            importMsg.append("\nimport java.util.Date;");
        }
        if (column.getJavaType().indexOf("BigDecimal") > 0 && importMsg.indexOf("BigDecimal") <= 0) {
            importMsg.append("\nimport java.math.BigDecimal;");
        }
    }

    private static void writeGet(StringBuilder stringBuilder, Column column){
        /*public String getTableName() {
            return tableName;
        }*/
        stringBuilder.append("\n    public ").append(column.getJavaTypeName()).append(" ").append(column.getGetMethodName()).append("() {");
        stringBuilder.append("\n        return ").append(column.getFieldName()).append(";");
        stringBuilder.append("\n    }\n");
    }



    private static void writeSet(StringBuilder stringBuilder, Column column) {
        /*public void setPrimaryKey(Boolean primaryKey) {
            this.primaryKey = primaryKey;
        }*/
        stringBuilder.append("\n    public void ").append(column.getSetMethodName()).append("(").append(column.getJavaTypeName()).append(" ").append(column.getFieldName()).append(") {");
        stringBuilder.append("\n        this.").append(column.getFieldName()).append(" = ").append(column.getFieldName()).append(";");
        stringBuilder.append("\n    }\n");
    }

    public static void writeModel(FileWriter writer, Table table) throws IOException {
        Map<String, Column> map = table.getColumnMap();
        Iterator<Map.Entry<String, Column>> iterator = map.entrySet().iterator();
        StringBuilder importMsg = new StringBuilder();//导入包信息
        StringBuilder filedMsg = new StringBuilder();//字段信息
        StringBuilder getMethod = new StringBuilder();//get方法
        StringBuilder setMethod = new StringBuilder();//set方法

        while (iterator.hasNext()) {
            Map.Entry<String, Column> entry = iterator.next();
            Column column = entry.getValue();
            writeField(filedMsg, column, importMsg);
            writeSet(setMethod, column);
            writeGet(getMethod, column);
        }
        if (importMsg.length() > 0) {
            writer.write(importMsg.toString());
            writer.write("\n");
        }

        writer.write("\n");
        writer.write("public class " + table.getClassName() + "{");
        writer.write("\n");
        writer.write(filedMsg.toString());
        writer.write("\n");

        writer.write(getMethod.toString());
        writer.write(setMethod.toString());

        writer.write("\n}");
    }

    public static String jdbcTypeToJavaType(String jdbcType) {
        String javaType = "";
        switch (jdbcType) {
            case "CHAR":
                javaType = "java.lang.String"; break;
            case "VARCHAR":
                javaType = "java.lang.String"; break;
            case "LONGVARCHAR":
                javaType = "java.lang.String"; break;
            case "NUMERIC":
                javaType = "java.math.BigDecimal"; break;
            case "DECIMAL":
                javaType = "java.math.BigDecimal";break;
            case "BIT":
                javaType = "java.lang.Boolean"; break;
            case "BOOLEAN":
                javaType = "java.lang.Boolean"; break;
            case "TINYINT":
                javaType = "java.lang.Byte"; break;
            case "SMALLINT":
                javaType = "java.lang.Short"; break;
            case "INTEGER":
                javaType = "java.lang.Integer"; break;
            case "BIGINT":
                javaType = "java.lang.Long"; break;
            case "REAL":
                javaType = "java.lang.Float"; break;
            case "FLOAT":
                javaType = "java.lang.Double"; break;
            case "DOUBLE":
                javaType = "java.lang.Double"; break;
            case "DATE":
                javaType = "java.util.Date"; break;
            case "TIME":
                javaType = "java.util.Date"; break;
            case "TIMESTAMP":
                javaType = "java.util.Date"; break;
            case "NUMBER":
                javaType = "java.math.BigDecimal"; break;
            default:
                javaType = "java.lang.String"; break;
        }
        return javaType;
        /*CHAR                String
        VARCHAR             String
        LONGVARCHAR         String
        NUMERIC             java.math.BigDecimal
        DECIMAL             java.math.BigDecimal
        BIT             boolean
        BOOLEAN             boolean
        TINYINT             byte
        SMALLINT            short
        INTEGER             int
        BIGINT              long
        REAL                float
        FLOAT               double
        DOUBLE              double
        BINARY              byte[]
        VARBINARY           byte[]
        LONGVARBINARY               byte[]
        DATE                java.sql.Date
        TIME                java.sql.Time
        TIMESTAMP           java.sql.Timestamp
        CLOB                Clob
        BLOB                Blob
        ARRAY               Array
        DISTINCT            mapping of underlying type
        STRUCT              Struct
        REF                         Ref
        DATALINK            java.net.URL[color=red][/color*/
    }

    /**
     * oracle 数据类型对应mybatis jdbcType
     * @return
     */
    public static String oracleColTypeToJdbcType(String colType) {
        String jdbcType = "VARCHAR";
        switch (colType) {
            case "BLOB":    jdbcType = "BLOB";  break;
            case "CHAR":    jdbcType = "CHAR";  break;
            case "CLOB":    jdbcType = "CLOB";  break;
            case "DATE":    jdbcType = "TIMESTAMP";  break;
            case "TIMESTAMP":    jdbcType = "TIMESTAMP";  break;
            case "TIMESTAMP(6)":    jdbcType = "TIMESTAMP";  break;
            case "DECIMAL":    jdbcType = "DECIMAL";  break;
            case "DOUBLE":    jdbcType = "NUMBER";  break;
            case "FLOAT":    jdbcType = "FLOAT";  break;
            case "INTEGER":    jdbcType = "INTEGER";  break;
            case "LONGVARCHAR":    jdbcType = "LONG VARCHAR";  break;
            case "NCHAR":    jdbcType = "NCHAR";  break;
            case "NCLOB":    jdbcType = "NCLOB";  break;
            case "NUMERIC":    jdbcType = "NUMERIC";  break;
            case "NUMBER":    jdbcType = "NUMERIC";  break;
            case "SMALLINT":    jdbcType = "SMALLINT";  break;
            case "VARCHAR":    jdbcType = "VARCHAR";  break;
            case "VARCHAR2":    jdbcType = "VARCHAR";  break;
            default: jdbcType = "VARCHAR"; break;
        }
        return jdbcType;
    }
}
