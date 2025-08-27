# Backend LLD: Concurrency-4: Synchronization with Semaphores

## CONTENTS:
1. Semaphore
2. How permits are maintained in Semaphore
3. Producer-Consumer Problem using Semaphore
4. Atomic Datatypes in java
5. Volatile keyword in Java
6. ConcurrentHashMap in Java
7. Deadlocks
8. Links

---

## I] Semaphore:

1) `Semaphore` is a class in java. Its object is a synchronization object with methods.

    ```java
    import java.util.concurrent.Semaphore;
    Semaphore semaphore = new Semaphore(2);
    ```
   here, *2* represents that two threads can be present inside the critical section at any given instant of time.
2) In Java, a Semaphore is a synchronization aid that controls access to a shared resource by maintaining a set of permits.
3) **Key Concepts:**
   - **Permits:**
     A Semaphore is initialized with a specific number of permits. These permits represent the available slots for threads to 
     access the shared resource.
   - **Acquiring Permits:**
     When a thread wants to access the shared resource, it must `acquire()` a permit from the Semaphore. If permits are available, 
     the thread acquires one, and the semaphore's internal count of available permits is decremented. If no permits are available,
     the thread will be blocked (wait) until a permit is released by another thread.
   - **Releasing Permits:**
     When a thread finishes using the shared resource, it must `release()` the permit back to the Semaphore. This increments the 
     semaphore's internal count, potentially unblocking a waiting thread.
4) **How it Works:**
   - A Semaphore essentially acts as a gatekeeper, allowing only a specified number of threads to pass through at any given time. 
   - This is particularly useful in scenarios where:
     1. **Controlling access to a critical section:**
        While synchronized blocks and Lock objects typically allow only one thread at a time, a Semaphore can allow multiple 
        threads (up to its permit count) to enter a critical section.
5) **Two Types:**
   - Binary Semaphore (1 permit): Equivalent to a mutex (mutual exclusion).
   - Counting Semaphore (N permits): Allows controlled access to multiple threads.
   - Remember, Mutex and Binary Semaphore are not same. They may seem similar, they have critical differences in behavior and purpose.
6) "N threads can be present inside the critical section at any given instant" is accurate if:
   - **Each thread** calls `semaphore.acquire()` before entering the critical section.
   - **Each thread** calls `semaphore.release()` after exiting.
7) The semaphore doesn’t automatically define a critical section. You must manually wrap the critical section code between `acquire()` and `release()`.

---

## II] How permits are maintained in Semaphore:
In Java's Semaphore, permits are maintained as a non-negative integer counter.
Here's a more detailed breakdown:

2.1)  **Initialization:** A Semaphore is initialized with a given number of permits.

2.2)  **Acquiring Permits:**
        
- The `acquire()` method attempts to decrement the permit count.

- If the count is greater than zero, the thread acquires the permit and the count is reduced. 
        
- If the count is zero, the thread blocks until another thread releases a permit. 

2.3)  **Releasing Permits:**
        
- The `release()` method increments the permit count, potentially releasing a waiting thread.

2.4)  **Internal Representation:** 

- The Semaphore class uses a counter to track the available permits, rather than managing individual 
  permit objects.  

2.5)  **No Ownership Requirement:**

- There's no requirement that a thread releasing a permit must have acquired it previously.

---

## III] Producer-Consumer Problem using Semaphore:

NOTE:  Please read this section after reading both the codes of Deepak sir's class, i.e. without lock and with lock.

   Let, maxLimit of the store is 6. Initially, 6 producer threads can access the store and produce shirts, also alongside it, 0 consumer
   threads can access the store as no shirt is available in the store. Now, as soon as even one of the producers produces the shirt,  
   then that producer should leave the store and a consumer should be allowed to consume the shirt. So, now 5 Producers and 1 Consumer 
   are accessing the store. In this way, at any point of time, total 6 threads will be accessing the store. They can be 4 Producer threads
   and 2 consumer threads, etc. i.e. Number of Producer threads (say x), plus number of Consumer threads (say y) must sum to 6, at any instant of time.
   This problem statement is solved using the Semaphore. The Rest of the explanation will be given in the 3rd code of Deepak Sir. Go, read it. 

### 3.1) Interrupted Exception on calling `acquire()`:

