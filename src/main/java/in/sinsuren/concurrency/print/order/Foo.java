package in.sinsuren.concurrency.print.order;

import java.util.concurrent.Phaser;

public class Foo {

  private final Phaser phaser = new Phaser(1);

  public Foo() {}

  public void first(Runnable printFirst) throws InterruptedException {
    printFirst.run();
    phaser.arriveAndAwaitAdvance();
  }

  public void second(Runnable printSecond) throws InterruptedException {
    phaser.awaitAdvance(1); // wait for first to complete
    printSecond.run();
    phaser.arriveAndAwaitAdvance();
  }

  public void third(Runnable printThird) throws InterruptedException {
    phaser.awaitAdvance(2); // wait for second to complete
    printThird.run();
    phaser.arriveAndAwaitAdvance();
  }
}
