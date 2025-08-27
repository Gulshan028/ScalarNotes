package LLD1.Concurrency_4__Synchronization_with_Semaphores.AtomicIntegers;

import java.util.concurrent.atomic.AtomicInteger;

public class adderAtomic implements Runnable{
    private AtomicInteger count;

    public adderAtomic(AtomicInteger count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0;i<10000;i++){
            count.getAndIncrement();
        }

    }
}
