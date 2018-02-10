package com.concurrent.lab3;

public class Bus extends Thread {

    @Override
    public void run() {
        busIsArriving();
        if (Properties.ridersCount.get() > 0) {
            busParked();
            waitForAllRiders();
        }
        busDepart();
    }

    /**
     * Bus is arriving
     */
    private void busIsArriving() {
        try {
            Properties.semLateArrivals.acquire();
            System.out.printf(ColorCode.MAGENTA + "\nBus-%d is arriving", this.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bus arrived to the destination
     */
    private void busParked() {
        Properties.semBus.release();
        System.out.printf(ColorCode.BLUE + "\nBus-%d arrived\n", this.getId());
    }

    /**
     * Bus leave
     */
    private void busDepart() {
        Properties.semLateArrivals.release();
        System.out.printf(ColorCode.RED + "\nBus-%d departed\n\n", this.getId());
    }

    /**
     * Bus will wait for all the riders in the boarding area
     */
    private void waitForAllRiders() {
        try {
            Properties.semAllAboard.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
