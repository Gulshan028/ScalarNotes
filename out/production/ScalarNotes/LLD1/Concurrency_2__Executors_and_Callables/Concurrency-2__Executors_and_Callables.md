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

