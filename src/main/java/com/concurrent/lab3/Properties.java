package com.concurrent.lab3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("WeakerAccess")
public class Properties {

    public static final int BUS_MEAN = 20 * 60;
    public static final int RIDER_MEAN = 30;
    public static final AtomicInteger ridersCount = new AtomicInteger();
    public static final Semaphore semLateArrivals = new Semaphore(1);
    public static final Semaphore semBus = new Semaphore(0);
    public static final Semaphore semAllAboard = new Semaphore(0);

    private static final int SEAT_LIMIT = 50;

    public static final Semaphore multiplex = new Semaphore(SEAT_LIMIT);

}
