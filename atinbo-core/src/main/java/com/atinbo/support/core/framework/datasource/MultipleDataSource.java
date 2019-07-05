package com.atinbo.support.core.framework.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();


    public static void setDataSourceKey(String dataSource) {
        dataSourceKey.set(dataSource);
    }

    public static void clearDataSourceKey() {
        dataSourceKey.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceKey.get();
    }
}
