package in.sinsuren.concurrency.print.order;

import java.util.concurrent.Phaser;

public class Foo {

  private Phaser phaser;

  public Foo(Phaser phaser) {
    this.phaser = phaser;
  }

  public void first(Runnable printFirst) throws InterruptedException {
    printFirst.run();
    phaser.arrive();
  }

  public void second(Runnable printSecond) throws InterruptedException {
    phaser.arriveAndAwaitAdvance();
    printSecond.run();
    phaser.awaitAdvance(0); // wait for first to complete
  }

  public void third(Runnable printThird) throws InterruptedException {
    phaser.arriveAndAwaitAdvance();
    printThird.run();
    phaser.awaitAdvance(1); // wait for second to complete
  }
}
