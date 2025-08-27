package LLD1.Concurrency_4__Synchronization_with_Semaphores.DeepakSirClass.ProducerConsumer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Client {
    public static void main(String[] args) {
        Queue<Object> store = new ConcurrentLinkedDeque<>();
        //ConcurrentLinkedDeque are preferred in Multithreading environment
        int maxSize = 6;

        //we are creating multiple Producer and assigning each Producer task via a different thread because we want that
        //multiple producer should be able to create shirts simultaneously in the store, if the slots are empty
        //same goes for consumers
        Producer p1 = new Producer(6, store, "P1");
        Thread tp1 = new Thread(p1);
        tp1.start();

        Producer p2 = new Producer(6, store, "P2");
        Thread tp2 = new Thread(p2);
        tp2.start();

        Producer p3 = new Producer(6, store, "P3");
        Thread tp3 = new Thread(p3);
        tp3.start();

        Producer p4 = new Producer(6, store, "P4");
        Thread tp4 = new Thread(p4);
        tp4.start();

        Producer p5 = new Producer(6, store, "P5");
        Thread tp5 = new Thread(p5);
        tp5.start();

        Consumer c1 = new Consumer(6, store, "C1");
        Thread tc1 = new Thread(c1);
        tc1.start();

        Consumer c2 = new Consumer(6, store, "C2");
        Thread tc2 = new Thread(c2);
        tc2.start();

        Consumer c3 = new Consumer(6, store, "C3");
        Thread tc3 = new Thread(c3);
        tc3.start();

        Consumer c4 = new Consumer(6, store, "C4");
        Thread tc4 = new Thread(c4);
        tc4.start();

        Consumer c5 = new Consumer(6, store, "C5");
        Thread tc5 = new Thread(c5);
        tc5.start();

        Consumer c6 = new Consumer(6, store, "C6");
        Thread tc6 = new Thread(c6);
        tc6.start();
    }
    /*
    // before writing print statement inside run() method
    on running this code, we got,
    **Exception in thread "Thread-8" java.util.NoSuchElementException:
    at LLD1.Concurrency_4_Semaphores.DeepakSirClass.Consumer.run(Consumer.java:22)**
    this has happened because the consumer must have tried to consume the shirt from the empty store.
    But we have a *if* block then how can such a thing happen.
    It happens because of synchronization issue.
    say, store size = 1, and 2 threads parallely checked 'if' condition, so they both entered 'if' block, but one thread could
    remove shirt and the other threw exception as store got empty by then

    //after writing print statement inside run() method
    now, we can see that on running the code, it gives multiple print statements.  the important thing to observe is that
    the store size becomes 9 multiple times. This happened when store size is say 4 and 5 threads check the 'if' condition,
    all of them gets condition true. So, all of them add the shirt parallely. Hence, next time the store size goes to 9.
    We stopped the code on our own rather than waiting for the condition when it gives us the noSuchElementException as in
    previous case. if we wait, then definitely we will get such a condition.
    Now, even though, we had written 'if' block, still the store size is becoming more than maxSize and also going below 0.
    This is happening because of Synchronizing. Multiple threads are accessing the shared resource, which is the QUEUE.
    We will take care of it in the next code.
     */
}
