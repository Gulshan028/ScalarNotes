# Java Advanced Concepts - 1

---
**GENERICS**
---

# 1) Generics:
- Generics in Java allow us to define classes, interfaces, and methods using type parameters instead of concrete data types. These type parameters, such as `<T>`, `<K>`, or `<V>`, act as placeholders and the actual type is specified at the time of object creation or method call.
- This enables the same code to work for multiple data types without duplication and, more importantly, provides compile-time type safety.
- Before generics, developers commonly used `Object` class (as it is the superclass of all other classes) to write reusable code, but this lost type safety and required explicit casting, which could lead to runtime `ClassCastException`. Generics move these errors to compile time and eliminate most casts.
- Type parameters are declared using angle brackets after the class, interface, or method name.

# 2) Raw Types:
- Java is backward compatible, which means code written for older Java versions continues to compile and run on newer Java versions. This makes it easier for developers and enterprises to upgrade their Java runtime without breaking existing systems.
- Generics were introduced in Java 5. Before that, reusable code commonly relied on `Object`. To preserve backward compatibility, Java still allows the use of generic classes without specifying their type parameters. This is called a *raw type*.
- For example, using a generic class without type arguments is valid:
```java
Calculator calc = new Calculator(true, true);
```
- This form is called a raw type. It is accepted for backward compatibility, but it loses compile-time type safety and results in unchecked warnings.
- The recommended way is to always use parameterized types:
```java
Calculator<Boolean, Boolean> calc = new Calculator<>(true, true);
```
- In short, raw types exist only to support pre–Java 5 code, and modern Java code should avoid them to retain type safety.

# 3) Generics under the hood and Primitive types:
- Java Generics are built on top of Java `Object` so they can deal with objects only, hence Java generics are implemented using **type erasure**. This results in, at compile time, generic type parameters are removed, and at runtime they are replaced with their upper bound (or `Object` if no bound is specified).
- Because of type erasure, generics work only with **reference types**, not primitive types. That is why we cannot use primitive datatypes like `int`, `double`, etc. directly with generics. Primitive datatypes are the fundamental data types in Java that lack the object-oriented features of reference types.
- To support this, Java provides **wrapper classes** such as `Integer`, `Double`, `Boolean`, etc., which wrap primitive values as objects. These wrapper classes are used with generics and the collections framework, and they also allow `null` values, which primitives do not support.
- Java supports automatic conversion between primitives and their wrappers using:
* **Autoboxing** – converting a primitive to its wrapper
* **Unboxing** – converting a wrapper to its primitive
- Example:
```java
int x = 5;
Integer X = x;   // autoboxing

Integer Y = 5;
int y = Y;       // unboxing
```

# 4) Generic Methods:
- A generic method is a method that declares its own type parameter and uses it in its parameter list and/or return type.
- A generic method can belong to either a generic class or a non-generic class. Even inside a generic class, a method may introduce its own independent type parameter that is separate from the class-level type parameter, and this is the defining feature of a generic method.
- The type parameter of a generic method is declared in angle brackets immediately before the return type.
- Example of a generic method:

# 5) Java is invariant with Generics:
- In Java, if a method expects an `Animal` parameter, we can pass any subclass object, such as `Dog` or `Cat`, because of polymorphism.
- However, if a method expects a `List<Animal>`, we cannot pass a `List<Dog>`. This is because generics in Java are **invariant**—`List<Dog>` is not a subtype of `List<Animal>`.
- To allow a list of `Animal` or any of its subclasses, we must use an upper-bounded wildcard:
- `List<? extends Animal>`
- This allows the method to accept a list of `Animal` or a list of any subclass of `Animal`.

# 6) Type Erasure:
- Type erasure means Java removes generic type information post compilation i.e. from the compiled bytecode. At runtime, generic type parameters are replaced by their upper bound, or by `Object` if no bound is specified.
- Generics are mainly a compile-time feature that provide type safety. After compilation, the JVM works with non-generic bytecode.
- This design was chosen to maintain backward compatibility with pre-Java 5 code, which was written using `Object`-based collections.
- As a result, the JVM does not maintain separate runtime representations such as `List<String>` and `List<Integer>`—both are treated as the same raw type at runtime.
- Due to type erasure, arrays of generic types are not allowed. You can't create an array of a generic type like `T[] array = new T[5];`.

