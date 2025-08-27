package LLD1.Concurrency_4__Synchronization_with_Semaphores.SandeepSir;

import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Producer implements Runnable{
    private Queue<Shirt> store;
    private int maxSize;
    private String name;
    private Semaphore semaProducer;
    private Semaphore semaConsumer;

    public Producer(Queue<Shirt> store, int maxSize, String name, Semaphore semaProducer, Semaphore semaConsumer) {
        this.store = store;
        this.maxSize = maxSize;
        this.name = name;
        this.semaProducer = semaProducer;
        this.semaConsumer = semaConsumer;
    }


    @Override
    public void run() {
        while (true){
            try {
                semaProducer.acquire();//decreases the key for producer by 1
                System.out.println("Current size : "+ store.size()+", added by producer: "+name
                        + ", by Thread: "+ Thread.currentThread().getName());
                store.add(new Shirt());
                semaConsumer.release();//increases the key for consumer by 1
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
