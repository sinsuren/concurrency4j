package in.sinsuren.concurrency.foo.bar;

import java.util.concurrent.CyclicBarrier;

public class FooBar {

  private int n;
  private CyclicBarrier barrier = new CyclicBarrier(2); // For 2 threads

  public FooBar(int n) {
    this.n = n;
  }

  public void foo(Runnable printFoo) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      printFoo.run();
      try {
        barrier.await(); // Wait for bar to finish
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void bar(Runnable printBar) throws InterruptedException {
    for (int i = 0; i < n; i++) {
      try {
        barrier.await(); // Wait for foo to finish
      } catch (Exception e) {
        e.printStackTrace();
      }
      printBar.run();
    }
  }
}
