package LLD1.Concurrency_4__Synchronization_with_Semaphores.DeepakSirClass.ProducerConsumer;

import java.util.Queue;

//Consumer task will be given to separate thread, hence it implements Runnable
public class Consumer implements Runnable{
    private Queue<Object> store;
    private int maxSize;
    private String name;


    public Consumer(int maxSize, Queue<Object> store, String name) {
        this.maxSize = maxSize;
        this.store = store;
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            //Consumer should only consume the shirt in case when there is available shirt in the store
            if(store.size() > 0){
                System.out.println("Consumer: " + name + " is consuming the shirt, store size: " + store.size()+
                        ", by Thread : "+ Thread.currentThread().getName());
                store.remove();
            }

        }

    }
}
