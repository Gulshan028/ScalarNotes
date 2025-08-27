# CONTEST 1

## I] **Key Learnings: Lambda Expressions & Variable Access**

### **1. The Core Principle**
Lambda expressions can **read** but **not modify** (reassign) local variables from 
their **enclosing scope** (the method or block where they are defined). They can only 
modify their own parameters or the state of objects.

### **2. The "Effectively Final" Rule**
A variable used in a lambda must be **final** or **effectively final**. This means:
*   **Final:** Declared with the `final` keyword.
*   **Effectively Final:** Not explicitly declared `final` but never reassigned 
    after its initial declaration.

### **3. What Lambdas CANNOT Do**
*   **❌ Modify local variables** (primitive or object references) from the enclosing scope.
    ```java
    int sum = 0;
    list.forEach(n -> sum += n); // COMPILATION ERROR
    ```

### **4. What Lambdas CAN Do**
*   **✅ Modify their own parameters** (if the parameter is a mutable object).
    ```java
    list.forEach(sb -> sb.append("!")); // OK
    ```
*   **✅ Read** from effectively final local variables.
    ```java
    final int discount = 10;
    prices.forEach(price -> System.out.println(price - discount)); // OK
    ```
*   **✅ Modify the state of objects** that a local variable references.
    ```java
    List<String> resultList = new ArrayList<>();
    list.forEach(item -> resultList.add(item)); // OK (modifying the object)
    ```
*   **✅ Modify instance or static class variables.**
    ```java
    class Calculator {
        private int total = 0;
        void calculate(List<Integer> nums) {
            nums.forEach(n -> total += n); // OK
        }
    }
    ```

### **5. Why This Restriction Exists**
*   **Thread Safety:** Lambdas are often used in parallel streams. Allowing mutation 
    of shared local variables would lead to data races and inconsistent results.
*   **Predictability:** It ensures the value of a variable does not change 
    unexpectedly after the lambda is defined.

---

### **In a Nutshell**
| Can Lambda... | Local Variable | Object's State | Class Variable |
| :--- | :---: | :---: | :---: |
| **Read** | ✅ (if effectively final) | ✅ | ✅ |
| **Modify** | ❌ | ✅ | ✅ |

**Simple Rule:** You can change the **contents** of an object (what's inside the box), but you cannot change the **variable** itself to point to a new object (you can't change the label on the box).

---
## Q.2] -> Answer:
`putIfAbsent()`method in ConcurrentHashMap is used for inserting a key-value pair 
if the key is not already associated with a value.

---

## Q.3 -> Answer:
The core role of the Banker's algorithm is to allocate resources based on a safe sequence to prevent deadlocks.

---

## Q.4] -> Answer:
JVM (Java Virtual Machine) is responsible for initiating the garbage collection process in Java.

---

## Q.5] **Java String Pool & String Literals - Cheat Sheet**

### **1. What is the Java String Pool?**
- A **special area in the Heap Memory** where String literals are stored.
- Designed to **optimize memory** and **improve performance** by reducing duplicate Strings.
- **From Java 7 onwards**, the String Pool is located in the main Heap (priorly in PermGen).

---

### **2. What is a String Literal?**
- A **sequence of characters enclosed in double quotes** (`" "`) appearing directly in source code.  
  Example: `"hello"`, `"Java"`, `"123"`.
- The compiler **automatically stores literals in the String Pool**.
- **Same literals share the same memory reference**.

---

### **3. How String Pool Works**
- When a literal is created:  
  → Java checks if it already exists in the pool.  
  → If **yes**, returns the reference to the existing object.  
  → If **no**, adds it to the pool and returns the reference.
- **Only literals are pooled by default**. Objects created with `new` are not pooled unless interned.

---

#### **4. String Literal vs. `new String()`**
| **Aspect**          | **String Literal**                                      | **`new String()`**                                                                              |
|----------------------|---------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| **Syntax**           | `String s = "hello";`                                   | `String s = new String("hello");`                                                               |
| **Memory Location**  | String Pool                                             | Heap (general memory)                                                                           |
| **Object Reuse**     | Yes, if literal exists. <br/> its reference is returned | No (always creates new object)                                                                  |
| **Reference Comparison** | `s1 == s2` returns `true` for same literals             | `s1 == s2` returns `false` even for same content because they are stored at different locations |

