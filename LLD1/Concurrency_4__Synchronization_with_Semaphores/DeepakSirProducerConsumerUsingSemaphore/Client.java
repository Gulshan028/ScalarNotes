package LLD1.Concurrency_4__Synchronization_with_Semaphores.DeepakSirProducerConsumerUsingSemaphore;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public class Client {
    public static void main(String[] args) {
        Queue<Object> store = new ConcurrentLinkedDeque<>();
        //ConcurrentLinkedDeque are preferred in Multithreading environment
        int maxSize = 6;
        /*
        These Semaphores are created here in client class as these same must be common to both Producer and Consumer object.
        The initial permits are given based on our discussion that at the start, no consumer thread can access the store and producer threads
        equal to the number of maxSize will be allowed.
         */
        Semaphore ProducerSemaphore = new Semaphore(maxSize);
        Semaphore ConsumerSemaphore = new Semaphore(0);

        //we are creating multiple Producers and assigning each Producer, a task, via a different thread because we want that
        //multiple producers should be able to create shirts simultaneously in the store, if the slots are empty.
        //Same goes for consumers.
        Producer p1 = new Producer(6, store, "P1",  ProducerSemaphore, ConsumerSemaphore);
        Thread tp1 = new Thread(p1);
        tp1.start();

        Producer p2 = new Producer(6, store, "P2",  ProducerSemaphore, ConsumerSemaphore);
        Thread tp2 = new Thread(p2);
        tp2.start();

        Producer p3 = new Producer(6, store, "P3",  ProducerSemaphore, ConsumerSemaphore);
        Thread tp3 = new Thread(p3);
        tp3.start();

        Producer p4 = new Producer(6, store, "P4",  ProducerSemaphore, ConsumerSemaphore);
        Thread tp4 = new Thread(p4);
        tp4.start();

        Producer p5 = new Producer(6, store, "P5",  ProducerSemaphore, ConsumerSemaphore);
        Thread tp5 = new Thread(p5);
        tp5.start();

        Consumer c1 = new Consumer(6, store, "C1",  ProducerSemaphore, ConsumerSemaphore);
        Thread tc1 = new Thread(c1);
        tc1.start();

        Consumer c2 = new Consumer(6, store, "C2",  ProducerSemaphore, ConsumerSemaphore);
        Thread tc2 = new Thread(c2);
        tc2.start();

        Consumer c3 = new Consumer(6, store, "C3",  ProducerSemaphore, ConsumerSemaphore);
        Thread tc3 = new Thread(c3);
        tc3.start();

        Consumer c4 = new Consumer(6, store, "C4",  ProducerSemaphore, ConsumerSemaphore);
        Thread tc4 = new Thread(c4);
        tc4.start();

        Consumer c5 = new Consumer(6, store, "C5",  ProducerSemaphore, ConsumerSemaphore);
        Thread tc5 = new Thread(c5);
        tc5.start();

        Consumer c6 = new Consumer(6, store, "C6",  ProducerSemaphore, ConsumerSemaphore);
        Thread tc6 = new Thread(c6);
        tc6.start();

        /*
        Now, when we see the output, then we can see that the output is entirely random. So, it has solved issue caused while using lock.
        Thus, it is evident that this Semaphore has served our purpose. So, 6 threads are running in parallel that too based on the
        availability of the empty slots and shirts to be purchased.
        While when we were using lock then it had become sequential but here it is complete parallel operation serving our agenda.
         */

    }

}
