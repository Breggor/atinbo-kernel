package com.atinbo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * property工具类
 *
 * @author breggor
 */
public class PropertyUtils {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
    private final ResourceBundle resourceBundle;

    public PropertyUtils(String propertyName) {
        try {
            resourceBundle = ResourceBundle.getBundle(propertyName);
        } catch (MissingResourceException e) {
            logger.warn(String.format("file '%s.properties' not found!", propertyName));
            throw e;
        }
    }

    public String getPropertyValue(String key) {
        if (!resourceBundle.containsKey(key)) {
            logger.warn(String.format("key '%s' not found!", key));
            return null;
        }
        return resourceBundle.getString(key);
    }
}