- If a thread is **blocked** in `acquire()` (because no permits are available), another thread can **interrupt** it by 
  calling `interrupt()` on the waiting thread.
- When interrupted, the waiting thread:
    1. Stops waiting (throws `InterruptedException`).
    2. Does **not** acquire the permit (it fails to acquire).

### 3.2) Example Scenario:
```java
Semaphore semaphore = new Semaphore(0); // Initially 0 permits available

Thread thread1 = new Thread(() -> {
    try {
        semaphore.acquire(); // This current thread will be blocked if no permits are available
        System.out.println("Permit acquired!"); // Won't reach here if interrupted
    } catch (InterruptedException e) {
        System.out.println("Thread was interrupted while waiting!");
    }
});

thread1.start();

// Another thread interrupts thread1 after some time
Thread thread2 = new Thread(() -> {
    try {
        Thread.sleep(1000); // Wait a bit
        thread1.interrupt(); // Interrupt thread1 while it's blocked in acquire()
    } catch (InterruptedException ignored) {}
});

thread2.start();
```

### 3.3) Key Points:
1. `acquire()` is **blocking**: If no permits are available, the thread waits.
2. It is **interruptible**: Another thread can interrupt the waiting thread.
3. If interrupted, `acquire()` throws `InterruptedException`, and the thread does **not** get the permit.

### 3.4) Why This Matters:
- This behavior allows threads to be **cancelled** while waiting for a resource.
- Without interruption, a thread might wait indefinitely, causing potential deadlocks or unresponsive systems.

---

## IV] Atomic Datatypes in java:
In Java, "atomic datatypes" refer to classes within the `java.util.concurrent.atomic` package that provide thread-safe, 
atomic operations on single variables. These classes are designed to handle concurrent updates to *shared variables* without the 
need for explicit synchronization mechanisms like synchronized blocks or locks, thereby reducing the overhead and complexity 
associated with traditional locking.
The key atomic classes in Java include:

- **AtomicInteger:** For atomic operations on an `int` value.
- **AtomicLong:** For atomic operations on a `long` value.
- **AtomicBoolean:** For atomic operations on a `boolean` value.
- **AtomicReference&lt;V&gt;:** For atomic operations on an object reference of type `V`.
- **AtomicIntegerArray, AtomicLongArray, AtomicReferenceArray:** For atomic operations on elements within an array of `int`, `long`, 
  or object references, respectively.

- These classes offer methods like `get()`, `set()`, `compareAndSet()`, `getAndSet()`, and for numeric types, `incrementAndGet()`, 
  `decrementAndGet()`, and `addAndGet()`. 
- The `compareAndSet()` method is particularly important as it allows for a conditional update: the value is updated only if 
  its current value matches an expected value, ensuring atomicity in read-modify-write operations.
- The underlying implementation of these atomic classes often utilizes techniques like Compare-And-Swap (CAS) operations, 
  which are hardware-supported instructions that allow for atomic updates without locking, contributing to their efficiency 
  in concurrent environments.

--- 

## V] Volatile keyword in Java:

### 5.1) 🧠 What is `volatile`?

* The **`volatile`** is a *modifier* (just like access modifiers) which ensures that a variable’s value is always read from and written to *main memory*, 
   not thread-local caches—guaranteeing **visibility** across threads 
* It prevents **instruction reordering** around volatile variables, maintaining a consistent execution order 

### 5.2) Why is it needed?

* On multicore systems, CPU caches and compiler/JIT optimizations may cause threads to see stale or out-of-order values—
  leading to unexpected behavior (e.g., a flag not being seen or seen in the wrong order) .

---

### 5.3) Guarantees provided by `volatile`

1. **Visibility** – Changes to a volatile field are immediately visible to all threads.
2. **Ordering** – volatile also provides a limited form of ordering guarantee, ensuring that operations before a volatile write
                   happen before the write, and operations after a volatile read happen after the read.
3. **Happens-before** – A `write` to a volatile in thread A *happens-before* any subsequent read from that same variable in thread B. 
   This establishes a memory barrier, extending visibility to other variables as well.

### 5.4) Purpose:

