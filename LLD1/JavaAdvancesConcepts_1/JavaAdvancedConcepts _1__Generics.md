# Lecture | Backend LLD: Java Advanced Concepts - 1 [Generics]

## CONTENTS: Sandeep sir class
1. Key aspects of Generics
2. Generics with Inheritance

---
## CONTENTS: Deepak sir class
1. Introduction to Generics
2. Raw Data-types and Backward Compatibility
3. Wildcards, its Bounds and Inheritance 
4. Type Erasure
5. More about Wildcard
6. Variables
7. Type Parameters (Generics)
8. Don't use wildcards here
9. Summary

---

## I] Key aspects of Generics:

### 1.1) Generics in Java:

   Generics in Java allow us to create classes, interfaces, 
   and methods where the type of the data is specified as a parameter ( a placeholder for types 
   (< T >, < K >, < V >, etc.)), which are later specified when the class is instantiated or the method 
   is called. If we use generics, we do not need to write multiple versions of the same code for 
   different data types. Generics are also called as `Parameterized types`. 


   #### (1) Why Use Generics?

   Before Generics, the Java developers used `Object` to store any type of data. The `Object` is the superclass of all 
   other classes, and an `Object` reference can refer to any object. These features lack type-safety. For example, 
   adding an `Integer` to a `List` of `String` would not show an error until runtime. Generics add that type of safety feature.

   #### (2) Declaring Generics:
   Generics are defined along with the class names inside angular brackets as:
```java
    public class Calculator<X, Y>{}
```
   so, it shows that the class has 2 generics, namely X and Y, and they will be used anywhere in the class as per the need.

---

### 1.2) Constructor:
We can use Parameterized Constructors also for the Generics as shown in the Calculator class.
```java
public class Calculator<X,Y> {

    private X x;
    private Y y;

    public Calculator(X x, Y y){
        this.x = x;
        this.y = y;
    }

    public void print(X x, Y y){
        System.out.print(x+" ");
        System.out.println(y);
    }
}
```
---

### 1.3) Before and After Java 5:
Before the Generics came into the picture i.e. before Java 5, we have been creating objects like:
```java
ClassName objName = new ClassName();
```
This is called as 'Raw Data type in Generics'.

Now, post Generics, if java asks to change the syntax to, only as stated below:
```java
ClassName<X, Y> objName = new ClassName<>();
```
Then, we will have Backward Compatibility issues. As we will need to change the older codebase with these new changes. Hence,
Java supports both the syntaxes. However, the first one isn't preferred for Generics and hence the classNames are 
highlighted with yellow colour when this syntax is used. The second one is recommended for Generics.

---

### 1.4) Backward compatibility in Java: 
   Backward compatibility in Java ensures that code compiled with older JDK versions can still run on newer JREs. 
   This allows developers to upgrade their Java environments without needing to rewrite or recompile existing applications(methods and classes). 
   It's a key feature that simplifies transitions, encourages new technology adoption, and protects investments in existing codebases. 

---

### 1.5) Raw Data Types:
  The first syntax, above, is called as 'Raw Data type in Generics'. For ex:

```java
import LLD1.JavaAdvancesConcepts_1.Calculator;

Calculator boolCalculator = new Calculator(true, true);
```
Here, you can see that the syntax has been accepted because this follows with the pre-existing syntax of object creation, 
but it isn't recommended for Generics and hence the className `Calculator` is underlined by yellow line showing the error 
as "Raw use of parameterized class 'Calculator' ". (Note: The error is not shown here, it will be shown in the IDE.)

In Java Generics, a Raw type refers to the name of a generic class or interface used without providing any type arguments. 
Essentially, it treats a generic type as if it were a non-generic type. 
Above syntax, Calculator is raw type, where the type of elements it will contain is not specified. However, 
```java
Calculator<Boolean, Boolean> booleanCalculator  = new Calculator<>(true, true);
```
`Calculator<Boolean, Boolean>` is a parameterized type, explicitly stating that the Calculator will hold both the Boolean datatypes.
For Generics, this syntax is accepted and recommended too, and hence there is no yellow underline or anything else.

---

### 1.6) Loss of Type Safety:
   The primary drawback of raw types is the loss of compile-time type safety, which is provided by generics. The compiler cannot 
   enforce type constraints, which can lead to `ClassCastException` at runtime if incompatible types are added or retrieved. (you will see the example ahead)

---

### 1.7) Backward Compatibility:
   Raw types are primarily supported for backward compatibility with older Java code written before the introduction of
   generics (Java 5).

---

### 1.8) Use of Generics:
   The biggest use case of generics is enabling the creation of reusable, type-safe, and flexible code by abstracting 
   data types. This allows developers to write code that can work with various data types without sacrificing 
   type checking or requiring explicit casting, primarily within `Collections` and `Algorithms`. 

---

### 1.9) Generics, under the Hood:
   Java's generics are built on top of the `Object` class, meaning they can only handle objects. They achieve this 
   through a process called type erasure, where the generic type is replaced with `Object` (or its upper bound) at runtime. 
   Thus, it doesn't support primitive datatype. Hence, we use wrapper classes for primitive datatypes. i.e. `Integer` instead of `int`, etc.

