package LLD1.Concurrency_4__Synchronization_with_Semaphores.DeepakSirProducerConsumerUsingSemaphore;

import java.util.Queue;
import java.util.concurrent.Semaphore;

//Producer tasks will be given to separate threads, hence it implements Runnable
public class Producer implements Runnable{

    private Queue<Object> store;
    private int maxSize;
    private String name;
    //ProducerSemaphore is passed to be acquired so that producer thread can produce the shirt
    private Semaphore ProducerSemaphore;
    //ConsumerSemaphore is passed to be released as soon one shirt has been added, so that consumer thread can be notified to consume the shirt
    private Semaphore ConsumerSemaphore;

    //queue should be common to all the tasks, hence it is passed from main to all the tasks
    public Producer(int maxSize, Queue<Object> store, String name, Semaphore ProducerSemaphore, Semaphore ConsumerSemaphore) {
        this.maxSize = maxSize;
        this.store = store;
        this.name = name;
        this.ProducerSemaphore = ProducerSemaphore;
        this.ConsumerSemaphore = ConsumerSemaphore;
    }

    @Override
    public void run() {
        while (true){
            try {
                //the acquire method first checks for the number of available permits. If it is greater than 0, then it reduces the counter
                //allows the thread to access the critical section.
                ProducerSemaphore.acquire();
                //we have written about this exception in the .md file
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(store.size() < maxSize){
                System.out.println("Producer: " + name + " is producing the shirt, store size: " + store.size()+
                        ", by Thread : "+ Thread.currentThread().getName());
                this.store.add(new Object());
            }
            //this next line is telling consumer thread that the shirt is ready. So, it is notifying the consumer thread.
            //this way, ConsumerSemaphore increases its number of permits and allows the waiting consumer thread to acquire().

            ConsumerSemaphore.release();

            /*
            Yes, it is crucial to enclose the critical section within the acquire() and release() operations of a semaphore.
            This ensures mutual exclusion and prevents race conditions when multiple processes or threads are accessing
            shared resources. The acquire() operation blocks a process until a resource is available, while the release() operation
             makes the resource available again, allowing other waiting processes to proceed.
             */
        }

    }
}
