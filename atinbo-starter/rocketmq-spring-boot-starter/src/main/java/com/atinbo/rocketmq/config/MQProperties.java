package com.atinbo.rocketmq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RocketMQ的配置参数
 *
 * @author breggor
 */
@Data
@ConfigurationProperties(prefix = "spring.rocketmq")
public class MQProperties {
    /**
     * rocketmq服务地址
     */
    private String nameServerAddress;
    /**
     * 生产者分组 , default to DPG+RANDOM UUID like DPG-fads-3143-123d-1111
     */
    private String producerGroup;
    /**
     * 发送消息超时时间
     */
    private Integer sendMsgTimeout = 3000;
    /**
     * 是否开启轨迹: send message consumer info to topic: rmq_sys_TRACE_DATA
     */
    private Boolean traceEnabled = Boolean.TRUE;

    /**
     * 是否开启vip通道
     */
    private Boolean vipChannelEnabled = Boolean.TRUE;

    /**
     * 访问Key
     */
    private String accessKey;

    /**
     * 密码key
     */
    private String secretKey;

}