---

### 1.10) **Primitives are not Objects:**
   Primitive data types in Java are not objects; they are fundamental data types with their own representation.

---

### 1.11) **Wrapper Classes:**
   Wrapper classes in Java are a set of classes that allow primitive data types to be treated as objects. While Java's 
    primitive data types (like int, float, double, char, boolean, byte, short, long) are efficient for storing basic values, 
    they lack the object-oriented features and functionalities that are sometimes required. 

#### (1) **Purpose and Use Cases:** 
Few are mentioned below:

- **Collections:**

   Java's collection frameworks (e.g., ArrayList, HashMap) can only store objects, not primitive types. 
    Wrapper classes enable you to store primitive values within these collections by encapsulating them as objects.

- **Generics:**

   Generics in Java work with types, and primitive types cannot be used directly as type parameters. 
    Wrapper classes allow you to use primitive values in generic classes and methods.

- **Method Arguments and Return Types:**

   In scenarios where methods expect or return objects, wrapper classes facilitate the use of primitive values.

- **Null Values:**

  Primitive types cannot hold a null value, but wrapper class objects can, which is useful in situations where a value might be absent.

---

### 1.12) **Autoboxing and Unboxing:**

   Java provides automatic conversions between primitive types and their corresponding wrapper classes through 
    a feature called autoboxing and unboxing.

- **1) Autoboxing:**
    - The automatic conversion of a primitive type to its corresponding wrapper class object (e.g., int to Integer).
    - Example: `int num = 10;` `Integer obj = num;` (Here, the `int` is automatically wrapped into an `Integer` object).
    
- **2) Unboxing:**
    - The automatic conversion of a wrapper class object to its corresponding primitive type (e.g., Integer to int).
    - Example: `Integer obj = new Integer(5);` `int num = obj;` (Here, the Integer object is automatically converted to an int primitive). 
 
---

### 1.13) **Generic Method:**
A generic method can be declared inside both, a generic class or even a non-generic class in Java. This means the method can have its own type 
    parameters, independent of or in addition to the type parameters of the enclosing generic class.
```java
public class MyGenericClass<T> { // Generic class with type parameter T
    //attribute
    private T classValue;
    
    //Constructor
    public MyGenericClass(T classValue) {
        this.classValue = classValue;
    }

    //generic method as it has generic return type
    public T getClassValue() {
        return classValue;
    }

    // Generic method within the generic class:
    // This below method has its own type parameter U, independent of T
    /*
            This method can accept some other parameter apart from mentioned in the class. So, we can specify 
            those just before the return-type of the method inside angular brackets.
     */
    public <U> void printAndCompare(U methodValue) {
        System.out.println("Class value: " + classValue);
        System.out.println("Method value: " + methodValue);

        // Example of using both class and method type parameters
        if (classValue.equals(methodValue)) {
            System.out.println("Class value and method value are equal.");
        } else {
            System.out.println("Class value and method value are different.");
        }
    }
    
}
```
Basically, a method having either input parameters or return type or both, as generic data types are Generic methods.

---

## II] Generics with Inheritance:

