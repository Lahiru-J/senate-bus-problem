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

    private void busIsArriving() {
        try {
            Properties.mutex.acquire();
            System.out.printf(ColorCode.MAGENTA + "\nBus-%d is arriving", this.getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void busParked() {
        Properties.bus.release();
        System.out.printf(ColorCode.BLUE + "\nBus-%d arrived\n", this.getId());
    }

    private void busDepart() {
        Properties.mutex.release();
        System.out.printf(ColorCode.RED + "\nBus-%d departed\n\n", this.getId());
    }

    private void waitForAllRiders() {
        try {
            Properties.allAboard.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
