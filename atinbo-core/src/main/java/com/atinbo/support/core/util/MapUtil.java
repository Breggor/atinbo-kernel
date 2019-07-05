package com.atinbo.support.core.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class MapUtil {

    /**
     * 返回值:String 形如 username'chenziwen^password'1234
     */
    public static String transMapToString(Map<String, String> map) {
        Entry<?, ?> entry;
        StringBuilder sBuilder = new StringBuilder();
        for (Iterator<?> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Entry<?, ?>) iterator.next();
            sBuilder.append(entry.getKey().toString())
                    .append("'")
                    .append(null == entry.getValue() ? "" : entry.getValue().toString())
                    .append(iterator.hasNext() ? "^" : "");
        }
        return sBuilder.toString();
    }

    /**
     * 传入参数:mapString 形如 username'chenziwen^password'1234
     */
    public static Map<String, String> transStringToMap(String mapString) {
        Map<String, String> map = new HashMap<String, String>();
        StringTokenizer items;
        for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys.hasMoreTokens();
             map.put(items.nextToken(), items.hasMoreTokens() ? ((String) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), "'");
        return map;
    }
}
