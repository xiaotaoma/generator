package com.generator;

import java.io.File;
import java.io.FileWriter;

import static com.generator.GeneratorUtils.firstLetterLow;

public class GeneratorService {

    private String className;//TestService
    private String path;//com.test

    private String modelName;//Test
    private String modelClass;//com.test.Test
    private String daoName;//TestMapper
    private String daoClass;//com.test.TestMapper
    private Column primaryKey;

    public GeneratorService(Table table, Config config) {
        String className = table.getClassName();
        String classFullName = table.getClassFullName();
        this.className = config.getServicePath() + "." + table.getClassName();
        this.path = config.getServicePath();

        this.modelName = table.getClassName();
        this.modelClass = config.getPackageModel() + "." + table.getClassName();

        this.daoName = table.getDaoName();
        this.daoClass = config.getPackageDao() + "." + table.getDaoName();

        this.primaryKey = table.getPrimaryKey();
    }

    public void generator() {
        String[] split = path.split("\\.");
        File file = new File("");
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath + "/src/main/java";
        for (int i = 0; i < split.length; i++) {
            file = new File(absolutePath + "/" + split[i]);
            if (!file.exists()) {
                file.mkdir();
            }
            absolutePath = file.getAbsolutePath();
        }

        System.out.println(className);
        File model = new File(absolutePath + "/"+ className + ".java");
        System.out.println(model.getName());
        try {
            FileWriter fileWriter = new FileWriter(model);
            fileWriter.write("package " + path + ";");
            fileWriter.write("\n");

            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void write(FileWriter fileWriter) {
        StringBuilder importMsg = new StringBuilder();//导入包信息
        StringBuilder filedMsg = new StringBuilder();//字段信息

        importMsg.append("\n\n").append(daoClass).append(";");
        importMsg.append("\norg.springframework.beans.factory.annotation.Autowired;");
        importMsg.append("\norg.springframework.stereotype.Service;");

        importMsg.append("\n\nimport java.util.List;");

        filedMsg.append("\n\n@Service");
        filedMsg.append("\npublic class ").append(className).append(" {");

        filedMsg.append("\n\n    @Autowired");
        filedMsg.append("\n    private ").append(daoName).append(" ").append(firstLetterLow(daoName)).append(";");

        filedMsg.append(listAll());//listAll();
        filedMsg.append(insert());//insert(Test test);
        filedMsg.append(update());//update(Test test);
        filedMsg.append(update());//update(Test test);

    }

    private String listAll() {
        StringBuilder listAll = new StringBuilder();
        listAll.append("\n\n    public List<").append(modelName).append("> listAll() {");
        listAll.append("\n        ").append("return ").append(firstLetterLow(daoName)).append(".listAll();");
        listAll.append("\n    }");
        return listAll.toString();
    }

    private String insert() {
        StringBuilder insert = new StringBuilder();
        insert.append("\n\n    ").append("public int insert(").append(modelName).append(" ").append(firstLetterLow(modelName)).append(") {");
        insert.append("\n        return ").append(firstLetterLow(daoName)).append(".insert(").append(firstLetterLow(daoName)).append(");");
        insert.append("\n    }");
        return insert.toString();
    }

    private String update() {
        if (primaryKey == null) {
            return "";
        }
        StringBuilder update = new StringBuilder();
        update.append("\n\n    ").append("public int update(").append(modelName).append(" ").append(firstLetterLow(modelName)).append(") {");
        update.append("\n        return ").append(firstLetterLow(daoName)).append(".update(").append(firstLetterLow(daoName)).append(");");
        update.append("\n    }");
        return update.toString();
    }

    private String getByPid() {
        if (primaryKey == null) {
            return "";
        }
        String fieldName = primaryKey.getFieldName();//
        String javaTypeName = primaryKey.getJavaTypeName();//String

        StringBuilder getByPid = new StringBuilder();
        getByPid.append("\n\n    public ").append(modelName).append(" getByPid() {");
        getByPid.append("\n        ").append("return ").append(firstLetterLow(daoName)).append(".listAll();");
        getByPid.append("\n    }");
        return getByPid.toString();
    }
}