# 7) Wildcards in Generics:
- In Java generics, a wildcard (`?`) represents an unknown type.
- Wildcards are mainly used when writing methods that operate on collections, and we do not know the exact generic type of the incoming or outgoing collection.
- Wildcards are different from type parameters.
- Type parameters (like `<T>`) are used when we want to define and work with a specific type, whereas wildcards are used when we only want to consume or expose a generic type without fixing it and to be specific, during variable declarations.
- There are three kinds of wildcards:
   - **1. Unbounded wildcard – `List<?>`**
      - It can accept a list of any type.
      - We can only read elements as `Object`.
      - We cannot add any element except `null`.
   - **2. Upper-bounded wildcard – `List<? extends Animal>`**
      - It can accept a list of `Animal` or any of its subclasses.
      - We can read elements using an `Animal` reference.
      - We cannot add any element except `null`.
   - **3. Lower-bounded wildcard – `List<? super Animal>`**
      - It can accept a list of `Animal` or any of its superclasses.
      - We can add `Animal` objects or any of its subclasses.
      - While reading, we can only use an `Object` reference.
- Wildcards can be used in method parameters, return types, fields and local variables.
However, wildcards cannot be used while creating an object, for example we cannot write:
```java
new ArrayList<?>()
```
because the actual type must be known at object creation time.
- Wildcards CANNOT be used in:
   - Class/interface definitions
   - Generic type parameter declarations
   - Object instantiation
- As Wildcards represent **unknown types** used for reading data safely (for flexibility when consuming data). Type parameters represent **specific types** that the class/method needs to work with for both reading and writing. You can't define a class that works with an "unknown type" - you need to know what type you're working with. Wildcards are for **usage**, type parameters are for **definition**.

---
**COLLECTIONS FRAMEWORK**
---

# 8) Comparable Interface:
- In Java, primitive wrapper classes like `Integer`, `String` have **natural ordering** (ascending by default) defined by the `Comparable` interface. But custom objects like `Student` have multiple attributes (name, age, rollNo) - so **which attribute should determine the sort order?** There's no default answer, so we must define it ourselves.
- "The `Comparable<T>` is a functional interface that defines the **natural ordering** for a class based on one or more attributes. It has a single abstract method, `compareTo(T o)`, which compares `this` object with the input parameter object. For ascending order, it returns negative if `this` is smaller, zero if equal, and positive if `this` is greater. For ascending order, return negative when smaller; for descending, reverse the logic. Any class implementing `Comparable` can be sorted using `Collections.sort()`. The key difference from `Comparator` is that `Comparable` defines **one natural ordering inside the class**, while `Comparator` allows **multiple custom orderings outside the class**."

# 9) Comparator Interface:
- Comparable defines one natural ordering inside the class. Comparator allows multiple custom orderings outside the class. Comparator is a functional interface with a `compare(T o1, T o2)` method that takes two objects. For ascending order, return negative if o1 < o2, zero if equal, positive if o1 > o2. For descending, reverse it. You pass the Comparator to `Collections.sort(list, comparator)`. You can also create multiple Comparators for different sorting criteria without modifying the original class.

# 10) Collection Framework - :

