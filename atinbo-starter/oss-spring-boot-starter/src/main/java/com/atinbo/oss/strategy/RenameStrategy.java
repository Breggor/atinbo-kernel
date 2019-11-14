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
     * @return
     */
    String fileName(String fileName);

    /**
     * 生成新的文件路径
     * @return
     */
    String filePath(String fileName);
}
