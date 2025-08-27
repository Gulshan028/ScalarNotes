# Lecture | Backend LLD: Concurrency-2: Executors and Callables

## CONTENTS:
1. Revision from Last Class
2. Thread Pool

---

## CONTENTS: Deepak sir's class
1. Executor Framework & ThreadPool class
2. MultiThreading using ExecutorService and ThreadPool
3. Fixed ThreadPool vs Cached ThreadPool
4. Callable Interface
5. Links

---

## I] Revision from Last Class:

1. **Clear Naming Conventions:**
    - Ensure that class and interface names clearly describe their purpose.
    - **Example:** A class that calculates the sum of the first `n` natural numbers should be named `SumCalculator.java`.

2. **Threads and Task Objects:**
    - In the `NumberPrinter` example, we created **100 `NumberPrinter` objects** (each representing a task) and assigned them to **100 separate Threads**.
    - **Key Learning:** A **Thread** requires a **task object** (like `NumberPrinter`) that defines the work to be executed.

---

## II] Thread Pool:
### 2.1) Why we require Thread Pool:
1. **Definition:** A thread pool in Java is a managed collection of pre-created, reusable threads designed to execute a set of tasks 
  efficiently. Instead of creating a new thread for every task, tasks are submitted to the thread pool, and available threads from
  the pool execute them. After completing a task, the thread returns to the pool, ready to process another.
2. **Other Arguments supporting Thread Pool:**
   - Creating a thread manually (using `new Thread(...)`) involves allocating memory and system (OS) resources.
   - Threads in a pool are already in a `running` or `waiting` state. So task assignment is faster than creating and starting a new thread.
3. **Conclusion:** 
   Thread pools are preferred over manual thread creation because they **reuse existing threads**, reducing the overhead of 
  creating and destroying threads repeatedly. They offer **better resource management**, **scalability**, and control over concurrency
  by limiting the number of active threads and queuing tasks efficiently. This makes them ideal for high-performance and 
  long-running applications.

---

# Deepak sir's class:

## I] Executor Framework & ThreadPool class:
1) Consider, we are building Backend server of Amazon. Say, it gets 1 million requests at the same time. So, we cannot create 
   1 million `Threads` for each request. Because, each `Thread` is an object of `Thread` class, which occupies space 
   (default stack size is 1MB per thread in JVM). So, these millions of threads will take huge space in the memory, hence it is not feasible.
2) Also, say, somehow, we created these huge number of threads, but, since, these many threads will compete for a very limited number
   of cores (16 for 16-core CPU) since, only 16 operations/request can be processed in parallel. So, this will lead to a lot of 
   `Context Switching`, which slows down the system. Hence, creating these many threads is not a good idea.
3) In last class, we have created threads manually. We know that it is a very lengthy process. Also, managing it is other worry,
   as with manual threads, the more tasks you run, the more threads you create, which can lead to:
   1. Context switching overhead as discussed above;
   2. System crash due to `OutOfMemoryError`; 
   So, the program shouldn't crash. Thread pools limit the number of concurrent threads, providing better control and preventing resource exhaustion.
4) Here comes `Executor` Framework, which handles the thread creation and management. So that, we developers can focus on our 
   business logic.
5) So, ThreadPool has a number of threads. Now, consider these threads as a production line in car manufacturing. Each thread will
   take up a task. If number of tasks are more compared to available threads in `fixedThreadPool` then `ExecutorService` has 
   `waiting queue`, where task will be waiting for a Thread to get available. As soon as any thread gets free, then it will be assigned
    the next task in the queue (faster than new Thread()). This is how the `ExecutorService` works.
6) So, what change did this `ExecutorService` pushed:
   - Look, we didn't create millions of threads at the same time. Existing threads were reused.  
   - At any time, fewer threads were in waiting period i.e. live, so less context switching happened. Rather than, tasks were in waiting queue.
   These led to efficient processing.
