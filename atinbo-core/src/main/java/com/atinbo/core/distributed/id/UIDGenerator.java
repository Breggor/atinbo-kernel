package com.atinbo.core.distributed.id;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class UIDGenerator {

    /**
     * 每个服务器节点workerId
     */
    private Long workerId;
    private SnowFlake flake;

    public UIDGenerator(@Value("${workerId}") Long workerId) {
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