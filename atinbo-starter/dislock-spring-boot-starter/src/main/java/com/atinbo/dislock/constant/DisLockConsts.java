package com.atinbo.dislock.constant;

/**
 * 公共常量
 *
 * @author breggor
 * @version 1.1.0
 * @apiNote 知识改变命运，技术改变世界
 * @since 2018-12-23 15:35
 */
public interface DisLockConsts {
    /**
     * 默认客户端名字
     */
    String LOCK = "Lock";
    /**
     * 默认SSL实现方式：JDK
     */
    String JDK = "JDK";
    /**
     * 逗号
     */
    String COMMA = ",";
    /**
     * 冒号
     */
    String COLON = ":";
    /**
     * 分号
     */
    String SEMICOLON = ";";
    /**
     * redis默认URL前缀
     */
    String REDIS_URL_PREFIX = "redis://";
    /**
     * 锁的key的分隔符
     */
    String KEY_SPLIT_MARK = ":";
    /**
     * 锁的前缀
     */
    String KEY_PREFIX = "tooleek" + KEY_SPLIT_MARK + "lock" + KEY_SPLIT_MARK + "key" + KEY_SPLIT_MARK;


    /**
     * 读取操作/订阅操作的负载均衡模式常量
     */
    String SLAVE = "SLAVE";
    String MASTER = "MASTER";
    String MASTER_SLAVE = "MASTER_SLAVE";


    /**
     * 负载均衡算法类型
     */
    String RANDOM_LOAD_BALANCER = "RandomLoadBalancer";

    String ROUND_ROBIN_LOAD_BALANCER = "RoundRobinLoadBalancer";

    String WEIGHTED_ROUND_ROBIN_BALANCER = "WeightedRoundRobinBalancer";
}