---

#### **5. Interning Strings**
- Use `intern()` to manually add a String to the pool or get a pooled reference:
  ```java
  String s1 = new String("hello"); // Creates in heap and not in String Pool
  String s2 = s1.intern();         // Returns pooled reference
  System.out.println(s1 == s2);    // false
  System.out.println("hello" == s2); // true
  ```
- `intern()` simply created String literal from String object.
- **Warning**: Excessive interning can increase memory usage.

---

### **6. Key Rules**
- ✅ Strings are **immutable** (safe for sharing).
- ✅ Literals are **automatically interned**.
- ✅ Use `equals()` for **content comparison**, `==` for **reference comparison**.
- ❌ Avoid `==` for non-pooled Strings.

---

### **7. Example Code**
```java
// Literals (pooled)
String a = "hello";
String b = "hello";
System.out.println(a == b); // true (same reference)

// new keyword (not pooled)
String c = new String("hello");
String d = new String("hello");
System.out.println(c == d); // false (different references)

// Interning
String e = c.intern();
System.out.println(a == e); // true (both pooled)
```

---

### **8. Why String Pool Matters**
- **Memory Efficiency**: Prevents duplicate Strings.
- **Performance**: Faster comparison with `==`.
- **Foundation**: Critical for understanding Java memory management and common interview questions.

---

### **9. Summary**
- **String Literal**: Text in double quotes in source code → stored in String Pool.
- **String Pool**: Memory area for storing unique literals → saves memory by reuse.
- **Rule of Thumb**: Prefer literals over `new String()` for better performance unless explicit duplication is needed.

This is a concise yet comprehensive overview of String Pool and literals! 🚀     

---

## Q.6] ->Functional Programming: The One Main Idea

Imagine you are solving a math problem on a whiteboard. You never *erase* or *change* a number you've already written. Instead, you work things out step-by-step, writing each new result *below* the previous one.

**That's exactly how Functional Programming works.**

Its one golden rule is: **"Don't Change Things, Create New Things."**

1.  **It Uses "Math-Style" Functions:**
    *   Think of a function like a simple math equation, `y = x + 2`.
    *   If `x` is `5`, `y` is **always** `7`. Every single time. The function doesn't look at the weather, the time, or any other variable. It just reliably gives the same answer from the same input.

2.  **It Never "Edits" Data:**
    *   In other styles of coding, a common thing to do is change a variable, like `score = score + 10`.
    *   Functional Programming **forbids this**. Instead of changing the old score, you would **calculate a brand new score** and use that. The original data is left untouched, safe and reliable.

**Why is this useful?**
Because if you never change anything, you can never accidentally break anything that was relying on the old value. It makes your code incredibly predictable, easy to test, and much less prone to weird bugs.

**In a nutshell: Functional Programming is the style of coding where you solve problems by combining reliable, math-like functions that never edit data, only create new data.**

---

## Q.7] **Daemon Thread** 

### 1. What is a Daemon Thread?

A daemon thread is a **low-priority background thread** in Java that provides services to user threads (non-daemon threads). Its life depends on the user threads.

### 2. The Key Analogy: The **Shopkeeper** and the **Cleaner**

Imagine a shop:
*   The **Shopkeeper** is a **user thread**. Their work is essential (serving customers). As long as the shopkeeper is working, the shop is open.
*   The **Cleaner** is a **daemon thread**. Their job is to clean the shop in the background. They are helpful but not essential.

**The Rule:** When the last shopkeeper (user thread) leaves and closes the shop, the cleaner (daemon thread) is **automatically asked to leave immediately**, whether their cleaning is finished or not.

---

### 3. Key Characteristics of Daemon Threads:

1.  **Background Servants:** They are meant to perform supporting tasks for user threads, like garbage collection, auto-save features, monitoring, listening for incoming connections, etc.
2.  **Automatic Termination:** The Java Virtual Machine (JVM) **exits** when the only threads running are daemon threads. In other words, the JVM doesn't wait for a daemon thread to finish its execution if all user threads have finished.
3.  **Inheritance:** A thread inherits its daemon status from the thread that created it. The main thread is always a user thread, so any thread created in `main()` is, by default, a user thread unless you explicitly change it.
4.  **You Cannot Change It Later:** You must set a thread as daemon **before** you start it (`thread.start()`). If you try to set it after the thread has started, it will throw an `IllegalThreadStateException`.

