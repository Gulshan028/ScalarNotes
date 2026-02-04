# Java Multithreading and Concurrency - Interview Preparation Notes
## Complete Guide with Technical Corrections and Advanced Topics

---

# 1) How a computer application works?
- Computer applications are complex systems that run on a computer's operating system, interacting with hardware and software components to perform various tasks.

## (1.1) program → process → CPU scheduling → context switching → concurrency vs parallelism
- A program is a passive, ready-to-run set of compiled instructions stored on disk as an executable file (`.exe`, `.apk`). When we start it, the operating system loads it into RAM, allocates memory regions (code, stack, heap, registers) and system resources, transforming it into an active process—the CPU then fetches, decodes, and executes instructions directly from RAM because **RAM is significantly faster than disk storage, though still much slower than CPU cache and registers**.
- Since multiple processes exist at the same time, the OS rapidly switches the CPU between them, a mechanism known as context switching. This creates the illusion that multiple programs are running simultaneously, which we call multitasking. On a single-core CPU, this is concurrency—tasks make progress by time-slicing. On multicore systems, tasks can truly execute at the same time, which is parallelism.

## (1.2) Thread & Multithreading:
- Within a process, a thread is the smallest unit of execution that the operating system and JVM can schedule. A single process can contain multiple threads. All threads of a process share the same memory and resources, such as heap and open files, but each thread has its own execution stack and program counter and they run independently.
- Multithreading allows multiple threads to run within the same process. On multicore systems, these threads can truly execute in parallel, while on single-core systems they run concurrently through time-slicing. This enables better CPU utilization, responsiveness, and throughput within a single application.
- These threads are assigned to CPU cores by the OS scheduler, and the cores execute them. How a program utilizes the CPU depends on its design: a single-threaded program executes its logic sequentially on one thread, whereas a multithreaded program splits its work across multiple threads, allowing better utilization of multiple cores and improving responsiveness and throughput.

## (1.3) Task Management by the Operating System:
- The operating system keeps track of all running processes and threads and is responsible for allocating CPU time, memory and other resources. This is handled by the OS scheduler, which ensures fairness and efficiency. It uses different scheduling algorithms such as First-Come-First-Serve, Round Robin, Priority Scheduling, and Shortest Job First.
- These strategies determine how CPU time is distributed so that no single process starves and the system remains responsive. On modern systems, this scheduling creates the illusion that many tasks are running simultaneously, even on limited hardware.
- It also handles context switching, saving the state of a process or thread when switching out and restoring it when switching back in.

## (1.4) Interrupts
- The CPU can be interrupted to handle external events, like input from a user or data arriving from a network. Interrupts are crucial for maintaining responsiveness in a multitasking environment.

## (1.5) Termination of Processes
- When a program finishes its task or is closed by the user, the associated process is terminated. The OS reclaims the allocated resources and frees up memory.

## (1.6) Computer Systems and fundamentals:
- The processor, or CPU, is the brain of the computer. It is a hardware chip responsible for executing machine-level instructions that run software. A modern CPU consists of multiple components, including cores, an Arithmetic Logic Unit (ALU) for computations, a control unit to manage instruction flow, and multiple levels of cache for fast data access.
- The CPU operates in a fetch–decode–execute cycle: it fetches an instruction from memory, decodes it, and then executes/processes it. In the process, it coordinates with RAM, storage, and I/O devices.
- A core is the smallest complete processing unit within a CPU. Each core has its own execution pipeline, ALU, control logic, and cache. Because of this, each core can independently execute threads assigned to it. As the number of cores increases, the system can perform more work in parallel, which directly benefits multitasking and multithreaded applications.

## (1.7) Process Control Block (PCB)
- For every running process, the OS creates a small data structure called the *Process Control Block* (PCB). The PCB does not store the actual code or data; instead, it maintains metadata such as the process ID, memory mappings, open resources, scheduling information, and the CPU state, including the program counter. This allows the OS to pause and later resume a process exactly from where it left off.
- A process contains one or more threads. Each thread has its own stack and program counter, but all threads of a process share the same code, heap, and resources. During execution, when the OS switches between threads or processes, it saves the current thread's state and restores another—this is context switching.
- On a single-core system, threads make progress through rapid context switching. On a multicore system, multiple threads can truly execute at the same time on different cores, eliminating switching overhead and enabling real parallelism.
- Modern operating systems schedule **threads**, not entire processes. The scheduler assigns threads to CPU cores, and multiple threads from the same process can run simultaneously on different cores, sharing memory while executing independently.

---

# 2) Concurrency in Java:-
- Concurrency/Concurrent programming, which is a fundamental concept in modern software development refers to the ability of a system to handle multiple tasks at the same time by making progress on more than one unit of work within the same time period. In Java, this is achieved using multiple threads, which are the smallest units of execution within a process. These threads are scheduled by the operating system and the JVM, allowing tasks such as I/O, computation, and background processing to overlap, improving responsiveness and throughput.

## (2.1) Concurrency vs Parallelism:
- Concurrency is about dealing with multiple tasks by interleaving their execution. The tasks appear to run simultaneously, but on a single core they actually run alternately through rapid context switching. Concurrency can exist on both single-core and multi-core systems.
- Parallelism, on the other hand, means true simultaneous execution—where multiple tasks or threads run at the same time on different CPU cores. It requires multi-core or multi-processor hardware.

---

# 3) Benefits and Challenges of Multithreading:
- The primary benefit of multithreading is improved performance and responsiveness. By running multiple threads concurrently—or in parallel on multi-core systems—an application can utilize the CPU more efficiently. Long-running tasks can be offloaded to background threads, keeping the main thread free to handle user interactions, which is crucial for responsive applications.
- In server-side systems like web servers, multithreading enables handling multiple client requests simultaneously. However, since threads within a process share the same data, we must ensure proper synchronization to maintain data consistency. We also need to be careful about concurrency issues such as race conditions and deadlocks, which can arise when threads compete for shared resources.

---

# 4) Creating Threads in Java:
- In Java, threads are the unit of multithreading, and there are two primary ways to create them.
- The first approach is by extending the `Thread` class and overriding the `run()` method to define the task. We then start the thread using `start()`.
- The second and preferred approach is implementing the `Runnable` interface. We define the task in the `run()` method, pass the `Runnable` instance to a `Thread` object, and invoke `start()`. This is preferred because Java supports single inheritance—extending `Thread` prevents the class from extending any other class, whereas implementing `Runnable` keeps the design flexible.
- Once a thread is created, it is in the *new* state. Calling `start()` moves it to the *runnable* state, where it waits for CPU time from the JVM scheduler. When scheduled, it executes the `run()` method. After completion, the thread terminates, and attempting to restart it results in an `IllegalThreadStateException`.
- From Java 8 onward, lambda expressions allow us to pass a `Runnable` directly, making thread creation concise and expressive.

