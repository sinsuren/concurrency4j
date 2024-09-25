package in.sinsuren.concurrency.print.order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FooRunner {
  public static void main(String[] args) {

    Foo foo = new Foo();
    ExecutorService executors = Executors.newFixedThreadPool(3);
    Runnable first =
        () -> {
          try {
            foo.first(() -> System.out.println("first"));
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        };

    Runnable second =
        () -> {
          try {
            foo.second(() -> System.out.println("second"));
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        };
    Runnable third =
        () -> {
          try {
            foo.third(() -> System.out.println("third"));
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        };

    executors.submit(third);
    executors.submit(first);
    executors.submit(second);

    executors.shutdown();
  }
}
