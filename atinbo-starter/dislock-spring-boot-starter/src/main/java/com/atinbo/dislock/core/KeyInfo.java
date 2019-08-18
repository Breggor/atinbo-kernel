package com.atinbo.dislock.core;

import com.atinbo.dislock.constant.DisLockConsts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 锁key信息
 *
 * @author breggor
 */
public class KeyInfo {

    private List<String> keys = new ArrayList<>();
    private long leaseTime = -1;
    private long waitTime = -1;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    private KeyInfo(List<String> keys, long leaseTime, long waitTime, TimeUnit timeUnit) {
        this.keys = keys;
        this.leaseTime = leaseTime;
        this.waitTime = waitTime;
        this.timeUnit = timeUnit;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public List<String> getKeys() {
        return keys;
    }

    public long getLeaseTime() {
        return leaseTime;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public static class Builder {

        private List<String> keyList = new ArrayList<>();
        private long leaseTime = -1;
        private long waitTime = -1;
        private TimeUnit timeUnit = TimeUnit.SECONDS;

        public Builder appendKey(String key) {
            keyList.add(DisLockConsts.KEY_PREFIX + key);
            return this;
        }

        public Builder leaseTime(long leaseTime) {
            this.leaseTime = leaseTime;
            return this;
        }

        public Builder waitTime(long waitTime) {
            this.waitTime = waitTime;
            return this;
        }

        public Builder timeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public boolean isEmptyKeys() {
            return this.keyList.isEmpty();
        }

        public KeyInfo build() {
            return new KeyInfo(keyList, leaseTime, waitTime, timeUnit);
        }
    }
}