## (4.1) Commonly used Methods on Threads:
- As developers, we cannot control the execution order of threads. It is entirely managed by the JVM and the underlying OS thread scheduler.
- Another important point is that calling `run()` directly does not create a new thread—it simply executes the code in the current thread. To start a new thread of execution, we must call `start()`, which internally invokes `run()` on a new call stack.
- The `Thread` class provides several important methods:
    * `start()` to initiate a new thread
    * `run()` to define the task
    * `sleep(ms)` to pause the current thread
    * `join()` to make the current thread wait until another thread completes
    * `interrupt()` to signal a thread to stop or wake from a blocking state
    * `setName()`, `getName()`, `setPriority()`, `getPriority()` for thread metadata
    * `Thread.currentThread()` to obtain the currently executing thread
- These APIs allow coordination and control, but the actual scheduling and execution order always remain outside our direct control.

## (4.2) Thread Lifecycle in Java:
- In Java, the `Thread` class exposes a `State` enum that represents the lifecycle of a thread.
- When a thread object is created but `start()` has not been called, it is in the `NEW` state.
- After invoking `start()`, the thread enters the `RUNNABLE` state. This means it is either ready to run or actually running on the CPU—Java does not distinguish a separate "running" state.
- If a thread tries to enter a synchronized block or method but the lock is held by another thread, it moves to the `BLOCKED` state.
- When a thread waits indefinitely—for example, by calling `wait()` or `join()`—it enters the `WAITING` state.
- If it waits for a bounded time, such as via `sleep(timeout)` or `wait(timeout)`, it enters the `TIMED_WAITING` state.
- Once the `run()` method completes, the thread enters the `TERMINATED` state and cannot be restarted.
- It's important to note that interruption is not a thread state—it is a signal. Calling `interrupt()` sets a flag and may cause blocking operations like `sleep()` or `wait()` to throw `InterruptedException`.

---

# 5) Thread Pools:
- A thread pool is a managed collection of pre-created, reusable threads that execute tasks from a shared task queue (`ExecutorService` has waiting queue). Instead of creating a new thread for every task, tasks are submitted to the pool. Whenever a thread becomes available, it picks up a task, executes it, and then returns to the pool to be reused.
- This approach is far more efficient than creating and destroying threads repeatedly as thread creation requires allocating memory and resources and hence the thread creation is expensive. Since threads in pools already exist and are usually in a waiting state, task assignment is much faster.
- Thread pools are therefore ideal for high-performance and long-running applications, such as servers, where a large number of short-lived tasks must be handled efficiently while keeping resource usage under control.
- Consider a backend system like Amazon during peak traffic, receiving millions of requests simultaneously. We cannot create one thread per request because each thread consumes memory—typically around 1 MB for its stack. Creating a million threads would quickly exhaust memory and lead to `OutOfMemoryError`.
- Moreover, the number of CPU cores is limited—say 16 or 32. When a massive number of threads compete for a small number of cores, the system spends most of its time in rapid context switching instead of doing real work, severely degrading performance.
- Creating and managing threads manually is also expensive and complex. This is where the Executor Framework comes in. It provides efficient creation, reuse, and management of a fixed number of threads. Tasks are submitted to the executor, and the framework handles scheduling and lifecycle management.
- This allows developers to focus on business logic while ensuring controlled resource usage, better throughput, and stable performance—making thread pools ideal for high-load, server-side applications.

---

