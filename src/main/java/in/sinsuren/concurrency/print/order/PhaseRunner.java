package in.sinsuren.concurrency.print.order;

import java.util.concurrent.Phaser;

public class PhaseRunner implements Runnable {
  private Phaser phaser;
  private String printString;

  public PhaseRunner(Phaser phaser, String printString) {
    this.phaser = phaser;
    this.printString = printString;
    this.phaser.register();
  }

  @Override
  public void run() {
    System.out.println(printString);
  }
}
