package in.sinsuren.concurrency.print.order;

import java.util.concurrent.CountDownLatch;

public class Foo {

  private final CountDownLatch printSecondLatch = new CountDownLatch(1);
  private final CountDownLatch printThirdLatch = new CountDownLatch(1);

  public Foo() {}

  public void first(Runnable printFirst) throws InterruptedException {

    // printFirst.run() outputs "first". Do not change or remove this line.
    printFirst.run();
    printSecondLatch.countDown();
  }

  public void second(Runnable printSecond) throws InterruptedException {

    printSecondLatch.await();

    printSecond.run();

    printThirdLatch.countDown();
  }

  public void third(Runnable printThird) throws InterruptedException {
    printThirdLatch.await();
    printThird.run();
  }
}
