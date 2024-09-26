package in.sinsuren.concurrency.print.order;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class FooRunner {
  public static void main(String[] args) {
    Phaser phaser = new Phaser(2);
    Foo foo = new Foo(phaser);
    ExecutorService executors = Executors.newFixedThreadPool(3);
    Runnable first = new PhaseRunner(phaser, "first");

    Runnable second = new PhaseRunner(phaser, "second");

    Runnable third = new PhaseRunner(phaser, "third");

    executors.submit(third);
    executors.submit(first);
    executors.submit(second);

    executors.shutdown();
  }
}
