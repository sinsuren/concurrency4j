package in.sinsuren.concurrency.zero.odd.even;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

class ZeroOddEven {
  private int n;
  private CountDownLatch zeroLatch = new CountDownLatch(1);
  private CountDownLatch oddLatch = new CountDownLatch(0);
  private CountDownLatch evenLatch = new CountDownLatch(0);

  public ZeroOddEven(int n) {
    this.n = n;
  }

  public void zero(java.util.function.IntConsumer printNumber) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      zeroLatch.await(); // Wait for the signal to print zero
      printNumber.accept(0);
      zeroLatch = new CountDownLatch(1); // Reset latch after printing zero
      if (i % 2 == 0) {
        oddLatch.countDown(); // Signal odd thread
      } else {
        evenLatch.countDown(); // Signal even thread
      }
    }
  }

  public void odd(java.util.function.IntConsumer printNumber) throws InterruptedException {
    for (int i = 1; i <= n; i += 2) {
      oddLatch.await(); // Wait for the signal to print odd
      printNumber.accept(i);
      oddLatch = new CountDownLatch(1); // Reset latch after printing odd
      zeroLatch.countDown(); // Signal zero thread
    }
  }

  public void even(java.util.function.IntConsumer printNumber) throws InterruptedException {
    for (int i = 2; i <= n; i += 2) {
      evenLatch.await(); // Wait for the signal to print even
      printNumber.accept(i);
      evenLatch = new CountDownLatch(1); // Reset latch after printing even
      zeroLatch.countDown(); // Signal zero thread
    }
  }
}
