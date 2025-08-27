package LLD1.Concurrency_4__Synchronization_with_Semaphores.DeepakSirProducerConsumerUsingLock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Client {
    public static void main(String[] args) {
        Queue<Object> store = new ConcurrentLinkedDeque<>();
        //ConcurrentLinkedDeque are preferred in Multithreading environment
        int maxSize = 6;

        //we are creating multiple Producer and assigning each Producer, a task, via a different thread because we want that
        //multiple producers should be able to create shirts simultaneously in the store, if the slots are empty
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
    now, we have solved the issue faced in the first code. Here, the store size is never greater than 6 and never less than 0.
    this is because we have solved the synchronization issue. Thus, at any given instant of time, either only one Producer thread or
    only one Consumer thread can access the store.
    But it came with a cost. What is that cost?
    look, as you can also see in the output section. Say, initially, store size is 0. Then, all the 5 producer threads are trying to
    access the store but because of the synchronized keyword, any one thread can get the monitor lock of the store, and it will start
    adding shirt to the store.
    so, as we can see that once a producer thread enters the critical section, it keeps on adding the shirts one after another, till the store size becomes 6.
    Then, as it goes on waiting state then one of the Consumer thread access the store and keep on purchasing the shirts, one by one, till the store
    size becomes 0. This has become SEQUENTIAL OPERATION
    so, we can clearly say that we haven't solved the  problem efficiently.
    because our agenda is that, say at any point of time say, 4 slots are empty, then 4 different producers threads should be able to access the
    store simultaneously and also since there are 2 shirts available, so 2 different consumer threads should purchase the shirts simultaneously.
    i.e. 4 different producer threads must produce 4 shirts and 2 different consumer threads must consume 2 shirts, all of these in parallel.
    But, in this solution, since synchronized keyword allows only one thread, hence at any instant only one thread is accessing the store, making the process Sequential.
    the solution to this issue is Semaphore. Let's see it in the next code.
     */
}
