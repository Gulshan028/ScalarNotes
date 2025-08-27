package LLD1.Concurrency_4__Synchronization_with_Semaphores.SandeepSir;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Consumer implements Runnable{
    private Queue<Shirt> store;
    private String name;
    private Semaphore semaProducer;
    private Semaphore semaConsumer;

    public Consumer(Queue<Shirt> store, String name, Semaphore semaProducer, Semaphore semaConsumer) {
        this.store = store;
        this.name = name;
        this.semaProducer = semaProducer;
        this.semaConsumer = semaConsumer;
    }


    @Override
    public void run() {
        while (true){
            try {
                semaConsumer.acquire(); // decreases the key for consumer by 1
                System.out.println("Current size : "+ store.size()+", removed by Consumer : "+name
                        + ", by Thread: "+ Thread.currentThread().getName());
                store.remove();
                semaProducer.release(); // increases the keys for Producer by 1
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
