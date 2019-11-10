package com.atinbo.dislock.config;

import com.atinbo.dislock.annotation.EnableDisLock;
import com.atinbo.dislock.constant.DisLockConsts;
import com.atinbo.dislock.constant.ServerPattern;
import com.atinbo.dislock.core.DisLockInterceptor;
import com.atinbo.dislock.core.ServerPatternFactory;
import com.atinbo.dislock.exception.UnknownLoadBalancerException;
import com.atinbo.dislock.exception.UnknownReadModeException;
import com.atinbo.dislock.exception.UnknownSubscriptionModeException;
import com.atinbo.dislock.service.LockServiceFactory;
import com.atinbo.dislock.service.impl.*;
import com.atinbo.dislock.util.ValidateUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.redisson.connection.balancer.LoadBalancer;
import org.redisson.connection.balancer.RandomLoadBalancer;
import org.redisson.connection.balancer.RoundRobinLoadBalancer;
import org.redisson.connection.balancer.WeightedRoundRobinBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动装配
 *
 * @author breggor
 */
@Configuration
@ConditionalOnBean(annotation = EnableDisLock.class)
@EnableConfigurationProperties(DisLockProperties.class)
public class DisLockAutoConfiguration {

    @Autowired
    private DisLockProperties disLockProperties;

    /**
     * 创建redisSonClient
     *
     * @return RedissonClient
     */
    @Bean(name = "disLockRedissonClient" , destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws URISyntaxException {
        Config config = new Config();
        ServerPattern serverPattern = ServerPatternFactory.getServerPattern(disLockProperties.getPattern());

        if (serverPattern == ServerPattern.SINGLE) {
            SingleServerConfig singleServerConfig = config.useSingleServer();
            initSingleConfig(singleServerConfig);
        }
        if (serverPattern == ServerPattern.CLUSTER) {
            ClusterServersConfig clusterConfig = config.useClusterServers();
            initClusterConfig(clusterConfig);
        }
        if (serverPattern == ServerPattern.MASTER_SLAVE) {
            MasterSlaveServersConfig masterSlaveConfig = config.useMasterSlaveServers();
            initMasterSlaveConfig(masterSlaveConfig);
        }
        if (serverPattern == ServerPattern.REPLICATED) {
            ReplicatedServersConfig replicatedServersConfig = config.useReplicatedServers();
            initReplicatedServersConfig(replicatedServersConfig);
        }
        if (serverPattern == ServerPattern.SENTINEL) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            initSentinelServersConfig(sentinelServersConfig);
        }
        return Redisson.create(config);
    }