```java
//we make an Animal class
public class Animal {
    public void print(){
        System.out.println("Hello from animal");
    }
}

---
////we make a Dog class which is a child class of Animal class
public class Dog extends Animal {
    //here, it overrides 'print()' method from Animal
   @Override
   public void print(){
      System.out.println("Hello from Dog");
   }
}
---
//this is our Main class
public class Main {
    public static void main(String[] args) {
        //we create Animal and dog objects as below
       Animal animal = new Animal();
       Dog dog = new Dog();
       
       //this `print()` method is written inside the `Main` class outside the `main()` method. To see, scroll down 
       print(animal); //output:- Hello from animal
       /*
               Above print(animal); statement works fine as the `print` method required an Animal object which we are passing.
        */
       print(dog); // Animal animal = new Dog(); || upcasting
       /*
               output for print(dog); :- Hello from Dog
               here, this statament works fine too as the `print` method expects an Animal object however we pass 
               dog object which works well as dog is child of animal and because of upcasting, this works well.
               So, the input parameter is `Animal animal` which accepts `new Dog()`.
               However, since for Dog object, the `print()` method is overridden hence the overridden method is implemented,
               and we get the output accordingly.
        */
        //now, let's create List of Animals and add few animals to it.
       List<Animal> animals = new ArrayList<>();
       animals.add(new Animal());
       animals.add(new Animal());
       
        //lly, create a List of Dogs and add few dogs to it.
       List<Dog> dogs = new ArrayList<>();
       dogs.add(new Dog());
       dogs.add(new Dog());
       
       //this `printList()` method is written inside the `Main` class outside the `main()` method. To see, scroll down  
       printList(animals);
       //now, we call `printList method` and pass List of Animals. So,we get the desired output as
       /*
               output:- 
               Printing from list method
               Hello from animal
               Hello from animal
        */
       // This method worked well as the  `printList()` method accepts a list of animals, and we passed the required list
           
        printList(dogs); // upcasting inside generics does not work; this gives the Compilation error.
       /*
               Now, this above statement `printList(dogs);` doesn't work as the method `printList()` expects List of animal objects 
               but, we are passing List of dog objects. Java gives us Compilation error. i.e. doesn't even allow us to run this.
               Because, Just like, Animal is NOT parent of Animal.
                lly, List is NOT parent of List.
                List<Animal> is at the same level as List<Dog> because both are nothing but Lists
                so, List<Animal> is not parent of List<Dog>. Both are different List.
                Since, we aren't passing the required List<Animal> hence it gives us Compilation error.
        */
       /*
               WHY this happens?:-
               Generics in Java are invariant: 
               This means List<Dog> is not a subtype of List<Animal>, even though Dog is a subtype of Animal.

                Type safety issue: 
                If Java allowed this, you could add a Cat (another subclass of Animal) into the List<Dog>, 
                which would break type safety.
                
                GULSHAN LOGIC:
                Dekh gulshan, smjne ki koshish kr,
                List objects ki hoti hh. Suru me, hum object generic rakhte hh. But, List create krte waqt 
                to object type specify krte h n. To, create hone k baad, List specific bn gyi.
                Ab agar input parameter me ek specific List chahiye to mujhe wahi List bhejna pdega. Me dusri list nah bhej skta.
                kyunki ek List dusre List ki parent class nah hh. Okay.
        */

       //Now, let's play with Printer class. We create object of Printer class, named `printer`
       Printer<Animal> printer = new Printer<>();
       //this `printer` object has `print()` method which accept generic object `P`. here, it is Animal. Thus gives correct output.
       printer.print(animal); // output: Hello from animal
       //now, this method also accepts `Dog` object as Dog extends Animal, and we have defined the same while defining the class
       printer.print(dog); // output: Hello from Dog
       /*
          LEARNING:-
          Look, `List of Dog` was not subtype of `List of Animal` in above case. BUt, here `Printer of Dog` is
           subtype of `Printer of Animal`. 
          This is because, while defining the Printer  class and its generic, we defined that the generic will extend Animal class
          BUt, if you see the code of `List` class then you will see that the generic is simple. It doesn't extend any class.
          i.e. `List<P>` and not like `List<P extends Animal>` Hence `List<Dog>` is not subtype of `List<Animal>`.
          Hope, you got the difference between the two.
          So, for Generic to support Upcasting, we need to specify the same(i.e. extends parent-class) while defining the Generic parameter
          with the class. If we don't do that then Generic doesn't support upcasting.
         
        */
        
    }
    
    //print method which accepts Animal object
   public static void print(Animal animal){
      animal.print();
   }
   ////printList method which accepts List of Animal objects
   public static void printList(List<Animal> list){
      System.out.println("Printing from list method");
      for(int i=0;i<list.size();i++){
         list.get(i).print();
      }
   }
} 

---
//Now, we create a work-around for this concept. To understand it, let's create a `Printer` class
/*
        Observe one thing in the Class declaration below. The class has a Generic variable `P`. But, `P` cannot be any random object.
        It can be either `Animal` object or child-class of Animal object. As we can see that `P` extends the Animal class.
 */
/*
        Just like, `public class Printer<P extends Animal>{}`, we cannot do `public class Printer<P implements IAnimal>{}
        where `IAnimal` is say,` an interface.
 */
public class Printer<P extends Animal>{
    /*
            Now, this `Printer<P>` class has a method `print()` which accepts generic object `p`. The method body has 
            one instruction which is `p.print()`. Now, this is valid because, the object `p` has `print()` method because
            no matter, which class `p` object belongs to, its parent class is `Animal`, which has `print()` method.
            In this way, we call print() method of `P` object.
     */
   public void print(P p) {
      p.print();
   }
}

//new concept: Generic method:
public class Pair<X, Y>{
    private X x;
    private Y y;
    
    Pair(X x, Y y){
        this.x  = x;
        this.y = y;
    }
    
    public static <Z extends Animal> void doSomething(Z z){
        return;
        /*
                here, this is a Generic Method with inheritance. As we can see that, the Generality that we can pass
                any object in place of `Z` is now restricted. Z can only take objects which are either `Animal` or
                child classes of Animal. This is how we can use Inheritance with Generics.
         */
        /*
                In this scenario, `extends` is called the "Upper Bound" as it does not allow any classes which are parent of 
                `Animal` class.  Hence, this is the "Upper Bound".
         */
    }
}

```
 ## LINKS:
1. GENERICS: https://docs.oracle.com/javase/tutorial/java/generics/why.html //this article is a must-read.
2. GENERICS: https://www.baeldung.com/java-generics
3. GENERIC METHOD: https://jenkov.com/tutorials/java-generics/methods.html
   

---

# DEEPAK SIR'S CLASS:

## I] Introduction to Generics:
Before the introduction of generics in Java 5, collections and other data structures primarily operated with the `Object` type. 
As `Object` is the parent class of every other class in Java. This meant that:

