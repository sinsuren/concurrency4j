package in.sinsuren.concurrency.foo.bar;

import in.sinsuren.concurrency.print.order.Foo;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FooRunner {
  public static void main(String[] args) {

    FooBar foo = new FooBar(4);
    ExecutorService executors = Executors.newFixedThreadPool(2);
    Runnable first =
        () -> {
          try {
            foo.foo(() -> System.out.println("foo"));
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        };

    Runnable second =
        () -> {
          try {
            foo.bar(() -> System.out.println("bar"));
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        };

    executors.submit(first);
    executors.submit(second);

    executors.shutdown();
  }
}