7) Consider ThreadPool as equivalent to a factory of car manufacturing. So, you can understand that we don't create a new factory
   every now and then. Rather, we look to use the factory as much as we can. This way, we avoid more ThreadPool.

---

## II] MultiThreading using ExecutorService and ThreadPool:
Consider code:
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for(int i=0; i<100;i++){
            executor.execute(new NumberPrinter(i));
        }
        executor.shutdown();
    }
}
```

### 2.1) Explanation of the ExecutorDemo Code:

#### (1) Import Statements
```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
```
- These import the necessary classes from Java's concurrency package:
    - `ExecutorService`: An interface that represents an asynchronous execution mechanism
    - `Executors`: A factory class for creating different types of `ExecutorService` implementations

#### (2) Creating a Thread Pool
```java
ExecutorService executor = Executors.newFixedThreadPool(10);
```
- Creates a thread pool with 10 worker threads using `Executors.newFixedThreadPool()`. This single line has created 10 threads 
  and will also manage them. Say, be it, assigning tasks to the threads, keeping tasks in queue, etc. 
  Now, imagine how much task it has saved compared to doing it manually.
- The `ExecutorService` will maintain a pool of 10 threads to execute tasks.
- Tasks submitted will be executed by one of these 10 threads

#### (3) Submitting Tasks
```java
for(int i=0; i<100;i++){
    executor.execute(new NumberPrinter(i));
}
```
- A loop that runs 100 times (from 0 to 99)
- Each iteration:
    - Creates a new `NumberPrinter` task (which is a `Runnable` task) with the current loop index, i
    - Submits the task to the executor using `execute()`
- Even though we're submitting 100 tasks, only 10 will run concurrently (due to our thread pool size) and remaining will be waiting in the `waiting queue`.

#### (4) Shutting Down the Executor
```java
executor.shutdown();
```
- Initiates an orderly shutdown of the ExecutorService
- The executor will:
    - Stop accepting new tasks
    - Continue executing already submitted tasks
    - Eventually terminate all threads after all tasks are complete

Note: The code assumes there's a `NumberPrinter` class that implements `Runnable`, which would contain the actual work to be performed by each thread.

---

### 2.2) What is `ExecutorService`:
The Java `ExecutorService` is a key interface within the `java.util.concurrent` package, providing a robust framework for managing 
and executing tasks asynchronously using a pool of threads. It abstracts away the complexities of manual thread creation and
management, promoting efficient resource utilization and simplified concurrent programming.

#### (1) Key aspects of ExecutorService:

##### 1. Thread Pool Management:
   ExecutorService maintains a pool of reusable threads to execute submitted tasks.

##### 2. Task Queuing:
When the number of submitted tasks exceeds the available threads in the pool, `ExecutorService` manages a queue to hold pending 
tasks, ensuring they are processed once a thread becomes available.

##### 3. Asynchronous Execution:
Tasks submitted to an `ExecutorService` are executed asynchronously, allowing the submitting thread to continue its operations 
without waiting for the task to complete.

##### 4. Lifecycle Management:
It provides methods for managing the lifecycle of the executor itself, including `shutdown()` (allows submitted tasks to complete 
before termination) and `shutdownNow()` (attempts to stop currently executing tasks and prevents waiting tasks from starting).

---

### 2.3) What is `Executors` class:

#### (1) Definition: 
The Executors utility class provides convenient factory methods for creating various types of ExecutorService instances, 
such as newFixedThreadPool (fixed number of threads), newCachedThreadPool (dynamically adjusts thread count), and 
newSingleThreadExecutor (a single worker thread), etc.

#### (2) Task Submission:
Tasks can be submitted as `Runnable` (for tasks that don't return a result) or `Callable` (for tasks that return a result and can 
throw exceptions). The `submit()` method returns a Future object, which represents the result of an asynchronous computation and 
can be used to check task status, retrieve results, or cancel the task.

---

### 2.4) **ExecutorService & Executors - Simplified Explanation**

#### **(1) ExecutorService Interface**
##### 1. What It Is
- A **high-level thread management interface** in Java (`java.util.concurrent`).
- Represents an **asynchronous task execution service** (like a "thread pool manager").

##### 2. Key Responsibilities
✔ **Manages threads** (reuses them instead of creating new ones for each task).  
✔ **Accepts tasks** (`Runnable` or `Callable`) and executes them.  
✔ **Provides lifecycle control** (start, shutdown, force termination).  
✔ **Returns results** via `Future` objects for tracking progress.

##### 3. Key Methods
| Method | Purpose                                          |
|--------|--------------------------------------------------|
| `void execute(Runnable task)` | Submits a task (fire-and-forget).                |
| `submit(Runnable/Callable)` | Submits a task and returns a `Future`.           |
| `void shutdown()` | Gracefully stops accepting new tasks.            |
| `List<Runnable> shutdownNow()` | Forcefully stops all running tasks.              |
| `invokeAll()` | Runs multiple tasks and waits for all to finish. |
| `Future<?> submit(Runnable task)` | Submits a task and Returns Future for tracking.  |
| `Future<T> submit(Callable<T> task)` | Submits a task and Returns result via Future.  |

---

#### **(2) Executors Class**
##### **1. What It Is**
- A **factory utility class** to create `ExecutorService` instances.
- Provides **predefined thread pool configurations** (no need to configure manually).

##### **2. Why Use It?**
- Simplifies thread pool creation with **one-line methods**.
- Avoids complex `ThreadPoolExecutor` setup.

##### **3. Common Factory Methods**
| Method | Behavior | Best For |
|--------|----------|----------|
| `newFixedThreadPool(n)` | Fixed number of threads. | Heavy workloads (stable thread count). |
| `newCachedThreadPool()` | Dynamically grows/shrinks. | Short-lived tasks (e.g., HTTP requests). |
| `newSingleThreadExecutor()` | Single thread (sequential). | Tasks requiring order (like a queue). |
| `newScheduledThreadPool(n)` | Delayed/periodic tasks. | Timers, recurring jobs. |

##### **4. Under the Hood**
- `Executors.newFixedThreadPool(4)` actually creates a **`ThreadPoolExecutor`** with:
    - 4 core threads.
    - Unbounded task queue (`LinkedBlockingQueue`).
    - Default rejection policy (`AbortPolicy`).

---

#### **(3) Key Differences**
| Feature | `ExecutorService` | `Executors` |
|---------|------------------|-------------|
| **Type** | Interface | Factory class |
| **Purpose** | Manages threads/tasks | Creates thread pools |
| **Customization** | Configured via impls (e.g., `ThreadPoolExecutor`) | Provides ready-made pools |

---

#### **(4) Best Practices**
✔ **Always shut down `ExecutorService`** (prevents thread leaks; use `shutdown()` + `awaitTermination()`).  
✔ **Prefer `newFixedThreadPool` for production** (avoids uncontrolled thread growth).  
✔ **Use submit() for result handling**
    - Prefer over `execute()` when you need task results  
✔ **For scheduled tasks**, use `newScheduledThreadPool`.

✔ **Handle rejected tasks**
    :- Default policy throws exception (override for custom behavior)

---

#### **(5) Study Cheat Sheet**
##### **1. ExecutorService**
- Interface for thread pools.
- Key methods: `execute()`, `submit()`, `shutdown()`.
- Uses `Future` to track results.

##### **2. Executors**
- Factory for `ExecutorService`.
- Common pools: `Fixed`, `Cached`, `SingleThread`, `Scheduled`.
- Simplifies thread pool creation.

##### **3. Remember**
- **`Executors` creates, `ExecutorService` manages**.
- **Shutdown pools!**

---

#### **(6) How They Work Together**
##### **1. Standard Workflow**
1. **Create pool** using `Executors`
2. **Submit tasks** to `ExecutorService`
3. **Manage lifecycle** (start → shutdown)
4. **Handle results** via `Future` objects

##### **2. Complete Example**
```java
ExecutorService executor = Executors.newFixedThreadPool(3);

