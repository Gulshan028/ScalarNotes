# Lecture | Backend LLD: Java Advanced Concepts - 3 [Streams and Lambdas]

## CONTENTS:
1. Functional Interface
2. Lambdas
3. Streams

---

## CONTENTS: Sandeep Sir
1. Anonymous Class
2. Streams

---

Streams and Lambdas were introduced in Java 8 and hence they are also known as Java 8 features.

## I] Functional Interface:
- It is the interface having only one Abstract method.
- There is an Annotation called `@FunctionalInterface`, if we use this annotation with the functional interface and then 
  if anybody tries to add second abstract method to the interaface then that person will be notified with an error. Whereas if we don't 
  specify the annotation, then the person adding second abstract method won't get any error.
```java
@FunctionalInterface
interface MyFunctionalInterface {
    void myMethod();
}
```
- ex: Runnable, Callable, Comparator<T>, Comparable<T>, etc.
- A functional interface in Java can have both `default` methods and `static` methods. 

---

## II] Lambdas:
- NOTE: Read Anonymous class first at the bottom of the page then continue here.
- It is just a syntactical change. Apart from it, it doesn't provide any unique functionality.
- It is known as the anonymous method with just the method implementation.
- It is just shorter code for Anonymous class implementation for Functional interfaces.

### 2.1) Syntax of Lambda Expression:
- The basic syntax of a lambda expression consists of the 'input parameter list', the 'arrow' (->), and the 'body'. 
  The **body** can be either an **expression** or a **block of statements**.
```java
(parameters) -> expression
(parameters) -> { statements }
```
- **Parameter List:** This represents the input parameters passed to the lambda expression. It can be empty if the method doesn't take any input parameters or contain 
  one or more input parameters enclosed in parentheses. If there's only one parameter and its type is inferred, you can omit the parentheses.

- **Arrow Operator (->):** This separates the parameter list from the body of the lambda expression.

- **Lambda Body:** This contains the code that makes up the implementation of the abstract method of the functional interface. 
  The body can be a single expression or a block of code enclosed in curly braces.

### 2.2) My Doubt:
#### (1) Question: 
In lambda syntax, as we've seen above. How can we be sure that the implementation belongs to the particular method we want, as 
  there can be several methods that might have the same number of input parameters and the same return type.
#### (2) Answer: 
- For the answer, consider the example of the `run()` method of the `Runnable` interface.
- The lambda expression for the implementation of the `run()` method would look like:
```java
() -> {System.out.println("Hello world!");}
```
  This method doesn't have any input parameter, hence the parentheses are empty. Also, the return type is `void`, 
  hence we are returning nothing from the curly brackets' expression.
- Now, we will define a Runnable reference. Look, we cannot create object of the interface unless we provide the implementation
  of the abstract method. But we have already provided the implementation using lambda. So, we will pass this implementation directly to 
  the Runnable reference as below:
```java
Runnable runnable = () -> {System.out.println("Hello World");};
```
- Now, Java will understand that `Runnable` is a functional interface and in its reference, we are providing the lambda expression
  having no input parameter and no return statement i.e. a void return-type. Thus, given implementation belongs to `run()` method.
  And, Java will allow to create the object pointed by `Runnable` reference.
- Now, we can use this object with Runnable reference directly into say desired place such as input parameter into Thread creation as below.
```java
Thread t = new Thread(runnable);
t.start();
```
- So, the point to be noted here is that we have completely opted out the process of the creating class to provide the 
  implementation of the Runnable interface. We have not created any class.
- We can go even one step ahead. Rather than creating the Runnable reference 'runnable', we can directly pass the lambda 
  expression as the input parameter of the Thread constructor as follows:
```java
Thread thread = new Thread(() -> {System.out.println("Hello World");});
```
This is possible because the constructor of the thread class accepts the runnable object. So, this becomes equal as,
```java
Runnable runnable = () -> {System.out.println("Hello World");};
Thread t = new Thread(runnable);
//since, there is only one statement inside the method body. So, we can omit the curly brackets from the lambda expression as follows:
Runnable runnable = () -> System.out.println("Hello World");
//this would still create the runnable object.
```
- Now, we know that inside the thread, we either pass `Runnable` or `Callable`. So, java will be sure that we have passed the 
  Runnable and not Callable. Look, we know that Callable takes the input parameter and also returns the future value. But our 
  lambda expression doesn't have any input parameter and no return value, which means void return type. Thus, we can be sure 
  that we have passed the Runnable object and not Callable object.
- This is one of the reasons why lambda expressions are only applicable for functional interfaces. Because, if the interface has 
  more than one abstract methods, then we wouldn't be able to define which method we are implementing in the lambda expression.

---

## III] Streams:
- Similar to Lambdas, Streams too are just Syntactical-sugar. They don't come with any extra specific feature.
- In Java, we can use Streams on Collection framework say, List, Map, etc. So, we can start a Stream from there. i.e. Say, if we are having a list of integers, then we are creating a stream of Integers from that list. 
  Say, these list of integers is now flowing. While these integers are flowing. We can do some operations on data. i.e. we can manipulate the data. And, after manipulation, we can collect the 
  data at the end. This is the understanding of the Streams.