## 10.1) List Interface and its Implementations: ArrayList vs LinkedList vs Vector vs Stack
- At the top, `Iterable` is extended by `Collection`. `List`, `Set` and `Queue` extend `Collection`.
- A `List` interface has an ordered collection. It preserves insertion order and allows duplicates.
- **ArrayList** is a dynamic array that grows by approximately 1.5x (old capacity + old capacity >> 1) when capacity is reached, giving amortized O(1) insertion at the end. It's not thread-safe. ArrayList implements `RandomAccess` marker interface for O(1) index access.
- **LinkedList** is a doubly-linked list implementing both `List` and `Deque`, so it has forward as well as backward pointer. LinkedList doesn't implement `RandomAccess`, so access is O(n). It isn't thread-safe.
- **Vector** is like ArrayList but thread safe—all methods are synchronized, causing performance overhead from coarse-grained locking. It is legacy and not recommended for new code.
- **Stack** extends Vector, so it's also synchronized and legacy and hence not recommended. For thread-safety today, prefer `Collections.synchronizedList()` or `CopyOnWriteArrayList`. Generally replaced by `Deque` implementations (like `ArrayDeque`) in modern code.
- *"CopyOnWriteArrayList creates a new copy of the array on every write. Great for read-heavy scenarios where writes are rare—no locks needed for reads, but writes are expensive."*
- Remember, Load factor is for **HashMap**, not ArrayList. ArrayList grows by **1.5x** (new capacity = old × 1.5), not 2x as there is No load factor.
- Also, since ArrayList internally has objects thus *"All **references** are copied to the new array"* (objects aren't duplicated, just references).
- **When to use which?**
   - ArrayList    → Random access, fast reads, rarely insert/delete in middle
   - LinkedList   → Frequent insertions/deletions at ends (queue/deque operations)
   - Vector/Stack → Avoid (legacy), use ArrayDeque or synchronized wrappers instead

## 10.2) Queue Interface and its Implementations: LinkedList vs ArrayDeque vs PriorityQueue
- "`Queue` extends `Collection` and processes elements in some order. `LinkedList` and `ArrayDeque` follow **FIFO**. `PriorityQueue` doesn't—it's a **min-heap** where the smallest element is at the root, and it directly implements the `Queue` interface. 
- For custom objects in PriorityQueue, they must either implement `Comparable<T>` for natural ordering, or you can pass a `Comparator<T>` to the `PriorityQueue` constructor. 
- Deque extends Queue for double-ended operations, implemented by ArrayDeque and LinkedList."

## 10.3) Set Interface and its implementations: HashSet, LinkedHashSet, TreeSet
- "Set extends Collection. It disallows duplicates and doesn't guarantee order. When a duplicate element is added to classes implementing `HashSet`, the new element is not inserted—the existing element remains. It is not overridden.
- **HashSet** uses a hash table with load factor 0.75—when 75% full, it resizes to 2x capacity and rehashes all elements. Operations are O(1) average. It does not preserve insertion order.
- **LinkedHashSet** extends HashSet (based on `Doubly LinkedList` and `LinkedHashMap` internally) and maintains insertion order via a doubly-linked list. 
- **TreeSet** implements **NavigableSet**, which extends SortedSet, providing additional navigation methods like `lower()`, `higher()`, `ceiling()`, `floor()`.", and stores elements in sorted order using a Red-Black Tree, giving O(log n) operations. Elements must implement Comparable or you provide a Comparator."
- **When to use which?**
```
HashSet       → Fastest, no order needed
LinkedHashSet → Need insertion order preserved
TreeSet       → Need sorted order, range queries
```

## 10.4) Map Interface and its Implementations: HashMap vs Hashtable vs ConcurrentHashMap vs TreeMap:
- The Map interface is part of the Java Collections Framework, but it does not extend the Collection interface because it represents a mapping of keys to values rather than a collection of individual elements.
- **HashMap** is the fastest—not thread-safe, allows one null key and multiple null values. 
- **Hashtable** is legacy and thread-safe but Only one thread can access it at a time—even for reads. This is **coarse-grained locking**, causing poor concurrency. 
- **ConcurrentHashMap** is modern (Java 8+) and thread-safe—allows concurrent reads using CAS operations and uses **synchronized on individual nodes** for writes (fine-grained) i.e locks only the specific bin (bucket) during writes, providing much better concurrency than Hashtable.*
- **SortedMap** Interface: "SortedMap is an interface extending Map that maintains keys in **sorted order**. 
- **TreeMap:** *"TreeMap implements NavigableMap and stores entries sorted by keys using a **Red-Black Tree**. Operations are **O(log n)**. Keys must implement Comparable or you must provide a Comparator. Unlike HashMap, TreeMap **does not allow null keys** (throws NullPointerException) but allows null values. Use TreeMap when you need sorted keys or range queries."*

### (1) 💡 **Comparison Table (Interview Gold):**

| Feature | HashMap | Hashtable | ConcurrentHashMap | TreeMap |
|---------|---------|-----------|-------------------|---------|
| **Thread-safe** | ❌ | ✅ | ✅ | ❌ |
| **Null key** | 1 allowed | ❌ | ❌ | ❌ |
| **Null values** | ✅ | ❌ | ❌ | ✅ |
| **Order** | No order | No order | No order | Sorted |
| **Performance** | O(1) | O(1) | O(1) | O(log n) |
| **Locking** | None | Entire table | Per-bin | None |
| **Use case** | General purpose | Legacy (avoid) | Concurrent access | Sorted keys |

### (2) 🎯 **When to Use What:**
```
HashMap           → Default choice, fastest, single-threaded
ConcurrentHashMap → Multi-threaded, high concurrency
TreeMap           → Need sorted keys, range queries
LinkedHashMap     → Need insertion/access order
```

## 10.5) **How HashMap Works Internally:**
- HashMap is an array of buckets, where each bucket stores entries (nodes) as key–value pairs.
- *"HashMap uses `int hash = hash(key.hashCode())` and then `int index = (n-1) & hash` i.e a bitwise `AND` operation to determine the index, where n is the array capacity (always a power of 2)."*
- Collisions are handled by chaining—entries with the same index form a linked list. If a bucket's size exceeds 8, the linked list is **treeified** into a Red-Black Tree for better worst-case performance (O(log n) instead of O(n)). If it shrinks below 6, it converts back to a linked list. 
- Actually, Treeification happens when bucket size > 8 AND total capacity >= 64. If capacity < 64, HashMap resizes instead of treeifying.
- When the total number of entries exceeds `capacity × load factor` (typically 0.75), the HashMap resizes to double capacity and rehashes all entries thus redistributing all the nodes.
- Average time complexity is O(1), worst case is O(log n) due to treeification."*
- **Why Treeify at 8 and Untreeify at 6?**
   - *"The threshold of 8 is based on Poisson distribution—the probability of a bin having 8+ elements is extremely low with good hash functions. The gap between 8 (treeify) and 6 (untreeify) prevents frequent conversions (hysteresis) when size fluctuates around the threshold."*
- **Why Capacity is Always Power of 2?** :- (i)Allows fast index calculation using bitwise `AND` operator instead of expensive modulo (%) operation.
- **Q: What happens if hashCode() always returns the same value?**
   - **A:** All entries go to the same bucket. Before Java 8, this degrades to O(n). After Java 8, the bucket treeifies at size 8, giving O(log n).
- **Q: Why is load factor 0.75?**
   - **A:** It's a trade-off between space and time. Lower (0.5) = more memory waste, fewer collisions. Higher (1.0) = more collisions, less memory waste. 0.75 is empirically optimal.
- **Q: Can you change load factor?**
   - **A:** Yes, via constructor: `new HashMap<>(initialCapacity, loadFactor)`. But 0.75 is recommended.

### More on HashMap Keys:
- When a custom object is used as a key in a `HashMap`, that class must override both `equals()` and `hashCode()`.
- The `hashCode()` method is used to decide the bucket in which the entry will be placed, and the `equals()` method is used to determine whether two keys represent the same logical key.
- It is mandatory that if two objects are considered equal by `equals()`, then they must return the same value from `hashCode()`. This ensures that logically equal keys map to the same bucket and can be correctly found in the map.
- If this contract is violated, `HashMap` may fail to retrieve values correctly.
- Also, `hashCode()` should be implemented efficiently and distribute keys well, otherwise excessive collisions can degrade performance.

---
**STREAMS AND LAMBDAS**
---

# 11) Functional Interfaces:
- Lambdas and the Stream API were introduced in Java 8 and are commonly referred to as Java 8 features.
- A **functional interface** is an interface that has **exactly one abstract method**. It may still contain any number of `default` and `static` methods. It is the target type for lambda expressions.
- The annotation `@FunctionalInterface` is optional.
- It is used only as a compiler check—if we annotate an interface with `@FunctionalInterface` and accidentally add a second abstract method, the compiler throws an error.
- Even without the annotation, an interface is still considered functional as long as it has only one abstract method. The annotation simply helps enforce the rule.
- Common examples of functional interfaces are: `Runnable`, `Callable`, `Comparator`, `Comparator`, etc.

# 12) Anonymous class:
- An anonymous class is a class without a name.
- It is declared and instantiated at the same time in the single expression using `new InterfaceName(){...}` and we have to provide implementation of all abstract methods of the interface or override methods of the abstract/concrete class inside the curly braces `{}`.
- It is commonly used to provide a quick implementation of an interface or to override methods of an abstract or concrete class when the usage is short-lived or one-time. We can surely override default methods of the interface, but it is not mandatory. However, we cannot overrride static methods of the interface as they belong to the interface itself and they can be called using the interface name only.
- Correct syntax for implementing an interface using an anonymous class
```java
InterfaceName obj = new InterfaceName() {
    @Override
    public void method1() {
        System.out.println("This is method-1");
    }
    @Override
    public void method2() {
        System.out.println("This is method-2");
    }
};
```

# 13) Lambda Expressions:
- A lambda represents the implementation of the single abstract method of a functional interface. It is just a syntactical sugar and doesn't introduce any new feature.
- The syntax has three parts: the parameter list, the arrow `->`, and the method body. `(parameters) -> expression` or `(parameters) -> { statements }`. 
- The parameter list may be empty or contain one or more parameters. Parameters can have explicit types or be inferred. If there is only one parameter and its type can be inferred, the parentheses can be omitted.
- The arrow separates the parameters from the implementation.
- If the body is a single expression, curly braces and `return` are omitted. For multiple statements, use curly braces and explicit `return`. 
- The compiler uses target type inference. It examines the target interface, identifies the single abstract method (ignoring default and static methods which already have implementations), and maps the lambda to that method. This is why lambdas only work with functional interfaces
- Q: Can lambdas access variables from outside?
  A: Yes, but they must be effectively final (not modified after initialization).
```java
int x = 10;
Runnable r = () -> System.out.println(x);  // OK
// x = 20;  // Error! Lambda captures x, so x must be effectively final
```
- Lambdas are restricted to Functional Interfaces without creating extra .class files. Whereas the Anonymous Class creates a separate .class file for each anonymous class, and it can implement interfaces with multiple abstract methods (not functional interfaces only) and extend abstract/concrete classes.
- Key architectural differences include the `this` context, which refers to the enclosing class in a Lambda but to the Anonymous class instance itself in an Anonymous Class. 
- We can call the overridden methods of the anonymous class using the object reference, but in lambdas, we cannot call the abstract method directly as it doesn't have a name.

# 14) Stream API:
- Streams in Java provide a declarative and functional style of processing collections. You create a stream using `collection.stream()`. 
- They allow us to perform operations such as filtering, mapping and aggregation on data without writing explicit loops i.e. they provide a rich set of built-in operations to manipulate Collections (or any data source) efficiently with minimal code.
- A stream is created from a data source such as a `List` using `list.stream()`. We can also create streams from arrays using `Arrays.stream(array)` or from other primitive data structures however they become restrictive hence don't prefer them much. Try to use data structures from the Collections Framework to create streams.
- Streams can be created directly as below:
```java
import LLD1.JavaAdvancedConcepts_1.Cat;

import java.util.stream.Stream;

Stream<Integer> directStream = Stream.of(1, 2, 3, 4, 5, 6, 4, 8);
Stream<Boolean> directBooleanStream = Stream.of(true, true, false, false);
Stream<Cat> directStream = Stream.of(new Cat(), new Cat(), new Cat());
```
- The stream does not store data—it only represents a pipeline of operations to be performed on the data source.
- Operations on streams are of two types:
   - **Intermediate operations** such as `filter()`, `map()`, `sorted()`, etc. that are **lazy**. They do not execute immediately, just build a pipeline. For intermediary methods, its input and output, both are Streams.
   - **Terminal operations** such as `forEach()`, `collect()`, `findFirst()`, etc. are eager—they trigger execution of the entire pipeline. For terminal operations, input is a Stream but output is some required data structure.
- Once a Data structure is converted into a Stream then we can apply number of intermediary methods on it. It will remain as a Stream only. But, once we apply a terminal method on it, it is converted into the desired data structure and the stream is closed/stopped.
- Execution starts only when a **terminal operation** is invoked.
- The `filter()` method takes a `Predicate<T>` and is used to select elements based on a boolean condition.
- The `map()` method takes a `Function<T, R>` and is used to transform each element from one form to another.
- Streams are optimized internally through lazy evaluation and short-circuiting. For example, when using `findFirst()`, the stream processes only as many elements as required to produce the result (i.e. to find one match).
- A stream can be consumed only once. After a terminal operation, the stream cannot be reused.
- Stream operations do not modify the original collection. The result is produced separately, usually using a terminal operation such as `collect()`.
- Streams also support parallel processing via `parallelStream()` for multicore utilization.

# 15) Optional Class:
- The `Optional<T>` class is a container that may or may not contain a non-null value. It was introduced in Java 8 to explicitly represent the possible absence of a value and to reduce the risk of `NullPointerException`.
- `Optional` should mainly be used as a **method return type** to indicate that a value may be missing, instead of returning `null`.
- It provides methods like `isPresent()`, `get()`, `orElse()`, `orElseGet()`, and `orElseThrow()` to handle the contained value safely.
- `Optional` is not intended to be used as a field type or in collections.

---
**EXCEPTION HANDLING**
---

# 16) Exception Handling:
- An exception is an event that disrupts the normal flow of program execution. When an exceptional situation occurs, an object representing the exception is thrown.
- Java provides an exception-handling mechanism so that we can catch such situations and take appropriate action instead of crashing the program.

## 16.1) Exception hierarchy
- Java has a hierarchy: **`Object` → `Throwable` → `Exception`/`Error`**.
- *"All exceptions inherit from the `Throwable` class, which makes them throwable using the `throw` keyword."* 
- `Throwable` has two direct subclasses:
   - `Exception`
   - `Error`
## 16.2) Error
- Errors represent serious JVM-level failures that make the application unstable and unrecoverable.
- For example, **StackOverflowError** occurs when the call stack is exhausted (often from infinite recursion)—without stack space, the JVM cannot execute any method calls.
- **OutOfMemoryError** means the heap is full—the JVM cannot allocate any new objects.
- In these scenarios, the JVM itself is in an unstable state, so attempting to handle these errors is futile and dangerous.
- The application should fail fast with a clear error message, and the best recovery strategy is to **restart** the application after investigating the root cause (e.g., fix infinite recursion, increase heap size with `-Xmx`, fix memory leaks).
- This is why errors extend `Throwable` but **should not be caught or handled by the application code**—they signal catastrophic failures, not recoverable conditions.
## 16.3) Exception
- `Exception` represents conditions that an application may want to handle.
- It is divided into: Checked exceptions and Unchecked exceptions.
### 1. Checked exceptions
- **Checked exceptions** extend `Exception` (directly or indirectly) but do not extend `RuntimeException`.
- The compiler checks that you handle them at compile-time. Remember, the compiler doesn't throw them—the **code throws them at runtime**. But, the programmer is forced by the compiler to handle them explicitly using `try-catch` blocks or declare them in the method signature using the `throws` keyword(i.e. pass to the parent) so that these are handled during Compile time itself.
- Examples include `IOException` and `FileNotFoundException`.
### 2. Unchecked exceptions (Runtime exceptions)
- `RuntimeException` is a subclass of `Exception`. **Unchecked exceptions** extend `RuntimeException`.
- Exceptions such as `NullPointerException`, `ArithmeticException` and `ArrayIndexOutOfBoundsException` fall under this category.
- They are **not checked at compile time** and occur during program execution. Thus, the compiler doesn't force handling because they typically result from programming errors that should be prevented through proper code.
## 16.4) Important interview clarification
- In Java, checked exceptions handle external failures (file not found) and must be handled at compile time, runtime exceptions represent programming errors that developers should fix, and errors indicate serious failures that 
applications usually should not try to handle.
## 16.5) 🎯 **Common Follow-Up Questions:**
- **Q.1: Why have checked exceptions if developers often just catch and ignore them?**
- **A:** Checked exceptions force awareness of failure scenarios (file not found, network down). They're controversial—some languages (Kotlin, C#) don't have them. The debate is: should the compiler force handling (Java) or trust developers to handle appropriately (other languages)?
- **Q.2: Can you catch Error?**
- **A:** **Technically yes, but you shouldn't.** Errors indicate serious JVM problems that can't be recovered (OutOfMemoryError, StackOverflowError). Catching them can lead to unpredictable behavior.

# 17) Exception handling importance:
- Exception handling is important because if an exception is not handled, the JVM prints the stack trace, exception type and message, which exposes internal implementation details. This is a security and confidentiality concern and also gives a poor user experience.
- In real applications—such as web applications running on a container like Tomcat, desktop applications, or command-line programs—an unhandled exception can propagate to the runtime environment (on user end) and be shown directly to the user or the console.
- Instead, applications should catch and handle exceptions properly so that:
   * internal details are not exposed, and
   * meaningful and user-friendly messages can be returned to the client.
- In short, exception handling protects system details and allows graceful and controlled failure instead of abrupt crashes.
- As `main()` is the first method to be operated. So, if `main()` method passes any exception, then that exception cannot be handled anywhere else, and it will be shown to the user/client. Thus, we must handle exceptions in `main()` method to prevent showing stack trace to the user and to provide a better user experience.
- In the method signature, we can write `throws Exception` instead of writing multiple exceptions with the `throws` keyword. However, it is not a good practice to write `throws Exception` because it makes it unclear which specific exceptions the method can throw, reducing code readability and maintainability. It is better to declare multiple specific exceptions that the method can throw, so that callers can handle them specifically and appropriately. This promotes better error handling and makes the code more self-documenting.

# 18) try-catch block:
- A `try–catch` block is used to handle exceptions so that the application does not crash and can recover in a controlled manner. A `try` block must have either `catch` blocks or a `finally` block, or both.
- The code that may throw an exception is placed inside the `try` block. Each `try` block can have multiple `catch` blocks to handle different types of exceptions that may be thrown within the try block.
- Each `catch` block specifies the type of exception it can handle and receives the exception object.
- If an exception occurs inside the `try` block, the remaining statements in the `try` block are skipped and control immediately moves to the matching `catch` block.
- The JVM checks the catch blocks in order and executes the first one whose exception type matches the thrown exception (or its superclass).
- *"If no catch block matches, the exception **propagates up the call stack** to the calling method. If no method handles it, the application crashes with a stack trace."*
- After a matching catch block is executed, normal program execution continues after the entire try–catch construct.
- When using multiple catch blocks, they must be ordered from the most specific exception to the most general one(`Exception`). Otherwise, the more specific catch blocks become unreachable.
- It is good developer practice to keep a generic catch block (for example, `Exception`) at the end to safely handle unexpected edge cases.
- From Java 7 onwards, Java supports *multi-catch*, which allows a single catch block to handle multiple unrelated exception types using the pipe (`|`) operator.
- This helps reduce duplicate handling code and improves readability when the handling logic is the same for different exceptions.
- If code inside a `catch` block can also throw an `exception`, it must be handled using another nested `try–catch` block. Otherwise, the new `exception` will propagate up the call stack and may terminate the thread.

## 18.1) Common interview questions on try-catch:
- **Q.1): What happens if `exception` occurs in catch block?**
   - **A:** First of all, `finally` block, if exist will execute and then if an exception occurs in a catch block, it will propagate up the call stack just like any other exception. The original exception that triggered the catch block is effectively lost unless it is explicitly handled or logged before the new exception is thrown. This can lead to confusion during debugging, as the root cause may be obscured by the new exception.
