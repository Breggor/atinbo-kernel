package com.atinbo.dislock.core;


import com.atinbo.dislock.core.strategy.KeyStrategy;

public class KeyStrategyContext {

    private KeyStrategy keyStrategy;


    public KeyStrategyContext(KeyStrategy keyStrategy) {
        this.keyStrategy = keyStrategy;
    }

    public KeyInfo.Builder generateBuilder() {
        return this.keyStrategy.generateBuilder();
    }

}