• **Visibility:** When a variable is declared volatile, it instructs the Java Virtual Machine (JVM) and the compiler to always 
read the variable's value directly from main memory and write any changes back to main memory immediately. This prevents 
threads from caching the variable's value locally in their own CPU caches, which could lead to stale data being used by other 
threads.

### 5.5) How it works:

• **Memory Barriers:** The volatile keyword introduces memory barriers. When a volatile variable is written, a "write barrier" 
is inserted, ensuring that all preceding writes are flushed to main memory and become visible to other threads. When a 
volatile variable is read, a "read barrier" is inserted, ensuring that the latest value from main memory is fetched.

### 5.6) Limitations:

• **No Atomicity:** volatile guarantees visibility but does not guarantee atomicity for compound operations (e.g., i++). Operations 
  like incrementing a variable involve multiple steps (read, modify, write), and volatile does not prevent other threads from 
  interfering during these intermediate steps. For atomic operations, `synchronized blocks` or `java.util.concurrent.atomic` classes 
  are required.

• **Not a Replacement for `synchronized`:** `volatile` is a lighter-weight mechanism than synchronized and is suitable for specific 
  scenarios where only visibility is required. It does not provide mutual exclusion or prevent race conditions for complex 
  operations.

### 5.7) When to use volatile:

- When a variable is shared among multiple threads and changes made by one thread need to be immediately visible to others.
- When the operations on the volatile variable are simple assignments (single read/single write) and do not involve compound 
  operations that require atomicity.
- You can optimize by marking only **one flag** as volatile (e.g., `ready`), enabling other shared variables (e.g.:- `number`)
  to piggy-back on its memory barrier for visibility.

---

## VI] **ConcurrentHashMap in Java:**

`ConcurrentHashMap` (CHM) is a thread-safe implementation of the `Map` interface in Java, designed for high concurrency with 
better performance than `Hashtable` or `Collections.synchronizedMap()`. It achieves this through **fine-grained locking, 
CAS (Compare-And-Swap) operations, and partitioning**.

---

### **1. Key Design Principles**
- **Thread-safety without full synchronization**: Unlike `Hashtable`, which locks the entire map, `ConcurrentHashMap` uses 
  **segment-level locking (Java 7)** or **CAS + synchronized blocks (Java 8+)**.
- **Scalability**: Multiple threads can read/write simultaneously without blocking each other (unless modifying the same bucket).
- **Non-blocking reads**: Reads are lock-free (volatile reads ensure visibility).

---

### **2. Internal Structure (Java 8+)**
In Java 8, `ConcurrentHashMap` was redesigned to improve scalability further:
- **Array of `Node` buckets** (similar to `HashMap`).
- **Fine-grained synchronization**: Only the bucket (or bin) being modified is locked.
- **Tree structure for collisions**: If a bucket has too many collisions, it converts from a linked list to a **balanced tree (Red-Black Tree)**.

### **3. Storage Structure**
```java
transient volatile Node<K,V>[] table;
```
- Each `Node` contains `key`, `value`, and `next` (for chaining).
- Volatile ensures visibility across threads.

---

### **4. Example Usage**
```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("Key1", 1); // Thread-safe
map.get("Key1");    // Lock-free
```

---

### **5. When to Use `ConcurrentHashMap`?**
✅ **High-concurrency environments** (e.g., web servers, caching).  
✅ **Frequent reads, fewer writes**.  
✅ **Need thread-safety without excessive locking**.

❌ **Not needed** if the map is only accessed by a single thread (use `HashMap` instead).

---

## VII] Deadlocks:

### 7.1) Definition:
Deadlock in Java is a situation in multi-threaded programming where two or more threads are permanently blocked, each waiting 
for a resource that another blocked thread holds. This creates a circular dependency, preventing any of the involved threads 
from progressing.

### 7.2) Conditions for Deadlock (Coffman Conditions):

- **Mutual Exclusion:** Resources involved must be non-sharable, meaning only one thread can use a resource at a time.
- **Hold and Wait:** A thread holding at least one resource is waiting to acquire additional resources held by other threads.
- **No Preemption:** Resources cannot be forcibly taken away from a thread; they must be released voluntarily by the thread holding them.
- **Circular Wait:** A set of threads are waiting for resources in a circular fashion, where each thread in the cycle is waiting for a resource held by the next thread in the cycle.