- **Q: Can you have try without catch?**
   - **A:** **Yes!** `try-finally` (no catch):
- **Q: Can you have multiple `finally` blocks?**
   - **A:** **No!** Only one `finally` per `try`. Compile error otherwise.
- **Q: Does `finally` execute if JVM crashes or `System.exit()`?**
   - **A:** **No.** `finally` only guarantees execution in normal JVM operation. If `System.exit(0)` is called or JVM crashes, `finally` won't run.
- **Q: What's the difference between throw and throws?**
- **A:** `throw` is used to actually throw an exception instance, while `throws` is used in a method signature to declare that the method may throw certain exceptions, which must be handled by the caller.

# 19) `finally` block:
- The `finally` block is optional and is used with a `try` (with or without `catch`). There can be only one `finally` block for each `try` block.
- Code inside the `finally` block is executed **regardless of whether an exception occurs or not**, and is mainly used for **resource cleanup** such as closing files, database connections, or releasing system resources.
- The `finally` block is executed even if a `return` statement is executed inside the `try` or `catch` block.
- If a `return` statement is written inside the `finally` block, it **overrides** the return value from the `try` or `catch` block. This is a bad practice as it is overriding the program flow of the business, and hence it can lead to confusion and bugs. Also, the `finally` block should stick to resource cleanup and should not contain any business logic.
- If the JVM exits (via `System.exit()`) in the `try` or `catch` block, the `finally` block won't execute. 
- In modern Java (Java 7+), resource cleanup is usually done using **try-with-resources**, which is preferred over using `finally` for closing resources.