- Collections stored Object references: 

A List or Map, for example, would store references to Object instances, regardless of the actual type of data being put into them.
```java
class Pair{
    Object first; //there is no restriction on the data-type of data that can be stored in the `first` variable as any object can be referenced by `Object` class. 
    Object second;// i.e. Object second = new Animal(); or Object second = "Fatima";
}
/*
        This `Pair` class would solve the problem of creating infinite classes having two attributes. Now, List can directly have
        Pair objects into them. Suppose, I have to store coordinates of a location into  List then rather than creating `Coordinates` class, I can
        directly create List<Pair> and store pair objects into it.
        Where each Pair object, say, `p` will have `p.first` as a `double` latitude-value and `p.second` as double longitude-value.
        Similarly, If I want to store a List of Students objects that has name and psp as the two attributes then I can make 
        List<Pair> and store pair objects into it, where each Pair object, say, `p` will have `p.first` as a `String` name and `p.second` as double psp.
        In this way, we have solved the problem of creating multiple classes such as `Coordinates`, `Student`, etc.
        
 */
```
- Manual casting was required: 

  When retrieving an element from a collection, it had to be explicitly cast back to its original, specific type. 
  For instance, if a List was intended to hold String objects, retrieving an element would require a cast like (String) list.get(0).

```java
import java.util.ArrayList;
//given below is a List of Pair as discussed above
List<Pair> list = new ArrayList<>();
//each Pair object is formed as below
Pair p = new Pair();
p.first = "Vishal";
p.second = 91.50;
/*
        Now, when I want to use this name and psp in my further code then I will need to explicitly typecast it as,
        String name = p.first; 
        above line will give Compilation error as `p.first` is of `Object` type and we are storing this `Object` into String.
        Hence, to do it, we need to explicitly typecast it as,
        String name = (String) p.first; // now, it will be stored
        lly, we can have,
        Double marks = (Double) p.second;
        However, there is an issue:
        while storing p.second = 91.50; There is no restriction that p.second must hold a double value only.
        Since, p.second is of `Object` type, even if by mistake we store p.second = "94.50" i.e. a String object, 
        even then Java won't give any Compile time error because p.second is of `Object` type so it can accept any `Object` type.
        But, this creates issue while: Double marks = (Double) p.second; in Run-time,
        During Run-time, Java tries to store the `p.second` value which is String object into double varible (because of explicit typecasting)
        but it gives Run-time error.
        Doubt:- Why it doesn't give Compile time error?
        Answer: Look, at compile time, you can see that in, Double marks = (Double) p.second;
        Both sides, we have double datatype, so, compilation error is checked and passed. But, at run-time, when it actually tries to store
        String object into double variable, we get runtime error.
     
 */

/*
        So, because of this issue that
        1. object class gives lot of generality
        2. it doesn't offer Compile-time type-safety
        Hence, this was deemed to be problematic and hence in Java 5, concept of Generics was brought.
 */
```
- Type safety issues at runtime: 

  The lack of compile-time type checking meant that it was possible to accidentally insert objects of the wrong type into a collection. 
  This would not be caught by the compiler and would only result in a `ClassCastException` at runtime when an incorrect cast was attempted.

In essence, developers were responsible for ensuring type correctness through careful coding and manual casting, 
which was prone to errors and made the code less robust and more verbose compared to the type-safe approach offered by generics.

1.2) `Object` served the primary purpose of allowing users to use whichever datatype they want use however it came at the cost
compile time type safety as discussed earlier. To solve this, Generics were introduced.
- This Generic datatype is a placeholder datatype.
- So now, class Pair can be created as:
```java
class Pair<T, V>{
    T first; // first can only be of type `T`. `T` will be defined by the user while creating object of Pair class. 
    V second; //this way, we can get compile-time type-safety, which was missing in above case.`
}

//let's do object creation:
Pair<String, String> p1 = new Pair<>();
p1.first = "Naresh"; // if we put anything apart from String then we get compile time error as we have specified the type of `first` attribute as String.
p1.second = "Walde";

//lly, let's create another object
Pair<String, Double> p2 = new Pair<>();
p2.first = "Dinesh";
p2.second = 98.0; 
//if we try to enter other datatype, we get compile error 
p2.second = "Sonule"; //this leads to compile error
```

Thus, Generic has got tremendous use cases today, many data structures we use today like Maps, List, Stack, etc. If we go into 
our codebase and see the implementation of these data structures, then we will realise that they are using generics.

---

## II] Raw Data-types and Backward Compatibility

Java supports Backward Compatibility.

```java
import java.util.ArrayList;
import java.util.List;

List<Integer> list = new ArrayList<>();

