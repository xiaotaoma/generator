package com.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.generator.GeneratorUtils.writeModel;

/**
 * 生成dao
 */
public class GeneratorDao {
    private Table table;

    public GeneratorDao(Table table) {
        this.table = table;
    }

    public void generator() throws IOException {
        String[] split = Generator.config.getPackageDao().split("\\.");
        File file = new File("");
        System.out.println(file.getAbsolutePath());
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath + "/src/main/java";
        for (int i = 0; i < split.length; i++) {
            file = new File(absolutePath + "/" + split[i]);
            if (!file.exists()) {
                file.mkdir();
            }
            absolutePath = file.getAbsolutePath();
        }

        File model = new File(absolutePath + "/"+ table.getClassName() + "Mapper.java");
        if (!model.exists()) {
            model.createNewFile();
        }

        System.out.println(model.getName());
        StringBuilder importMsg = new StringBuilder();
        importMsg.append("\nimport ").append(table.getClassFullName()).append(";");

        StringBuilder update = new StringBuilder();
        StringBuilder getByPid = new StringBuilder();
        Column primaryKey = table.getPrimaryKey();
        if (primaryKey != null) {
            update.append("\n    int update(").append(table.getClassName()).append(" ").append(GeneratorUtils.firstLetterLow(table.getClassName())).append(");");
            getByPid.append("\n    ").append(table.getClassName()).append(" getByPid(").append(primaryKey.getJavaTypeName())
                    .append(" ").append(GeneratorUtils.firstLetterLow(primaryKey.getFieldName())).append(");");

            importMsg.append("\nimport ").append(primaryKey.getJavaType()).append(";");
        }

        FileWriter fileWriter = new FileWriter(model);

        fileWriter.write("package " + Generator.config.getPackageDao() + ";");
        fileWriter.write("\n");
        fileWriter.write(importMsg.toString());
        fileWriter.write("\n");
        fileWriter.write("\npublic interface " + table.getDaoName() + "{");
        fileWriter.write("\n");

        StringBuilder insert = new StringBuilder();
        insert.append("\n    int insert(").append(table.getClassName()).append(" ").append(GeneratorUtils.firstLetterLow(table.getClassName())).append(");");

        fileWriter.write(insert.toString());
        fileWriter.write("\n");

        if (primaryKey != null) {
            fileWriter.write(update.toString());
            fileWriter.write("\n");

            fileWriter.write(getByPid.toString());
            fileWriter.write("\n");
        }

        fileWriter.write("\n");
        fileWriter.write("}");
        fileWriter.flush();
        fileWriter.close();

    }
}
