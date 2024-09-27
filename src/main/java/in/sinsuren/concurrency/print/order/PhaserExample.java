package in.sinsuren.concurrency.print.order;

import java.time.Duration;
import java.util.concurrent.Phaser;

public class PhaserExample {
  public static void main(String[] args) {
    Phaser phaser = new Phaser(4); // Register the main thread

    for (int i = 0; i < 3; i++) {
      //      phaser.register(); // Register a new party
      new Thread(
              () -> {
                try {
                  System.out.println(
                      "Waiting Before Phase 1 for " + Thread.currentThread().getName());
                  phaser.arriveAndAwaitAdvance(); // Wait for other threads

                  Thread.sleep(Duration.ofSeconds(10));
                  System.out.println("Before Phase 2 for" + Thread.currentThread().getName());
                  // Phase 2
                  phaser.arriveAndAwaitAdvance(); // Wait for next phase
                } catch (Exception e) {
                  e.printStackTrace();
                }
              })
          .start();
    }

    phaser.arriveAndDeregister();
  }
}