//let's create a list with raw data types
List list = new ArrayList();
//Now, this second list is also accepted because of Backward Compatibility. but, we got a warning as this style is deprecated, 
//which means that this style is acceptable but not recommended.
//this second list with raw data types is based on `Object` as we've discussed that prior to Java 5, Object was used instead of 
//generics. So, we need to use first one only.
//lly, we can create HashMap without specifying the datatypes of Key-value pair as there exist the implementation of Hashmap,
//which was introduced before the generics came into the picture. And, it is supported because of Backward Compatibility.
```
---

## III] Generics and Inheritance and also Bounds:
 
### 3.1) Understanding Wildcard (?) in Java Generics and Inheritance:

The question mark (`?`) in Java generics is called the "wildcard" and plays an important role in making generics more
flexible, especially when dealing with inheritance.

---

### 3.2) Wildcard Basics

The wildcard `?` represents an unknown type in generics. There are three main forms:

1. **Unbounded Wildcard**: `<?>`
    - Represents any type
    - Example: `List<?>` can be a `List<String>`, `List<Integer>`, etc.

2. **Upper Bounded Wildcard**: `<? extends Type>`
    - Represents an unknown type that is a subtype of `Type`
    - Example: `List<? extends Number>` can be `List<Integer>`, `List<Double>`, etc.

3. **Lower Bounded Wildcard**: `<? super Type>`
    - Represents an unknown type that is a supertype of `Type`
    - Example: `List<? super Integer>` can be `List<Integer>`, `List<Number>`, `List<Object>`

---
### 3.3) Wildcards and Inheritance

Wildcards help bridge the gap between generics and inheritance:

```java
// Without wildcards - this won't work
List<Number> numbers = new ArrayList<Integer>(); // Compile error

// With wildcards - this works
List<? extends Number> numbers = new ArrayList<Integer>(); // OK
```

---

### 3.4) Key Point:
Remember that wildcards are a compile-time feature - they don't exist at runtime due to type erasure.

---

## IV] Type Erasure:

4.1) Before Java 5, i.e. before the introduction of Generics, Raw data types were used. Everywhere, `Object` class was used.
     For ex: `List<Object>` was there or say even `List` would also under the hood go to `List<Object>`.

4.2) After Java 5, we have so many specific lists say `List<String`, `List<Integer>`, `List<Animal>`, etc. So, at runtime,
     it will be very difficult to have so many lists, some from before java 5 and some from after java 5.

4.3) And, the main purpose of the introduction of Generics was to ensure Compile-time type-safety. So, Java developers 
     decided to erase the types once the code is successfully compiled. So, at runtime, every thing is Raw data types.
     For ex: Rather than maintaining so many different Lists, after successful compilation, everything will be just 
     `List<Object>`.

4.4) Here's a breakdown of type erasure in Java:

- **Compile-time type checking:**

    The Java compiler uses type erasure to enforce type safety during compilation. It checks the generic types used in 
    your code and ensures they are used correctly.
    
- **Runtime behavior:**

    After compilation, the compiler removes the explicit type parameters, replacing them with their bounds or Object. 
    For example, if you have `List<String>`, the `String` type information is removed, and it becomes simply `List`.
    
- **Performance:**

    Because type information is discarded at runtime, there is generally no performance overhead associated with using 
    generics in Java, unlike some other languages where type parameters might be maintained at runtime.
    
- **Example:**

    If you have a generic class `Box<T>`, where `T` is not bounded, the compiler will replace `T` with `Object` during 
    type erasure. 

---

**NOTE:** After studying the project module, you have to watch the last 10 min of deepak sir class to understand the example.

---

## V] More about Wildcard:

### 5.1) What is Wildcard (?):
In generic code, the question mark (?), called the wildcard, represents an unknown type. The wildcard can be used in a variety of situations: 
as the type of a parameter, field, or local variable; sometimes as a return type (though it is better programming practice to be more specific rather than using wildcard).

---

### 5.2) 🧠 Java Wildcard `?` Usage Examples

Java wildcards (`?`) are used when the exact type parameter is unknown. They are useful for **flexibility** and **type safety** when reading data from generics.
Let's see how they are used at various places.

#### ✅ (1) Wildcard as Method Parameter

```java
public void printList(List<?> list) {
    for (Object item : list) {
        System.out.println(item);
    }
}
````
- This method can accept a List<?> of any type (List< String >, List< Integer >, etc.).
- The for loop works because all elements are referenced as `Object`.
- Restriction: Can’t add elements (except null) because the compiler doesn’t know the exact type.

---

#### ✅ (2) Wildcard as Field

```java
class Box {
    List<?> items;

    Box(List<?> items) {
        this.items = items;
    }

    void display() {
        for (Object item : items) {
            System.out.println(item);
        }
    }
}
```

- The field `items` can hold a reference to any generic list. Though, rarely used directly as fields (prefer bounded wildcards or concrete types).

- Main limitation: as with all List<?>, you can’t add new elements (except `null`) to it.  (e.g., `Box.items.add(...)` fails).

---

#### ✅ (3) Wildcard as Local Variable

```java
public void demo() {
    List<String> names = List.of("Alice", "Bob");
    List<?> unknownList = names;

    for (Object item : unknownList) {
        System.out.println(item);
    }
}
```

Local variable `unknownList` holds a list of unknown type.

You can read from it as `Object`, but cannot add (except null).

---