### 7.3) **Example Scenario:**
Consider two threads, Thread A and Thread B, and two resources, Resource X and Resource Y.

- Thread A acquires a lock on Resource X.
- Thread B acquires a lock on Resource Y.
- Thread A then tries to acquire a lock on Resource Y (which is held by Thread B).
- Thread B then tries to acquire a lock on Resource X (which is held by Thread A).

In this scenario, both Thread A and Thread B are blocked indefinitely, leading to a deadlock.

### 7.4) Graph Cycle Detection:
**Graph cycle detection** is a fundamental technique used in operating systems and concurrent programming, including Java, 
to detect and potentially eliminate deadlocks.
1. Here's how it works:

- **Resource Allocation Graph (or Wait-For Graph):** A graph is constructed where:
   - **Nodes:** represent processes (threads in Java) and resources (e.g., locks, shared objects).
   - **Directed Edges:** represent:
      - **Allocation:** An edge from a resource to a process indicates that the resource is currently allocated to that process.
      - **Request:** An edge from a process to a resource indicates that the process is requesting that resource.

- **Cycle Detection:** A cycle in this graph signifies a potential deadlock. If a cycle exists, it means there's a circular 
  dependency where each process in the cycle is waiting for a resource held by another process in the same cycle, leading to a 
  standstill.
- **Elimination/Recovery:** Once a deadlock is detected through cycle detection, various strategies can be employed for recovery:
   - **Process Termination:** One or more processes involved in the deadlock can be terminated to break the cycle.
   - **Resource Preemption:** Resources can be forcibly taken away from a process and allocated to another to resolve the deadlock.
   - **Process Rollback:** Processes can be rolled back to a previous safe state, releasing resources and potentially breaking the deadlock.

2. **In Java specifically:**
While Java's built-in concurrency utilities do not automatically perform graph-based deadlock detection and recovery, the 
underlying principles are applicable. Tools like JConsole, thread dump analyzers, and specialized libraries can analyze thread 
dumps and identify deadlock situations by examining the call stacks and lock contention, which implicitly reveals the waiting 
relationships that form cycles. Programmatic solutions can also be implemented to create and analyze resource allocation graphs 
for deadlock detection in specific scenarios.

### 7.5) Timeout on thread:

Elimination of deadlocks in Java using a timeout mechanism primarily involves preventing indefinite waiting for resources by 
setting a time limit for lock acquisition attempts. If a thread cannot acquire a necessary lock within the specified timeout, 
it releases any locks it currently holds and backs off, allowing other threads to potentially acquire the required resources and 
break the deadlock cycle. 
This approach is implemented using the `tryLock(long timeout, TimeUnit unit)` method of the Lock interface in Java's `java.util.concurrent.locks package`.

1. **Mechanism:**

- **Attempting Lock Acquisition with Timeout:** Instead of using `lock()`, which blocks indefinitely, threads use 
  `tryLock(timeout, timeunit)` to attempt to acquire a lock within a specified duration.
- **Timeout Expiration:** If the lock is not acquired within the timeout period, the `tryLock()` method returns `false`.
- **Backoff and Release:** Upon a `false` return (timeout), the thread releases any locks it might have already acquired in the 
  current sequence and may implement a "backoff" strategy. This strategy often involves pausing for a random duration before 
  retrying the lock acquisition, which helps prevent livelock where threads repeatedly time out and retry without making progress.
- **Successful Acquisition:** If `tryLock()` returns `true`, the thread has successfully acquired the lock and can proceed with its 
  critical section.

This mechanism helps mitigate deadlocks by introducing a recovery path when resources are not immediately available, 
preventing threads from indefinitely blocking each other.

---

## VIII] Links:
1) Concurrent HashMap: https://www.baeldung.com/concurrenthashmap-reading-and-writing
2) Leetcode problems: https://leetcode.com/problem-list/concurrency/
3) Leetcode problems: https://leetcode.com/problems/fizz-buzz-multithreaded/description/
4) Oracle's Java Tutorials: https://docs.oracle.com/javase/tutorial/essential/concurrency/collections.html
5) Volatile keyword: https://www.baeldung.com/java-volatile
6) Concurrency Resources from Baeldung: https://www.baeldung.com/java-concurrenc











