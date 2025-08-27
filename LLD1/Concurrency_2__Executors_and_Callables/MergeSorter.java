package LLD1.Concurrency_2__Executors_and_Callables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

//first, read the old code, then come here.

public class MergeSorter implements Callable<List<Integer>> {

    /*
    Improvement: Here, we are passing the same threadPool from the `main` thread which is used at all the recursive stages. Hence, that CachedThreadPool is passed as
    the parameter of the MergeSorter object. This same CachedThreadPool is used in all the recursive calls. So, as a result, we are using only one Thread Pool.
     */

    private List<Integer> listToSort;
    ExecutorService executor;

    public MergeSorter(List<Integer> listToSort, ExecutorService executor) {
        this.listToSort = listToSort;
        this.executor = executor;
    }

    @Override
    public List<Integer> call() throws Exception {
        System.out.println(listToSort + " Sorted by thread : " + Thread.currentThread().getName());

        //base case: if the list has one or fewer elements, then the list is already sorted. So, return the same list.

        if(listToSort.size() <= 1) return listToSort;

        // now, we divide the given list into two halves using the sublist function which returns a sublist defined by the indexes in the bracket

        List<Integer> leftHalf  = listToSort.subList(0, listToSort.size()/2);
        List<Integer> rightHalf = listToSort.subList(listToSort.size()/2, listToSort.size());

        /*
        Now as per our agenda, we will sort the two parts of the list in the multithreaded environment, hence we will give these
        two lists to two different threads. Hence, creating two different task objects as below
         */
        MergeSorter leftMergeSorter = new MergeSorter(leftHalf, executor);
        MergeSorter rightMergeSorter = new MergeSorter(rightHalf, executor);

        //so I have created the two task objects. Now, I need to assign these two tasks to two different threads. For this purpose, I am using existing thread pool.
        //which will assign the two tasks to two different threads;

        Future<List<Integer>> leftSortedFuture = executor.submit(leftMergeSorter); //non-blocking call
        Future<List<Integer>> rightSortedFuture = executor.submit(rightMergeSorter); //non-blocking call

        List<Integer> leftSorted = leftSortedFuture.get(); //blocking call
        List<Integer> rightSorted = rightSortedFuture.get(); //blocking call


        //now we have the leftSorted and rightSorted lists. So, we just need to merge them, which will be done by next few lines.

        int i=0, j=0;
        List<Integer> sorted = new ArrayList<>();

        while(leftSorted.size() > i && j < rightSorted.size()){
            if(leftSorted.get(i) < rightSorted.get(j)){
                sorted.add(leftSorted.get(i));
                i++;
            }
            else{
                sorted.add(rightSorted.get(j));
                j++;
            }
        }

        while(leftSorted.size() > i){
            sorted.add(leftSorted.get(i));
            i++;
        }

        while(j < rightSorted.size()){
            sorted.add(rightSorted.get(j));
            j++;
        }

        return sorted;

        //now let's write the `Client-Main` code for it.
    }
}
