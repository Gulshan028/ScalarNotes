# Lecture | Backend LLD: Java Advanced Concepts - 2 [Collections]

## CONTENTS:
1. Comparable vs Comparator
2. Java's Collections Framework: Explanation of each interface and class
3. Some Amazing Points
4. More about HashMap

---

# Deepak Sir's class:

---

## I] Comparable vs Comparator:

### 1.1) Why we need them?

Look, if we have a `List<Integer>`, then it can be sorted naturally. As Java knows to sort the integers list naturally.
But, in the case of Objects like `Student` objects which have multiple attributes, Java cannot sort it naturally as these objects don't 
have any natural order of sorting. In such scenarios, we use `Comparable` interface to define the natural order of sorting for 
a particular class.

---

### 1.2) What is Comparable Interface:
- Note: Please refer `Student.java`, `Client.java` and other classes inside the package.
- It is a `Functional Interface` which has one `compareTo()` method. 
- The class which needs to define the natural ordering needs to implement this interface.
- In `List<Integer>`, `Integer` doesn't need to implement this interface. As, `Integer` already extends `Number` class which
  has natural ordering and `compare` method defined for them.
- In the `compareTo(className ObjName)` method; logically, there should be 2 objects to be compared. But, look, we haven't 
  seen the implementation of this method under the hood. So, yes, at any instant, two objects are definitely compared.
  Look, in general, a method is called on an object. So, when we call `Collections.sort(ListName)` or `Arrays.sort(ArrayName)` then internally, the
  `compareTo(className ObjName)` method must have been called on some object. So, this way we have two entities to compare, 
  one coming out of `this` object belonging to the object on which the method is called and `other` object is passed as 
  input parameter to the method.
- In the method body of `compareTo()`, we define the comparison logic based on either one or more attributes.
- Now, this `compareTo()` method has return type `int`. So, it returns an integer value as follows:
  1. The list will be sorted in ascending order:
     - if `this` object is smaller than `other` object, and you make `compareTo()` return a negative value;
     - if `this` object is greater than `other` object, and you make `compareTo()` return a positive value;
     - if `this` object is equal to `other` object, and you make `compareTo()` return a zero. 
  2.  The list will be sorted in descending order:
    - if `this` object is smaller than `other` object, and you make `compareTo()` return a positive  value;
    - if `this` object is greater than `other` object, and you make `compareTo()` return a negative value;
    - if `this` object is equal to `other` object, and you make `compareTo()` return a zero.
- In `Student.java` file, I have shown all the cases, please have a look.
- This is all about the `Comparable Interface`, which we wanted to know. When we call `Collections.sort(ListName)` then
   the list of particular type of objects is sorted based on already written logic inside the `compareTo()` method. Hence, 
   this is called as the natural order of sorting.

---

### 1.3) Comparator Interface:
- Note: Please refer `Student.java`, `Client.java` and other `Comparator` classes inside the package.
- Look, we need it because, apart from the natural order of sorting defined for a class, we may need to sort that list of those objects
  based on some other attributes too as per the requirement. These requirements keep coming. For ex: at Amazon, we sort a list of items based on their prices, reviews, etc. 
  So, we aren't going to change our code of natural sorting. Here comes `Comparator Interface` handy.
- We have to create a `class` which will implement the functional interface `Comparator<T>`. Yes, this is a functional interface as
  remaining methods in this interface are either `default` methods or `static` methods, which keeps the number of abstract methods inside the Interface limited to 1.
- Now, the Comparator class implements the `compare(T obj1, T obj2)` method, which takes two objects of the class, which are to be compared,
  as the input parameter, with the comparison logic. (for ex: refer `StudentAttComparator.java` class).
- Now, to use this `Comparator`, we just need to pass the object of the `Comparator` class to the `Collections.sort(ListName, ComparatorObject)` as shown
  also in the client class. This sorts the list based on comparison logic of the Comparator.
- Refer `StudentComparator.java`, it shows logic for the list to be sorted based on some custom parameter. Look, we may require to sort the List based on some random 
  parameters, and every time we can't keep changing our Comparable interface logic. This is the reason, we are having Comparator interface. Also, we can have as many number of
  Comparator classes (i.e. multiple logics of Comparison) as required.

---

## II] Java's Collections Framework:
- The Collections Framework in Java provides a set of interfaces and classes to store and manipulate groups of data as a single unit, a collection.

### 2.1) Tree Diagram:
![Collections _framework.webp](../Images/Collections%20_framework.webp)

---

### 2.2) Explanation of each Block:
**Most important:** Read major highlights in the official implementation of each one of the interface and class mentioned below.

#### (1) << List >>:
- It is an `Interface` which extends `Collection` interface. 
- Classes implementing it, preserves the order of insertion. Also, at each index, only one element is inserted unlike `Map`, 
  where a pair of Key-Value is inserted. However, duplicate elements can be inserted at different indexes in the `List`.

