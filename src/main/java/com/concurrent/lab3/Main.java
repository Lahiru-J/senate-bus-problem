package com.concurrent.lab3;

public class Main {

    public static void main(String[] args) {

        Thread dispatchBus = new Thread(() -> {
            ExpDistributionGen exp = new ExpDistributionGen(Properties.BUS_MEAN);
            while (true) {
                try {
                    Thread.sleep(exp.sample());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Bus().start();
            }
        });

        Thread dispatchRider = new Thread(() -> {
            ExpDistributionGen exp = new ExpDistributionGen(Properties.RIDER_MEAN);
            while (true) {
                try {
                    Thread.sleep(exp.sample());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new Rider().start();
            }
        });

        dispatchBus.start();
        dispatchRider.start();
    }
}
