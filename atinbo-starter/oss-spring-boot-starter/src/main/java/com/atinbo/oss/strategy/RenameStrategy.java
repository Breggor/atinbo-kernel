package com.atinbo.oss.strategy;

/**
 * 重命名规则
 *
 * @author zenghao
 * @date 2019-11-14
 */
public interface RenameStrategy {

    /**
     * 生成新的文件名
     * @param fileName 原文件名
     * @return 默认实现为 使用 UUID
     */
    String fileName(String fileName);

    /**
     * 生成新的文件路径
     * @param fileName 原文件名
     * @return 默认实现为使用 年/月/日
     */
    String filePath(String fileName);
}
