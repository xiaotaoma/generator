package com.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
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

    /**
     * 缩进
     * @param fileWriter
     * @param space
     * @throws IOException
     */
    public static void writeSpace(FileWriter fileWriter, int space) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < space; i++) {
            stringBuilder.append(" ");
        }
        fileWriter.write(stringBuilder.toString());
    }

    private static void writeField(FileWriter writer, Column colnum, int space) throws IOException {
        String colName = colnum.getFieldName();
        writeSpace(writer, space(space));
        writer.write("private " + colnum.getJavaTypeName() + " " + colName + ";");
        writer.write("\n");
    }

    private static void writeGet(FileWriter writer, Column colnum, int space) throws IOException {
        space = space(space);
        writeSpace(writer, space);
        writer.write("private " + colnum.getJavaTypeName() + " " + colnum.getGetMethodName() + "(){");
        writer.write("\n");
        space = space(space);
        writeSpace(writer, space);
        writer.write("return " + colnum.getFieldName() + ";");
        writer.write("\n");
        space = backSpace(space);
        writeSpace(writer, space);
        writer.write("}");
        writer.write("\n");
    }

    private static void writeSet(FileWriter writer, Column colnum, int space) throws IOException {
        space = space(space);
        writeSpace(writer, space);
        writer.write("private " + colnum.getJavaTypeName() + " " + colnum.getSetMethodName() + "("+ colnum.getJavaTypeName() + " " + colnum.getFieldName() +"){");
        writer.write("\n");
        space = space(space);
        writeSpace(writer, space);
        writer.write("this." + colnum.getFieldName() + " = " + colnum.getFieldName() + ";");
        writer.write("\n");
        space = backSpace(space);
        writeSpace(writer,space);
        writer.write("}");
        writer.write("\n");
    }

    public static void writeModel(FileWriter writer, List<Column> list, int space) throws IOException {
        for (int i = 0; i < list.size(); i++) {
            Column column = list.get(i);
            writeField(writer, column, space);
        }
        writer.write("\n");
        writer.write("\n");
        for (int i = 0; i < list.size(); i++) {
            Column column = list.get(i);
            writeGet(writer, column, space);
            writeSet(writer, column, space);
        }
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
}
