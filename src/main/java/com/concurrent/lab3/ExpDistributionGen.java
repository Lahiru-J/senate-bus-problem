package com.concurrent.lab3;

import org.apache.commons.math3.distribution.ExponentialDistribution;

@SuppressWarnings("WeakerAccess")
public class ExpDistributionGen {

    private final ExponentialDistribution expDis;

    public ExpDistributionGen(int mean) {
        this.expDis = new ExponentialDistribution(mean);
    }

    public long sample() {
        return (long) expDis.sample();
    }
}