    @Bean
    @ConditionalOnMissingBean
    public LockServiceFactory serviceBeanFactory() {
        return new LockServiceFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReentrantLockServiceImpl reentrantLockServiceImpl() {
        return new ReentrantLockServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public FairLockServiceImpl fairLockServiceImpl() {
        return new FairLockServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public MultiLockServiceImpl multiLockServiceImpl() {
        return new MultiLockServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public RedLockServiceImpl redLockServiceImpl() {
        return new RedLockServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReadLockServiceImpl readLockServiceImpl() {
        return new ReadLockServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public WriteLockServiceImpl writeLockServiceImpl() {
        return new WriteLockServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DisLockInterceptor lockInterceptor() {
        return new DisLockInterceptor();
    }

    /**
     * 初始化单机模式参数
     *
     * @param singleServerConfig 单机模式配置
     */
    private void initSingleConfig(SingleServerConfig singleServerConfig) throws URISyntaxException {
        DisLockProperties.SingleConfig singleConfig = disLockProperties.getSingleServer();
        singleServerConfig.setAddress(String.format("%s%s%s%s" , DisLockConsts.REDIS_URL_PREFIX, singleConfig.getAddress(), DisLockConsts.COLON, singleConfig.getPort()));
        singleServerConfig.setClientName(disLockProperties.getClientName());
        singleServerConfig.setConnectionMinimumIdleSize(singleConfig.getConnMinIdleSize());
        singleServerConfig.setConnectionPoolSize(singleConfig.getConnPoolSize());
        singleServerConfig.setConnectTimeout(singleConfig.getConnTimeout());
        singleServerConfig.setDatabase(singleConfig.getDatabase());
        singleServerConfig.setDnsMonitoringInterval(singleConfig.getDnsMonitoringInterval());
        singleServerConfig.setIdleConnectionTimeout(singleConfig.getIdleConnTimeout());
        singleServerConfig.setKeepAlive(singleConfig.isKeepAlive());
        singleServerConfig.setPassword(singleConfig.getPassword());
        singleServerConfig.setRetryAttempts(singleConfig.getRetryAttempts());
        singleServerConfig.setRetryInterval(singleConfig.getRetryInterval());
        singleServerConfig.setSslEnableEndpointIdentification(disLockProperties.isSslEnableEndpointIdentification());
        if (disLockProperties.getSslKeystore() != null) {
            singleServerConfig.setSslKeystore(new URI(disLockProperties.getSslKeystore()));
        }
        if (disLockProperties.getSslKeystorePassword() != null) {
            singleServerConfig.setSslKeystorePassword(disLockProperties.getSslKeystorePassword());
        }
        singleServerConfig.setSslProvider(DisLockConsts.JDK.equalsIgnoreCase(disLockProperties.getSslProvider()) ? SslProvider.JDK : SslProvider.OPENSSL);
    }

    /**
     * 初始化集群模式参数
     *
     * @param clusterServerConfig 集群模式配置
     */
    private void initClusterConfig(ClusterServersConfig clusterServerConfig) {
        DisLockProperties.ClusterConfig clusterConfig = disLockProperties.getClusterServer();
        String[] addressArr = clusterConfig.getNodeAddresses().split(DisLockConsts.COMMA);
        Arrays.asList(addressArr).forEach(
                address -> clusterServerConfig.addNodeAddress(String.format("%s%s" , DisLockConsts.REDIS_URL_PREFIX, address))
        );
        clusterServerConfig.setScanInterval(clusterConfig.getScanInterval());

        ReadMode readMode = getReadMode(clusterConfig.getReadMode());
        ValidateUtil.notNull(readMode, UnknownReadModeException.class, "未知读取操作的负载均衡模式类型");
        clusterServerConfig.setReadMode(readMode);

        SubscriptionMode subscriptionMode = getSubscriptionMode(clusterConfig.getSubMode());
        ValidateUtil.notNull(subscriptionMode, UnknownSubscriptionModeException.class, "未知订阅操作的负载均衡模式类型");
        clusterServerConfig.setSubscriptionMode(subscriptionMode);

        LoadBalancer loadBalancer = getLoadBalancer(clusterConfig.getLoadBalancer(), clusterConfig.getWeightMaps(), clusterConfig.getDefaultWeight());
        ValidateUtil.notNull(loadBalancer, UnknownLoadBalancerException.class, "未知负载均衡算法类型");
        clusterServerConfig.setLoadBalancer(loadBalancer);

        clusterServerConfig.setSubscriptionConnectionMinimumIdleSize(clusterConfig.getSubConnMinIdleSize());
        clusterServerConfig.setSubscriptionConnectionPoolSize(clusterConfig.getSubConnPoolSize());
        clusterServerConfig.setSlaveConnectionMinimumIdleSize(clusterConfig.getSlaveConnMinIdleSize());
        clusterServerConfig.setSlaveConnectionPoolSize(clusterConfig.getSlaveConnPoolSize());
        clusterServerConfig.setMasterConnectionMinimumIdleSize(clusterConfig.getMasterConnMinIdleSize());
        clusterServerConfig.setMasterConnectionPoolSize(clusterConfig.getMasterConnPoolSize());
        clusterServerConfig.setIdleConnectionTimeout(clusterConfig.getIdleConnTimeout());
        clusterServerConfig.setConnectTimeout(clusterConfig.getConnTimeout());
        clusterServerConfig.setTimeout(clusterConfig.getTimeout());
        clusterServerConfig.setRetryAttempts(clusterConfig.getRetryAttempts());
        clusterServerConfig.setRetryInterval(clusterConfig.getRetryInterval());
        clusterServerConfig.setPassword(clusterConfig.getPassword());
        clusterServerConfig.setSubscriptionsPerConnection(clusterConfig.getSubPerConn());
        clusterServerConfig.setClientName(disLockProperties.getClientName());
    }

    /**
     * 初始化哨兵模式参数
     *
     * @param sentinelServersConfig 哨兵模式配置
     * @throws URISyntaxException URISyntaxException
     */
    private void initSentinelServersConfig(SentinelServersConfig sentinelServersConfig) throws URISyntaxException {
        DisLockProperties.SentinelConfig sentinelConfig = disLockProperties.getSentinelServer();
        String[] addressArr = sentinelConfig.getSentinelAddresses().split(DisLockConsts.COMMA);
        Arrays.asList(addressArr).forEach(
                address -> sentinelServersConfig.addSentinelAddress(String.format("%s%s" , DisLockConsts.REDIS_URL_PREFIX, address))
        );

        ReadMode readMode = getReadMode(sentinelConfig.getReadMode());
        ValidateUtil.notNull(readMode, UnknownReadModeException.class, "未知读取操作的负载均衡模式类型");
        sentinelServersConfig.setReadMode(readMode);

        SubscriptionMode subscriptionMode = getSubscriptionMode(sentinelConfig.getSubMode());
        ValidateUtil.notNull(subscriptionMode, UnknownSubscriptionModeException.class, "未知订阅操作的负载均衡模式类型");
        sentinelServersConfig.setSubscriptionMode(subscriptionMode);

        LoadBalancer loadBalancer = getLoadBalancer(sentinelConfig.getLoadBalancer(), sentinelConfig.getWeightMaps(), sentinelConfig.getDefaultWeight());
        ValidateUtil.notNull(loadBalancer, UnknownLoadBalancerException.class, "未知负载均衡算法类型");
        sentinelServersConfig.setLoadBalancer(loadBalancer);

        sentinelServersConfig.setMasterName(sentinelConfig.getMasterName());
        sentinelServersConfig.setDatabase(sentinelConfig.getDatabase());
        sentinelServersConfig.setSlaveConnectionPoolSize(sentinelConfig.getSlaveConnectionPoolSize());
        sentinelServersConfig.setMasterConnectionPoolSize(sentinelConfig.getMasterConnectionPoolSize());
        sentinelServersConfig.setSubscriptionConnectionPoolSize(sentinelConfig.getSubscriptionConnectionPoolSize());
        sentinelServersConfig.setSlaveConnectionMinimumIdleSize(sentinelConfig.getSlaveConnectionMinimumIdleSize());
        sentinelServersConfig.setMasterConnectionMinimumIdleSize(sentinelConfig.getMasterConnectionMinimumIdleSize());
        sentinelServersConfig.setSubscriptionConnectionMinimumIdleSize(sentinelConfig.getSubscriptionConnectionMinimumIdleSize());
        sentinelServersConfig.setDnsMonitoringInterval(sentinelConfig.getDnsMonitoringInterval());
        sentinelServersConfig.setSubscriptionsPerConnection(sentinelConfig.getSubscriptionsPerConnection());
        sentinelServersConfig.setPassword(sentinelConfig.getPassword());
        sentinelServersConfig.setRetryAttempts(sentinelConfig.getRetryAttempts());
        sentinelServersConfig.setRetryInterval(sentinelConfig.getRetryInterval());
        sentinelServersConfig.setTimeout(sentinelConfig.getTimeout());
        sentinelServersConfig.setConnectTimeout(sentinelConfig.getConnectTimeout());
        sentinelServersConfig.setIdleConnectionTimeout(sentinelConfig.getIdleConnectionTimeout());
        setLockSslConfigAndClientName(sentinelServersConfig);
    }

    /**
     * 初始化云托管模式参数
     *
     * @param replicatedServersConfig 云托管模式配置
     * @throws URISyntaxException URISyntaxException
     */
    private void initReplicatedServersConfig(ReplicatedServersConfig replicatedServersConfig) throws URISyntaxException {
        DisLockProperties.ReplicatedConfig replicatedConfig = disLockProperties.getReplicatedServer();

        String[] addressArr = replicatedConfig.getNodeAddresses().split(DisLockConsts.COMMA);
        Arrays.asList(addressArr).forEach(
                address -> replicatedServersConfig.addNodeAddress(String.format("%s%s" , DisLockConsts.REDIS_URL_PREFIX, address))
        );
        ReadMode readMode = getReadMode(replicatedConfig.getReadMode());
        ValidateUtil.notNull(readMode, UnknownReadModeException.class, "未知读取操作的负载均衡模式类型");
        replicatedServersConfig.setReadMode(readMode);

        SubscriptionMode subscriptionMode = getSubscriptionMode(replicatedConfig.getSubscriptionMode());
        ValidateUtil.notNull(subscriptionMode, UnknownSubscriptionModeException.class, "未知订阅操作的负载均衡模式类型");
        replicatedServersConfig.setSubscriptionMode(subscriptionMode);

        LoadBalancer loadBalancer = getLoadBalancer(replicatedConfig.getLoadBalancer(), replicatedConfig.getWeightMaps(), replicatedConfig.getDefaultWeight());
        ValidateUtil.notNull(loadBalancer, UnknownLoadBalancerException.class, "未知负载均衡算法类型");
        replicatedServersConfig.setLoadBalancer(loadBalancer);

        replicatedServersConfig.setScanInterval(replicatedConfig.getScanInterval());
        replicatedServersConfig.setDatabase(replicatedConfig.getDatabase());
        replicatedServersConfig.setSlaveConnectionPoolSize(replicatedConfig.getSlaveConnectionPoolSize());
        replicatedServersConfig.setMasterConnectionPoolSize(replicatedConfig.getMasterConnectionPoolSize());
        replicatedServersConfig.setSubscriptionConnectionPoolSize(replicatedConfig.getSubscriptionConnectionPoolSize());
        replicatedServersConfig.setSlaveConnectionMinimumIdleSize(replicatedConfig.getSlaveConnectionMinimumIdleSize());
        replicatedServersConfig.setMasterConnectionMinimumIdleSize(replicatedConfig.getMasterConnectionMinimumIdleSize());
        replicatedServersConfig.setSubscriptionConnectionMinimumIdleSize(replicatedConfig.getSubscriptionConnectionMinimumIdleSize());
        replicatedServersConfig.setDnsMonitoringInterval(replicatedConfig.getDnsMonitoringInterval());
        replicatedServersConfig.setSubscriptionsPerConnection(replicatedConfig.getSubscriptionsPerConnection());
        replicatedServersConfig.setPassword(replicatedConfig.getPassword());
        replicatedServersConfig.setRetryAttempts(replicatedConfig.getRetryAttempts());
        replicatedServersConfig.setRetryInterval(replicatedConfig.getRetryInterval());
        replicatedServersConfig.setTimeout(replicatedConfig.getTimeout());
        replicatedServersConfig.setConnectTimeout(replicatedConfig.getConnectTimeout());
        replicatedServersConfig.setIdleConnectionTimeout(replicatedConfig.getIdleConnectionTimeout());

        setLockSslConfigAndClientName(replicatedServersConfig);
    }

    /**
     * 初始化主从模式参数
     *
     * @param masterSlaveServersConfig 主从模式配置
     * @throws URISyntaxException URISyntaxException
     */
    private void initMasterSlaveConfig(MasterSlaveServersConfig masterSlaveServersConfig) throws URISyntaxException {
        DisLockProperties.MasterSlaveConfig masterSlaveConfig = disLockProperties.getMasterSlaveServer();
        masterSlaveServersConfig.setMasterAddress(String.format("%s%s" , DisLockConsts.REDIS_URL_PREFIX, masterSlaveConfig.getMasterAddress()));
        String[] addressArr = masterSlaveConfig.getSlaveAddresses().split(DisLockConsts.COMMA);

        Arrays.asList(addressArr).forEach(address -> {
            masterSlaveServersConfig.addSlaveAddress(String.format("%s%s" , DisLockConsts.REDIS_URL_PREFIX, address));
        });

        ReadMode readMode = getReadMode(masterSlaveConfig.getReadMode());
        ValidateUtil.notNull(readMode, UnknownReadModeException.class, "未知读取操作的负载均衡模式类型");
        masterSlaveServersConfig.setReadMode(readMode);

        SubscriptionMode subscriptionMode = getSubscriptionMode(masterSlaveConfig.getSubMode());
        ValidateUtil.notNull(subscriptionMode, UnknownSubscriptionModeException.class, "未知订阅操作的负载均衡模式类型");
        masterSlaveServersConfig.setSubscriptionMode(subscriptionMode);

        LoadBalancer loadBalancer = getLoadBalancer(masterSlaveConfig.getLoadBalancer(), masterSlaveConfig.getWeightMaps(), masterSlaveConfig.getDefaultWeight());
        ValidateUtil.notNull(loadBalancer, UnknownLoadBalancerException.class, "未知负载均衡算法类型");
        masterSlaveServersConfig.setLoadBalancer(loadBalancer);

        masterSlaveServersConfig.setDatabase(masterSlaveConfig.getDatabase());
        masterSlaveServersConfig.setSlaveConnectionPoolSize(masterSlaveConfig.getSlaveConnectionPoolSize());
        masterSlaveServersConfig.setMasterConnectionPoolSize(masterSlaveConfig.getMasterConnectionPoolSize());
        masterSlaveServersConfig.setSubscriptionConnectionPoolSize(masterSlaveConfig.getSubscriptionConnectionPoolSize());
        masterSlaveServersConfig.setSlaveConnectionMinimumIdleSize(masterSlaveConfig.getSlaveConnectionMinimumIdleSize());
        masterSlaveServersConfig.setMasterConnectionMinimumIdleSize(masterSlaveConfig.getMasterConnectionMinimumIdleSize());
        masterSlaveServersConfig.setSubscriptionConnectionMinimumIdleSize(masterSlaveConfig.getSubscriptionConnectionMinimumIdleSize());
        masterSlaveServersConfig.setDnsMonitoringInterval(masterSlaveConfig.getDnsMonitoringInterval());
        masterSlaveServersConfig.setSubscriptionsPerConnection(masterSlaveConfig.getSubscriptionsPerConnection());
        masterSlaveServersConfig.setPassword(masterSlaveConfig.getPassword());
        masterSlaveServersConfig.setRetryAttempts(masterSlaveConfig.getRetryAttempts());
        masterSlaveServersConfig.setRetryInterval(masterSlaveConfig.getRetryInterval());
        masterSlaveServersConfig.setTimeout(masterSlaveConfig.getTimeout());
        masterSlaveServersConfig.setConnectTimeout(masterSlaveConfig.getConnectTimeout());
        masterSlaveServersConfig.setIdleConnectionTimeout(masterSlaveConfig.getIdleConnectionTimeout());
        setLockSslConfigAndClientName(masterSlaveServersConfig);
    }

    /**
     * 根据用户的配置类型设置对应的LoadBalancer
     *
     * @param loadBalancerType   负载均衡算法类名
     * @param customerWeightMaps 权重值设置，当负载均衡算法是权重轮询调度算法时该属性有效
     * @param defaultWeight      默认权重值，当负载均衡算法是权重轮询调度算法时该属性有效
     * @return LoadBalancer OR NULL
     */
    private LoadBalancer getLoadBalancer(String loadBalancerType, String customerWeightMaps, int defaultWeight) {
        if (DisLockConsts.RANDOM_LOAD_BALANCER.equals(loadBalancerType)) {
            return new RandomLoadBalancer();
        }
        if (DisLockConsts.ROUND_ROBIN_LOAD_BALANCER.equals(loadBalancerType)) {
            return new RoundRobinLoadBalancer();
        }
        if (DisLockConsts.WEIGHTED_ROUND_ROBIN_BALANCER.equals(loadBalancerType)) {
            Map<String, Integer> weights = new HashMap<>(16);
            String[] weightMaps = customerWeightMaps.split(DisLockConsts.SEMICOLON);
            Arrays.asList(weightMaps).forEach(
                    weightMap -> weights.put(DisLockConsts.REDIS_URL_PREFIX + weightMap.split(DisLockConsts.COMMA)[0], Integer.parseInt(weightMap.split(DisLockConsts.COMMA)[1]))
            );
            return new WeightedRoundRobinBalancer(weights, defaultWeight);
        }
        return null;
    }

    /**
     * 根据readModeType返回ReadMode
     *
     * @param readModeType 读取操作的负载均衡模式类型
     * @return ReadMode OR NULL
     */
    private ReadMode getReadMode(String readModeType) {
        if (DisLockConsts.SLAVE.equals(readModeType)) {
            return ReadMode.SLAVE;
        }
        if (DisLockConsts.MASTER.equals(readModeType)) {
            return ReadMode.MASTER;
        }
        if (DisLockConsts.MASTER_SLAVE.equals(readModeType)) {
            return ReadMode.MASTER_SLAVE;
        }
        return null;
    }

    /**
     * 根据subscriptionModeType返回SubscriptionMode
     *
     * @param subscriptionModeType 订阅操作的负载均衡模式类型
     * @return SubscriptionMode OR NULL
     */
    private SubscriptionMode getSubscriptionMode(String subscriptionModeType) {
        if (DisLockConsts.SLAVE.equals(subscriptionModeType)) {
            return SubscriptionMode.SLAVE;
        }
        if (DisLockConsts.MASTER.equals(subscriptionModeType)) {
            return SubscriptionMode.MASTER;
        }
        return null;
    }

    /**
     * 设置SSL配置
     *
     * @param lockAutoConfig lockAutoConfig
     * @param <T>            lockAutoConfig
     * @throws URISyntaxException URISyntaxException
     */
    private <T extends BaseMasterSlaveServersConfig> void setLockSslConfigAndClientName(T lockAutoConfig) throws URISyntaxException {
        lockAutoConfig.setClientName(disLockProperties.getClientName());
        lockAutoConfig.setSslEnableEndpointIdentification(disLockProperties.isSslEnableEndpointIdentification());
        if (disLockProperties.getSslKeystore() != null) {
            lockAutoConfig.setSslKeystore(new URI(disLockProperties.getSslKeystore()));
        }
        if (disLockProperties.getSslKeystorePassword() != null) {
            lockAutoConfig.setSslKeystorePassword(disLockProperties.getSslKeystorePassword());
        }
        if (disLockProperties.getSslTruststore() != null) {
            lockAutoConfig.setSslTruststore(new URI(disLockProperties.getSslTruststore()));
        }
        if (disLockProperties.getSslTruststorePassword() != null) {
            lockAutoConfig.setSslTruststorePassword(disLockProperties.getSslTruststorePassword());
        }
        lockAutoConfig.setSslProvider(DisLockConsts.JDK.equalsIgnoreCase(disLockProperties.getSslProvider()) ? SslProvider.JDK : SslProvider.OPENSSL);
    }

}
