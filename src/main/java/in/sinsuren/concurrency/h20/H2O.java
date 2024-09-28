package in.sinsuren.concurrency.h20;

public class H2O {
    private int hydrogenCount;
    private int oxygenCount;

    public H2O() {
        hydrogenCount = 0;
        oxygenCount = 0;
    }

    public synchronized void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        while (hydrogenCount == 2 && oxygenCount == 0) {
            wait();
        }
        hydrogenCount++;
        releaseHydrogen.run();
        if (hydrogenCount == 2 && oxygenCount == 1) {
            hydrogenCount = oxygenCount = 0;
            notifyAll();
        }

    }

    public synchronized void oxygen(Runnable releaseOxygen) throws InterruptedException {
        while (hydrogenCount < 2 && oxygenCount == 1) {
            wait();
        }
        oxygenCount++;
        releaseOxygen.run();
        if (hydrogenCount == 2 && oxygenCount == 1) {
            hydrogenCount = oxygenCount = 0;
            notifyAll();
        }
    }
}
