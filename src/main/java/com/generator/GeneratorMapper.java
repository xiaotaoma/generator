package com.generator;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GeneratorMapper {

    private Table table;
    private String basePackage;
    private String mapperName;

    public GeneratorMapper(Table table, String basePackage) {
        this.table = table;
        this.basePackage = basePackage;
        this.mapperName = table.getClassName() + "Mapper";
    }

    public void generator() throws IOException {
        String[] split = basePackage.split("\\.");
        File file = new File("");
        System.out.println(file.getAbsolutePath());
        String absolutePath = file.getAbsolutePath();
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
        fileWriter.write("\n<mapper namespace=\""+table.getTableName()+"\">");
        fileWriter.write("\n");

        resultMap(fileWriter);

        int space = 0;
        space = GeneratorUtils.space(space);
        GeneratorUtils.writeSpace(fileWriter, space);
        fileWriter.write("<select id=\"listAll\" resultType=\""+table.getTableName()+"\">");
        fileWriter.write("\n");
        space = GeneratorUtils.space(space);
        GeneratorUtils.writeSpace(fileWriter, space);
        String select = "select * from " + table.getTableName();
        fileWriter.write(select);
        fileWriter.write("\n");
        space = GeneratorUtils.backSpace(space);
        GeneratorUtils.writeSpace(fileWriter, space);
        fileWriter.write("</select>");
        fileWriter.write("\n");
        fileWriter.write("\n");

        GeneratorUtils.writeSpace(fileWriter, space);
        fileWriter.write("<insert id=\"insert\" parameterType=\""+table.getTableName()+"\">");
        fileWriter.write("\n");
        space = GeneratorUtils.space(space);
        GeneratorUtils.writeSpace(fileWriter, space);
        String insert = "insert into " + table.getTableName() + " (" + "col" + ") values (" + "#{col, jdbcType=VARCHAR}" + ")";
        fileWriter.write(insert);
        fileWriter.write("\n");
        space = GeneratorUtils.backSpace(space);
        GeneratorUtils.writeSpace(fileWriter, space);
        fileWriter.write("</insert>");
        fileWriter.write("\n");
        fileWriter.write("\n");

        GeneratorUtils.writeSpace(fileWriter, space);
        fileWriter.write("<update id=\"update\" parameterType=\""+table.getTableName()+"\">");
        fileWriter.write("\n");
        space = GeneratorUtils.space(space);
        GeneratorUtils.writeSpace(fileWriter, space);
        String updateSql = "update table_name set col = col where id = #{id}";
        fileWriter.write(updateSql);
        fileWriter.write("\n");
        space = GeneratorUtils.backSpace(space);
        GeneratorUtils.writeSpace(fileWriter, space);
        fileWriter.write("</update>");
        fileWriter.write("\n");
        fileWriter.write("\n");

        fileWriter.write("\n</mapper>");
        fileWriter.flush();
        fileWriter.close();


        /*OutputFormat format = new OutputFormat();

        format.setEncoding("UTF-8");
        format.setNewlines(true);

        Document document = DocumentHelper.createDocument();
        document.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
        //添加节点信息
        Element rootElement = document.addElement("mapper");
        rootElement.addAttribute("namespace", table.getClassName());

        Element select = rootElement.addElement("select");
        select.addAttribute("id", "listAll");
        select.addAttribute("resultType", table.getClassName());
        select.addText("select * from " + table.getTableName());

        Element insert = rootElement.addElement("insert");
        insert.addAttribute("id", "insert");
        insert.addAttribute("parameterType", table.getClassName());
        String insertSql = "insert into " + table.getTableName() + " (" + "col" + ") values (" + "#{col, jdbcType=VARCHAR}" + ")";
        insert.addText(insertSql);

        Element update = rootElement.addElement("update");
        update.addAttribute("id", "update");
        update.addAttribute("parameterType", table.getClassName());
        String updateSql = "update table_name set col = col where id = #{id}";
        update.addText(updateSql);

        try {
            System.out.println(document.asXML()); //将document文档对象直接转换成字符串输出
            FileWriter fileWriter = new FileWriter(model);
            //dom4j提供了专门写入文件的对象XMLWriter
            XMLWriter xmlWriter = new XMLWriter(fileWriter, format);
            xmlWriter.write(document);
            xmlWriter.flush();
            xmlWriter.close();
            System.out.println("xml文档添加成功！");
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    private void resultMap(FileWriter fileWriter) throws IOException {
        List<Column> columns = table.getColumns();
        int space = 4;
/*<resultMap id="BaseResultMap" type="com.zkzn.dzgj.model.AppMyUser">
        <result column="" property="" javaType="" jdbcType=""/>
    </resultMap>*/

        GeneratorUtils.writeSpace(fileWriter, space);
        fileWriter.write("<resultMap id=\"BaseResultMap\" type=\""+table.getClassName()+"\">");

        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);
            fileWriter.write("\n");
            space = GeneratorUtils.space(space);
            GeneratorUtils.writeSpace(fileWriter, space);
            String result = "<result column=\""+column.getColName()+"\" property=\""+column.getFieldName()+"\" javaType=\""+column.getJavaType()+"\" jdbcType=\""+column.getJdbcType()+"\"/>";
            fileWriter.write(result);
            space = GeneratorUtils.backSpace(space);
        }
        fileWriter.write("\n");
        GeneratorUtils.writeSpace(fileWriter, space);
        fileWriter.write("</resultMap>");
        fileWriter.write("\n");
        fileWriter.write("\n");



    }
}
