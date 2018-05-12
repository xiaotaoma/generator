package com.generator;

import static com.generator.GeneratorUtils.nameFix;

/**
 * 表名转化类名
 */
public class TableNameToClassName {
    private String prefix;
    private String tableName;
    private String suffix;

    /**
     *
     * @param prefix 表名忽略前缀
     * @param tableName 表名
     * @param suffix 表名忽略后缀
     */
    public TableNameToClassName(String prefix, String tableName, String suffix) {
        this.prefix = prefix.toLowerCase();
        this.tableName = tableName.toLowerCase();
        this.suffix = suffix.toLowerCase();
    }

    public String toClassName() {
        prefix();
        suffix();
        return nameFix(tableName);
    }

    /**
     * 表名前缀处理
     */
    private void prefix() {
        if (tableName.startsWith(prefix)) {
            tableName = tableName.substring(prefix.length(), tableName.length());
        }
    }

    /**
     * 表名后缀处理
     */
    private void suffix() {
        if (tableName.endsWith(suffix)) {
            tableName = tableName.substring(0, tableName.length() - suffix.length());
        }
    }


}
