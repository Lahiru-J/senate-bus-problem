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
     * {@link Properties#ridersCount} does not exceeds {@link Properties#SEAT_LIMIT} and
     * the bus is not arriving or already parked
     */
    private void enterToBoardingArea() {
        try {
            Properties.multiplex.acquire();               // ensure no more than 50 riders will be on the boarding area
            Properties.semLateArrivals.acquire();         // prevent late arrivals
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rider goes to the boarding area
     */
    private void steppedInToBoardingArea() {
        Properties.semLateArrivals.release();
        System.out.printf(ColorCode.CYAN + "Rider-%d arrived, ", this.getId());
    }

    /**
     * Rider waits for the next bus
     */
    private void waitForBus() {
        try {
            Properties.semBus.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * All waited riders will get on to the bus
     */
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

    /**
     * This method will be called by the last rider
     */
    private void notifyBusToDepart() {
        Properties.semAllAboard.release();      // last rider has to notify the bus
    }

    /**
     * All the riders except the last one should remind
     * every next rider to get on to the bus
     */
    private void notifyTheNextRider() {
        Properties.semBus.release();            // awake the next rider who is waiting for the bus
    }
}