#### [1.1] ArrayList:
- It is a class which implements the `List` interface.
- **ArrayList** is a dynamic array that grows by 1.5x when capacity is reached (Remember, Load factor is for **HashMap**, not ArrayList), giving amortized O(1) insertion at the end. It's not thread-safe. ArrayList implements `RandomAccess` marker interface for O(1) index access.
- `ArrayList` stores object references in an array. During resizing, only these references are copied to a new, larger array using `System.arraycopy()`. The actual objects remain in the heap and aren't duplicated—so it's a shallow copy of the array, not a deep copy of the objects.
- On seeing the official implementation, you will see that ArrayList implements `RandomAccess` which enables to access any random index of the Arraylist.

#### [1.2] LinkedList:
- It is a class which implements the `List` interface as well as the `Deque` interface.
- It is `Doubly Linked List` actually. The difference between `Doubly Linked List` and `Linked List`is that in `Doubly Linked List`, each 
  node has two pointer, one which points the next node and the other is called `previous` which points the previous node.
- This feature of pointing forward as well as backward came from implementing `Deque`.
- On seeing the official implementation, you will see that LinkedList doesn't implement `RandomAccess` , hence, we cannot access any random index of the LinkedList.

#### [1.3] Vector:
- It is a class which implements the `List` interface. Though, it is not used much these days.
- It is simply the Dynamic array. The difference with respect to the `ArrayList` is that the `Vector` is thread-safe.
  All the methods of the `Vector` are Synchronised (See the official implementation). Hence, if multiple threads are trying to access the `Vector`, then any one of them 
  can access it. But this thread-safe nature introduces the performance overhead and hence `ArrayList` is preferred over `Vectors` more frequently.
- If thread safety is needed, `Collections.synchronizedList(new ArrayList<>())` or `java.util.concurrent` classes like `CopyOnWriteArrayList` offer more flexible 
  and potentially more performant solutions than Vector.

#### [1.4] Stack:
- It is a class which extends the `Vector` class. Hence, by default, it is thread-safe.

---

### (2) << Queue >>:
- It is an `Interface` which extends `Collection` interface.
- The `Queue` interface in Java represents a collection where elements:

  1. Wait for their turn to be processed (order depends on the implementation).

  2. Follow FIFO (First-In-First-Out) by default (e.g., LinkedList, ArrayDeque).

  3. Exception: `PriorityQueue` orders elements based on **Priority** (not FIFO).

- As you can see in the tree diagram above, 
  1. `PriorityQueue` class implements `<<Queue>>` interface.
  2. `<<Deque>>` interface extends `<<Queue>>` interface.
  3. `ArrayDeque` class implements `<<Deque>>` interface.
  4. `LinkedList` class also implements `<<Deque>>` interface.

#### [2.1] Priority Queue:

- It is internally a Min Heap i.e. a dynamic Array working as a Min Heap. So, the root element is the smallest element.
- Now, if the `PriorityQueue` is of `Integer` then `PriorityQueue` can compare it naturally. But, if we have some custom
  object as the `PriorityQueue` element then that objects' class must have implemented the 'Comparable interface', i.e.
  those objects' class must have a natural order of sorting defined for them. Then only it can compare two objects and
  keep the object with the lower parameter at the root so that it can be `poll()` out.
- Here, the focus is on the 'Comparable interface' and not on the working of the `PriorityQueue`, as we've already learnt it.
- please go through the Client class to see the coding part. The conclusion is simple that whenever comparison is required with custom
  object then we need to provide it through either comparable or comparator interface.
 
---

### (3) << Set >>
- It is an `Interface` which extends `Collection` interface.
- There are two main differences between `Set` and `List`. They are
  1. Duplicate elements are not allowed in `Set`. Even if we try to insert the duplicates, then the previous value is overridden.
  2. The insertion order of elements is not preserved in `Set`.
- There are 3 different types of `Set` present: viz. `HashSet`, `LinkedHashSet`, `TreeSet`.

#### [3.1] HashSet:
- It is a class which implements the `Set` interface.
- The insertion order of elements is not preserved. i.e. elements are present in random order.
- It is `HashTable` based data structure.
- The **load factor** in a `HashSet` (or `HashMap`) is a measure of **how full the hash table is allowed to get before resizing (rehashing)** to maintain efficient O(1) operations.
- Mathematically, it is defined as:  
$$
\text{Load Factor} = \frac{\text{Total number of elements}}{\text{Total number of buckets (array size)}}
$$
- **Default load factor in Java = 0.75** (i.e., when the average bucket has **0.75 elements**, the HashSet resizes to reduce collisions).
- It helps balance **memory usage** vs. **performance** (lower load factor → fewer collisions but more memory).
#### [3.2] LinkedHashSet:
- It is a class which implements the `Set` interface.
- The insertion order of elements is **preserved**.
- Please go through the official implementation.
- It is based on `Doubly LinkedList` and `HashMap`.

#### [3.3] << SortedSet >>:
- It is an interface which extends `Set` interface.

