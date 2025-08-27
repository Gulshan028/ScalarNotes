package LLD1.Concurrency_4__Synchronization_with_Semaphores.DeepakSirProducerConsumerUsingSemaphore;

import java.util.Queue;
import java.util.concurrent.Semaphore;

//Producer task will be given to separate thread, hence it implements Runnable
public class Consumer implements Runnable{
    private Queue<Object> store;
    private int maxSize;
    private String name;
    private Semaphore ProducerSemaphore;
    private Semaphore ConsumerSemaphore;


    public Consumer(int maxSize, Queue<Object> store, String name, Semaphore ProducerSemaphore, Semaphore ConsumerSemaphore) {
        this.maxSize = maxSize;
        this.store = store;
        this.name = name;
        this.ProducerSemaphore = ProducerSemaphore;
        this.ConsumerSemaphore = ConsumerSemaphore;
    }

    @Override
    public void run() {
        while (true) {
            //
            try {
                ConsumerSemaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(store.size() > 0){
                System.out.println("Consumer: " + name + " is consuming the shirt, store size: " + store.size()+
                        ", by Thread : "+ Thread.currentThread().getName());
                store.remove();
            }
            ProducerSemaphore.release();

        }

    }
}
