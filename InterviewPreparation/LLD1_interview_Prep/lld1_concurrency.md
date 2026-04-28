# 1) How a computer application works?
- Computer applications are complex systems that run on a computer's operating system, interacting with hardware and software components to perform various tasks.

## (1.1) program → process → CPU scheduling → context switching → concurrency vs parallelism
- A program is a passive, ready-to-run set of compiled instructions stored on disk as an executable file (`.exe`, `.apk`). When we start it, the operating system loads it into RAM, allocates memory regions (code, stack, heap, registers) and system resources, transforming it into an active process—the CPU then fetches, decodes, and executes instructions directly from RAM because RAM operates at speeds comparable to the CPU, unlike the much slower disk storage.
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
- The CPU can be interrupted to handle external events, like input from a user or data arriving from a network.Interrupts are crucial for maintaining responsiveness in a multitasking environment.

## (1.5) Termination of Processes
- When a program finishes its task or is closed by the user, the associated process is terminated. The OS reclaims the allocated resources and frees up memory.

## (1.6) Computer Systems and fundamentals:
- The processor, or CPU, is the brain of the computer. It is a hardware chip responsible for executing machine-level instructions that run software. A modern CPU consists of multiple components, including cores, an Arithmetic Logic Unit (ALU) for computations, a control unit to manage instruction flow, and multiple levels of cache for fast data access.
- The CPU operates in a fetch–decode–execute cycle: it fetches an instruction from memory, decodes it, and then executes/processes it. In the process, it coordinates with RAM, storage, and I/O devices.
- A core is the smallest complete processing unit within a CPU. Each core has its own execution pipeline, ALU, control logic, and cache. Because of this, each core can independently execute threads assigned to it. As the number of cores increases, the system can perform more work in parallel, which directly benefits multitasking and multithreaded applications.

## (1.7) Process Control Block (PCB)
- For every running process, the OS creates a small data structure called the *Process Control Block* (PCB). The PCB does not store the actual code or data; instead, it maintains metadata such as the process ID, memory mappings, open resources, scheduling information, and the CPU state, including the program counter. This allows the OS to pause and later resume a process exactly from where it left off.
- A process contains one or more threads. Each thread has its own stack and program counter, but all threads of a process share the same code, heap, and resources. During execution, when the OS switches between threads or processes, it saves the current thread’s state and restores another—this is context switching.
- On a single-core system, threads make progress through rapid context switching. On a multicore system, multiple threads can truly execute at the same time on different cores, eliminating switching overhead and enabling real parallelism.
- Modern operating systems schedule **threads**, not entire processes. The scheduler assigns threads to CPU cores, and multiple threads from the same process can run simultaneously on different cores, sharing memory while executing independently.

# 2) Concurrency in Java:-
- Concurrency/Concurrent programming, which is a fundamental concept in modern software development refers to the ability of a system to handle multiple tasks at the same time by making progress on more than one unit of work within the same time period. In Java, this is achieved using multiple threads, which are the smallest units of execution within a process. These threads are scheduled by the operating system and the JVM, allowing tasks such as I/O, computation, and background processing to overlap, improving responsiveness and throughput.

## (2.1) Concurrency vs Parallelism:
- Concurrency is about dealing with multiple tasks by interleaving their execution. The tasks appear to run simultaneously, but on a single core they actually run alternately through rapid context switching. Concurrency can exist on both single-core and multi-core systems.
- Parallelism, on the other hand, means true simultaneous execution—where multiple tasks or threads run at the same time on different CPU cores. It requires multi-core or multi-processor hardware.

