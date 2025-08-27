package LLD1.JavaAdvancedConcepts_3_LambdasAndStreams.DeepakSir;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaClient {
    public static void main(String[] args) {
        //creating runnable object and passing it to thread constructor
        //also, in lambda expression, since there is only one statement in the method body hence we have omitted the curly brackets.

        Runnable runnable = () -> System.out.println("Hello world");
        Thread thread = new Thread(runnable);
        thread.start();

        //Since, we know that the Thread accepts the Runnable object, we directly pass the lambda expression into the constructor of the thread.

        Thread thread1 = new Thread(()-> System.out.println("Hey buddy."));
        thread1.start();
        Thread thread2 = new Thread();

        Student s1 = new Student(21, "Taposhii", 91.00, 99.50);
        Student s2 = new Student(22, "Sonakshi", 92.87, 95.36);
        Student s3 = new Student(23, "Soumya", 93.89, 92.85);
        Student s4 = new Student(24, "Dolly", 92.87, 85.36);

        List<Student> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);

        for(Student student: list){
            System.out.println(student.getName()+" "+student.getPsp());
        }

        System.out.println("Using COMPARATOR based on psp--------------------------");

        //Now, I want to sort the list of Students based on psp, so, I will use `Collections.sort()`;

       // Collections.sort(list);
        /*
        if we uncomment above line (Collections.sort(list);) then we can see that we are getting error, and we aren't able
        to sort the `list`. This is happening because there is no natural order of sorting provided in the Student class, nor we
        have any provided related Comparator object to the Collections.sort(list) constructor.
        Now, let's pass a Comparator object.
        we know that we need to create a class for Comparator<Student> object that will implement Comparator interface.
        Now, for one just one time sorting, why create a class when we can use lambda expression for that.
        let's see
         */
        /*
        We have just one method in Comparator interface which is `int compare(T obj1, T obj2)`.
        i.e. this methods' return type is `int` and it accepts two objects of the type that are to be compared.
        Now, similar to what we have done for Runnable interface, we will write lambda functions for this too.
        Now the question is, do we need to define the data-type of the two objects which are to be passed as the arguments?
        the answer is NO. Because the data-type is the same as the data-type we declare while declaring Comparator object.
        Since, while Comparator object declaration, we chose `Student`, so the data-type of the two objects at input parameters
        will be `Student`.
         */
        //Finally, let's write lambda expression for Comparator object.

        Comparator<Student> comparator = (o1, o2) -> {
                                            return  (int) (o1.getPsp() - o2.getPsp());
                                         };
        /*
        this is how we can use lambda expression to create an object which implements Comparator interface.
        Here, you can say, we have used an anonymous class to create the object.
         */
        //now, we will sort the list using the Comparator object:

        Collections.sort(list, comparator);
        for(Student student: list){
            System.out.println(student.getName()+" "+student.getPsp());
        }
        //thus, we have successfully used lambda expression to create `Comparator<Student>` object which we passed to Collections.sort() to sort our list of `Student` objects.
        /*
        Now, let's do small enhancements to our code of lambda expression above
        1. since, there is only one statement inside the method body so we can remove curly bracket.
        2. once the curly brackets are removed, then we can also remove the `return` keyword. Java will understand on its own that it is a return statement.
         Let's apply these changes.
         */

        Comparator<Student> comparator1 = (o1, o2) -> (int) (o1.getPsp() - o2.getPsp());

        // let's try passing this to the constructor directly.
        System.out.println("///////////////////////////////");
        Collections.sort(list,(o1, o2) -> (int) (o1.getPsp() - o2.getPsp()) );
        for(Student student: list){
            System.out.println(student.getName()+" "+student.getPsp());
        }
        //So, all the styles are working, great job, Gulshan.

    }
}
