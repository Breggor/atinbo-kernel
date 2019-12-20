package com.atinbo.oss.strategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 默认实现 文件名使用 UUID、文件路径使用 日期
 *
 * @author zenghao
 * @date 2019-11-14
 */
public class DefaultStrategy implements RenameStrategy {

    @Override
    public String fileName(String fileName) {
        return UUID.randomUUID().toString();
    }

    @Override
    public String filePath(String fileName) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