#### ✅ (4) Wildcard as Return Type

```java
public List<?> getRandomList(boolean flag) {
    if (flag) return List.of("A", "B", "C");
    else     return List.of(1, 2, 3);
}
```

- Return type is a list of unknown type; since the method can return a List< String > or a List< Integer >, only List<?> is safe at compile time.

- Callers cannot add elements to the returned list due to the wildcard.
- Problem: Caller can only read as Object:
```java
List<?> result = getRandomList(true);
Object first = result.get(0); // ✅  
// String s = result.get(0); ❌ Needs casting (unsafe)
```

---

#### ⚠️ (5) You Cannot Add to `List<?>`

```java
List<?> list = new ArrayList<String>();
list.add("Hello");  // ❌ Compile-time error
```

Because the exact type is unknown, adding is not allowed (except `null`).

---

#### (6) Additional Notes

- Limitation: You cannot use ? as a type argument for creating new objects, e.g., new List<?>(); is not valid.

---

### 5.3) Usage of wildcard:

Java generics wildcards, represented by the question mark (?), provide flexibility when working with generic types, particularly in methods that interact with `Collections`.
They address the challenge that while `Integer` is a subtype of `Number`, `List<Integer>` is not a subtype of `List<Number>`, which means you can't pass a `List<Integer>` to a method 
expecting a `List<Number>` directly.

Here are the primary use cases for wildcards, broken down by type:

---

#### (1) Unbounded wildcards (<?>)
   - **Use case:** When you need a `method` that can work with a List of any type, but you only need to read elements and perform actions that don't depend on the specific type,
     such as printing the elements or calling methods defined in the `Object` class.
   - **Example:** Imagine a `method` that prints all the elements of a list, regardless of their type:
```java
public static void printList(List<?> list) {
    for (Object elem : list) {
        System.out.println(elem);
    }
}

```
- **Explanation:** The `List<?>` indicates a list of an unknown type. This `method` can accept a `List<String>`, `List<Integer>`, or any other List type. 
  However, because the type is unknown, you can't add any elements to the list (except `null`) and you can only treat the retrieved elements as `Object` instances.

---

#### (2) Upper-bounded wildcards (<? extends Type>)
- **Use case:** When you need a `method` that can process a List of a specific type or any of its subtypes. This is commonly used when you are reading data from a collection.
- **Example:** A `method` that calculates the sum of numbers in a list, where the list can contain `Number` objects or any subclass of `Number` (like `Integer`, `Double`, etc.).
```java
public static double sumOfList(List<? extends Number> list) {
    double sum = 0.0;
    for (Number n : list) {
        sum += n.doubleValue();
    }
    return sum;
}

```
- **Explanation:** Here, `List<? extends Number>` means the `method` can accept a list containing `Number` objects or any type that extends `Number` 
  (e.g., `List<Integer>`, `List<Double>`). This allows you to safely iterate through the list and use `Number` reference on the elements, 
  knowing they are at least of type `Number`. You cannot add new elements (except `null`) to this list because you cannot be sure that 
  the element you add will match the specific unknown type of the list.
- Initialize with a concrete subtype (e.g., ArrayList<Cat>).
```java
List<? extends Animal> animalAndChildren = new ArrayList<Cat>();
// animalAndChildren.add(new Cat()); // ❌ Still fails (type unsafe)
Animal a = animalAndChildren.get(0); // ✅ Safe to read
/*
        From this example, it is clear to me that when into the input parameter of a method a List with a wildcard will take a specific list only. for ex: here it has taken
        list of Cat. But, it could have been the list of Dog too. Hence, compiler doesn't allow to add to the list as we cannot add cat to list of Dog
 */
```
---

#### (3) Lower-bounded wildcards (<? super Type>)
- **Use case:** When you need a `method` that can add elements to a List of a specific type or any of its supertypes. This is useful when you are putting data into a `Collection`.
- **Example:** A `method` that adds `Integer` objects to a list, where the list can hold `Integer` or any supertype of `Integer` (like Number or Object).
```java
public static void addIntegers(List<? super Integer> list) {
    list.add(10);
    list.add(20);
    // list.add(new Double(5.5)); // This would cause a compile-time error
}

```
- **Explanation:** `List<? super Integer>` allows the `method` to work with `List<Integer>`, `List<Number>`, or `List<Object>`. 
  You can add `Integer` objects (or its subtypes but not supertypes) to the list because any of these list types can store an Integer. However, when you retrieve elements from this list, 
  you can only treat them as `Object` instances, as you don't know the exact type they originally held.

---

#### (4) In essence
- Wildcards empower you to write more flexible and reusable code that interacts with `collections` of various types while maintaining the type safety offered by generics. 
  They are particularly useful when defining methods that:
    - Only read from a generic collection (<? extends Type>)
    - Only write to a generic collection (<? super Type>)
    - Don't care about the specific type of elements but still need type safety (<?>)
