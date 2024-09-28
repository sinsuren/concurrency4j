package in.sinsuren.concurrency.zero.odd.even;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

class ZeroOddEven {
  private int n;
  private int currentNumber = 1;
  private boolean zeroTurn = true; // To alternate between zero, odd, and even turns
  private ReentrantLock lock = new ReentrantLock();
  private Condition zeroCondition = lock.newCondition();
  private Condition oddCondition = lock.newCondition();
  private Condition evenCondition = lock.newCondition();

  public ZeroOddEven(int n) {
    this.n = n;
  }

  public void zero(IntConsumer printNumber) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      lock.lock();
      try {
        while (!zeroTurn) {
          zeroCondition.await(); // Wait for the zero turn
        }
        printNumber.accept(0); // Print zero
        zeroTurn = false; // Switch to odd or even
        if (currentNumber % 2 == 1) {
          oddCondition.signal(); // Notify odd thread
        } else {
          evenCondition.signal(); // Notify even thread
        }
      } finally {
        lock.unlock();
      }
    }
  }

  public void odd(IntConsumer printNumber) throws InterruptedException {
    for (int i = 1; i <= n; i += 2) {
      lock.lock();
      try {
        while (zeroTurn || currentNumber % 2 == 0) {
          oddCondition.await(); // Wait for the odd number turn
        }
        printNumber.accept(currentNumber); // Print the odd number
        currentNumber++; // Increment the number
        zeroTurn = true; // Switch back to zero turn
        zeroCondition.signal(); // Notify zero thread
      } finally {
        lock.unlock();
      }
    }
  }

  public void even(IntConsumer printNumber) throws InterruptedException {
    for (int i = 2; i <= n; i += 2) {
      lock.lock();
      try {
        while (zeroTurn || currentNumber % 2 == 1) {
          evenCondition.await(); // Wait for the even number turn
        }
        printNumber.accept(currentNumber); // Print the even number
        currentNumber++; // Increment the number
        zeroTurn = true; // Switch back to zero turn
        zeroCondition.signal(); // Notify zero thread
      } finally {
        lock.unlock();
      }
    }
  }
}
