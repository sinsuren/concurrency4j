package in.sinsuren.concurrency.zero.odd.even;

public class Main {
  public static void main(String[] args) {
      ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(5);

    Runnable printZero =
        () -> {
          try {
            zeroEvenOdd.zero(System.out::print);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        };

    Runnable printOdd =
        () -> {
          try {
            zeroEvenOdd.odd(System.out::print);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        };

    Runnable printEven =
        () -> {
          try {
            zeroEvenOdd.even(System.out::print);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        };

    new Thread(printZero).start();
    new Thread(printOdd).start();
    new Thread(printEven).start();
  }
}