Choosing the right wildcard depends on whether your `method` acts as a "producer" (reads elements) or a "consumer" (writes elements) of the collection. 
Remember the "PECS" principle (Producer Extends, Consumer Super) as a helpful guideline.
    - Producer Extends: If your `method` is primarily producing values from the `collection` (i.e., you are only reading elements), use an upper-bounded wildcard with `extends`.
    - Consumer Super: If your `method` is primarily consuming values into the `collection` (i.e., you are only adding elements), use a lower-bounded wildcard with `super`.
    - If your `method` needs to both read from and write to the `collection`, avoid using a `wildcard`.

---

### 5.4) Why Not Always Use List< Object >?
- `List<Object>` allows all types, but loses type relationships:
```java
List<Object> objs = new ArrayList<>();
objs.add("Hello"); // Works
objs.add(42);      // Works
```
- But you can’t pass a `List<String>` to a `List<Object>` in a `method` (Java generics are invariant).

- Wildcards (?) preserve flexibility while keeping type safety.

---

### 5.5) Summary of Wildcard Behavior:
- `List<?>` can take any specific list. Inside a `method`, we can read the elements of the list as `Object` reference, but we cannot add anything into the list except null.
- `List<? extends Animal>` can read as `Animal` reference, but we cannot add anything except `null`. It can take the list of `Animal` or list of subtypes of `Animal`.
- `List<? super Animal>`	can read as `Object` reference, and we can add `Animal` objects or objects of `Animal` subtypes. It can take the list of `Animal` or list of supertypes of `Animal`.

---

### 5.6) Code
```java
package LLD1.JavaAdvancesConcepts_1;

import java.util.ArrayList;
import java.util.List;

class Animal extends LivingBeings{} // Assuming LivingBeings is a superclass of Animal
class LivingBeings  {} 
class Cat extends Animal {}

public class Client {
    public static void main(String[] args) {

        // Fix 1: Use List<Object> if you want to add any type
        List<Object> allList = new ArrayList<>();
        allList.add(new Animal()); // Now works
        allList.add(new LivingBeings()); // Works
        allList.add(new Cat()); // Works

        // Fix 2: List<? extends Animal> can only be read from, not written to (except null)
        List<? extends Animal> animalAndChildren = new ArrayList<Cat>(); // Example: List of Cat
        // animalAndChildren.add(new Cat()); // ❌ Compile error (cannot add)
        Animal animal = animalAndChildren.get(0); // ✅ Can read as Animal

        // Fix 3: List<? super Animal> can accept Animal or its supertypes
        List<? super Animal> animalAndParent = new ArrayList<LivingBeings>(); // Example: List of LivingBeings
        animalAndParent.add(new Animal()); // ✅ Works
        animalAndParent.add(new Cat()); // ✅ Works (Cat is Animal)
        // animalAndParent.add(new LivingBeings()); // ❌ not allowed if LivingBeings is super of Animal
        Object obj = animalAndParent.get(0); // ✅ Can only read as Object
    }
}

}
```
---

### 5.7) Extra points:
- `List<?>` is not the same as `List<Object>`.
- `List<?>` means "a list of some specific but unknown type," while `List<Object>` means "a list that can hold any Object."
- Java’s wildcards (?) are designed for flexible reading (consumer) but strict writing (producer) to enforce type safety.
- Wildcards are Primarily used for Collections (But Not Exclusively)
  - **Main Use Case:** Yes, wildcards (`?`, `? extends T`, `? super T`) are most commonly used with collections (List, Set, Map, Queue, etc.) to make methods flexible yet type-safe when accepting input parameters.
  - **Wildcards Aren’t Only for `Collections`:** They can also be used in generic classes/methods (e.g., Class<?>, Comparator<? super T>). However, collections are the most common and practical use case.

---

### 5.8) Doubt: **Why `List<M extends Animal> animal1 = new ArrayList<>();` Fails**

The error occurs because you're mixing **generic type parameter declarations** (`<M extends Animal>`) with **variable declarations** (`List<...>`). Here’s why it’s invalid and how to fix it:

---

#### **(1) The Problem: Misplaced Type Parameter**
- **What you wrote**:
  ```java
  List<M extends Animal> animal1 = new ArrayList<>(); // ❌ Error
  ```
    - This is syntactically incorrect because:
        1. `M extends Animal` is a **generic type parameter declaration**, which belongs in **class/method definitions**, not variable declarations.
        2. `List<...>` expects a **concrete type or wildcard**, not a type parameter declaration. Because, `List<...>` is used while defining a variable 
            `animal1`, i.e. it is used while variable declaration.

- **What the compiler sees**:
    - It interprets `M extends Animal` as an attempt to **declare a new type parameter `M`** at the variable level, which Java doesn’t allow.

---

#### **(2) Why Java Rejects Your Syntax**
- **Type parameters (`<M extends T>`)** can only be declared in:
    - Class definitions: `class Box<T extends Animal> { ... }`
    - Method definitions: `<T extends Animal> void foo(List<T> list) { ... }`
- **Variables** must use:
    - Concrete types: `List<Cat>`
    - Wildcards: `List<? extends Animal>`
    - Existing type parameters: `List<M>` (if `M` is already declared in the class/method).

