package com.atinbo.core.distributed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * 分布式ID
 */
@Component
public class IdGenerator {

    /**
     * 每个服务器节点workerId
     */
    private Long workerId;
    private SnowFlake flake;

    public IdGenerator(@Value("${disId.workerId}") Long workerId) {
        this.workerId = workerId;
    }

    public Long getId() {
        return flake.nextId();
    }

    @PostConstruct
    public void init() {
        Objects.requireNonNull(workerId, "[workerId] 不能为null");
        this.flake = new SnowFlake(this.workerId);
    }
}