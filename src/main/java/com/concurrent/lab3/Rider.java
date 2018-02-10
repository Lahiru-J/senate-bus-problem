package com.concurrent.lab3;

public class Rider extends Thread {

    @Override
    public void run() {
        enterToBoardingArea();
        Properties.ridersCount.incrementAndGet();
        steppedInToBoardingArea();
        waitForBus();
        boardBus();
    }

    /**
     * A rider will enter to the boarding area only if boarded
     * rider count does not exceeds {@link Properties#SEAT_LIMIT} and
     * the bus is not arriving or already parked
     */
    private void enterToBoardingArea() {
        try {
            Properties.multiplex.acquire();     // ensure no more than 50 riders will be on the boarding area
            Properties.mutex.acquire();         // prevent late arrivals
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void steppedInToBoardingArea() {
        Properties.mutex.release();
        System.out.printf(ColorCode.CYAN + "Rider-%d arrived, ", this.getId());
    }

    private void waitForBus() {
        try {
            Properties.bus.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void boardBus() {
        Properties.multiplex.release();
        if (Properties.ridersCount.decrementAndGet() == 0) {
            notifyBusToDepart();
            System.out.printf(ColorCode.GREEN + "Last Rider-%d boarded\n", this.getId());
        } else {
            System.out.printf(ColorCode.GREEN + "Rider-%d boarded, ", this.getId());
            notifyTheNextRider();
        }
    }

    private void notifyBusToDepart() {
        Properties.allAboard.release();         // last rider have to notify the bus
    }

    private void notifyTheNextRider() {
        Properties.bus.release();
    }
}
