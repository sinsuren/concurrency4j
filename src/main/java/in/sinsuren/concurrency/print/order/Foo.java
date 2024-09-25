package in.sinsuren.concurrency.print.order;

import java.util.concurrent.Semaphore;

public class Foo {

  private Semaphore secondSemaphore = new Semaphore(0);
  private Semaphore thirdSemaphore = new Semaphore(0);

  public Foo() {}

  public void first(Runnable printFirst) throws InterruptedException {
    printFirst.run();
    secondSemaphore.release(); // signal second can proceed
  }

  public void second(Runnable printSecond) throws InterruptedException {
    secondSemaphore.acquire(); // wait for first to complete
    printSecond.run();
    thirdSemaphore.release(); // signal third can proceed
  }

  public void third(Runnable printThird) throws InterruptedException {
    thirdSemaphore.acquire(); // wait for second to complete
    printThird.run();
  }
}
