package com.atinbo.core.util;

/**
 * 标签工具类
 *
 * @author robin on 2016年7月28日
 */
public class TagUtils {

    /**
     * 标签截取
     */
    public static String subStringTag(String tag) {
        String subStringTag = "";
        if (tag != null && !"".equals(tag)) {
            if ((tag.split(",")).length > 3) {
                String substring = tag.substring(0, tag.indexOf(",", tag.indexOf(",", tag.indexOf(",", 0) + 1) + 1));
                subStringTag = substring.replaceAll(",", " ");
            } else if ((tag.split(",")).length == 1) {
                subStringTag = tag;
            } else if ((tag.split(",")).length > 1 && (tag.split(",")).length <= 3) {
                subStringTag = tag.replaceAll(",", " ");
            }
        }

        return subStringTag;
    }

}
