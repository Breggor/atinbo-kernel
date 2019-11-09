package com.atinbo.disid;

/**
 * SnowFlakeService
 *
 * @author breggor
 */
public class SnowflakeService {

    private Snowflake flake;

    /**
     * 每个服务器节点workerId
     *
     * @param workerId
     */
    public SnowflakeService(Long workerId) {
        this.flake = new Snowflake(workerId);
    }

    public Long getId() {
        return flake.nextId();
    }
}