- let's take an example. Say, you have a list of integers, and you want to filter out odd numbers out of it. Consider the list as,

```java
import java.util.ArrayList;
import java.util.List;

List<Integer> list = new ArrayList<>();
//now, we will use StreamAPI on it as follows,
list.Stream();//
//this makes data flowing into a stream.
//now, we will manipulate the data using the functionalities provided by the StreamAPI. StreamAPI provides a bunch of functionalities like stream.filter(), stream.map(), etc
/*
        let's first of all see the filter() functionality. While using filter(), we need to provide some conditions on which the data will be filtered.
        Elements that pass the condition will be able to get through it and others will be filtered.
        Now, let's first of all see what all input parameters this filter function takes.
 */

```
NOTE: Almost everything from here is written `StreamsClient.java` file in the same folder. Please do read it.

### 3.1) What is `Optional<Integer>` in java:
- In Java, `Optional<Integer>` is a container object introduced in Java 8 that may or may not contain a non-null value of type `Integer`.
- It's used to avoid null pointer exceptions and make it explicit when a value might be missing.
- 🛑 Important Notes
  - `Optional` is not meant for fields (don't use it as a class field).
  - It is mostly used for method return types to signal optional values.

---

## IIv] Links:
1. Streams: https://stackify.com/streams-guide-java-8/
2. Streams: https://www.hackerrank.com/challenges/java-lambda-expressions/problem
3. Streams: https://www.geeksforgeeks.org/java/java-stream-lambda-expression-coding-practice-problems/
4. Streams: https://www.w3resource.com/java-exercises/lambda/index.php#google_vignette

---

# SANDEEP SIR: Follow SandeepSir folder 

## I] Anonymous Class:
- In Java, an `anonymous class` is a `class` without a name that is declared and instantiated in a single expression.
  It is typically used when a local class is needed only once and provides a concise way to implement interfaces or 
  extend abstract/concrete classes on the fly. 
- It is used when:
   1. Use case of the object is very rare.
   2. the implementation of the abstract method changes everytime.
- In such cases, creating a class isn't that beneficial. Hence, if possible we create anonymous class.
- We can say it is an internal hidden class with implementation and object creation capability for single use.
- That's how we create Anonymous class:
```java
InterfaceName ObjName = new InterfaceName(){
    //implement all the abstract methods for the interface
  public void method1(){
    System.out.println("This is method-1");
  }
  
  public void method2(){
    System.out.println("This is method-2");
  }
};
//all of these lines make up a single statament

```
- **Default Methods:** You do not need to re-implement `default methods` because they already have an implementation 
  in the interface. The anonymous class will inherit them automatically.

- **Static Methods:** Static methods belong to the interface itself, not to instances, so you cannot override 
  or re-declare them in the anonymous class. You can only call them via `InterfaceName.staticMethod()`.

---

## II] Streams:
- Java Streams (introduced in Java 8) are a powerful abstraction for processing sequences of data in a declarative and functional way.
- They provide a rich set of built-in operations to manipulate Collections (or any data source) efficiently with minimal code.
- They have **Declarative Style**: i.e. Focus on "what to do" rather than "how to do it" (e.g., filtering, mapping, reducing).
- **Less Boilerplate:** Reduces lines of code compared to traditional loops.
- **Lazy Evaluation:** Operations are executed only when needed (optimizes performance).
- **Functional Nature:** Operations on a stream produce a result without modifying the original source data.
- They basically have two kind of methods:
   1.  Intermediary methods, wherein input and output, both are Streams.
      - has methods like `filter()`, `sorted()`, `distinct()`, `map()`, `skip()`, `limit()`, etc
   2.  Terminal methods, whereas input is a `Stream` but output is some required data structure.
       - has methods like `reduce()`, `collect()`, `forEach()`, `toList()`, etc.
- Once a Data structure is converted into a `Stream` then we can apply number of intermediary methods on it. 
  It will remain as a Stream only. But, once we apply a terminal method on it, it is converted into the desired data structure and the stream is closed/stopped.

 ### 2.1) Generation of Stream:
- We can create Streams of primitive types or generic types:

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//- primitive types:
int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
IntStream intStream = Arrays.stream(arr); //this is a stream of primitive type 'int'
//lly, you can create the stream of char, boolean, long, etc

//another way is stream of generics:
List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
Stream<Integer> integerStream = list.stream();
```
- Ideally, we should use `Stream<Generic>` with generics. We shouldn't rely much on primitive streams because they become restrictive.
- Try using Collection data structure rather than using primitive arrays.

- we can also create a stream directly as below:

```java
import LLD1.JavaAdvancedConcepts_1.Cat;

import java.util.stream.Stream;

Stream<Integer> directStream = Stream.of(1, 2, 3, 4, 5, 6, 4, 8);
Stream<Boolean> directBooleanStream = Stream.of(true, true, false, false);
Stream<Cat> directStream = Stream.of(new Cat(), new Cat(), new Cat());

```
- NOTE: Sir has given a pdf file with names `Java Streams Lambdas.pdf` on 22 June 2025 in your mobile phone. Follow it for more practice.
        also see the `streamsDemo.java` file for more problems solved in the class.

