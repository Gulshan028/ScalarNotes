package LLD1.JavaAdvancedConcepts_3_LambdasAndStreams.SandeepSir;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class streamsDemo {
    public static void main(String[] args) {
        // Q1. given List of names, print all names starting with letter 'A'
        List<String> names = Arrays.asList("Alice", "Bob", "Alex", "Brian", "Anita");

        /*
        Code:
        for(String n : names){ // picking up items from list "names" and putting it in variable `n` one by one
           System.out.println(n);
           Explanation:
           Just like this, in below examples, the intermediary functions are picking items one by one from the stream and doing operation on them.
       }

         */
        /// /ANSWER to Q.1]
        names.stream()
                .filter(n -> n.startsWith("A"))  // intermediate operation -> filters out the names which do not start with "A"
                .forEach(n -> System.out.println(n)); // terminal operation -> print each name.

        //this below 3 lines belong to the alternate answer to above Q.1, here we have just shortened the code using Method reference:

        /*
        - A "method reference" is a shorthand syntax for writing a lambda expression that calls an existing method.
          It makes code cleaner and more readable when a lambda simply passes parameters to another method.
        - 4 types: 'Static methods', 'instance methods', 'arbitrary object methods', and 'constructors'.
        - Avoid them when you need complex logic inside the lambda: If the lambda modifies `arguments` or adds logic, you must use a lambda.
        - A method reference only works when:
            - The lambda takes parameters (`x`, `s`, `name`, etc.).
            - It passes them directly to a method without changes.
            - No extra logic (conditions, calculations, etc.) is applied.
        - ✅ Correct Use (Forwards Arguments)
        ```java
                x -> Math.sqrt(x)      => Math::sqrt
                s -> s.length()        => String::length
        ```
        - ❌ Invalid Use (Modifies Arguments)
        ```java
                x -> Math.sqrt(x) + 1  // Can't use method reference (extra +1)
                s -> s.substring(1)    // Can't use method reference (modifies 's')
        ```
        - Method references work with any number of arguments, but the way arguments are passed depends on the type.
         */
        /// /Alternate answer to Q.1

        names.stream()
                .filter(n -> n.startsWith("A"))  // intermediate operation
                .forEach(System.out::println); // terminal operation written using Method reference

        System.out.println("------[Q.2]-----------");

        //Q.2] print all names which have length more than 4 in the above given list

        names.stream()
                .filter(n -> n.length() > 4) // -> only those names are allowed having length greater than 4
                .forEach(n -> System.out.println(n));

        names.stream()
                .filter(n -> n.length() > 4)
                .forEach(System.out::println); // shorter code with method reference


        System.out.println("------Q3------");

        // Q3. Convert a list of lowerCaseNames to uppercase and store it new a new list

        List<String> lowerCaseNames =
                Arrays.asList("alice", "bob", "charlie", "darwin", "alicia", "jackie", "rockie", "wu", "xu");
        /*
        **Concise Summary:**  of what this above line does:
        This line creates an immutable-size `List<String>` named `lowerCaseNames` containing lowercase names ("alice", "bob", etc.)
        using `Arrays.asList()`. The list is fixed in length (can’t add/remove elements), but existing elements can be modified.
        For a fully modifiable list, wrap it in `new ArrayList<>(Arrays.asList(...))`.
         */

        List<String> upperCaseNames = lowerCaseNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(upperCaseNames);

        // Q.4] make those names capital which have atleast 4 letters in sorted order
        /// /Answer to Q.4]
        List<String> upperCase4Names = lowerCaseNames.stream()
                .filter(n -> n.length() > 4) // intermediate 1
                .map(String::toUpperCase) // intermediate 2
                .sorted() // intermediate 3
                .collect(Collectors.toList()); // terminal
        System.out.println(upperCase4Names);

        System.out.println("-----Q5--------");

        // Q.5] Given a list of numbers, find the sum of all even numbers

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        /// /Answer to Q.5]

        int sum = numbers.stream()
                .filter(n -> n%2==0) // intermediate 1 -> chooses those numbers which are even
                .mapToInt(n -> n) // converts stream into int, as it is [ 2(Stream) -> 2(Integer) ]
                .sum(); // terminal
        System.out.println(sum);

/*
     1. Use Stream<Generic> with generics, dont rely much on primitive streams
     2. Try using Collection data structure, rather than using primitive arrays
 */
    }
}
