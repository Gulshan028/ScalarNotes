package LLD1.Concurrency_2__Executors_and_Callables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSorterOld implements Callable<List<Integer>> {

    // We have created this class 'MergeSorter' to define our task in its `call()` method. So, we implemented the `Callable interface`,
    // This Callable interface has a generic datatype, hence it is enclosed inside angular brackets. We have to provide the datatype,
    // which we want to be returned. Here, we wanted a sorted list of integers to be returned, hence we chose return type accordingly as List<Integer>
    //Now, this same datatype becomes the return type of the call() method.

    private List<Integer> listToSort; //this is the attribute of the class

    public MergeSorterOld(List<Integer> listToSort) {
        this.listToSort = listToSort;
    } //Constructor

    // For MergeSort algorithm, we need a `list, to be sorted` as an input parameter, hence we have defined the attribute accordingly,
    // and also we have defined the respective Constructor.

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
        MergeSorterOld leftMergeSorter = new MergeSorterOld(leftHalf);
        MergeSorterOld rightMergeSorter = new MergeSorterOld(rightHalf);

        //so I have created the two task objects. Now, I need to assign these two tasks to two different threads. Thus, rather than'
        // manual thread creation and assigning the task, I will create ExecutorService which will create thread pool and manage it for us.

        ExecutorService executor = Executors.newCachedThreadPool();

        //I created a CachedThreadPool as this is a deep recursion, so, I don't know how many threads will be used.
        //Now, I will assign the two tasks to the ExecutorService i.e. the threadPoolManager, which will give it to different threads.

        Future<List<Integer>> leftSortedFuture = executor.submit(leftMergeSorter); //non-blocking call
        Future<List<Integer>> rightSortedFuture = executor.submit(rightMergeSorter); //non-blocking call

        List<Integer> leftSorted = leftSortedFuture.get(); //blocking call
        List<Integer> rightSorted = rightSortedFuture.get(); //blocking call

        /*
        When we submitted the task objects to ExecutorService, then it is supposed to be executed by the separate threads. And we
        cannot guarantee that threads will return the results right away. Hence, we used to `Future` interface.
        This Future is an interface in Java's `java.util.concurrent` package that represents the result of an asynchronous computation.
        <List<Integer>> is the generic type parameter indicating that this Future will eventually hold a `List of Integers`.

        - `leftSortedFuture` and `rightSortedFuture`:
        These are variables that hold references to Future objects which will eventually contain the sorted left and right halves of the list.
         So, we get the result using the `get()` method on future objects. This `get()` method is a blocking method, i.e. it will wait
         till we get the result. Hence, the main thread cannot go ahead till we get the result.
         - `get()` method:
         This is a blocking call that waits if necessary for the computation to complete, and then retrieves its result.
        If the computation isn't finished when `get()` is called, the calling thread will block until the result is available.
         */

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

        //now let's write the `Client-MainOld` code for it.
    }
}
