package LLD1.Concurrency_4__Synchronization_with_Semaphores.AtomicIntegers;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        adderAtomic adder = new adderAtomic(count);
        subtractorAtomic subtractor = new subtractorAtomic(count);

        Thread t1 = new Thread(adder);
        Thread t2 = new Thread(subtractor);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Count : " + count);
    }
}