# 6) MultiThreading using ExecutorService and ThreadPool
```java
ExecutorService executor = Executors.newFixedThreadPool(10);
```
- The Java `ExecutorService` is a key interface within the `java.util.concurrent` package, providing a robust framework (acts like thread pool manager) for managing and executing tasks using a pool of threads. **Tasks can be submitted asynchronously, but results can be retrieved synchronously or asynchronously depending on how you interact with the returned `Future`.**
- The `Executors` utility class provides convenient factory methods for creating various types of ExecutorService instances, such as `newFixedThreadPool` (fixed number of threads), `newCachedThreadPool` (dynamically adjusts thread count), and `newSingleThreadExecutor` (a single worker thread), etc.
- To the `ExecutorService`, tasks can be submitted as `Runnable` (for tasks that don't return a result) using `execute()` method or `Callable` (for tasks that return a result and can throw exceptions) using the `submit()` method that returns a Future object, which represents the result of an asynchronous computation and can be used to check task status, retrieve results, or cancel the task.
- `ExecutorService` provides lifecycle control (start, shutdown, force termination):- When we call `executor.shutdown()`, the executor stops accepting new tasks, but continues executing all previously submitted tasks. Once those tasks complete, the worker threads are terminated gracefully and `shutdownNow()` (attempts to stop currently executing tasks and prevents waiting tasks from starting).
- 📌 Key Insight: `Executors` creates `ExecutorService` → `ExecutorService` manages the thread pool and task lifecycle → The worker `Threads` execute the submitted tasks.

---

# 7) Difference between Runnable and Callable in Java:
- Manual Thread creation: Uses `new Thread(Runnable)`. Limited to `void` returns because constructors cannot return a `Future`. So, manual thread creation only supports `Runnable` tasks as constructor accepts only `Runnable`. This is `Fire and forget` model.
- While, In the Executor framework, we can submit both the kinds of tasks: `Runnable` and `Callable`.
- A `Runnable` represents a task whose `run()` method returns `void`. It is used when we only want to execute logic and do not expect any result. Such tasks are typically submitted using `execute()` or `submit()`. When submitted via `submit()`, it returns a `Future<?>`. **Calling `get()` on this Future returns `null` (the Future object itself exists and is useful for tracking completion status via `isDone()`, waiting for completion via `get()`, or cancelling the task).**
- A `Callable<T>` task, on the other hand, represents a task that returns a value. Its `call()` method returns a result of type `T` and can also throw checked exceptions. Callable tasks are submitted using `submit()` to the `ExecutorService`, and in return we get a `Future<T>`. Remember, we don't submit `Callable<T>` to threads manually.
- Now, consider merge sort. The algorithm works by recursively dividing an array into two halves, sorting each half, and then merging them. The key observation is that sorting the left and right halves are independent operations. This makes merge sort a natural candidate for parallel execution using multithreading.
- In a multithreaded implementation, each half can be processed as a separate task. Since each task produces a sorted sub-array, we use `Callable<List<Integer>>` instead of `Runnable`. Each task implements `Callable<T>`, defines its logic in the `call()` method, and is submitted to the `ExecutorService` using `submit()`.
- The `submit()` method returns a `Future<T>`. To retrieve the result, we call `future.get()`, which is a blocking call—meaning the calling thread waits until the task completes. Once both halves return their results, we merge them to produce the final sorted output.
- This approach leverages parallelism while keeping thread management abstracted by the Executor framework.

---

# 8) Introduction to Synchronization in Java:
- In a multithreaded environment, multiple threads may operate on a shared resource at the same time. If this access is not controlled, the state of the shared data can become inconsistent. A classic example is multiple threads incrementing or decrementing a shared counter.
- The core problem arises in the **critical section**—the part of code where shared data is read or modified. At any given time, only one thread should be allowed to execute this section. When implementing synchronization, it's important to keep the critical section as small as possible. This reduces the performance overhead introduced by synchronization, which is inversely proportional to the performance.
- A **race condition** occurs when multiple threads access and modify shared data concurrently, leading to unpredictable and incorrect results due to the non-deterministic order of thread execution. This race condition is the actual problem and critical section is the place where this problem arises.
- Preemption makes this worse. The OS can suspend a running thread at any point and schedule another. If a thread is preempted while updating shared state, another thread may observe or modify partially updated data, leading to inconsistency.
- To prevent these issues, we use synchronization mechanisms such as `synchronized` blocks or methods, locks, mutexes, or semaphores. These ensure mutual exclusion, allowing only one thread to enter the critical section at a time and preserving data consistency.

---

# 9) Properties of a Good Synchronization Mechanism:
- A good synchronization mechanism should satisfy a few key properties.
- First is **mutual exclusion**—at any point in time, only one thread should be allowed to enter the critical section that accesses shared data. This prevents race conditions and keeps the shared state consistent.
- Second is **bounded waiting**—no thread should wait indefinitely to enter the critical section. Every thread that requests access should eventually get a turn, ensuring fairness.
- Third is **progress**—the system as a whole must keep moving forward. If no thread is inside the critical section, one of the waiting threads should be allowed to enter without unnecessary delay.
- A good design also avoids **busy waiting**. Threads should not continuously poll to check whether the resource is free. Instead, they should block and be notified when the resource becomes available, using mechanisms like `wait()` and `notify()`.
- Additionally, synchronization should be **scalable** and **efficient**—it should introduce minimal overhead and allow non-conflicting threads to run concurrently.
- Finally, it should be **portable**, meaning the behavior remains correct and predictable across different platforms and operating systems.

---

# 10) Solutions to Synchronisation Problem

## (10.1) Mutex Locks:
- One common solution to synchronization problems is using a **mutex lock**, where mutex stands for *mutual exclusion*. The idea is to ensure that only one thread can enter the critical section at a time.
- In Java, this can be implemented using `ReentrantLock`:
```java
Lock lock = new ReentrantLock();
// For fair ordering (FIFO):
Lock fairLock = new ReentrantLock(true);
```
- The same lock object is shared among all threads that want to access the critical section. Whichever thread acquires the lock first is allowed to enter the critical section, while the remaining threads are blocked.
- Once the thread finishes its work, it releases the lock using `unlock()`. At that point, one of the waiting threads is allowed to acquire the lock and enter the critical section. This ensures **mutual exclusion** and prevents race conditions on shared data.
- Using locks also ensures **system progress**, because threads do not spin or busy-wait. Instead, they are suspended until the lock becomes available. **When fairness is enabled (`new ReentrantLock(true)`), threads acquire the lock in FIFO order, preventing starvation and guaranteeing bounded waiting. Without fairness, lock acquisition order is not guaranteed.**
- Overall, `ReentrantLock` provides explicit control over locking and unlocking, avoids busy waiting, and helps solve synchronization issues in a clean and scalable way in multithreaded applications.
- *ReentrantLock gives explicit mutual exclusion with better control than `synchronized`, especially for complex locking scenarios like try-lock with timeout, interruptible locks, and fair ordering.*

## (10.2) Synchronized Keyword:
- Java provides every object with an intrinsic lock (also called a monitor lock). This is the lock used when you apply the `synchronized` keyword to a method or a block. So, we aren't required to create the lock and manage it i.e. lock it, unlock it. To use the intrinsic monitor lock inside the Synchronized block, we have to also specify the object whose lock will be used, in the round bracket after `synchronized` keyword. The Rest of all other objects will be accessed by other threads as it is, even if they are inside the synchronized block as we aren't using their lock.
- When a thread enters a synchronized method or block, it must acquire the corresponding monitor lock. While one thread holds this lock, no other thread can enter the same synchronized region guarded by that lock. This ensures mutual exclusion and prevents race conditions on shared data. Java's `synchronized` block allows locking only one object at a time, so we cannot lock multiple objects together using `synchronized` block.
- For instance-level synchronization, the lock is taken on the object. One instance lock per object (`this`) → controls all non-static synchronized methods of that object
- For static synchronized methods, the lock is taken on the corresponding Class object. One class lock per class (`ClassName.class`) → controls all static synchronized methods of that class.
- In addition to mutual exclusion, `synchronized` also provides memory visibility guarantees—changes made by one thread inside a synchronized block become visible to other threads when the lock is released.
- Threads that cannot acquire the lock are blocked by the JVM, so there is no busy waiting.
- **However, `synchronized` does NOT guarantee fairness or bounded waiting—there is no assurance about the order in which waiting threads will acquire the lock. `synchronized` does NOT guarantee that threads acquire the lock in the order they requested it (FIFO order). A thread that arrives later might get the lock before a thread that has been waiting longer.**
- **In contrast, `ReentrantLock` with fairness enabled (`new ReentrantLock(true)`) DOES guarantee FIFO ordering of lock acquisition.**

## (10.3) Atomic Data Types:
- Java provides atomic data types in the `java.util.concurrent.atomic` package. These classes support lock-free, thread-safe operations on single variables.
- Common examples are `AtomicInteger`, `AtomicLong`, `AtomicBoolean` and `AtomicReference<V>`. They guarantee that operations such as increment, update, and compare-and-set are performed atomically.
- Unlike `synchronized` or locks, atomic variables do not block threads. Instead, they rely on underlying low-level CPU-level operations like Compare-And-Swap (CAS). This avoids race conditions without using mutual exclusion.
- Atomic variables achieve thread-safety without mutual exclusion locks. Instead of blocking competing threads, they use lock-free Compare-And-Swap (CAS) operations at the CPU level. If a CAS operation fails because another thread modified the value, the thread simply retries immediately rather than waiting in a queue. This eliminates the overhead of lock acquisition, context switching, and thread blocking, making atomic operations significantly faster under low to moderate contention.
- Atomic classes are especially useful when we need thread safety for simple shared variables, because they provide better performance and lower overhead compared to locks.
- Example:
```java
AtomicInteger counter = new AtomicInteger(0);
```
- Typical operations include `incrementAndGet()`, `decrementAndGet()`, `addAndGet()`, and `compareAndSet()`.
- Atomic variables also provide proper memory visibility guarantees, so updates made by one thread are visible to other threads.

## (10.4) Volatile Keyword:
- `volatile` is a variable-level modifier in Java. It is used to ensure **visibility of changes across threads**.
- In a multithreaded environment, **each thread may cache variables in CPU registers or CPU cache for performance reasons**. When one thread updates a shared variable, other threads may continue to read stale values from their local cache because the updated value hasn't been written back to main memory yet, or their cache hasn't been invalidated.
- When a variable is declared as `volatile`, every read of that variable is done from main memory and every write is immediately flushed to main memory, ensuring all threads see the most recent value. This prevents caching inconsistency.
- Ordering – `volatile` also provides a limited form of ordering guarantee (happens-before relationship), ensuring that operations before a volatile write happen before the write, and operations after a volatile read happen after the read.
- However, `volatile` does **not** provide mutual exclusion and does **not** make compound operations (like `count++`) thread-safe.
- The `volatile` keyword ensures that updates to a variable are immediately visible to all threads, but it does not provide locking or atomicity for compound operations.

## (10.5) Concurrent Data Structures:
- `HashMap` is not thread-safe. It is not synchronized, and in a multithreaded environment, concurrent read and write operations can lead to data inconsistency and even internal corruption. Therefore, `HashMap` is suitable only for single-threaded use cases unless external synchronization is applied.
- Java provides a synchronized wrapper using:
```java
Map<K,V> map = Collections.synchronizedMap(new HashMap<>());     //synchronizedMap() is the method of the Collections class.
```
- This makes all access to the map thread-safe by synchronizing every operation on a single lock. However, while iterating over such a synchronized map, we must manually synchronize on the map object:
```java
synchronized(map) {
    for (K key : map.keySet()) {
        ...
    }
}
```
- This is required because iteration is a compound operation and is not automatically protected by the synchronized wrapper.
- **This happens because the synchronized wrapper synchronizes individual method calls. So, when we call `map.keySet()` method, the lock is released after the method returns the key-set. Now the iterator is working on the key-set without any lock, and hence another thread can modify the map during the iteration. However, the iterator detects that the map has been changed and throws `ConcurrentModificationException` to indicate that the iteration is no longer valid.**
- Let's see how `synchronised(map)` helps here for this compound operation.
- When we use `synchronized(map)`, we acquire the lock on the `SynchronizedMap` wrapper object (i.e., the synchronized map instance returned by `Collections.synchronizedMap()`).
- Internally, `Collections.synchronizedMap()` creates a wrapper that uses a mutex lock—specifically, the wrapper object itself (`this`)—to synchronize all its methods like `put()`, `get()`, `keySet()`, etc. This means all these methods synchronize on the same lock object.
- Java's intrinsic locks (monitors) are **reentrant**, which means if a thread already holds a lock, it can acquire the same lock again without blocking itself. The lock maintains an internal counter that tracks how many times it has been acquired by the same thread.
- So when we write `synchronized(map) { ... map.keySet() ... }`:
    1. The outer `synchronized(map)` acquires the lock on the wrapper object (counter = 1)
    2. Inside the block, when `map.keySet()` is called, it internally tries to acquire the same lock (because `keySet()` is also synchronized on the wrapper)
    3. Since the current thread already owns this lock, it successfully reacquires it (counter = 2) without blocking
    4. When `keySet()` completes, the lock counter decrements (counter = 1)
    5. Throughout the iteration, the outer lock is still held (counter = 1), preventing other threads from calling any synchronized methods like `put()`, `get()`, or `remove()`
    6. When the synchronized block ends, the lock is fully released (counter = 0)
- This ensures that during the entire iteration, no other thread can modify the map, preventing `ConcurrentModificationException` and ensuring thread safety for the compound operation.
- Earlier, `Hashtable` was used as a thread-safe map. All its public methods are synchronized, which means only one thread can access the map at a time. While it provides correctness, it suffers from poor scalability due to coarse-grained locking.
- `ConcurrentHashMap` is a modern, high-performance, thread-safe alternative. It allows multiple threads to read the map concurrently, and also allows concurrent updates without locking the entire map. Internally, it uses fine-grained synchronization and lock-free techniques (`CompareAndSwap`) so that contention is significantly reduced, if CAS fails then fine-grained locking is done.
- As a result, `ConcurrentHashMap` scales much better than synchronized maps and `Hashtable`, and is the preferred choice for concurrent access in modern Java applications.

### One-line interview summary
- `HashMap` is not thread-safe, `Collections.synchronizedMap` and `Hashtable` use a single global lock (coarse-grained locking), while `ConcurrentHashMap` provides high concurrency by avoiding full-map locking and supporting concurrent reads and updates through fine-grained locking and lock-free CAS operations.

---

# 11) Semaphore in Java: Introduction
- A `Semaphore` is a synchronization utility provided in `java.util.concurrent`. A semaphore controls how many threads can access a resource concurrently by using permits, and threads must explicitly acquire and release those permits around the critical section.
```java
Semaphore semaphore = new Semaphore(n);
```
- Here, `n` represents the number of permits, which means at most `n` threads are allowed to enter the protected section at the same time.
- When a thread wants to enter the critical section, it must call `semaphore.acquire()`.
- If a permit is available, the thread acquires it and proceeds. If no permit is available, the thread is blocked until a permit is released.
- After finishing its work in the critical section, the thread must call `semaphore.release()` so that another waiting thread can proceed.
- A semaphore itself does not automatically define the critical section. The developer must explicitly place `acquire()` before the critical section and `release()` after it.
- A semaphore with **1 permit** behaves like a binary semaphore. **However, Mutex and Binary Semaphore are NOT the same despite appearing similar:**
    - **Mutex:** Has ownership - only the thread that acquired it can release it. Better for mutual exclusion.
    - **Binary Semaphore (permit=1):** No ownership - any thread can call `release()`, even if it didn't call `acquire()`. Better for signaling between threads.
- A semaphore with **more than 1 permit** is called a counting semaphore and is useful for limiting concurrent access to a limited resource (for example, a connection pool or limiting concurrent users in a system).

---

# 12) How permits are maintained in Semaphore:
- In a semaphore, a permit is represented internally as a non-negative counter. When a semaphore is created, it is initialized with a fixed number of permits. At any given time, only that many threads are allowed to enter the protected section.
- When a thread calls `acquire()`, the semaphore checks the counter. If the counter is greater than zero, it is decremented and the thread is allowed to proceed. If the counter is zero, the thread is blocked until a permit becomes available.
- When a thread calls `release()`, the counter is incremented and one of the waiting threads can acquire the permit and continue execution. The semaphore itself manages this counter internally—there is no separate permit object.
- An important point is that semaphores do not enforce ownership. A thread that releases a permit does not have to be the same thread that acquired it. This makes semaphores suitable for coordination patterns (like producer-consumer signaling), but it must be used carefully, otherwise permits can be released incorrectly which can lead to logical errors.

---

# 13) Classic Producer-Consumer problem using Semaphore:
- In the classical producer–consumer problem using semaphores, we model producers and consumers as separate tasks executed by multiple threads.
- We use a `ConcurrentLinkedDeque` as the shared store so that multiple producer and consumer threads can safely add and remove elements concurrently without corrupting the data structure.
- Let the store capacity be **n**, and let the current number of items be **k**.
- At any point in time, at most **(n − k) producer threads** are allowed to produce, and at most **'k' consumer threads** are allowed to consume.
- This is implemented using two semaphores:
```java
Semaphore empty = new Semaphore(CAPACITY);  // Represents empty slots
Semaphore full = new Semaphore(0);           // Represents filled slots (items available)
```
- A producer thread must acquire an `empty` permit before producing, and after producing, it releases a `full` permit so that consumers can proceed.
- Similarly, a consumer must acquire a `full` permit before consuming, and after consuming, it releases an `empty` permit so that producers can proceed.
- Because the store is a concurrent data structure, we do not need a separate mutex for the queue itself.
- Semaphores are used only for coordination and capacity control, which allows multiple producers and consumers to operate in parallel while still respecting the store limits.

---

# 14) Thread Interruption:
- When a thread tries to acquire a semaphore permit and none are available, it blocks.
- To support cancellation and responsiveness, `Semaphore.acquire()` is **interruptible**.
- If a blocked thread is interrupted, it immediately stops waiting and an `InterruptedException` is thrown.
- This allows higher-level logic to cancel tasks and avoid threads waiting indefinitely.
- It is important to note that interruption itself does **not prevent deadlocks**. It is a cooperative cancellation mechanism that helps improve responsiveness and shutdown handling.
- Also, interruption does not automatically terminate the thread—the thread must handle the `InterruptedException` and decide how to exit gracefully.

---

# 15) Deadlocks:
- A deadlock is a situation in a multithreaded system where two or more threads are blocked forever, each waiting for a resource that is held by another waiting thread.
- For example, if Thread A holds resource R1 and waits for R2, while Thread B holds resource R2 and waits for R1, then neither thread can proceed. This circular waiting creates a deadlock, and the system makes no further progress.

## Four Necessary Conditions for Deadlock (All must be present):
1. **Mutual Exclusion** - Resources cannot be shared; only one thread can hold a resource at a time
2. **Hold and Wait** - Threads hold resources while waiting for additional resources
3. **No Preemption** - Resources cannot be forcibly taken from threads
4. **Circular Wait** - There exists a circular chain of threads, each waiting for a resource held by the next

## Deadlock Prevention:
- To prevent deadlock, we must break at least one of the four necessary conditions.
- One practical way to avoid deadlock in Java is to use `tryLock()` from `ReentrantLock`:
```java
Lock lock1 = new ReentrantLock();
Lock lock2 = new ReentrantLock();

if (lock1.tryLock()) {
    try {
        if (lock2.tryLock()) {
            try {
                // Critical section with both locks
            } finally {
                lock2.unlock();
            }
        }
    } finally {
        lock1.unlock();
    }
}
```
- With `tryLock()` (optionally with a timeout), a thread attempts to acquire the lock and, if it fails within the given time, it backs off and releases any other locks it already holds. **This breaks the "Hold and Wait" condition, preventing circular waiting and reducing the risk of deadlock.**
- Another approach is **deadlock detection**. Conceptually, this can be modeled using a resource-allocation graph where threads and resources are nodes, and edges represent requests and allocations. If a cycle is detected in the graph, it indicates a deadlock.
- Once a deadlock is detected, recovery can be done by terminating one of the involved threads or by pre-empting and reallocating resources.

---

# 16) ThreadLocal - Thread-Confined Variables [INTERMEDIATE]
- `ThreadLocal` provides thread-local variables. Each thread accessing a `ThreadLocal` variable has its own, independently initialized copy of the variable.
- This is useful when you want to associate state with a thread without using synchronization, because each thread has its own isolated copy.

## When to Use ThreadLocal:
- **Per-thread context:** User sessions, transaction IDs, database connections in web servers
- **Avoiding synchronization:** When each thread needs its own instance of a non-thread-safe object
- **Performance:** Eliminates contention by giving each thread its own copy

## Example:
```java
private static ThreadLocal<SimpleDateFormat> dateFormat = 
    ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

// Each thread gets its own SimpleDateFormat instance
String formatted = dateFormat.get().format(new Date());
```

## Important Points:
- **Memory Leaks:** Always call `threadLocal.remove()` when done, especially in thread pool environments, otherwise the thread-local value persists for the lifetime of the thread
- ThreadLocal variables are not inherited by child threads unless you use `InheritableThreadLocal`
- Not a replacement for synchronization when threads need to share data

## Interview Question:
**Q: When would you use ThreadLocal vs synchronized?**
**A:** Use ThreadLocal when each thread needs its own independent copy of a variable (like per-thread database connections). Use synchronized when threads need to share and modify the same data safely. ThreadLocal avoids synchronization overhead by eliminating sharing.

---

# 17) CountDownLatch & CyclicBarrier - Thread Coordination [INTERMEDIATE]

## (17.1) CountDownLatch
- `CountDownLatch` is a synchronization aid that allows one or more threads to wait until a set of operations being performed by other threads completes.
- It is initialized with a count, and threads call `await()` to wait. Other threads call `countDown()` to decrement the count. When the count reaches zero, all waiting threads are released.

### Use Case: Master-Worker Pattern
```java
CountDownLatch latch = new CountDownLatch(3);  // 3 workers

// Worker threads
for (int i = 0; i < 3; i++) {
    new Thread(() -> {
        // Do work
        System.out.println("Worker finished");
        latch.countDown();  // Signal completion
    }).start();
}

// Main thread waits for all workers
latch.await();  // Blocks until count reaches 0
System.out.println("All workers completed!");
```

### Key Characteristics:
- **One-time use:** Once count reaches zero, it cannot be reset
- **Use case:** Waiting for multiple tasks to complete before proceeding (e.g., service startup, parallel computations)

## (17.2) CyclicBarrier
- `CyclicBarrier` is a synchronization aid that allows a set of threads to wait for each other to reach a common barrier point.
- All threads must call `await()` at the barrier. When all threads have called `await()`, the barrier is tripped and all threads are released simultaneously.

### Use Case: Phased Computation
```java
CyclicBarrier barrier = new CyclicBarrier(3, () -> {
    System.out.println("All threads reached barrier, continuing...");
});

// Each thread
for (int i = 0; i < 3; i++) {
    new Thread(() -> {
        // Phase 1
        System.out.println("Phase 1 complete");
        barrier.await();  // Wait for others
        
        // Phase 2
        System.out.println("Phase 2 complete");
        barrier.await();  // Wait for others again (reusable!)
    }).start();
}
```

### Key Characteristics:
- **Reusable:** After all threads cross the barrier, it resets automatically
- **Optional action:** Can execute a barrier action when all threads arrive
- **Use case:** Iterative algorithms, parallel simulations where threads must synchronize at each phase

## Comparison Table:
| Feature | CountDownLatch | CyclicBarrier |
|---------|---------------|---------------|
| Reusability | One-time use | Reusable |
| Threads involved | One/multiple wait, others count down | All threads wait for each other |
| Typical use | Main waits for workers | Workers wait for each other |
| Barrier action | No | Yes (optional Runnable) |

## Interview Question:
**Q: CountDownLatch vs CyclicBarrier - when to use which?**
**A:** Use CountDownLatch when one or more threads need to wait for a set of tasks to complete (master waits for workers). Use CyclicBarrier when multiple threads need to wait for each other at a synchronization point, especially in iterative/phased algorithms where the barrier will be reused.

---

# 18) ReadWriteLock - Optimizing Read-Heavy Scenarios [INTERMEDIATE]
- `ReadWriteLock` maintains a pair of locks: one for read-only operations and one for writing.
- **Multiple threads can hold the read lock simultaneously** as long as no thread holds the write lock.
- **Only one thread can hold the write lock**, and when held, no other thread can acquire read or write locks.

## Why ReadWriteLock?
- In many applications, reads far outnumber writes (e.g., caches, configuration data, lookup tables)
- Using a single `synchronized` or `ReentrantLock` for both reads and writes is inefficient because reads don't conflict with each other
- ReadWriteLock allows concurrent reads while still ensuring exclusive writes

## Example:
```java
ReadWriteLock rwLock = new ReentrantReadWriteLock();
Lock readLock = rwLock.readLock();
Lock writeLock = rwLock.writeLock();

// Reading
readLock.lock();
try {
    // Multiple threads can read simultaneously
    return data;
} finally {
    readLock.unlock();
}

// Writing
writeLock.lock();
try {
    // Only one thread can write
    data = newValue;
} finally {
    writeLock.unlock();
}
```

## Key Characteristics:
- **Read Lock:** Shared lock - multiple threads can hold it simultaneously
- **Write Lock:** Exclusive lock - only one thread, blocks all other readers and writers
- **Fairness:** `ReentrantReadWriteLock` supports optional fairness policy
- **Upgrade/Downgrade:** Cannot directly upgrade from read lock to write lock (causes deadlock), but can downgrade from write to read

## When to Use:
- Data structures with frequent reads and infrequent writes
- Caches, configuration managers, read-heavy collections
- Performance critical code where read contention is high

## Interview Question:
**Q: Why can't you upgrade from a read lock to a write lock?**
**A:** If thread A holds a read lock and tries to acquire write lock, it must wait for all readers to release. But thread A itself is a reader that won't release until it gets the write lock - this creates a deadlock. ReadWriteLock prevents this by throwing an exception or blocking indefinitely. The safe pattern is: release read lock → acquire write lock → optionally downgrade back to read lock.

---

# 19) ForkJoinPool - Modern Parallelism Framework [ADVANCED]
- `ForkJoinPool` is a specialized thread pool designed for divide-and-conquer algorithms that can be broken down into smaller subtasks recursively.
- It uses a **work-stealing algorithm**: idle threads steal tasks from busy threads' queues, improving CPU utilization.

## How It Works:
1. **Fork:** Split a large task into smaller subtasks
2. **Compute:** Process subtasks in parallel (potentially forking further)
3. **Join:** Combine results from subtasks

## Example: Parallel Array Sum
```java
class SumTask extends RecursiveTask<Long> {
    private int[] array;
    private int start, end;
    private static final int THRESHOLD = 1000;
    
    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }
    
    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            // Base case: compute directly
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            // Recursive case: split and fork
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);
            
            leftTask.fork();  // Async execute left half
            long rightResult = rightTask.compute();  // Compute right half in current thread
            long leftResult = leftTask.join();  // Wait for left half
            
            return leftResult + rightResult;
        }
    }
}

// Usage
ForkJoinPool pool = new ForkJoinPool();
long result = pool.invoke(new SumTask(array, 0, array.length));
```

## Key Concepts:
- **RecursiveTask<V>:** Returns a result (like Callable)
- **RecursiveAction:** No result (like Runnable)
- **Work-Stealing:** Idle threads steal tasks from the tail of other threads' deques, balancing load dynamically
- **Common Pool:** `ForkJoinPool.commonPool()` is shared across the application (Java 8+)

## When to Use:
- Recursive divide-and-conquer algorithms (merge sort, quicksort, parallel tree/graph traversal)
- Large array/collection processing
- Computational tasks that can be split into independent subtasks

## Performance Considerations:
- **Threshold:** Too small = overhead from task creation; too large = insufficient parallelism
- **Sequential cutoff:** Once subtasks are small enough, compute sequentially
- Best for CPU-bound tasks, not I/O-bound

## Interview Question:
**Q: How does ForkJoinPool differ from a regular ExecutorService?**
**A:** ForkJoinPool is optimized for recursive divide-and-conquer tasks using work-stealing. Idle threads steal tasks from busy threads' deques, improving load balancing. Regular ExecutorService uses a shared task queue where all threads compete, which can cause contention. ForkJoinPool is ideal for CPU-bound recursive tasks, while ExecutorService is better for independent, non-recursive tasks.

---

# 20) CompletableFuture - Modern Async Programming [ADVANCED]
- `CompletableFuture` is a powerful class introduced in Java 8 for asynchronous programming. It represents a future result of an asynchronous computation and supports composing multiple async operations.
- Unlike `Future`, which only allows blocking `get()`, `CompletableFuture` enables non-blocking, callback-based programming.

## Basic Usage:
```java
// Create a CompletableFuture that runs asynchronously
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    // Runs in ForkJoinPool.commonPool() by default
    return "Hello";
});

// Non-blocking callback when complete
future.thenAccept(result -> System.out.println(result));

// Or block and get result (like Future)
String result = future.get();
```

## Chaining Operations:
```java
CompletableFuture.supplyAsync(() -> "Hello")
    .thenApply(s -> s + " World")           // Transform result
    .thenApply(String::toUpperCase)         // Further transform
    .thenAccept(System.out::println)        // Consume result
    .exceptionally(ex -> {                  // Handle exceptions
        System.err.println("Error: " + ex);
        return null;
    });
```

## Combining Multiple Futures:
```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");

// Wait for both and combine
CompletableFuture<String> combined = future1.thenCombine(future2, 
    (s1, s2) -> s1 + " " + s2);

// Or wait for the first one to complete
CompletableFuture<Object> fastest = CompletableFuture.anyOf(future1, future2);

// Or wait for all to complete
CompletableFuture<Void> all = CompletableFuture.allOf(future1, future2);
```

## Common Methods:
- **Creation:**
    - `supplyAsync(() -> T)` - Returns a value
    - `runAsync(() -> {})` - Returns void
- **Transformation:**
    - `thenApply(Function)` - Transform result
    - `thenCompose(Function)` - Flatten nested futures
- **Consumption:**
    - `thenAccept(Consumer)` - Consume result
    - `thenRun(Runnable)` - Run action after completion
- **Combination:**
    - `thenCombine()` - Combine two futures
    - `allOf()` - Wait for all
    - `anyOf()` - Wait for any
- **Error Handling:**
    - `exceptionally()` - Handle exceptions
    - `handle()` - Transform result or exception

## Async vs Sync Variants:
```java
// Runs in same thread as previous stage
future.thenApply(s -> s.toUpperCase())

// Runs in ForkJoinPool.commonPool()
future.thenApplyAsync(s -> s.toUpperCase())

// Runs in custom executor
future.thenApplyAsync(s -> s.toUpperCase(), customExecutor)
```

## Real-World Example: Parallel API Calls
```java
CompletableFuture<User> userFuture = 
    CompletableFuture.supplyAsync(() -> fetchUser(userId));
    
CompletableFuture<List<Order>> ordersFuture = 
    CompletableFuture.supplyAsync(() -> fetchOrders(userId));
    
CompletableFuture<Recommendations> recsFuture = 
    CompletableFuture.supplyAsync(() -> fetchRecommendations(userId));

// Combine all results
CompletableFuture<Dashboard> dashboard = userFuture
    .thenCombine(ordersFuture, (user, orders) -> new Pair<>(user, orders))
    .thenCombine(recsFuture, (pair, recs) -> 
        new Dashboard(pair.user, pair.orders, recs));
    
// Get result (blocks until all complete)
Dashboard result = dashboard.get();
```

## Key Advantages:
- **Non-blocking:** Callbacks execute when tasks complete, no thread blocking
- **Composable:** Chain multiple async operations fluently
- **Exception handling:** Built-in exception propagation and handling
- **Combinators:** Easily combine multiple independent async tasks

## Interview Question:
**Q: CompletableFuture vs Future - what's the difference?**
**A:** `Future` only supports blocking `get()` to retrieve results. `CompletableFuture` extends Future and adds non-blocking callbacks (`thenApply`, `thenAccept`), chaining, combining multiple futures, and built-in exception handling. It enables reactive, callback-based async programming instead of blocking and waiting, making it much more powerful for modern concurrent applications.

---

# 21) Memory Consistency Errors & Happens-Before Relationship [ADVANCED]

## What are Memory Consistency Errors?
- In a multithreaded program, memory consistency errors occur when different threads have inconsistent views of shared data.
- This happens because:
    1. **CPU caching:** Each thread may cache variables in CPU registers/cache
    2. **Compiler optimizations:** Compilers may reorder instructions for performance
    3. **CPU reordering:** Modern CPUs execute instructions out-of-order for efficiency

## Example of Memory Inconsistency:
```java
class SharedData {
    private int value = 0;
    private boolean ready = false;
    
    // Thread 1
    void writer() {
        value = 42;        // Statement 1
        ready = true;      // Statement 2
    }
    
    // Thread 2
    void reader() {
        if (ready) {                    // May see ready=true
            System.out.println(value);  // But may still see value=0!
        }
    }
}
```
- Without synchronization, Thread 2 might see `ready=true` but `value=0` because:
    - CPU/compiler might reorder statements 1 and 2
    - Thread 2's cache might not have the updated `value`

## Happens-Before Relationship
- The **happens-before** relationship is Java's guarantee about memory visibility and ordering between threads.
- If action A happens-before action B, then the memory effects of A are visible to B.

## Happens-Before Rules in Java:
1. **Program Order Rule:** Within a single thread, each action happens-before any subsequent action (in program order)

2. **Monitor Lock Rule:**
    - An unlock on a monitor happens-before every subsequent lock on that same monitor
    - Ensures synchronized blocks provide visibility

3. **Volatile Variable Rule:**
    - A write to a volatile variable happens-before every subsequent read of that volatile variable
   ```java
   volatile boolean ready = false;
   int value = 0;
   
   // Thread 1
   value = 42;
   ready = true;  // Volatile write
   
   // Thread 2
   if (ready) {  // Volatile read - sees value=42 guaranteed
       System.out.println(value);
   }
   ```

4. **Thread Start Rule:**
    - `thread.start()` happens-before any action in the started thread
   ```java
   int x = 5;
   Thread t = new Thread(() -> System.out.println(x));  // Will see x=5
   t.start();
   ```

5. **Thread Join Rule:**
    - All actions in a thread happen-before any thread successfully returns from `join()` on that thread
   ```java
   Thread t = new Thread(() -> value = 42);
   t.start();
   t.join();
   System.out.println(value);  // Guaranteed to see 42
   ```

6. **Transitivity:**
    - If A happens-before B, and B happens-before C, then A happens-before C

## How Synchronization Provides Happens-Before:
```java
class SynchronizedExample {
    private int value = 0;
    private final Object lock = new Object();
    
    // Thread 1
    void writer() {
        synchronized(lock) {
            value = 42;
        }  // Unlock happens-before subsequent lock
    }
    
    // Thread 2
    void reader() {
        synchronized(lock) {  // Lock happens-after unlock from Thread 1
            System.out.println(value);  // Guaranteed to see 42
        }
    }
}
```

## Volatile vs Synchronized for Memory Visibility:
- **volatile:** Provides happens-before for the variable itself, lightweight
    - Good for flags, status indicators
    - Does NOT provide atomicity for compound operations
- **synchronized:** Provides happens-before for all variables accessed in the block, also provides atomicity
    - Good for critical sections with multiple shared variables
    - Heavier weight (locks)

## Interview Question:
**Q: What is the happens-before relationship and why is it important?**
**A:** Happens-before is Java's memory visibility guarantee between threads. If action A happens-before B, then A's memory effects are visible to B. Without happens-before guarantees (via synchronization, volatile, or thread coordination), threads may see stale or reordered data due to CPU caching and compiler optimizations. Proper use of synchronized, volatile, locks, or concurrent utilities establishes happens-before relationships and prevents memory consistency errors.

---

# 22) Thread Safety Levels - Classification [ADVANCED]

## Thread Safety Spectrum:
Java objects and classes can be classified into different levels of thread safety:

### 1. Immutable (Strongest)
- **Definition:** Objects whose state cannot be modified after construction
- **Examples:** String, Integer, LocalDate, Collections created via `Collections.unmodifiableList()`
- **Thread Safety:** Perfectly thread-safe - no synchronization needed
- **Why Safe:** If state cannot change, there are no race conditions
```java
public final class ImmutablePerson {
    private final String name;
    private final int age;
    
    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // Only getters, no setters
    public String getName() { return name; }
    public int getAge() { return age; }
}
```

### 2. Unconditionally Thread-Safe
- **Definition:** Objects that are thread-safe under all conditions, no external synchronization required
- **Examples:** `ConcurrentHashMap`, `AtomicInteger`, `StringBuffer`, `Vector`
- **Thread Safety:** Fully thread-safe - internal synchronization handles everything
- **Usage:** Can be safely used by multiple threads without any additional synchronization
```java
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
// Multiple threads can safely call put/get without external synchronization
map.put("key", "value");
```

### 3. Conditionally Thread-Safe
- **Definition:** Thread-safe for individual operations, but compound operations require external synchronization
- **Examples:** `Collections.synchronizedMap()`, `Collections.synchronizedList()`, `Hashtable`
- **Thread Safety:** Individual methods are synchronized, but iterations and compound operations are not
- **Usage:** Need external synchronization for compound operations
```java
Map<String, String> map = Collections.synchronizedMap(new HashMap<>());

// Single operation - safe
map.put("key", "value");

// Compound operation - NOT safe without external synchronization
synchronized(map) {
    if (!map.containsKey("key")) {
        map.put("key", "value");
    }
}

// Iteration - NOT safe without external synchronization
synchronized(map) {
    for (String key : map.keySet()) {
        // ...
    }
}
```

### 4. Thread-Compatible (Not Thread-Safe)
- **Definition:** Not thread-safe internally, but can be made thread-safe with external synchronization
- **Examples:** `ArrayList`, `HashMap`, `StringBuilder`
- **Thread Safety:** Not thread-safe - race conditions if used concurrently
- **Usage:** Either use from single thread, or wrap with external synchronization
```java
List<String> list = new ArrayList<>();

// Option 1: Use from single thread only

// Option 2: External synchronization
synchronized(list) {
    list.add("item");
}

// Option 3: Use wrapper
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
```

### 5. Thread-Hostile (Worst)
- **Definition:** Unsafe even with external synchronization - should never be used concurrently
- **Examples:** System.setOut(), Swing components (must be used only on EDT)
- **Thread Safety:** Cannot be made safe - architectural limitations
- **Usage:** Avoid or use with extreme care in single-threaded context only

## Summary Table:
| Level | Description | Examples | External Sync Needed? |
|-------|-------------|----------|---------------------|
| Immutable | Cannot change after construction | String, Integer | Never |
| Unconditionally Safe | Always thread-safe | ConcurrentHashMap, AtomicInteger | Never |
| Conditionally Safe | Safe for individual ops, not compound | Collections.synchronizedMap | For compound ops |
| Thread-Compatible | Can be made safe with sync | ArrayList, HashMap | Always |
| Thread-Hostile | Cannot be made safe | System.setOut() | Avoid concurrent use |

## Design Recommendations:
1. **Prefer immutability** - safest and simplest
2. **Use concurrent collections** - better than synchronized wrappers
3. **Document thread safety** - clearly state which level your class provides
4. **Minimize shared mutable state** - reduces synchronization needs

## Interview Question:
**Q: What's the difference between conditionally thread-safe and unconditionally thread-safe?**
**A:** Unconditionally thread-safe classes (like ConcurrentHashMap) handle all synchronization internally - you can safely use them from multiple threads without any external synchronization. Conditionally thread-safe classes (like Collections.synchronizedMap) synchronize individual methods but not compound operations - you still need external synchronization for iterations or check-then-act sequences. The key difference is whether ALL possible operations are safe or only individual method calls.

---

# 23) Common Concurrency Interview Mistakes - Red Flags 🚩

## 1. Calling run() instead of start()
```java
Thread t = new Thread(() -> System.out.println("Hello"));
t.run();  // ❌ WRONG - executes in current thread
t.start(); // ✅ CORRECT - creates new thread
```

## 2. Not handling InterruptedException properly
```java
// ❌ WRONG - swallowing exception
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    // Do nothing
}

// ✅ CORRECT - restore interrupt status
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();  // Restore interrupt flag
    // Handle or propagate
}
```

## 3. Using stop() or suspend() (deprecated and dangerous)
```java
thread.stop();  // ❌ WRONG - deprecated, can corrupt state
// ✅ CORRECT - use interrupt and cooperative cancellation
```

## 4. Forgetting to shutdown ExecutorService
```java
ExecutorService executor = Executors.newFixedThreadPool(10);
executor.submit(() -> doWork());
// ❌ WRONG - JVM won't exit, threads keep running

executor.shutdown();  // ✅ CORRECT - graceful shutdown
// or executor.shutdownNow() for immediate termination
```

## 5. Compound operations without atomicity
```java
if (!map.containsKey(key)) {  // ❌ WRONG - race condition
    map.put(key, value);
}

// ✅ CORRECT
map.putIfAbsent(key, value);  // Atomic operation
```

## 6. Using volatile for compound operations
```java
private volatile int count = 0;
count++;  // ❌ WRONG - not atomic!

// ✅ CORRECT
private AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet();
```

## 7. Deadlock from lock ordering
```java
// ❌ WRONG - potential deadlock
synchronized(lock1) {
    synchronized(lock2) { /* ... */ }
}
// Thread 2 locks in opposite order - deadlock!

// ✅ CORRECT - consistent lock ordering
// Always acquire lock1 before lock2 across all threads
```

---

# 24) Quick Reference Card - Interview Cheat Sheet

## Synchronization Mechanisms Comparison:
```
synchronized vs Lock:
- synchronized: Simpler, automatic release, no fairness guarantee
- Lock: tryLock(), can interrupt, fairness option, more flexible

volatile vs Atomic:
- volatile: Visibility only, no atomicity for compound ops
- Atomic: Visibility + atomicity using CAS, better for counters

wait/notify vs await/signal:
- wait/notify: On Object monitor, simple, older API
- await/signal: On Condition, more flexible, multiple conditions per lock

Mutex vs Semaphore(1):
- Mutex: Ownership, only acquirer can release
- Semaphore: No ownership, any thread can release, better for signaling
```

## Collections Thread-Safety:
```
Not thread-safe: ArrayList, HashMap, HashSet, StringBuilder
Synchronized (legacy): Vector, Hashtable, StringBuffer
Concurrent (modern): ConcurrentHashMap, CopyOnWriteArrayList
```

## When to Use What:
```
Simple shared counter → AtomicInteger
Complex critical section → synchronized or ReentrantLock
Read-heavy data → ReadWriteLock or ConcurrentHashMap
Pool of limited resources → Semaphore(n)
Wait for multiple tasks → CountDownLatch
Phased synchronization → CyclicBarrier
Divide-and-conquer → ForkJoinPool
Async composition → CompletableFuture
Per-thread data → ThreadLocal
```

---

# 25) Common Follow-up Interview Questions

## After discussing synchronized:
- Q: What happens if an exception is thrown inside a synchronized block?
- A: The lock is automatically released - synchronized ensures unlock in finally block internally

## After discussing volatile:
- Q: Does volatile guarantee atomicity?
- A: No - only visibility. count++ is still not atomic with volatile

## After discussing ExecutorService:
- Q: What happens if you don't call shutdown()?
- A: Non-daemon threads keep running, JVM won't exit

## After discussing ConcurrentHashMap:
- Q: Is ConcurrentHashMap.size() accurate?
- A: Not always - it's an estimate due to concurrent modifications. Use mappingCount() for accurate count

## After discussing deadlock:
- Q: Can you have deadlock with just one lock?
- A: No - deadlock requires circular waiting, minimum 2 resources/locks

## After discussing ThreadLocal:
- Q: What's the risk with ThreadLocal in thread pools?
- A: Memory leaks - values persist for thread lifetime. Always call remove() when done

---

# End of Notes

**Total Topics Covered:** 25 (Basic → Intermediate → Advanced)

**Recommended Study Path:**
1. Sections 1-10: Core concepts (Must know for any interview)
2. Sections 11-15: Synchronization details (Common in mid-level interviews)
3. Sections 16-22: Advanced topics (Senior/expert level interviews)
4. Sections 23-25: Interview preparation (Practice these!)

**Good luck with your interviews! 🚀**