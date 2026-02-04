# GENERICS

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
- Java Generics are built on top of Java `Object` so they can deal with objects only hence Java generics are implemented using **type erasure**. This results in, At compile time, generic type parameters are removed, and at runtime they are replaced with their upper bound (or `Object` if no bound is specified).
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

# 8) Comparable Interface:
- In Java, primitive wrapper classes like `Integer`, `String` have **natural ordering** (ascending by default) defined by the `Comparable` interface. But custom objects like `Student` have multiple attributes (name, age, rollNo) - so **which attribute should determine the sort order?** There's no default answer, so we must define it ourselves.
- "The `Comparable<T>` is a functional interface that defines the **natural ordering** for a class based on one or more attributes. It has a single abstract method, `compareTo(T o)`, which compares `this` object with the input parameter object. For ascending order, it returns negative if `this` is smaller, zero if equal, and positive if `this` is greater. For ascending order, return negative when smaller; for descending, reverse the logic. Any class implementing `Comparable` can be sorted using `Collections.sort()`. The key difference from `Comparator` is that `Comparable` defines **one natural ordering inside the class**, while `Comparator` allows **multiple custom orderings outside the class**."

# 9) Comparator Interface:
- Comparable defines one natural ordering inside the class. Comparator allows multiple custom orderings outside the class. Comparator is a functional interface with a `compare(T o1, T o2)` method that takes two objects. For ascending order, return negative if o1 < o2, zero if equal, positive if o1 > o2. For descending, reverse it. You pass the Comparator to `Collections.sort(list, comparator)`. You can also create multiple Comparators for different sorting criteria without modifying the original class.

# 10) Collection Framework - :

## 10.1) List Interface and its Implementations: ArrayList vs LinkedList vs Vector vs Stack
- At the top, `Iterable` is extended by `Collection`. `List`, `Set` and `Queue` extend `Collection`.
- A `List` interface has an ordered collection. It preserves insertion order and allows duplicates.
- **ArrayList** is a dynamic array that grows by 1.5x when capacity is reached, giving amortized O(1) insertion at the end. It's not thread-safe. ArrayList implements `RandomAccess` marker interface for O(1) index access.
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
- Deque extends Queue for double-ended operations, implemented by ArrayDeque and LinkedList."*
