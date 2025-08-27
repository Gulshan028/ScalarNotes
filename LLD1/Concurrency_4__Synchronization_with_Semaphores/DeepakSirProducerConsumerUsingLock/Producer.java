package LLD1.Concurrency_4__Synchronization_with_Semaphores.DeepakSirProducerConsumerUsingLock;

import java.util.Queue;

//Producer task will be given to separate thread, hence it implements Runnable
public class Producer implements Runnable{
    private Queue<Object> store;
    private int maxSize;
    private String name;

    //queue should be common to all the tasks, hence it is passed from `main` to all the tasks
    public Producer(int maxSize, Queue<Object> store, String name) {
        this.maxSize = maxSize;
        this.store = store;
        this.name = name;
    }

    @Override
    public void run() {
        //this `while` loop is run because, one producer thread should create multiple shirts one after the other.
        //if there is no while-loop, then each Producer thread will create one shirt and then the thread will stop
        while (true){
            //in our first code, we noticed that, since, multiple threads are accessing `if` condition, so our store size was
            //going beyond the range, it should go. So, it is our CRITICAL SECTION. Hence, we use Synchronized keyword on `store`.
            //yes, although, we know that Queue is a data structure, but we can use Synchronized keyword on it.
            synchronized (store){
                if(store.size() < maxSize){
                    System.out.println("Producer: " + name + " is producing the shirt, store size: " + store.size()+
                            ", by Thread : "+ Thread.currentThread().getName());
                    this.store.add(new Object());
                }
            }

        }

    }
}