---

### 4. How to Create a Daemon Thread:

You use the `.setDaemon(true)` method on a Thread object.

```java
public class DaemonExample {
    public static void main(String[] args) {
        // Create a new thread
        Thread cleanerThread = new Thread(() -> {
            while (true) {
                System.out.println("Cleaning in the background...");
                try {
                    Thread.sleep(1000); // Simulate work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // SET THE THREAD AS A DAEMON BEFORE STARTING IT
        cleanerThread.setDaemon(true);

        // Start the thread
        cleanerThread.start();

        // This is the main (user) thread.
        System.out.println("Main thread is doing its work...");
        try {
            Thread.sleep(3000); // Let the main thread sleep for 3 sec
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread is finishing.");

        // As soon as this main (user) thread finishes,
        // the JVM will terminate, ALSO KILLING the daemon thread
        // even if it's in the middle of its loop.
    }
}
```

### 6. Possible Output:
```
Main thread is doing its work...
Cleaning in the background...
Cleaning in the background...
Cleaning in the background...
Main thread is finishing.
```
*(The program exits immediately after "Main thread is finishing." The daemon thread is terminated by the JVM and doesn't get to print its next message.)*

---

### 7. Daemon vs. User Threads:

| Feature | User Thread (Non-Daemon) | Daemon Thread |
| :--- | :--- | :--- |
| **Purpose** | Core, essential tasks | Background support tasks |
| **JVM Behavior** | The JVM **waits** for all user threads to finish before exiting. | The JVM **does not wait** for daemon threads to finish. |
| **Priority** | High | Low |
| **Creation** | Default type for a new thread | Must be explicitly set with `setDaemon(true)` |
| **Example** | Main thread, threads handling UI events | Garbage Collector, timer threads |

### 8. Summary:
A **daemon thread** is a background worker thread whose life is tied to the application's user threads. When all user threads finish, the daemon threads are automatically terminated by the JVM, whether their task is complete or not. This makes them perfect for non-critical maintenance jobs.

---

## Q.8] Amdahl’s Law:
In the context of parallel execution, Amdahl’s Law is:

- A law that states the speedup achievable by parallel processing is limited by the sequential portion of the program.

---

## Q.9]
-   Synchronized blocks are more efficient than using the synchronized keyword 
    on the entire method.
-   Use synchronized blocks when you need maximum performance and concurrency, 
    when your method contains operations that don't require synchronization, 
    or when you need to synchronize on an attribute object other than `this`.

-   Use synchronized methods for simplicity when the entire method body is 
    the critical section and the operation is very short, making the performance gain 
    from a block negligible. They are a good default for simple thread-safe classes.

---

## Q.10]
`Object` class provides the methods `wait()`, `notify()`, and `notifyAll()`.

---

## Q.11]
Banker’s algorithm allocates resources based on a safe sequence to prevent deadlocks.

---

## Q.12]
`sleep()` method is used to force a thread to pause for a specific duration in Java.

---

## Q.14]  Summary: Exception Handling in ExecutorService

### 14.1) **Core Principle**
The mechanism for handling exceptions depends **not on the task type (`Runnable` vs. `Callable`) but on the submission method (`execute` vs. `submit`)**.

### **14.2) Using `submit()` method (Recommended for Error Handling)**
*   **Applies to:** Both `Runnable` and `Callable` tasks.
*   **Behavior:** The ExecutorFramework **catches any exception** thrown by the task (checked or unchecked).
*   **Mechanism:** The exception is **wrapped** inside an **`ExecutionException`**.
*   **How to Access the Error:** The `ExecutionException` is stored in the `Future` object and is **thrown only when you call `future.get()`**.
*   **How to Handle:**
    ```java
    try {
        Future<?> future = executor.submit(myTask);
        future.get(); // ExecutionException is thrown here if the task failed
    } catch (ExecutionException e) {
        Throwable realCause = e.getCause(); // Retrieve the original exception
        System.out.println("Task failed because: " + realCause.getMessage());
    }
    ```
*   **Key Advantage:** Provides a controlled, programmatic way to handle failures in the calling thread.

### **14.3) Using `execute()` method (Fire-and-Forget)**
*   **Applies to:** Only `Runnable` tasks.
*   **Behavior:** The ExecutorFramework **does not catch** exceptions. An exception **terminates the worker thread**.
*   **Mechanism:** The exception is **printed to the standard error stream** (`stderr`) but is **lost** from the perspective of the main application code. There is no way to retrieve it programmatically.
*   **How to Handle:** You can't. Avoid this method for tasks where you need to know if they succeeded or failed.
*   **Key Disadvantage:** Poor error handling. Exceptions are lost, making debugging difficult.

---

### 14.4) **Quick Decision Guide**

| If you need to... | Then use... | Because... |
| :--- | :--- | :--- |
| **Know if a task failed** and handle it | **`executor.submit(task)`** | Exceptions are wrapped in `ExecutionException` and available via `future.get()`. |
| **Get a result back** from a task | `executor.submit(Callable)` | `Callable` returns a value, and `submit()` returns a `Future` for that value. |
| **Run a simple fire-and-forget task** where failure is acceptable or handled inside the task itself | `executor.execute(Runnable)` | It's slightly lighter weight, but you lose all error reporting. |

**Golden Rule:** **Always prefer `submit()` over `execute()`** unless you have a specific reason not to. The ability to handle exceptions via the `Future` object is crucial for writing robust, maintainable concurrent applications.

---

## Q.15]
The purpose of the `wait()` method in Java is to pause the execution of a thread 
until another thread invokes the `notify()` or `notifyAll()` method.

---

## Q.16]
Is the following interface a functional interface?
```java
@FunctionalInterface
interface MyInterface {
void myMethod(int x, int y);
boolean equals(Object obj);
}
```
### 16.1) Answer:
Yes, the interface `MyInterface` **is a valid functional interface**.

Here's the breakdown of why:

### 16.2) Definition of a Functional Interface
A functional interface is an interface that has **exactly one abstract method** 
(not counting methods that are `public` members of `Object` class). 
This single abstract method is called the *functional method*.

### 16.3) Analyzing `MyInterface`
The interface declares two methods:
1. `void myMethod(int x, int y);` - This is an abstract method.
2. `boolean equals(Object obj);` - This is also an abstract method.

At first glance, it seems like there are two abstract methods. However, the key rule is:

> **Methods that are public members of `java.lang.Object` (like `equals`, `hashCode`, 
> `toString`) do not count toward the "one abstract method" requirement.**

### 16.4) Why `equals` Doesn't Count
The `equals` method is already a public method defined in the `Object` class. 
Any implementation of this interface will ultimately inherit an implementation 
of `equals` from `Object` (unless explicitly overridden). Therefore, the compiler 
ignores it when determining if the interface is a functional interface.

### 16.5) The Functional Method
The only abstract method that counts is:
- `void myMethod(int x, int y);`

Since there is exactly one abstract method (when excluding `Object` methods), 
the interface qualifies as a functional interface.

### 16.6) The `@FunctionalInterface` Annotation
The annotation is used to enforce the rule at compile time. If the interface did 
not meet the criteria (e.g., if you added another abstract method that wasn't 
from `Object`), the compiler would throw an error.

**Example that would NOT compile:**
```java
@FunctionalInterface
interface InvalidInterface {
    void myMethod(int x, int y);
    boolean equals(Object obj);
    void anotherMethod(); // This third method causes a compile error!
}
```
Error: `InvalidInterface is not a functional interface: multiple non-overriding 
abstract methods...`

### 16.7) Conclusion:
**Yes, `MyInterface` is a functional interface.** It has exactly one abstract method
that is not a public member of `Object` (`myMethod`), and the `equals` method is 
ignored for the purpose of counting. The `@FunctionalInterface` annotation is 
correctly used and will not cause a compilation error.

## Q.17] Gulshan's doubt: 
How does Java recognize that this equals() method belongs to Object class?
I can create my custom class and make an equals() method there too?

### 17.1) Answer:
Excellent and very insightful question! You've hit on a crucial nuance of how 
Java determines what a "functional interface" is.

The key is not that the method is *named* `equals`. The key is the **method's 
signature and its origin in the type hierarchy**.

Here’s how the Java Language Specification (JLS) and the compiler recognize it:

### 17.2) The Rule is Based on Method Overriding, Not Just Name

The JLS (§9.8) states that an interface is a functional interface if it has **exactly
one abstract method that is not a *public method* of `java.lang.Object`**.

A method in an interface is considered to be a **public method of `Object`** if it:
*   Has the **same signature** (name and parameter types) and
*   Has the **same return type** as a `public` method in the `Object` class.

---

## Q.18] Predict the output:
```java
List<String> list = List.of("apple","mango","guava");
Comparator<String> cmp = (s1, s2) -> s2.compareTo(s1);
Collections.sort(list,cmp);
System.out.println(list);
```
[A] [apple,mango, guava];     [B] [mango, guava, apple];
[C] [apple, guava, mango];    [D] Runtime Exception;

### Answer: 
**Runtime Exception**

#### 18.2) Key Points:
1. **`List.of()` creates an immutable list:**
    - The `List.of()` method (introduced in Java 9) returns an **immutable** list. 
      This means the list cannot be modified (elements cannot be added, removed, or 
      changed).

2. **`Collections.sort()` attempts to modify the list:**
    - The `Collections.sort(list, comparator)` method tries to sort the list 
      **in-place**, meaning it rearranges the elements within the list. This requires 
      the list to be **mutable**.

#### 18.3) Output:
The code will throw a **`Runtime Exception`** (specifically, `UnsupportedOperationException`).

#### 18.4) Additional Note:
If you wanted to sort this list, you would need to create a mutable copy first, for example:
```java
List<String> mutableList = new ArrayList<>(List.of("apple", "mango", "guava"));
Collections.sort(mutableList, cmp);
System.out.println(mutableList); // Output: [mango, guava, apple]
```
This would output `[mango, guava, apple]`. But in the given code, the list is immutable, so it fails.

---

## Q.19] Java Static Blocks - Key Notes

### **19.1) What is a Static Block?**
*   A static block is a block of code declared inside a class with the `static` keyword.
*   Syntax:
    ```java
    static {
        // initialization code here
    }
    ```
*   It is also known as a **static initialization block**.

#### **19.2) Primary Purpose**
To perform **one-time, complex initialization** of static variables or execute setup tasks required for the class to function, which cannot be handled in a single line of code.

**Common Use Cases:**
*   Loading configuration files or properties.
*   Establishing connection pools (e.g., database, network).
*   Registering drivers (e.g., JDBC `DriverManager.registerDriver()`).
*   Loading native libraries using `System.loadLibrary()`.
*   Initializing static maps or collections with values.

#### **19.3) When is it Executed? (MOST IMPORTANT POINT)**
*   **Timing:** A static block is executed **exactly once**, automatically, when the class is **loaded into memory** by the JVM.
*   This happens **before**:
    *   The `main` method is called.
    *   Any static method of the class is called.
    *   Any instance (object) of the class is created.

#### **19.4) Execution Order**
*   If a class has multiple static blocks, they are executed **in the order they appear** (top to bottom) in the code.
*   Static variable assignments and static blocks are executed together in the order they are written. This combined sequence is often called the class's **static context**.

**Example of Order:**
```java
public class OrderExample {
    static int a = 5; // 1. Assignment first
    static {
        System.out.println("First static block: " + a); // 2. Then this block
        b = 10; // Allowed to assign to a variable declared later
    }
    static int b;
    static {
        System.out.println("Second static block: " + b); // 3. Then this block
    }
}
```

#### **19.5) What Can It Access?**
*   A static block can **only directly access and modify other static members** (static variables and static methods) of the class.
*   It **CANNOT** directly access instance variables or instance methods. This is because there is no instance of the class (`this` reference) available at the time the static block runs.

#### **19.6) Key Characteristics & Rules**
*   **No Keyword:** It is declared only with `static {}`. You cannot use keywords like `synchronized`, `abstract`, etc., with the block itself (though you can have synchronized code inside it).
*   **No Arguments/Return Type:** It cannot take arguments or return a value.
*   **No Name:** It has no name, so it cannot be called explicitly from your code. It is invoked automatically by the JVM.
*   **Exception Handling:** You can use try-catch blocks inside a static block to handle checked exceptions. If an unhandled exception occurs, the class will fail to load, often resulting in a `ExceptionInInitializerError`.

---

## Q.20]
The `final` keyword, when applied to a method, means that the method cannot be 
overridden by any subclass. This is a fundamental rule in Java to prevent subclasses
from changing the implementation of a method that is deemed critical by the superclass.

---

## Q.21] **Rule: Overriding and Access Modifiers**

**When overriding a method in a subclass, you cannot reduce the visibility 
(access modifier) of the method. The overriding method must be at least as 
accessible as the method in the parent class.**

### 21.1) **The Hierarchy of Accessibility (from most to least accessible):**
`public` > `protected` > *default* (package-private) > `private`

### 21.2) **What is Allowed?**
You can make the overriding method **more accessible** (or equally accessible), 
but **never less accessible**.

| Superclass Method Access | Allowed Overriding Access in Subclass                                                                 |
| :----------------------- | :---------------------------------------------------------------------------------------------------- |
| `public`                 | **`public` only**                                                                                     |
| `protected`              | **`public`** or **`protected`**                                                                       |
| *default* (no modifier)  | **`public`**, **`protected`**, or *default*                                                           |
| `private`                | (Not applicable. Private methods are not inherited and therefore cannot be overridden in the first place.) |

### 21.3) **Why This Rule Exists:**
This is a fundamental part of the **Liskov Substitution Principle** (a key 
object-oriented design principle). It ensures that a subclass can be used anywhere 
its superclass is expected without breaking the code. If a subclass could hide a method
that the superclass exposes, then using the subclass through a superclass reference 
(like `A obj = new B()`) would lead to errors, as the method might become inaccessible.

### 21.4) Point to REMEMBER:
If we break this rule then we get Compilation error.

---

## Q.22] The **`Cloneable` interface** and the **`clone()` method** in Java
The **`Cloneable` interface** and the **`clone()` method** in Java work together to 
enable object cloning (creating copies of objects). Here’s an overview of each and 
how they function:

### 22.1) Cloneable Interface
- It is a **marker interface** (empty interface with no methods).
- It is part of the `java.lang` package.
- **Purpose:** It signals to the `Object.clone()` method that it is legal to make a 
   field-for-field copy of instances of the implementing class.
- If a class **does not implement `Cloneable`**, calling `clone()` on its object 
   throws a **`CloneNotSupportedException`** at runtime.
- By convention, classes implementing `Cloneable` should **override the `clone()` 
  method** to make it accessible i.e. make it `public` (because `Object.clone()` is protected).

### 22.2) clone() Method
- Declared in the `Object` class as a **protected` method** that creates a copy of 
  the object.
- The default implementation performs a **shallow copy**: it copies primitive fields 
  and object references as-is.
- When a class overrides `clone()`, it can perform either:
    - **Shallow copy:** Just call `super.clone()`.
    - **Deep copy:** Manually clone nested mutable objects to avoid shared references.

### 22.3) Important Details
- Implementing `Cloneable` alone does not automatically enable cloning. You must also 
  override `clone()` and call `super.clone()` internally.
- The returned object from `clone()` is of type `Object`, so you normally cast it to 
  the desired class.
- You must handle (or declare) `CloneNotSupportedException` because `clone()` can 
  throw it if the class doesn't support cloning.

### 22.4) Example Usage

```java
public class Person implements Cloneable {
  private String name;
  private Address address;

  @Override
  public Object clone() throws CloneNotSupportedException {
    Person cloned = (Person) super.clone(); // shallow copy
    cloned.address = (Address) address.clone(); // deep copy for nested object
    return cloned;
  }
}
```

### 22.5) Summary
- **`Cloneable`**: Marker interface that allows cloning.
- **`clone()`**: Method to create a copy, must be overridden to provide public access
  and proper cloning logic.
- If not implemented or overridden properly, cloning will fail with an exception.

---
Of course. This is a fundamental but often misunderstood topic in Java. Let's break it down.

### **1. The `clone()` Method and the `Cloneable` Interface**

The ability to create an exact copy of an object is called **cloning**. In Java, this is primarily achieved using the:
*   **`clone()` method**: Defined in the root `Object` class.
*   **`Cloneable` interface**: A *marker interface* (it has no methods) that must be implemented by a class to allow cloning.

---

### **2. How to Use Cloning: The Steps**

To successfully clone an object, you must follow these steps:

#### **Step 1: Implement the `Cloneable` Interface**
If your class does not implement `Cloneable`, calling the `clone()` method (inherited from `Object`) will throw a `CloneNotSupportedException`.

```java
public class MyClass implements Cloneable {
    // fields and methods
}
```

#### **Step 2: Override the `clone()` Method**
The `clone()` method in the `Object` class is `protected`. To make it accessible from outside your class (e.g., from a `main` method in another class), you must override it and make it `public`.

```java
@Override
public Object clone() throws CloneNotSupportedException {
    return super.clone(); // This does the actual work
}
```

#### **Step 3: Handle the Exception**
You must either handle the `CloneNotSupportedException` with a try-catch block or declare that your method `throws` it.

---

### **3. Shallow Copy vs. Deep Copy in `clone()`**

This is the most critical concept to understand.

#### **Default Behavior (Shallow Copy)**
The default `Object.clone()` method performs a **shallow copy**. It creates a new object and copies all the field values from the original object to the new one.

*   For primitive fields (e.g., `int`, `double`), the value is copied.
*   For object reference fields (e.g., `String`, `Address`), the *memory address* (the reference) is copied, **not the object itself**.

**Problem with Shallow Copy:** If your class has mutable object references, the original and cloned objects will share those objects. A change in the mutable object of one will be reflected in the other.

**Example of Shallow Copy Issue:**
```java
class Address {
    String city;
    // constructor, etc.
}

class Person implements Cloneable {
    String name; // Immutable - safe for shallow copy
    Address address; // Mutable - DANGER!

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); // This does a SHALLOW COPY
    }
}

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        Person original = new Person("John", new Address("London"));
        Person clone = (Person) original.clone();

        clone.address.city = "Paris"; // This also changes original.address.city to "Paris"!
    }
}
```

#### **Solution: Implementing Deep Copy**
To create a truly independent copy, you must implement a **deep copy** inside your overridden `clone()` method. This means you need to manually clone any mutable object fields.

**Example of Deep Copy Implementation:**
```java
class Address implements Cloneable { // Address must also be cloneable
    String city;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); // Since Address only has a String, shallow copy is enough for it.
    }
}

