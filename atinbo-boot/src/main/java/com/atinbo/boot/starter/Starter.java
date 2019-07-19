//
// Source status recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atinbo.boot.starter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

public abstract class Starter {
    public static BeanFactory beanFactory;
    private static ApplicationContext springApplicationContext;
    private static DefaultListableBeanFactory defaultListableBeanFactory;

    public Starter() {
    }

    public static ApplicationContext getSpringApplicationContext() {
        return springApplicationContext;
    }

    public static void setSpringApplicationContext(ApplicationContext springApplicationContext) {
        if (Starter.springApplicationContext == null) {
            Starter.springApplicationContext = springApplicationContext;
            defaultListableBeanFactory = (DefaultListableBeanFactory) springApplicationContext.getAutowireCapableBeanFactory();
        }

    }

    public static DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return defaultListableBeanFactory;
    }
}
