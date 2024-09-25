package in.sinsuren.concurrency.print.order;

import java.util.concurrent.CountDownLatch;

public class Foo {

  private final Object lock = new Object();
  private boolean isFirstDone = false;
  private boolean isSecondDone = false;

  public Foo() {}

  public void first(Runnable printFirst) throws InterruptedException {

    // printFirst.run() outputs "first". Do not change or remove this line.
    synchronized (lock) {
      printFirst.run();
      isFirstDone = true;
      lock.notifyAll();
    }
  }

  public void second(Runnable printSecond) throws InterruptedException {

    synchronized (lock) {
      while (!isFirstDone) {
        System.out.println("sleeping second one");
        lock.wait();
      }

      printSecond.run();
      isSecondDone = true;

      lock.notifyAll();
    }
  }

  public void third(Runnable printThird) throws InterruptedException {

    synchronized (lock) {
      while (!isSecondDone) {
        System.out.println("sleeping third one");
        lock.wait();
      }

      printThird.run();
      lock.notifyAll();
    }
  }
}
