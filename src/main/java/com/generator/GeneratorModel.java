package com.generator;

import java.io.File;
import java.io.FileWriter;

import static com.generator.GeneratorUtils.writeModel;

public class GeneratorModel {
    private Table table;

    public GeneratorModel(Table table) {
        this.table = table;
    }

    public void generator() {
        String[] split = Generator.config.getPackageModel().split("\\.");
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

        String className = table.getClassName();
        System.out.println(className);
        File model = new File(absolutePath + "/"+ className + ".java");
        System.out.println(model.getName());
        try {
            FileWriter fileWriter = new FileWriter(model);
            fileWriter.write("package " + Generator.config.getPackageModel() + ";");
            fileWriter.write("\n");
            writeModel(fileWriter, table);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
