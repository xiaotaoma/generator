package com.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class GeneratorMapper {

    private Table table;
    private String mapperName;

    public GeneratorMapper(Table table) {
        this.table = table;
        this.mapperName = table.getClassName() + "Mapper";
    }

    public void generator() throws IOException {
        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        String packageXml = Generator.config.getPackageXml();
        if (packageXml == null || packageXml.equals("")) {
            packageXml = "/src/main/resources/mapper";
        }else {
            absolutePath = absolutePath + "/src/main/java";
        }

        String[] split = packageXml.split("\\.");
        for (int i = 0; i < split.length; i++) {
            file = new File(absolutePath + "/" + split[i]);
            if (!file.exists()) {
                file.mkdir();
            }
            absolutePath = file.getAbsolutePath();
        }

        File model = new File(absolutePath + "/"+ mapperName + ".xml");
        if (!model.exists()) {
            model.createNewFile();
        }
        System.out.println(model.getName());

        FileWriter fileWriter = new FileWriter(model);
        fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        fileWriter.write("\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        fileWriter.write("\n<mapper namespace=\""+table.getDaoFullName()+"\">");

        Column primaryKey = table.getPrimaryKey();

        StringBuilder resultMap = new StringBuilder();
        StringBuilder columns = new StringBuilder();
        StringBuilder completeColumns = new StringBuilder();
        StringBuilder insert = new StringBuilder();
        StringBuilder update = new StringBuilder();
        StringBuilder wherePK = new StringBuilder();
        StringBuilder getByPid = new StringBuilder();

        if (primaryKey != null) {
            getByPid.append("\n    <select id=\"getByPid\" parameterType=\"").append(primaryKey.getJavaType()).append("\" resultMap=\"BaseResultMap\">");
            getByPid.append("\n        select <include refid=\"columns\"/> from tb_dz_line where ").append(primaryKey.getColName()).append(" = ").append("#{").append(primaryKey.getFieldName())
                    .append(", jdbcType=").append(primaryKey.getJdbcType()).append("}");
            getByPid.append("\n    </select>");
        }

 /*<select id="getByPid" parameterType="java.lang.Integer" resultMap="BaseResultMap">
                select <include refid="columns"/> from tb_dz_line where id = #{id}
    </select>*/


        resultMap.append("\n    <resultMap id=\"BaseResultMap\" type=\""+table.getClassFullName()+"\">");
        columns.append("\n    <sql id=\"columns\">\n        ");
        completeColumns.append("\n    <sql id=\"completeColumns\">\n        ");
        insert.append("\n    <insert id=\"insert\" parameterType=\""+ table.getClassFullName() +"\">\n        ");
        insert.append("insert into ").append(table.getTableName()).append(" (");
        insert.append("\n        <include refid=\"columns\"/>").append(") \n        values (\n        ");
        update.append("\n    <update id=\"update\" parameterType=\"").append(table.getClassName()).append("\">");
        update.append("\n        update ").append(table.getTableName()).append(" set");



        Map<String, Column> columnMap = table.getColumnMap();
        Iterator<Map.Entry<String, Column>> iterator = columnMap.entrySet().iterator();
        int n = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Column> entry = iterator.next();
            Column value = entry.getValue();
            resultMap.append("\n        ").append("<result column=\"").append(value.getColName()).append("\" property=\"").
                    append(value.getFieldName()).append("\" javaType=\"").append(value.getJavaType()).append("\" jdbcType=\"").
                    append(value.getJdbcType()).append("\"/>");
            columns.append(value.getColName());
            completeColumns.append(table.getTableName()).append(".").append(value.getColName());

            String colValue = "#{" + value.getFieldName() + ", jdbcType=" + value.getJdbcType() + "}";
            insert.append(colValue);

            if (!value.isPrimaryKey()) {
                update.append("\n        ").append(value.getColName()).append(" = ").append(colValue);
            }else {
                wherePK.append("\n        where ").append(value.getColName()).append(" = ").append(colValue);
            }

            n++;
            if (n < columnMap.size()) {
                columns.append(",");
                completeColumns.append(",");
                insert.append(",");
                if (!value.isPrimaryKey()) {
                    update.append(",");
                }
            }
            if (n > 0 && n%4 == 0) {
                columns.append("\n        ");
                completeColumns.append("\n        ");
                insert.append("\n        ");
            }
        }

        resultMap.append("\n");
        resultMap.append("    </resultMap>");
        resultMap.append("\n");

        columns.append("\n    </sql>");
        completeColumns.append("\n    </sql>");
        insert.append("\n        )\n    </insert>");
        update.append(wherePK).append("\n    </update>");


        fileWriter.write(resultMap.toString());
        fileWriter.write(columns.toString());
        fileWriter.write(completeColumns.toString());
        fileWriter.write(insert.toString());
        if (primaryKey != null) {
            fileWriter.write(update.toString());
            fileWriter.write(getByPid.toString());
        }


        fileWriter.write("\n</mapper>");
        fileWriter.flush();
        fileWriter.close();

    }

    private void resultMap() throws IOException {
        StringBuilder resultMap = new StringBuilder();
        resultMap.append("\n    <resultMap id=\"BaseResultMap\" type=\""+table.getClassName()+"\">");

        Map<String, Column> columnMap = table.getColumnMap();
        Iterator<Map.Entry<String, Column>> iterator = columnMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Column> entry = iterator.next();
            Column value = entry.getValue();
            resultMap.append("\n        ").append("<result column=\"").append(value.getColName()).append("\" property=\"").
                    append(value.getFieldName()).append("\" javaType=\"").append(value.getJavaType()).append("\" jdbcType=\"").
                    append(value.getJdbcType()).append("\"/>");
        }

        resultMap.append("\n");
        resultMap.append("</resultMap>");
        resultMap.append("\n");
        resultMap.append("\n");
    }
}
