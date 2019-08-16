package com.atinbo.dislock.store;

import com.atinbo.dislock.constant.ServerPattern;
import com.atinbo.dislock.exception.StoreIsEmptyException;

import java.util.HashMap;
import java.util.Map;

public class MapStore {

    private static Map<String, ServerPattern> serverPatternMap = new HashMap<>();

    static {
        serverPatternMap.put(ServerPattern.SINGLE.getPattern(), ServerPattern.SINGLE);
        serverPatternMap.put(ServerPattern.CLUSTER.getPattern(), ServerPattern.CLUSTER);
        serverPatternMap.put(ServerPattern.MASTER_SLAVE.getPattern(), ServerPattern.MASTER_SLAVE);
        serverPatternMap.put(ServerPattern.REPLICATED.getPattern(), ServerPattern.REPLICATED);
        serverPatternMap.put(ServerPattern.SENTINEL.getPattern(), ServerPattern.SENTINEL);
    }

    /**
     * 根据字符串模式标识返回服务器模式枚举类
     *
     * @param pattern
     * @return
     */
    public static ServerPattern getServerPattern(String pattern) throws StoreIsEmptyException {
        ServerPattern serverPattern = serverPatternMap.get(pattern);
        if (serverPattern == null) {
            throw new StoreIsEmptyException("没有找到相应的服务器模式,请检测参数是否正常,pattern的值为:" + pattern);
        }
        return serverPattern;
    }
}
