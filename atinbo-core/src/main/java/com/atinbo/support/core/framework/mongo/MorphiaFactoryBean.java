package com.atinbo.support.core.framework.mongo;


import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.config.AbstractFactoryBean;


public class MorphiaFactoryBean extends AbstractFactoryBean<Morphia> {

    /**
     * 要扫描并映射的包
     */
    private String[] mapPackages;

    /**
     * 要映射的类
     */
    private String[] mapClasses;

    private boolean ignoreInvalidClasses;

    @Override
    protected Morphia createInstance() throws Exception {
        Morphia morphia = new Morphia();
        if (this.mapPackages != null) {
            for (String packageName : this.mapPackages) {
                morphia.mapPackage(packageName, this.ignoreInvalidClasses);
            }
        }
        if (this.mapClasses != null) {
            for (String entityClass : this.mapClasses) {
                morphia.map(Class.forName(entityClass));
            }
        }
        return morphia;
    }

    @Override
    public Class<?> getObjectType() {
        return Morphia.class;
    }

    public void setMapPackages(String[] mapPackages) {
        this.mapPackages = mapPackages;
    }

    public void setMapClasses(String[] mapClasses) {
        this.mapClasses = mapClasses;
    }

    public void setIgnoreInvalidClasses(boolean ignoreInvalidClasses) {
        this.ignoreInvalidClasses = ignoreInvalidClasses;
    }

}
