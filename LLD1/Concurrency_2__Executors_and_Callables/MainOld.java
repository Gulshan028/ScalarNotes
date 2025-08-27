package LLD1.Concurrency_2__Executors_and_Callables;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainOld {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // let's create a list of integer to be sorted.

        List<Integer> listToSort = Arrays.asList(9,8,7,6,5,4,3,2,1,0);

         // to sort, I need to create a task. so, creating that,

        MergeSorterOld mergeSorter = new MergeSorterOld(listToSort);

        //now, I need to pass on this task object to a thread. So, I will go for a cachedThreadPool.

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<List<Integer>> sortedFuture = executor.submit(mergeSorter);
        List<Integer> sortedList = sortedFuture.get();

        System.out.println(sortedList);

        /*
        ISSUE: On running this code, we can see that, this code is using multiple thread pools hence we improve our code. Because, using multiple thread pools isn't a good
        resource utilization. It's like creating multiple manufacturing plants.
        For improved code, see the `MergeSorter.java` code.
         */

    }
}
