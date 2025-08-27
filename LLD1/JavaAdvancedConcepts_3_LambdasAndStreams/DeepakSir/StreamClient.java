package LLD1.JavaAdvancedConcepts_3_LambdasAndStreams.DeepakSir;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamClient {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(10);
        list.add(21);
        list.add(30);
        list.add(41);
        list.add(50);

        ////filter out the even elements from the list using streamAPI:

        //let's create stream out of list:

        Stream<Integer> stream = list.stream();

        //now let's use filter() method on this stream.
       //// stream.filter(Predicate<? super T> predicate);  ----> this is the syntax of the filter method
        /*
       1. Go to official implementation of the `filter` and you will see that it takes `Predicate` object as an input parameter.
        - The predicate is an object that tests whether an element of type T (or any of its superclasses) satisfies a condition.
        - The wildcard `? super T` means the predicate can accept:
            - An argument of type `T`, or
            - An argument of any superclass of `T`
        - Example:
            - If `T` is `Integer`, the predicate can be:
                    - `Predicate<Integer>` (exact match)
                    - `Predicate<Number>` (since `Number` is a superclass of `Integer`)
                    - `Predicate<Object>` (since `Object` is a superclass of `Number`)
        - This ensures that the filter() method can work with predicates designed for `T` or any of its parent types.

       2. Now, let's find out that what is this predicate?
       On further going into the official implementation of the `Predicate`.
       We can see that `Predicate` is the functional interface which has abstract method **`boolean test(T t);`**
       - the `test()` method takes just one input parameter whose `type` is the same (i.e. T) which we define while declaring the `Predicate<T>`.
       - the return type of the `test()` method is `boolean` which we require in `filter()` method, as, if the condition is passed, then the data will be
         allowed otherwise it will be filtered out.

       3. Now, let's try to pass an object with reference `Predicate` in the filter() method. For that, we can either create a class which
          implements the Predicate interface and pass the object of that class to the filter method. But, we will use lambda expressions
          to create the required object with Predicate reference as below:
         */
        ////Predicate<Integer> predicate = (x) -> {return x%2 == 1;};
        ////stream.filter(predicate);
        //commented above two lines as we want to shorten it further by directly passing the lambda expression into the
        // input parameter section of the filter method as below:

       ////stream.filter((x) ->  x%2 == 1);

        /*
        - here, we have taken input parameter as 'x' because it is a variable which will take elements from the `Stream<Integer>`.
        - Data-type of `x` is automatically chosen as `Integer` because `filter` method takes object with `Predicate` reference,
          and the type-parameter of this object is the same as the type of Stream data.
        - Our `Stream<Integer>` is of `Integer` data hence automatically `x` got `Integer` type (we have already discussed the
          reason behind it while discussing lambda expression).
        Now, let's print the list:
         */
        System.out.println(list);

        //we have done the filtration, and we expect the list to print the expected output omitting the filtered data
        //however, we get the entire list at the output
        /*
        let's see, why this happens:
        Look, there is something called as `Intermediary` functions and something as `Terminal` fucntions.
        `filter()` is the intermediary function. So, there must be some other intermediary functions as well which perform the operation.
        hence, the data is not at all filtered out. Hence, we need to inform StreamAPI that I am done with my operation, and now
        I want to collect these data. now, we will catch the filtered data in the output list
         */

        ////List<Integer> output = stream.filter((x) ->  x%2 == 1).toList();

        //this `toList()` method acts like a terminal function. When we start our `stream`, then the stream kind of infinitely keeps flowing.
        //Meanwhile, we can do `n` number of operations on that stream. Once we want to stop our intermediary functions, then we need to give
        //our terminal function on that stream.
        //// REMEMBER:
        //When we give a `list` to stream, then the original list won't be changed. Rather, we will get the new list, which we will collect as the output list.

        System.out.println(list); // ----> you can see that the original list is the same. It has not changed.
        ////System.out.println(output);

        //however, this output is the desired list which has only the odd number because they have passed the condition and even numbers have been filtered out.
        // we can also directly shorten out code as this:

        System.out.println(stream.filter((x) ->  x%2 == 1).toList());

        /// /TASK 2: filter out odd numbers and do the square of each number:
        //now we already know how to filter odd numbers. our main task now is to convert the filtered list to their squares.
        //for this purpose, we use the `map()` function of StreamAPI. on seeing into the official implementation of this map() function, "<R> Stream<R> map(Function<? super T, ? extends R> mapper);"
        // we see that it takes one input parameter of type `Function` which has two generic type-parameters `T` and `R`.
        //`Function` is a functional interface which has a single abstract method, "R apply(T t);". this method takes an input of one type-parameter `T` and
        //returns another type-parameter `R` after applying any operation on that.
        //but, rather than creating `class` to implement this functional interface `Function`, we will directly use lambda expression
        //to create the `object` with `Function` reference. We will implement the apply() method inside the lambda expression.
        //now, our last job is to use this map() function on stream of integers post filter() function as shown below.THen, we will collect this output into another list:

    /*
        List<Integer> output2 = stream.filter((x)-> x%2==0)
                                      .map((x) -> x*x)
                                      .toList();
        System.out.println(output2);
    */
        //look, we have commented the above two statements because on running the code, we got the error that the stream has been closed.
        //and the reason is very much valid here as we have previously used terminal function ".toList()" on the same `stream`. so we cannot use the `stream` again.
        //thus we need to start the new stream out of our main list by doing "list.stream()":

        List<Integer> output2 = list.stream().filter((x)-> x%2==0)
                                             .map((x) -> x*x)
                                             .toList();
        System.out.println(output2);

        //thus we learnt that on the stream, we can use any number of intermediary functions, but once we have used
        // the terminal function then we cannot use the same stream again. to make a stream out of the same list, we need to re-initialise the stream.

//// why we need a terminal function:
//say, we don't use the terminal function for above stream and directly try to print the stream.

        Stream<Integer> stream1 = list.stream().filter((x)-> x%2==0)
                                               .map((x) -> x*x);
        System.out.println(stream1);

        //ideally, we should get the same output as above on printing the stream, But, we aren't getting the same response.
        //this happens because, stream is flow of data. No matter how many functions we apply to it. the output isn't generated
        //unless we apply the terminal function.
        //let's see some other thing:

        List<Integer> output3 = list.stream().filter((x)-> {
                                                                    System.out.println("Filtering" + x);
                                                                    return x % 2 == 0;
                                                                    })
                                            .map((x) -> {
                                                                    System.out.println("Mapping" + x);
                                                                    return x*x;
                                                                })
                                            .toList();
        System.out.println(output3);

        /*
        now, we expect that the output of the above two statements should be like:
        - firstly, all the data with filtering statement should have been printed since filter() method is applied first
        - then the data for mapping should get printed.
        i.e. the process should be sequential i.e. firstly only filtering function will be performed on the entire list and then the mapping function will be performed
        BUT, streams don't work like this. THey perform operation in the optimized manner using their internal optimization.
        So, streams first write down all the functions we want to do and then as soon as we apply the terminal function, then the
        stream starts performing all the functions in the optimized manner.
        SO, WE CAN CLAIM THAT STREAMS ONLY START PERFORMING THE FUNCTIONS ONLY AFTER THE TERMINAL FUNCTIONS ARE APPLIED.
        And, this brings in a lot of optimization. Let's see it with below code.
         */

        Optional<Integer> output4 = list.stream().filter((x)-> {
                                                                        System.out.println("Filtering" + x);
                                                                        return x % 2 == 0;
                                                                        })
                                                .map((x) -> {
                                                                        System.out.println("Mapping" + x);
                                                                        return x*x;
                                                                    })
                                                .findFirst();
        System.out.println(output4.get());// to get output from the object output4, we used get() method, i.e.getter

        /*
        So, are you not surprised after seeing the output!!!! we can see that stream has just filtered the first two numbers and mapped the just one number
        and then gave us output. This is possible because stream starts executing the functions applied on the stream, after the terminal function is applied.
        Here the terminal function findFirst() is applied then the stream understood that the desired output is just one number. SO, rather than filtering and mapping the entire list
        it has done its internal optimizations and only passed the required numbers to produce the output. So, it doesn't process the further list.
         */
        //here, I should tell what is "Optional<Integer>". But, please follow .md notes for it.
        //what we have seen just now is the biggest advantage of Streams i.e. Lazy and Eager execution of Streams:

        /// /Lazy and Eager execution of Streams:
        /*
        Look, all the intermediary functions of Streams are `Lazy` functions as they wait for terminal function, to get executed,
        whereas all the terminal functions of Streams are `Eager` functions since they execute as soon as they are called.
         */
        //Streams is a very vast topic. There are `n`, number of functions. However, we covered only a few. Hence, please read the
        //resource given by the sir.

        //there is a very good function which is to convert a list of integers into an array of integers using streams as follows:

        Integer[] array  = list.stream().toArray(Integer[]::new);
        System.out.println(array.length);

        //thus you can see that we have converted the same.










    }
}