---

### **5.9) Key Takeaway**
1. **Java’s grammar** strictly separates:
    - Type parameter declarations (`<T extends U>`).
    - Type usage (`List<T>`, `List<?>`).

---

## VI] Variables
### 6.1) What is a Variable?
- A variable is a named storage location in `memory` that holds a value of a specific type.
  - It can be of primitive type(`int`, `boolean`) or an `object` reference type (`String`, `List<Animal>`).
  - Variables are declared in methods, constructors, or class/instance blocks.
  - purpose: Holds data (value/reference).

---

### **6.2) Variable Declaration Syntax:**
```java
Type variableName = initialValue;
//Examples:
int count = 10;                          // Primitive variable
String name = "Java";                    // Object reference
List<? extends Animal> animals = null;   // Generic with wildcard
```
---

### ** 6.3) Key Properties of Variables:**
1. **Concrete Types:** Must resolve to a known type at compile time (e.g., `String`, `List<Animal>`).

2. **Memory Allocation:** Allocates space to hold data.

3. **Scope:** Limited to the block where they’re declared.

---

## **VII] Type Parameters (Generics):**
### 7.1) What is a Type Parameter?
- A type parameter is a placeholder for a type, used to define generic classes, interfaces, or methods.
  - Allows code to work with unknown types safely.
  - **purpose:** Defines a placeholder for a type.
  - Cannot use wildcards (only T) as wildcards are supposed to be used when we are using or referencing a generic type
    and not while defining it.

---

### 7.2) Type Parameter Declaration Syntax:
Type parameters are declared where generics are introduced/defined:

(1) **For a generic class/interface:**
```java
class ClassName<T extends Bound> { ... }  
interface InterfaceName<K, V> { ... }
```

(2) **For a generic method:**
```java
<T> void methodName(T arg) { ... }
```

(3) **Examples:**
```java
class Box<T> { ... }                     // Class-level type parameter
<T> void print(T item) { ... }           // Method-level type parameter
```
---

### 7.3) Key Properties of Type Parameters
1. **Placeholder for Types:** `T` is a stand-in for a concrete type (e.g., `Box<String>` replaces `T` with `String`).

2. **Bounds:** Can be constrained (T extends Animal).

3. **No Memory Allocation:** They exist only at compile time (due to type erasure).

4. **Scope:** Limited to the class/method where declared.

---

### 7.4) Never mix the two:

❌ `List<T extends Animal> list` (invalid while variable declaration).

✅ `List<? extends Animal> list` (wildcard in variable declaration are allowed).

✅ `class Box<T extends Animal>` (type parameters in class declaration).

---

## VIII) Don't use wildcards here:
- "The wildcard is never used as a type argument for a generic method invocation, a generic class instance creation, or a supertype." is a rule in Java’s generics system,
  and understanding this clearly will help you avoid type-safety errors. Let's break it down phrase by phrase:

### 8.1) "The wildcard is never used as a type argument for a generic method invocation":
- ❌ Not allowed:
```java
List<?> list = new ArrayList<>();
someMethod(<?>);  // ❌ Invalid
```
- ✅ Allowed:
```java
someMethod(list);  // ✅ Valid if method accepts List<?>
```
- 🔸 Meaning: You can’t pass `<?>` directly as a type argument in method calls.
     Use a variable like `List<?>`, but not the wildcard itself in the call.

---

### 8.2) "…or a generic class instance creation"
- ❌ Not allowed:
```java
List<?> list = new ArrayList<?>();  // ❌ Invalid

```
- ✅ Allowed:
```java
List<?> list = new ArrayList<String>();  // ✅ Valid

```
- 🔸 Meaning: You cannot write `new ArrayList<?>()` — Java needs a real, known type during `object` creation, not a `wildcard`.

---

### 8.3) "…or a supertype."
❌ Not allowed:
```java
class MyClass<?> { }       // ❌ Invalid
class SubClass extends MyClass<?> { }  // ❌ Invalid

```
- ✅ Allowed:
```java
class MyClass<T> { }
class SubClass extends MyClass<String> { }  // ✅ Valid

```
- 🔸 Meaning: You cannot extend or implement a class/interface using a wildcard type.
  Supertype definitions require concrete types, like String, Integer, etc.

---

## IX] Summary:
### 9.1) Introduction to Generics
#### (1) Why Generics?

Generics are used to define classes, interfaces, and methods with a placeholder for data types. 
This allows for code that is type-safe and reusable. The primary purpose of generics is to ensure type safety 
at compile time, reducing runtime errors and eliminating the need for casting.

#### (2) Benefits of Generics

- **Type Safety:** 
Generic code checks the types at compile time, preventing runtime type errors.
- **Reusability:** 
The same code can operate on any data type without being rewritten for each type.
- **Elimination of Casts:** 
With generics, there is no need to cast data types explicitly.

### 9.2) Amazing points:
1. `List<List<Integer>>` requires `List` to be a data-type. If someone says, is `List` a datatype, then we have to say `YES` 
    as in a 2D-List the outer-list is having inner-list.