# 20) Propagation of Runtime Exceptions:
- Runtime exceptions propagate up the call stack if they are not caught.
- They should be handled at an appropriate application layer, close to where meaningful recovery or user feedback can be provided.
- It is a good practice to catch **specific exceptions** whenever possible. A generic exception handler can be used as a last safety net, but it should not replace proper, specific handling.
- If runtime exceptions are left unhandled, they terminate the executing thread and may expose the stack trace to the user.

# 21) try-with-resources:
- `Try-with-resources` is a special form of `try` statement that declares one or more resources in its parentheses. It is used to automatically close resources after use at the end of the try block, guarantees that even if an exception is thrown. This eliminates the need for a boilerplate `finally` block to close resources and reduces the risk of **resource leaks**. 
- A `resource` is an `object` that must be closed after the program is finished with it. Any object that implements `AutoCloseable` (which includes `Closeable`) can be used as a resource (for example: streams, files, database connections, scanners, etc.).
- The syntax is:
```java
try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
    String line = br.readLine();
    System.out.println(line);
}
```
- Here, `BufferedReader` implements `AutoCloseable`. After the `try` block finishes (normally or exceptionally), `br.close()` is called automatically.
- You can declare multiple resources in a single `try-with-resources` statement, and they will be closed in the reverse order of their declaration.
- The `try-with-resources` statement was introduced in Java 7 and is the recommended way to manage resources in Java, as it provides better readability and safety compared to traditional `try-catch-finally` blocks for resource management.        
- Multiple resources example:
```java
try (
    FileInputStream fis = new FileInputStream("a.txt");
    FileOutputStream fos = new FileOutputStream("b.txt")
) {
    // use fis and fos
}
//They are closed automatically in **reverse order**.
```

# 22) Custom Exceptions:
- When Java's built-in exceptions (like `NullPointerException`, `IOException`) don't clearly describe your specific business problem, you create your
**own exception class** - that's a **custom exception**.
- A custom exception is created by extending either:
   * `Exception` → for a **checked** custom exception, or
   * `RuntimeException` → for an **unchecked** custom exception.
- Checked custom exceptions are used when the caller is expected to handle or recover from the exceptions like Recoverable external failures, Network/database failures, File not found, PaymentFailedException, etc.
- Unchecked custom exceptions are used for programming or validation errors where recovery is usually not possible (like invalid age input, insufficient balance during withdrawal).
- A custom exception should be used when a failure represents a meaningful business or domain condition rather than a low-level technical error. Ideally, we should make Custom Exceptions as the Checked Exceptions. It helps us because the Compiler gives us warnings.
- Example: How to create custom exception:
```java id="custom1"
class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message) {
        //**Add constructor** that takes a message
        super(message);
    }
}
```
- Example: How to throw custom exception:
```java id="custom2"
if (order == null) {
    throw new InvalidOrderException("Order cannot be null");
}
```