#### [3.4] TreeSet:
- It is a class which implements the `SortedSet` interface.
- It maintains the inserted elements in the sorted order.
- Internally, `TreeSet` uses `Balanced Binary Search Trees`(B-BST).

So, please see the Client code too.
The T.C. of insertion, deletion, search, etc in HashSet is Amortized O(1) as it is based on the same hashtable as that of HashMap.
For `LinkedHashSet` , it is also O(1). And, for `TreeSet` it is O(logN).

---

### (4) << Map >>
- It is an `Interface` and the part of the Collection framework. But, it doesn't extend `Collection` interface.
  The reason is that it is a collection of data, too. But in `Collection` interface, at one index, only one element is allowed
  whereas in `Map`, we have a key-value pair i.e. a 'pair of data', which doesn't go well with the `Collection` interface's single element at single index.

#### [4.1] HashMap:
- It is a class which implements the `Map` interface.
- It isn't Thread safe.
- One `null` `Key` is allowed whereas multiple `Values` can be `null`.
- If thread safety is not a concern, then always choose HashMap as it offers highest performance.

#### [4.2] HashTable:
- It is a class which implements the `Map` interface.
- It is Thread safe.
- Neither `Key` nor `Value` can be `null`.
- Only one `thread` can work (either read or write) on the entire `HashTable` at any given time as it locks the entire `HashTable`.
- It is older than `ConcurrentHashMap`.


#### [4.3] ConcurrentHashMap:
- It is a thread-safe implementation of the `Map` interface.
- It is Thread safe.
- One `null` `Key` is allowed whereas multiple `Values` can be `null`.
- It divides the entire ConcurrentHashMap into buckets and provides thread safety to each bucket. So, multiple threads equal to the
  number of buckets can work on ConcurrentHashMap simultaneously. However, only one thread can act on each bucket (for writing) at any given time.
  This improves the performance compared to the HashTable.
- Parallel reading is allowed on the entire ConcurrentHashMap.

---

## III] Some Amazing Points:
- "Backbone of most of the data structures in java is either `Array` or `LinkedList` or both" is mostly correct in a practical sense, especially for common concrete implementations
   within the Java `Collections` Framework. 
- The key exception would be tree-based structures like `TreeSet` and `TreeMap`, which are fundamentally based on `node-and-reference` relationships that form a tree structure, 
  not directly an `array` or a simple `linked list`, though their `nodes` do contain references (pointers) that behave somewhat like links.
- **CORRECTED STATEMENT:-** "Many core data structures in Java are implemented using `arrays` or `linked lists`, but some use `trees`,
  custom `node-based` designs, or other specialized representations."

---

## IV] LINKS:
1. Hashtable and ConcurrentHashMap: https://www.baeldung.com/java-hashtable-vs-concurrenthashmap
2. LinkedHashSet : https://www.baeldung.com/java-linkedhashset
3. Collections: https://www.baeldung.com/java-collections

---

## V] More about HashMap:

### 5.1) **How `HashMap` Works in Java**
1. **Initial Structure**:
   - Backed by an **array of `Node` objects** (default initial size = **16**).
   - Each `Node` contains a **key-value pair**.

2. **Insertion & Hashing**:
   - Java computes the **`hashCode()`** of the key.
   - The index is determined as:
    ```java
    index = hashCode(key) % array_size
    ```  
   - If multiple keys **collide** at the same index, they are stored in a **linked list** (chaining).

3. **Handling Collisions**:
   - **Linked List  for (≤ 8 nodes)**:
     - Collisions are resolved via a linked list (O(1) for small sizes). 
     - Takes only O(8) which is equivalent to O(1) for searching or insertion,
    
   - **Balanced BST for (> 8 nodes)**:
     - If collisions exceed **8**, the linked list converts to a **Red-Black Tree i.e. Balanced BST (O(log N) search)**.
     - If nodes reduce to **< 6**, the tree reverts to a linked list.

4. **Time Complexity**:
   - **Average Case (Amortized O(1))**:
     - Hashing + indexing = O(1).
     - Linked list (max 8 nodes) = O(1).
     - BST (even for 4Billion nodes at single index, log₂N ≈ 32) = O(1) in practice.
   - **Worst Case (O(log N))**: Only if many collisions force tree usage.

5. **Resizing (Rehashing)**:
   - When **75% (load factor = 0.75)** of the array is filled, the **size doubles** to reduce collisions.
   - Resizing depends on total elements, not filled buckets.
   - ✅ Even if all entries collide in 1 bucket, resizing happens when size > (capacity × loadFactor).
---

### 5.2) **Key Takeaways**
1. ✅ **Efficiency**: Insertion/search are **O(1)** amortized due to smart resizing and tree conversion.  
2. ✅ **Collision Handling**: Uses **linked lists (small)** → **BST (large)** for optimal performance.  
3. ✅ **Dynamic Resizing**: Doubles array at **75% capacity** to maintain low collisions.

---
