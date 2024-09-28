package in.sinsuren.concurrency.h20;

import java.util.concurrent.Semaphore;

public class H2O {
  // Semaphore to limit the number of hydrogen and oxygen threads.
  private Semaphore hydrogenSemaphore = new Semaphore(2);
  private Semaphore oxygenSemaphore = new Semaphore(1);

  // Barrier to ensure bonding only when two hydrogen and one oxygen are ready.
  private final Object lock = new Object();
  private int hydrogenCount = 0;
  private int oxygenCount = 0;

  public H2O() {}

  public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
    hydrogenSemaphore.acquire(); // Wait until it is hydrogen's turn.

    synchronized (lock) {
      hydrogenCount++;
      // If we have two hydrogens and one oxygen, bond the molecule.
      if (hydrogenCount == 2 && oxygenCount == 1) {
        // Reset counters and release the locks.
        hydrogenCount = 0;
        oxygenCount = 0;
        hydrogenSemaphore.release(2); // Allow the next hydrogen to proceed.
        oxygenSemaphore.release(); // Allow the next oxygen to proceed.
      }
    }

    // Release hydrogen (bond it).
    releaseHydrogen.run();
  }

  public void oxygen(Runnable releaseOxygen) throws InterruptedException {
    oxygenSemaphore.acquire(); // Wait until it is oxygen's turn.

    synchronized (lock) {
      oxygenCount++;
      // If we have two hydrogens and one oxygen, bond the molecule.
      if (hydrogenCount == 2 && oxygenCount == 1) {
        // Reset counters and release the locks.
        hydrogenCount = 0;
        oxygenCount = 0;
        hydrogenSemaphore.release(2); // Allow the next hydrogen to proceed.
        oxygenSemaphore.release(); // Allow the next oxygen to proceed.
      }
    }

    // Release oxygen (bond it).
    releaseOxygen.run();
  }
}