// Submit tasks
executor.execute(() -> System.out.println("Task 1"));
Future<String> future = executor.submit(() -> "Task 2 Result");

// Get result (blocks until complete)
String result = future.get(); 

// Shutdown (MANDATORY)
executor.shutdown(); 
```

> 📌 **Key Insight**: `Executors` creates → `ExecutorService` manages → Threads execute

---

## III] Fixed ThreadPool vs Cached ThreadPool:
In FixedThreadPool, there are fixed number of threads. If the number of assigned tasks is more than the number of available threads, 
then the tasks keep waiting in the *task queue*.
Whereas in CachedThreadPool, there are no fixed number of threads. If there are no available threads when the task is assigned
then the new thread is created. 

---

## IV] Callable Interface:
We've used `Runnable` while creating the task. It has a `run()` method, which is executed when the thread starts. The return type
of the `run()` method is `void`. So, a task given to a thread cannot return anything using `Runnable` interface. 
To solve this purpose, we have `CALLABLE<V>` interface, which has a `call()` method. This method can return something and has a return datatype <V>.
Let's study it using the 'Merge Sort Implementation'.

---

### 4.1) Merge Sort Implementation:
In MergeSort, we recursively divide the given array into two parts, and while returning back, we sort them and merge them back.
Now, when we are sorting the parts of the array, then sorting the two parts is completely independent of eachother, so, we can
give this to two different threads. This will help us improve the speed of the task using Multithreading. 
Now, each thread will return the sorted part of the array, it means that our thread is returning something. This shows that we 
cannot use `Runnable` interface here as the return type of the `run()` method is `void`. 
Here comes the use of the `Callable<V> Interface` which has a `call()` method as it can return a particular datatype.

Note:
follow the code blocks for mergeSort implementation using Multiple Threads.

---

## V] Links:
1) Deepak Sir Github notes: https://github.com/deepakkasera/LLDNov23/
2) anubhav sir: Github notes: https://github.com/ak-s-0723/ThreadRepo/commit/75b13ebcae6211475d3c3ab464362d2e1790e966
   first read, fibonaci using fixed thread pool and then its main.
   in second read, go for fibonacci using Cached thread pool and then its main

---

## VI] Executor interface:
In the context of concurrent programming in Java (and similar frameworks in other 
languages), the **Executor** is a high-level interface or framework that abstracts 
and manages the assignment of tasks to threads within a thread pool.

### 6.1) What is an Executor?
The `Executor` interface (and its sub-interfaces like `ExecutorService`) is part 
of the `java.util.concurrent` package. It provides a way to decouple the submission 
of tasks from the mechanics of how each task will be run (including details like 
thread creation, scheduling, and resource management).

---

### 6.2) Key Responsibilities of an Executor:
1. **Task Assignment**: You submit tasks (as `Runnable` or `Callable` objects) to 
    the Executor, and it assigns them to threads for execution.
2. **Thread Pool Management**: Instead of creating a new thread for every task 
   (which is expensive), the Executor typically uses a pool of reusable threads. 
   This improves performance and resource utilization.
3. **Task Scheduling**: The Executor handles when and how tasks are executed 
   (e.g., immediately, after a delay, or periodically).
4. **Resource Control**: It can limit the number of concurrent threads, queue tasks 
   when all threads are busy, and reject tasks if the system is overloaded.

---

### 6.3) Why Use an Executor?
- **Simplifies Code**: You don’t need to manually create and manage threads.
- **Improves Performance**: Thread pools reduce the overhead of thread creation.
- **Provides Control**: You can configure policies for task handling (e.g., queue size, rejection policies).
- **Promotes Best Practices**: Encourages structured concurrent programming.

In summary, the **Executor** is a framework that manages the assignment of tasks to 
threads (typically in a pool), abstracting away low-level thread management details.

---

## VII] `Executor` -> `ExecutorService` -> `Executors`

It's a chain of increasing complexity and capability.

### 7.1) The "Executor" Family Tree: A Clear Hierarchy

| Component                                         | Type | Role & Analogy                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
|:--------------------------------------------------| :--- |:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **`Executor` (The Basic Contract)**               | **Interface** | **Defines the *what*, not the *how*.** <br> Its single method `execute(Runnable task)` is a promise: "Give me a task, and I will decide how to run it." The *implementation* of this method (provided by `ExecutorService`/`ThreadPoolExecutor`) contains the actual decision-making logic. It's the root of the family.                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| **`ExecutorService` (The Full-Featured Manager)** | **Interface** | **Provides the sophisticated policies.** <br> *Extends* `Executor`, meaning it *must* fulfill the `execute` contract. It adds management features: shutting down, tracking tasks with `Future`s, submitting `Callable` tasks, and running multiple tasks at once. This is what you actually use. <br/>It uses advanced strategies like: <br> - **Thread Pooling:** Decides to reuse threads instead of creating new ones. <br> - **Task Queuing:** Decides what to do when all threads are busy (e.g., put tasks in a queue). <br> - **Resource Management:** Decides to reject tasks if the system is overloaded.                                                                                                                                                     |
| **`Executors` (The Factory)**                     | **Utility Class** | **Pre-packages common decisions for you.** <br> Its *only job* is to create ready-to-use instances of `ExecutorService` (e.g., fixed pools, cached pools). You never instantiate an `ExecutorService` directly; you use `Executors.newFixedThreadPool(10)`. <br> This is the key! You don't have to configure the "how" manually. You tell the factory the *type* of execution policy you want, and it gives you an `ExecutorService` pre-configured to make those decisions. <br> - `newFixedThreadPool(5)` -> decides to use exactly 5 threads and an unbounded queue. <br> - `newCachedThreadPool()` -> decides to create new threads as needed and kill idle ones. <br> - `newSingleThreadExecutor()` -> decides to run all tasks sequentially on one thread. |
| **`ThreadPoolExecutor` (The Actual Engine)**      | **Concrete Class** | **Is where the decisions are actually executed.** <br> This is the most common *implementation* of the `ExecutorService` interface. When you call `Executors.newFixedThreadPool(10)`, it returns a `ThreadPoolExecutor` configured in a specific way. <br/> Its code contains the actual algorithms for: <br> - Checking if a core thread is available. <br> - Adding a task to the queue if not. <br> - Creating a new thread if the queue is full (depending on configuration). <br> - Rejecting the task if the system is maxed out.                                                                                                                                                                                                                           |

---

### 7.2) How They Work Together: The Complete Picture

1.  **You want a thread pool.** You go to the **Factory (`Executors`)**.
2.  The factory gives you a **Manager (`ExecutorService`)**.
    ```java
    // The Factory (Executors) creates a Manager (ExecutorService)
    ExecutorService threadPoolManager = Executors.newFixedThreadPool(4);
    ```
3.  This Manager implements the **Basic Contract (`Executor`)**. This means it 
    *must* have an `execute()` method, but it also has many more powerful methods 
    like `submit()`, `shutdown()`, etc.
4.  You give tasks to the **Manager**.
    ```java
    // Using the basic execute() method (from the Executor interface)
    threadPoolManager.execute(() -> System.out.println("Hello with execute"));

    // Using the more powerful submit() method (from the ExecutorService interface)
    Future<String> future = threadPoolManager.submit(() -> {
        Thread.sleep(1000);
        return "Result from submit";
    });
    ```
5.  The Manager handles the rest, using its **Engine (`ThreadPoolExecutor`)** 
    internally to assign your tasks to threads.

### 7.3) Why the Simpler `Executor` Interface Exists

You might ask: "If `ExecutorService` is more powerful, why does `Executor` exist?"

It's a design principle called **Interface Segregation**. It allows for flexibility:

*   **`Executor`**: For very simple, fire-and-forget scenarios. If a component only
    needs to `execute()` tasks and doesn't care about the result or shutdown, 
    it can depend only on the simple `Executor` interface. This makes the code more 
    modular and easier to test.
*   **`ExecutorService`**: For all advanced use cases where you need full control 
    and results.

### Summary to Resolve Your Confusion

| | Your Previous Notes (Correct!) | The Missing Piece (Also Correct!) |
| :--- | :--- | :--- |
| ****ExecutorService**`** | The high-level **manager** interface with all the features. | **Extends** the simpler `Executor` interface. |
| ****Executors**`** | The **factory** class that creates `ExecutorService` instances. | Creates objects that are **both** `ExecutorService` *and* `Executor`. |
| **Executor** | *Was not mentioned in your notes.* | The **simple, parent interface** that defines the core `execute()` method. It's the foundation that `ExecutorService` builds upon. |

**Your notes are perfect for what you need to know 99% of the time.** 
You interact with `Executors` (the factory) to get an `ExecutorService` (the manager). 
The fact that `ExecutorService` implements `Executor` is an internal implementation 
detail that you don't usually need to worry about when writing application code.

---

## VIII) Assignment Q.2
The responsibility of the Executor is to decide how to run different tasks on 
different threads. 

Explain this:

### 8.1) Answer
Yes, that statement is **absolutely correct and perfectly encapsulates the core responsibility of the `Executor` framework.**

### 8.2) The Core Responsibility: Deciding "How to Run"

The fundamental job is to **decide the execution policy**: Should each task get a new thread? Should tasks wait in a queue? How many threads can run at once? The `Executor` family abstracts this decision away from you.

---

### 8.3) Concrete Example: Connecting the Dots

Let's trace a task through the system to see the "decision" in action.

**Scenario:** You want a fixed pool of 2 threads.

**1. You go to the Factory (`Executors`):**
```java
// You are deciding on the *policy*: "I want a fixed-size pool."
// The Factory's job is to implement that decision.
ExecutorService executor = Executors.newFixedThreadPool(2);
```
*   **Decision Made:** The factory creates a `ThreadPoolExecutor` configured to have exactly 2 core threads and an unbounded `LinkedBlockingQueue`.

**2. You submit tasks to the Manager (`ExecutorService`):**
```java
executor.execute(task1); // Task 1 is assigned to Thread 1
executor.execute(task2); // Task 2 is assigned to Thread 2
executor.execute(task3); // Task 3 arrives... what happens?
```

**3. The Engine (`ThreadPoolExecutor`) makes the runtime decision for task3:**
*   It checks: Are any of the 2 threads free? No.
*   It checks: Is the task queue full? (The unbounded queue is never full).
*   **Its decision:** Place `task3` into the waiting queue. It will be run as soon as either Thread 1 or Thread 2 finishes its current task.

**4. You submit 1000 more tasks.**
*   The `ThreadPoolExecutor`'s decision for all of them is the same: "All threads are busy, so queue them up." The queue just gets longer.

**5. You call `shutdown()`.**
*   The `ExecutorService` now makes a new set of decisions: "Stop accepting new tasks, but finish all the ones in the queue."

### 8.4) Conclusion

Your statement, **"The responsibility of the Executor is to decide how to run different tasks on different threads,"** is the golden thread that ties everything together.

*   **You** define the *type* of decision-making policy you want by choosing a factory method.
*   The **`Executors`** factory implements that policy by building a configured `ExecutorService`.
*   The **`ExecutorService`** (and its engine, `ThreadPoolExecutor`) carries out that policy for every single task you submit, making millions of micro-decisions about threading, queuing, and scheduling.

This separation of concerns—where you specify *what* you want to achieve and the framework handles *how* to achieve it—is the entire power and purpose of the `Executor` framework. Your initial understanding was spot on.

---