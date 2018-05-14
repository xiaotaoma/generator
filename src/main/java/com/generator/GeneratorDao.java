package com.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.generator.GeneratorUtils.writeModel;

/**
 * 生成dao
 */
public class GeneratorDao {
    private GeneratorModel generatorModel;
    private Table table;
    private String basePackage;
    private String daoName;

    public GeneratorDao(Table table, String basePackage, GeneratorModel generatorModel) {
        this.table = table;
        this.basePackage = basePackage;
        this.daoName = table.getClassName() + "Mapper";
        this.generatorModel = generatorModel;
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

        File model = new File(absolutePath + "/"+ daoName + ".java");
        System.out.println(model.getName());

        try {
            int space = 0;
            FileWriter fileWriter = new FileWriter(model);
            fileWriter.write("package " + basePackage);
            fileWriter.write("\n");
            fileWriter.write("\nimport "+Generator.model_package + "." + table.getClassName() + ";");
            fileWriter.write("\nimport java.util.List;");
            fileWriter.write("\n");
            fileWriter.write("\npublic interface " + daoName + "{");
            fileWriter.write("\n");
            fileWriter.write("\n");
            writeInsert(fileWriter, space);
            writeUpdate(fileWriter, space);
            writeList(fileWriter, space);
            fileWriter.write("\n");
            fileWriter.write("}");
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInsert(FileWriter writer, int space) throws IOException {
        writer.write("\n");
        space = GeneratorUtils.space(space);
        GeneratorUtils.writeSpace(writer, space);
        writer.write("void insert(" + table.getClassName() + " " + GeneratorUtils.firstLetterLow(table.getClassName()) + ");");
        writer.write("\n");
        writer.write("\n");
    }

    private void writeUpdate(FileWriter writer, int space) throws IOException {
        writer.write("\n");
        space = GeneratorUtils.space(space);
        GeneratorUtils.writeSpace(writer, space);
        writer.write("void update(" + table.getClassName() + " " + GeneratorUtils.firstLetterLow(table.getClassName()) + ");");
        writer.write("\n");
        writer.write("\n");
    }

    public void writeList(FileWriter writer, int space) throws IOException {
        writer.write("\n");
        space = GeneratorUtils.space(space);
        GeneratorUtils.writeSpace(writer, space);
        writer.write("List<" + table.getClassName() + "> " +"listAll();");
        writer.write("\n");
        writer.write("\n");
    }
}
