package LLD1.Concurrency_4__Synchronization_with_Semaphores.DeepakSirClass.ProducerConsumer;

import java.util.Queue;

//Producer tasks will be given to separate thread, hence it implements Runnable
public class Producer implements Runnable{
    private Queue<Object> store;
    private int maxSize;
    private String name;

    //queue should be common to all the tasks, hence it is passed from main to all the tasks
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
            //this 'if' block is necessary because, the Producer should only create the shirt in case there is empty slots in the store
            //i.e. if no of available shirts are less than the maxsize of the store
            if(store.size() < maxSize){
                System.out.println("Producer: " + name + " is producing the shirt, store size: " + store.size()+
                        ", by Thread : "+ Thread.currentThread().getName());
                this.store.add(new Object());
            }
        }

    }
}
