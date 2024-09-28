package in.sinsuren.concurrency.zero.odd.even;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

class ZeroEvenOdd {
  private int n;
  private Phaser phaser = new Phaser(1); // Register 1 for the main thread

  public ZeroEvenOdd(int n) {
    this.n = n;
  }

  public void zero(java.util.function.IntConsumer printNumber) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      printNumber.accept(0);
      phaser.arriveAndAwaitAdvance(); // Signal odd/even thread
    }
  }

  public void odd(java.util.function.IntConsumer printNumber) throws InterruptedException {
    for (int i = 1; i <= n; i += 2) {
      phaser.arriveAndAwaitAdvance(); // Wait for zero
      printNumber.accept(i);
      phaser.arriveAndAwaitAdvance(); // Signal zero thread
    }
  }

  public void even(java.util.function.IntConsumer printNumber) throws InterruptedException {
    for (int i = 2; i <= n; i += 2) {
      phaser.arriveAndAwaitAdvance(); // Wait for zero
      printNumber.accept(i);
      phaser.arriveAndAwaitAdvance(); // Signal zero thread
    }
  }
}
