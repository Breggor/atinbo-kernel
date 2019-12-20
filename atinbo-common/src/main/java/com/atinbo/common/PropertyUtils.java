package com.atinbo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyUtils {

    protected static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
    private ResourceBundle resourceBundle = null;

    public PropertyUtils(String propertyName) {
        try {
            resourceBundle = ResourceBundle.getBundle(propertyName);
        } catch (MissingResourceException e) {
            logger.warn(String.format("file '%s.properties' not found!", propertyName));
            throw e;
        }
    }

    public String getPropertyValue(String key) {
        if (resourceBundle.containsKey(key)) {
            return resourceBundle.getString(key);
        } else {
            logger.warn(String.format("key '%s' not found!", key));
            return null;
        }
    }
}