# 3) Benefits and Challenges of Multithreading:
- The primary benefit of multithreading is improved performance and responsiveness. By running multiple threads concurrently—or in parallel on multi-core systems—an application can utilize the CPU more efficiently. Long-running tasks can be offloaded to background threads, keeping the main thread free to handle user interactions, which is crucial for responsive applications.
- In server-side systems like web servers, multithreading enables handling multiple client requests simultaneously. However, since threads within a process share the same data, we must ensure proper synchronization to maintain data consistency. We also need to be careful about concurrency issues such as race conditions and deadlocks, which can arise when threads compete for shared resources.

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
- After invoking `start()`, the thread enters the `RUNNABLE` state. This means it is either ready to run or actually running on the CPU—Java does not distinguish a separate “running” state.
- If a thread tries to enter a synchronized block or method but the lock is held by another thread, it moves to the `BLOCKED` state.
- When a thread waits indefinitely—for example, by calling `wait()` or `join()`—it enters the `WAITING` state.
- If it waits for a bounded time, such as via `sleep(timeout)` or `wait(timeout)`, it enters the `TIMED_WAITING` state.
- Once the `run()` method completes, the thread enters the `TERMINATED` state and cannot be restarted.
- It’s important to note that interruption is not a thread state—it is a signal. Calling `interrupt()` sets a flag and may cause blocking operations like `sleep()` or `wait()` to throw `InterruptedException`.

# 5) Thread Pools:
- A thread pool is a managed collection of pre-created, reusable threads that execute tasks from a shared task queue (`ExecutorService` has waiting queue). Instead of creating a new thread for every task, tasks are submitted to the pool. Whenever a thread becomes available, it picks up a task, executes it, and then returns to the pool to be reused.
- This approach is far more efficient than creating and destroying threads repeatedly as thread creation requires allocating memory and resources and hence the thread creation is expensive. Since threads in pools already exist and are usually in a waiting state, task assignment is much faster.
- Thread pools are therefore ideal for high-performance and long-running applications, such as servers, where a large number of short-lived tasks must be handled efficiently while keeping resource usage under control.
- Consider a backend system like Amazon during peak traffic, receiving millions of requests simultaneously. We cannot create one thread per request because each thread consumes memory—typically around 1 MB for its stack. Creating a million threads would quickly exhaust memory and lead to `OutOfMemoryError`.
- Moreover, the number of CPU cores is limited—say 16 or 32. When a massive number of threads compete for a small number of cores, the system spends most of its time in rapid context switching instead of doing real work, severely degrading performance.
- Creating and managing threads manually is also expensive and complex. This is where the Executor Framework comes in. It provides efficient creation, reuse, and management of a fixed number of threads. Tasks are submitted to the executor, and the framework handles scheduling and lifecycle management.
- This allows developers to focus on business logic while ensuring controlled resource usage, better throughput, and stable performance—making thread pools ideal for high-load, server-side applications.

