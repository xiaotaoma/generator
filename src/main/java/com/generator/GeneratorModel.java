package com.generator;

import java.io.File;
import java.io.FileWriter;

import static com.generator.GeneratorUtils.writeModel;

public class GeneratorModel {
    private Table table;
    private String basePackage;

    public GeneratorModel(Table table, String basePackage) {
        this.table = table;
        this.basePackage = basePackage;
    }

    public void generator() {
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

        String tableName = table.getTableName();
        TableNameToClassName tableNameToClassName = new TableNameToClassName("", tableName, "");
        String className = tableNameToClassName.toClassName();
        System.out.println(className);
        File model = new File(absolutePath + "/"+ className + ".java");
        System.out.println(model.getName());
        try {
            int space = 0;
            FileWriter fileWriter = new FileWriter(model);
            fileWriter.write("package " + basePackage + ";");
            fileWriter.write("\n");
            fileWriter.write("\n");
            fileWriter.write("\n");
            fileWriter.write("public class " + className + "{");
            fileWriter.write("\n");
            fileWriter.write("\n");
            writeModel(fileWriter, table.getColumns(), space);
            fileWriter.write("\n");
            fileWriter.write("}");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
