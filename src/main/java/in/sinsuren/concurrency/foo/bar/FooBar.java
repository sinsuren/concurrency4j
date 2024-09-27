package in.sinsuren.concurrency.foo.bar;

import java.security.cert.CertPath;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FooBar {

  private final Lock lock = new ReentrantLock();

  private volatile boolean fooTurn = true;
  private final Condition fooTurnCondition = lock.newCondition();
  private final Condition barTurnCondition = lock.newCondition();

  private int n;

  public FooBar(int n) {
    this.n = n;
  }

  public void foo(Runnable printFoo) throws InterruptedException {

    for (int i = 0; i < n; i++) {

      lock.lock();

      while (!fooTurn) {
        fooTurnCondition.await();
      }
      // printFoo.run() outputs "foo". Do not change or remove this line.
      printFoo.run();
      fooTurn = false;
      barTurnCondition.signal();
    }
  }

  public void bar(Runnable printBar) throws InterruptedException {

    for (int i = 0; i < n; i++) {

      lock.lock();

      while (fooTurn) {
        barTurnCondition.await();
      }
      // printBar.run() outputs "bar". Do not change or remove this line.

      printBar.run();
      fooTurn = true;
      fooTurnCondition.signal();
    }
  }
}