# 6) MultiThreading using ExecutorService and ThreadPool
```java
ExecutorService executor = Executors.newFixedThreadPool(10);
```
- The Java `ExecutorService` is a key interface within the `java.util.concurrent` package, providing a robust framework (acts like thread pool manager) for managing and executing tasks **asynchronously** using a pool of threads. It abstracts away the complexities of manual thread creation and management, promoting efficient resource utilization and simplified concurrent programming.
- The `Executors` utility class provides convenient factory methods for creating various types of ExecutorService instances, such as `newFixedThreadPool` (fixed number of threads), `newCachedThreadPool` (dynamically adjusts thread count), and `newSingleThreadExecutor` (a single worker thread), etc.
- To the `ExecutorService`, tasks can be submitted as `Runnable` (for tasks that don't return a result) using `execute()` method or `Callable` (for tasks that return a result and can throw exceptions)using the `submit()` method that returns a Future object, which represents the result of an asynchronous computation and can be used to check task status, retrieve results, or cancel the task.
- `ExecutorService` provides lifecycle control (start, shutdown, force termination):- When we call `executor.shutdown()`, the executor stops accepting new tasks, but continues executing all previously submitted tasks. Once those tasks complete, the worker threads are terminated gracefully and `shutdownNow()` (attempts to stop currently executing tasks and prevents waiting tasks from starting).
- 📌 Key Insight: `Executors` creates `ExecutorService` → `ExecutorService` manages the thread pool and task lifecycle → The worker `Threads` execute the submitted tasks.

# 7) Difference between Runnable and Callable in Java:
- Manual Thread creation: Uses `new Thread(Runnable)`. Limited to `void` returns because constructors cannot return a `Future`. So, manual thread creation only supports `Runnable` tasks as constructor accepts only `Runnable`. This is `Fire and forget` model.
- While, In the Executor framework, we can submit both the kinds of tasks: `Runnable` and `Callable`.
- A `Runnable` represents a task whose `run()` method returns `void`. It is used when we only want to execute logic and do not expect any result. Such tasks are typically submitted using `execute()` or `submit()`. When submitted via `submit()`, it returns a `Future<?>` which gives `null`on calling `.get()` method, effectively acting as a signal that the `void` method has finished executing. The `Future` is still useful because it allows you to:
   - Check if the task is finished (isDone()).
   - Wait (block) until the task completes (get()).
   - Cancel the task.
- A `Callable<T>` task, on the other hand, represents a task that returns a value. Its `call()` method returns a result of type `T` and can also throw checked exceptions. Callable tasks are submitted using `submit()` to the `ExecutorService`, and in return we get a `Future<T>`. Remember, we don't submit `Callable<T>` to threads manually.
- Now, consider merge sort. The algorithm works by recursively dividing an array into two halves, sorting each half, and then merging them. The key observation is that sorting the left and right halves are independent operations. This makes merge sort a natural candidate for parallel execution using multithreading.
- In a multithreaded implementation, each half can be processed as a separate task. Since each task produces a sorted sub-array, we use `Callable<List<Integer>>` instead of `Runnable`. Each task implements `Callable<T>`, defines its logic in the `call()` method, and is submitted to the `ExecutorService` using `submit()`.
- The `submit()` method returns a `Future<T>`. To retrieve the result, we call `future.get()`, which is a blocking call—meaning the calling thread waits until the task completes. Once both halves return their results, we merge them to produce the final sorted output.
- This approach leverages parallelism while keeping thread management abstracted by the Executor framework.

# 8) Introduction to Synchronization in Java:
- In a multithreaded environment, multiple threads may operate on a shared resource at the same time. If this access is not controlled, the state of the shared data can become inconsistent. A classic example is multiple threads incrementing or decrementing a shared counter.
- The core problem arises in the **critical section**—the part of code where shared data is read or modified. At any given time, only one thread should be allowed to execute this section. When implementing synchronization, it's important to keep the critical section as small as possible. This reduces the performance overhead introduced by synchronization, which is inversely proportional to the performance.
- A **race condition** occurs when multiple threads access and modify shared data concurrently, leading to unpredictable and incorrect results due to the non-deterministic order of thread execution. This race condition is the actual problem and critical section is the place where this problem arises.
- Preemption makes this worse. The OS can suspend a running thread at any point and schedule another. If a thread is preempted while updating shared state, another thread may observe or modify partially updated data, leading to inconsistency.
- To prevent these issues, we use synchronization mechanisms such as `synchronized` blocks or methods, locks, mutexes, or semaphores. These ensure mutual exclusion, allowing only one thread to enter the critical section at a time and preserving data consistency.

# 9) Properties of a Good Synchronization Mechanism:
- A good synchronization mechanism should satisfy a few key properties.
- First is **mutual exclusion**—at any point in time, only one thread should be allowed to enter the critical section that accesses shared data. This prevents race conditions and keeps the shared state consistent.
- Second is **bounded waiting**—no thread should wait indefinitely to enter the critical section. Every thread that requests access should eventually get a turn, ensuring fairness.
- Third is **progress**—the system as a whole must keep moving forward. If no thread is inside the critical section, one of the waiting threads should be allowed to enter without unnecessary delay.
- A good design also avoids **busy waiting**. Threads should not continuously poll to check whether the resource is free. Instead, they should block and be notified when the resource becomes available, using mechanisms like `wait()` and `notify()`.
- Additionally, synchronization should be **scalable** and **efficient**—it should introduce minimal overhead and allow non-conflicting threads to run concurrently.
- Finally, it should be **portable**, meaning the behavior remains correct and predictable across different platforms and operating systems.

# 10) Solutions to Synchronisation Problem

## (10.1) Mutex Locks:
- One common solution to synchronization problems is using a **mutex lock**, where mutex stands for *mutual exclusion*. The idea is to ensure that only one thread can enter the critical section at a time.
- In Java, this can be implemented using `ReentrantLock`:
```java
Lock lock = new ReentrantLock();
```
- The same lock object is shared among all threads that want to access the critical section. Whichever thread acquires the lock first is allowed to enter the critical section, while the remaining threads are blocked.
- Once the thread finishes its work, it releases the lock using `unlock()`. At that point, one of the waiting threads is allowed to acquire the lock and enter the critical section. This ensures **mutual exclusion** and prevents race conditions on shared data.
- Using locks also ensures **system progress**, because threads do not spin or busy-wait. Instead, they are suspended until the lock becomes available. Also, the threads in the waiting queue acquire lock in FIFO order (fair) as `ReentrantLock` with `fairness=true` enabled ensures FIFO ordering, preventing starvation and guaranteeing bounded waiting.
- Overall, `ReentrantLock` provides explicit control over locking and unlocking, avoids busy waiting, and helps solve synchronization issues in a clean and scalable way in multithreaded applications.
- *ReentrantLock gives explicit mutual exclusion with better control than `synchronized`, especially for complex locking scenarios.*

## (10.2) Synchronized Keyword:
- Java provides every object with an intrinsic lock (also called a monitor lock). This is the lock used when you apply the `synchronized` keyword to a method or a block. So, we aren't required to create the lock and manage it i.e. lock it, unlock it. To use the intrinsic monitor lock inside the Synchronized block, we have to also specify the object whose lock will be used, in the round bracket after `synchronized` keyword. The Rest of all other objects will be accessed by other threads as it is, even if they are inside the synchronized block as we aren't using their lock.
- When a thread enters a synchronized method or block, it must acquire the corresponding monitor lock. While one thread holds this lock, no other thread can enter the same synchronized region guarded by that lock. This ensures mutual exclusion and prevents race conditions on shared data. Java's `synchronized` block allows locking only one object at a time, so we cannot lock multiple objects together using `synchronized` block.
- For instance-level synchronization, the lock is taken on the object. One instance lock per object (`this`) → controls all non-static synchronized methods of that object
- For static synchronized methods, the lock is taken on the corresponding Class object. One class lock per class (`ClassName.class`) → controls all static synchronized methods of that class.
- In addition to mutual exclusion, `synchronized` also provides memory visibility guarantees—changes made by one thread inside a synchronized block become visible to other threads when the lock is released.
- Threads that cannot acquire the lock are blocked by the JVM, so there is no busy waiting.
- However, `synchronized` does not guarantee fairness or bounded waiting—there is no assurance about the order in which waiting threads will acquire the lock. `synchronized` does NOT guarantee that threads acquire the lock in the order they requested it (FIFO order). A thread that arrives later might get the lock before a thread that has been waiting longer.

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
- In a multithreaded environment, each thread may cache variables locally for performance reasons. The operating system may not flush the first thread's changes to the heap, until the thread has finished executing, causing memory inconsistency errors. As a result, when one thread updates a shared variable, other threads may continue to see an outdated value.
- When a variable is declared as `volatile`, every read of that variable is done from main memory and every write is immediately made visible to other threads. This guarantees that all threads always observe the most recent value.
- Ordering – `volatile` also provides a limited form of ordering guarantee, ensuring that operations before a volatile write happen before the write, and operations after a volatile read happen after the read.
- However, `volatile` does **not** provide mutual exclusion and does **not** make compound operations thread-safe.
- The `volatile` keyword ensures that updates to a variable are immediately visible to all threads, but it does not provide locking or atomicity.

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
- This happens because the synchronized wrapper synchronizes individual method calls. So, when we call map.keySet() method, the lock is released after the method returns the list of keys. So, now the iterator is working on the key-set without any lock, and hence another thread can modify the map during the iteration. However, the iterator detects that the map has been changed and throws `ConcurrentModificationException` to indicate that the iteration is no longer valid.
- Let's see how synchronised(map) helps here for this compound operation.
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
- `HashMap` is not thread-safe, `Collections.synchronizedMap` and `Hashtable` use a single global lock, while `ConcurrentHashMap` provides high concurrency by avoiding full-map locking and supporting concurrent reads and updates.

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
- A semaphore with **1 permit** behaves like a mutex (binary semaphore). However, remember, Mutex and Binary Semaphore are not same. They may seem similar, they have critical differences in behavior and purpose.
   - Mutex: Has ownership - only the thread that acquired it can release it
   - Binary Semaphore: No ownership - any thread can call release(), even if it didn't call acquire()
   - This makes mutex better for mutual exclusion, semaphore better for signaling
- A semaphore with **more than 1 permit** is called a counting semaphore and is useful for limiting concurrent access to a limited resource (for example, a connection pool).

# 12) How permits are maintained in Semaphore:
- In a semaphore, a permit is represented internally as a non-negative counter. When a semaphore is created, it is initialized with a fixed number of permits. At any given time, only that many threads are allowed to enter the protected section.
- When a thread calls `acquire()`, the semaphore checks the counter. If the counter is greater than zero, it is decremented and the thread is allowed to proceed. If the counter is zero, the thread is blocked until a permit becomes available.
- When a thread calls `release()`, the counter is incremented and one of the waiting threads can acquire the permit and continue execution. The semaphore itself manages this counter internally—there is no separate permit object.
- An important point is that semaphores do not enforce ownership. A thread that releases a permit does not have to be the same thread that acquired it. This makes semaphores suitable for coordination patterns, but it must be used carefully, otherwise permits can be released incorrectly which can lead to logical errors.

# 13) Classic Producer-Consumer problem using Semaphore:
- In the classical producer–consumer problem using semaphores, we model producers and consumers as separate tasks executed by multiple threads.
- We use a `ConcurrentLinkedDeque` as the shared store so that multiple producer and consumer threads can safely add and remove elements concurrently without corrupting the data structure.
- Let the store capacity be **n**, and let the current number of items be **k**.
- At any point in time, at most **(n − k) producer threads** are allowed to produce, and at most **'k' consumer threads** are allowed to consume.
- This is implemented using two semaphores: one producerSemaphore represents the number of empty slots and the other producerSemaphore represents the number of available items.
- A producer thread must acquire an empty-slot producerSemaphore permit before producing, and after producing, it releases a consumerSemaphore item permit so that consumers can proceed.
- Similarly, a consumer must acquire a consumerSemaphore item permit before consuming, and after consuming, it releases an empty-slot producerSemaphore permit so that producers can proceed.
- Because the store is a concurrent data structure, we do not need a separate mutex for the queue itself.
- Semaphores are used only for coordination and capacity control, which allows multiple producers and consumers to operate in parallel while still respecting the store limits.

# 14) Thread Interruption:
- When a thread tries to acquire a semaphore permit and none are available, it blocks.
- To support cancellation and responsiveness, `Semaphore.acquire()` is **interruptible**.
- If a blocked thread is interrupted, it immediately stops waiting and an `InterruptedException` is thrown.
- This allows higher-level logic to cancel tasks and avoid threads waiting indefinitely.
- It is important to note that interruption itself does **not prevent deadlocks**. It is a cooperative cancellation mechanism that helps improve responsiveness and shutdown handling.
- Also, interruption does not automatically terminate the thread—the thread must handle the `InterruptedException` and decide how to exit.

# 15) Deadlocks:
- A deadlock is a situation in a multithreaded system where two or more threads are blocked forever, each waiting for a resource that is held by another waiting thread.
- For example, if Thread A holds resource R1 and waits for R2, while Thread B holds resource R2 and waits for R1, then neither thread can proceed. This circular waiting creates a deadlock, and the system makes no further progress.
- One practical way to avoid deadlock in Java is to use `tryLock()` from `ReentrantLock`.
- With `tryLock()` (optionally with a timeout), a thread attempts to acquire the lock and, if it fails within the given time, it backs off and releases any other locks it already holds. This avoids circular waiting and reduces the risk of deadlock.
- Another approach is **deadlock detection**. Conceptually, this can be modeled using a resource-allocation graph where threads and resources are nodes, and edges represent requests and allocations. If a cycle is detected in the graph, it indicates a deadlock.
- Once a deadlock is detected, recovery can be done by terminating one of the involved threads or by pre-empting and reallocating resources.
- 


