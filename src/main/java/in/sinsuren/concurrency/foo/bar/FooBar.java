package in.sinsuren.concurrency.foo.bar;

import java.util.concurrent.CountDownLatch;

public class FooBar {

  private int n;
  private CountDownLatch fooLatch = new CountDownLatch(0);
  private CountDownLatch barLatch = new CountDownLatch(1);

  public FooBar(int n) {
    this.n = n;
  }

  public void foo(Runnable printFoo) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      fooLatch.await();
      printFoo.run();
      fooLatch = new CountDownLatch(1);
      barLatch.countDown();
    }
  }

  public void bar(Runnable printBar) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      barLatch.await();
      printBar.run();
      barLatch = new CountDownLatch(1);
      fooLatch.countDown();
    }
  }
}