class Person implements Cloneable {
    String name;
    Address address;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Person cloned = (Person) super.clone(); // 1. Shallow copy (name, address ref)
        cloned.address = (Address) address.clone(); // 2. Deep copy the Address object
        return cloned;
    }
}
```
Now, changing `clone.address.city` will **not** affect the `original` object.

---

### **4. Best Practices and The "Copy Constructor" Alternative**

The cloning mechanism in Java is often considered broken and is rarely used in modern code for these reasons:
1.  **Cumbersome:** Requires implementing an interface, overriding a method, and handling exceptions.
2.  **Non-intuitive:** The `Cloneable` interface doesn't have a `clone` method, yet not implementing it causes an error.
3.  **Dangerous:** It's easy to accidentally create a shallow copy when you need a deep copy.

#### **Preferred Alternative: Copy Constructors**
A copy constructor is a constructor that takes an object of the same class as a parameter and copies all its fields. It's much simpler and gives you explicit control.

```java
public class Person {
    private String name;
    private Address address;

    // Copy Constructor
    public Person(Person other) {
        this.name = other.name;
        this.address = new Address(other.address); // Assume Address has a copy constructor too
    }
    // ... other constructors and methods
}

// Usage: No casting, no exceptions. Clean and clear!
Person original = new Person(...);
Person copy = new Person(original);
```

### **Summary Cheat Sheet**

| Feature | `clone()` Method | Copy Constructor |
| :--- | :--- | :--- |
| **Mechanism** | Inherited from `Object`, requires overriding. | A normal constructor you write. |
| **Interface** | Must implement `Cloneable` (marker interface). | No interface required. |
| **Exception** | Can throw `CloneNotSupportedException`. | No exceptions involved. |
| **Casting** | Returns `Object`, requires explicit casting. | Returns the exact type. |
| **Control** | Default is shallow copy; deep copy must be manually implemented. | You have full, explicit control over what is copied and how. |
| **Best For** | Legacy code. | **Modern Java development.** |

**Conclusion:** While you must understand how `clone()` works for exams and interviews, in practice, **using a copy constructor is almost always the better and safer choice.**

---

## Q.23]
Z Garbage Collector is designed for low-latency applications with large heap sizes.

---

## Q.24]
`parallelStream()` method is used to obtain a parallel stream from a ConcurrentHashMap.

---

## Q.25]
Which of the following statements about ConcurrentHashMap resizing is correct?
- It resizes segments individually, allowing other threads to operate on unaffected segments concurrently.

---

## Q.26]
In the context of semaphores, what is the purpose of the `tryAcquire()` method?
- To attempt to acquire the semaphore without blocking.
- How it Works:-
  - **Non-Blocking:** Unlike the `acquire()` method, `tryAcquire()` does not wait if a permit isn't immediately available.
  - **Immediate Result:** It returns `true` as soon as a permit is acquired and `false` if it cannot be acquired at that moment. 
     
---

## Q.27]
When the `notify()` method is called, which thread gets awakened among the waiting threads?
- It is not deterministic and depends on the JVM.

---

## Q.28] THREAD and its methods:
### 28.1) The `sleep()` Method

This is a static method of the `Thread` class used to pause the execution of the 
**current** thread for a specified period.

*   **Purpose:** To introduce a delay. Useful for simulating time-consuming tasks, 
    polling, or controlling the rate of execution.
*   **How it works:** `Thread.sleep(milliseconds)` causes the currently executing 
    thread to suspend execution for the specified number of milliseconds.
*   **Exception:** It throws a checked `InterruptedException`, which must be handled.
    This exception is thrown if another thread interrupts the sleeping thread.

```java
public class SleepExample {
    public static void main(String[] args) {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            try {
                System.out.println(threadName + " is about to sleep for 2 seconds.");
                Thread.sleep(2000); // Pause for 2000 milliseconds (2 seconds)
                System.out.println(threadName + " woke up!");
            } catch (InterruptedException e) {
                System.err.println(threadName + " was interrupted during sleep.");
            }
        };

