package com.atinbo.cache.caffeine;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.spi.SessionFactoryOptions;
import org.hibernate.cache.cfg.spi.DomainDataRegionBuildingContext;
import org.hibernate.cache.cfg.spi.DomainDataRegionConfig;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.hibernate.cache.spi.support.RegionFactoryTemplate;
import org.hibernate.cache.spi.support.StorageAccess;
import org.hibernate.engine.spi.SessionFactoryImplementor;

import java.util.Map;

/**
 * @author zenghao
 * @date 2019-07-19
 */
@Slf4j
public class CaffeineRegionFactory extends RegionFactoryTemplate {

    @Override
    protected StorageAccess createQueryResultsRegionStorageAccess(String regionName,
                                                                  SessionFactoryImplementor sessionFactory) {
        return new CaffeineDataRegion(regionName);
    }

    @Override
    protected StorageAccess createTimestampsRegionStorageAccess(String regionName,
                                                                SessionFactoryImplementor sessionFactory) {
        return new CaffeineDataRegion(regionName);
    }

    @Override
    protected DomainDataStorageAccess createDomainDataStorageAccess(DomainDataRegionConfig regionConfig,
                                                                    DomainDataRegionBuildingContext buildingContext) {
        return new CaffeineDataRegion(regionConfig.getRegionName());
    }

    @Override
    protected void prepareForUse(SessionFactoryOptions settings, @SuppressWarnings("rawtypes") Map configValues) {
        log.debug("RegionFactory is starting... options={}, properties={}", settings, configValues);
    }

    @Override
    protected void releaseFromUse() {

    }
}