        Thread thread = new Thread(task);
        thread.start();
    }
}
```

---

### 28.2) The `wait()` Method

**Important:** `wait()` is not a method of the `Thread` class. It is a method of the 
base `Object` class, and its use is deeply tied to **thread synchronization and 
inter-thread communication.**

*   **Purpose:** To make a thread release its lock and wait until another thread 
    notifies it. It is always used within a `synchronized` block or method.
*   **How it works:**
    1.  A thread acquires a lock on an object (enters a `synchronized` block).
    2.  It calls `object.wait()`. This causes the thread to:
        *   Release the lock on that object.
        *   Go into a waiting state (not runnable).
    3.  The thread will stay waiting until another thread calls `object.notify()` 
        or `object.notifyAll()` on the *same object*.
*   **Why it's used:** To coordinate work between threads. For example, a consumer 
    thread waits if there's no data to process, and a producer thread notifies it when
    data is available.

```java
public class WaitNotifyExample {
    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();

        // Thread 1 (The Waiter)
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    System.out.println("Thread 1: Waiting for notification...");
                    lock.wait(); // Releases the lock and waits
                    System.out.println("Thread 1: Received notification! Resuming...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2 (The Notifier)
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(1000); // Simulate some work
                    System.out.println("Thread 2: Sending notification...");
                    lock.notify(); // Wakes up one thread waiting on 'lock'
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Main thread: Finished.");
    }
}
```

---

### 28.3) Key Difference: `sleep()` vs. `wait()`

| Feature | `sleep()` | `wait()` |
| :--- | :--- | :--- |
| **Class** | `Thread` class static method | `Object` class instance method |
| **Lock** | **Does not** release the lock. | **Releases** the lock on the object. |
| **Usage** | Called on the `Thread` class itself. | Called on a monitor object (`object.wait()`). |
| **Context** | Can be called from any context. | **Must** be called from a `synchronized` block/method. |
| **Wake-up** | Wakes up after the specified time elapses. | Wakes up only when `notify()` or `notifyAll()` is called (or if interrupted). |
     
---

## Q.29]

