# Lecture | Backend LLD: Concurrency-3: Synchronization

## CONTENTS:
1. Thread.start() vs Thread.run()
2. Thread.join() method
3. Adder & Subtractor (Synchronization) Problem
4. Critical Section & Properties of a Good Synchronization Solution
5. Solutions to Synchronisation Problem
   - (1) Mutex Lock
   - (2) Synchronized keyword
   -  (3) Synchronized Method

---

## I] Thread.start() vs Thread.run()
In Java, `Thread.start()` and `Thread.run()` serve distinct purposes in the context of multithreading:

### 1.1) **`Thread.start()`:**
- Initiates the execution of a new thread.
- The Java Virtual Machine (JVM) creates a new call stack for this thread.
- The run() method of the Thread object (or the Runnable object it's associated with) is then executed within this newly created thread.
- Calling start() enables concurrent execution, allowing the new thread to run in parallel with the main thread and other active threads.
- start() can only be called once on a Thread object; attempting to call it multiple times will result in an IllegalThreadStateException.

---

### 1.2) **`Thread.run()`:**
- Executes the code within the run() method as a normal method call within the current thread (the thread that invoked run(), say `main` thread).
- No new thread is created, and no multithreading is involved.
- The run() method is simply treated as a regular function, and its execution occurs sequentially within the calling thread's context.
- run() can be called multiple times, just like any other method.

---

### 1.3) **In summary:**
To achieve multithreading and concurrent execution in Java, `Thread.start()` must be used. Directly calling `Thread.run()` will not create a new 
thread and will execute the code sequentially within the calling thread.

---

## III] Thread.join() method:
-   In Java, the `Thread.join()` method is used to control the execution flow of threads, specifically to make one thread wait for the 
    completion of another. When a thread calls join() on another thread, the calling thread will pause its execution until the target
    thread finishes its execution. For ex:
-   When `threadA.join()` is called by `threadB`, `threadB` enters a waiting state. It remains in this state until `threadA` finishes its 
    execution (i.e., its `run()` method completes). Once `threadA` terminates, `threadB` is notified and resumes its execution.

---

## IV] Adder & Subtractor (Synchronization) Problem:
Study it from GitHub notes and Deepak sir's Notepad notes. They are enough. 

Summary: Both `adder` and `subtractor` threads read the `count.val` at the same instant and hence it gives a varied result.
As in, +1-1 = 0;
This should have been the case, but, both read `0` and then, say, adder changed it to `+1` but this subtractor has already read `0`,
so, it changes `count.val` to `-1`. Hence, we get `-1` instead of `0`.

### 4.1) Why Synchronization arises:
Look, in Parallelism, multiple threads are acting at the same instant, on different cores of the CPU. As long as these threads are
acting on different resources, it doesn't bother us. For ex: in last class, we implemented Merge Sort using MultiThreading. In that
case, two threads were acting on different halves of the array. So, the results were certain and not corrupted.
However, in the above Adder-Subtractor problem, two threads are acting, in parallel, i.e. at the same instant, on a shared resource,
which is giving us unpredicted output, i.e. corrupted output.
This is called Synchronization, where Parallelism occurs on shared resource, which corrupts our output.

## V] Critical Section & Properties of a Good Synchronization Solution:
Refer GitHub notes and Deepak sir's Notepad notes.

### 5.1) **Race Condition:**
A race condition occurs when multiple threads access and modify shared data concurrently, leading to unpredictable and 
incorrect results due to the non-deterministic order of thread execution.

### 5.2) **Key Causes**:
1. **Shared Data**: Multiple threads read/write the same resource (e.g., variable, file).
2. **Concurrent Access**: Threads execute simultaneously without synchronization.
3. **Unpredictable Outcome**: Final result depends on thread timing, causing inconsistencies.

### 5.3) **Example**:
- **Bank Account Scenario**:
    - Thread 1 (Deposit) reads balance (Rupees 100) and updates it to "Rupees 150".
    - Thread 2 (Withdraw) *simultaneously* reads the old balance (Rupees 100) and updates it to "Rupees 50".
    - **Result**: The final balance is  "Rupees 50" (incorrect; should be "Rupees 100").

### 5.4) **How to Avoid Race Conditions**:
1. **Synchronization**: Use locks/mutexes to ensure only one thread accesses data at a time.
2. **Immutable Objects**: Use unchangeable data structures to prevent conflicts.
3. **Thread-Local Storage**: Give threads their own data copies when possible.
4. **Atomic Operations**: Use thread-safe operations (e.g., `AtomicInteger` in Java).

### 5.5) **Conclusion**:
Race conditions can corrupt data in multithreaded programs. Proper synchronization and design (e.g., immutability) are critical to ensure correctness.

---

## VI] Solutions to Synchronisation Problem

### 6.1) Mutex Lock:
-   As you can see the code from the GitHub notes(refer it for this section too as well as Deepak sir's notes), there is just one lock.
    Whichever thread gets the lock first can access the critical section of the code. This helps achieve Mutual Exclusion.
-   In the above code, if we put `lock-unlock` outside the for-loop then the entire adderThread will operate first and then the subtractorThread
    will act later or vice versa. So, this will bring in complete *Sequential* operation. i.e. the two threads even not running concurrently.
    Hence, we should always try to keep Critical Section as small as possible. i.e. mutex lock should be on the smallest possible section of the code.

### 6.2) `Synchronized` keyword:

1. The locks kind of `Mutex locks` are language independent. Every language has some sort of the lock.
2. However, the `Synchronized` keyword is specific to java. 
3. Java provides every object with an intrinsic lock (also called a monitor lock). This is the lock used when you apply the 
   synchronized keyword to a method or a block. So, we aren't required to create the lock and manage it i.e. lock it, unlock it.
    ```java
    public void increment() {
        synchronized (count) {
            count++;
            student.update();
        }
    }
    ```
    here, inside the synchronized block, the lock has been applied on the 'count' object. Inside this block, any operation on 'count'
    object is synchronized. As soon as we come out of the block, the lock is released. So, only one thread can now execute on the count object.
    However, if there is another object in the block, say, `student` then multiple thread can still act on the `student` object as we haven't chosen
    to use lock in this `student` object, though it is inside synchronized block. So, to use the lock inside the Synchronized block, we have to also specify
    the object whose lock will be used, in the round bracket after synchronized keyword. The Rest of all other objects will be used as it is,
    even if they are inside the synchronized block.
4. Java's synchronized block allows locking only one object at a time.
    ```java
       synchronized (obj1, obj2) {  // ERROR: Syntax does not support multiple locks
        // Critical section
    }
    ```
   In such cases, we can use Mutex locks.

### 6.3) Synchronized Method:
(1)  The synchronized keyword in Java is a fundamental mechanism for achieving thread synchronization and ensuring data consistency in multithreaded environments.
      Its primary purpose is to control access to shared resources, preventing multiple threads from concurrently modifying or accessing critical sections of code, 
      which could lead to data corruption or race conditions.

(2) Here's how synchronized works:
- **Intrinsic Locks (Monitors):** Every object in Java has an associated intrinsic lock, also known as a monitor. When a thread enters a synchronized block or method, 
        it attempts to acquire the lock of the object on which the synchronization is performed.
- **Exclusive Access:** Only one thread can hold the intrinsic lock of a specific object at a time. If a thread successfully acquires the lock, it gains exclusive access 
        to the synchronized code block or method. Other threads attempting to enter the same synchronized section on the same object will be blocked until the lock is released.
- **Lock Release:** The lock is automatically released when the thread exits the synchronized block or method, either normally or due to an exception.

(3) Usage of `synchronized`:

-  **Synchronized Methods:** Declaring a method as synchronized ensures that only one thread can execute that method on a given object instance at a time. 
  For static synchronized methods, the lock is acquired on the class object.
```java
    public synchronized void methodName() {
        // Critical section of code
    }
   ```
-  **Exclusive Access:**
  
   While one thread holds the object's lock, no other thread can enter any other synchronized method of the same object instance.

-  **Waiting Threads:**
  
   If another thread attempts to invoke a synchronized method on the same object while the lock is held, it will be blocked and wait until the lock becomes available.

-  **Synchronized Blocks:**
  
   synchronized can also be applied to a specific block of code within a method. This allows for more granular control over synchronization, 
  as only a portion of the method is protected. A synchronized block requires an object to synchronize on (the monitor object).
```java
    public void anotherMethod() {
        synchronized (this) { // Synchronizing on the current object
            // Critical section of code
        }
    }

    public void yetAnotherMethod() {
        Object lockObject = new Object();
        synchronized (lockObject) { // Synchronizing on a specific object
            // Critical section of code
        }
    }
```

(4) Benefits of using `synchronized`:

   - **Prevents Race Conditions:** 
  
     Ensures that shared data is accessed and modified by only one thread at a time, preventing inconsistent states.
   - **Ensures Memory Visibility:** 
   
     Guarantees that changes made to shared variables within a synchronized block are visible to other threads once the lock is released.

(5) NOTE: Go through, Deepak sir's notes, they are awesome.
-   When a thread enters a synchronized method (or block), it holds the lock on that object.
-   Other synchronized methods/blocks on the same object are blocked for other threads.
-   Non-synchronized methods can still be called by any thread at any time, even while the lock is held as they do not check for locks → can run concurrently.
-   When implementing synchronization, it's important to keep the critical section as small as possible. This reduces the performance overhead introduced by synchronization, 
    which is inversely proportional to the performance
-   When **Synchronization** is there, **Parallelism** cannot happen at that exact moment. They are opposites.


(6) **Can a `static synchronized` Method and an `instance synchronized` Method Run in Parallel on the Same Object?**
- **✅ Yes!**

(7) **Why?**
- **`static synchronized`** → Locks on the **class object** (e.g., `MyClass.class`).
- **`instance synchronized`** → Locks on the **current object instance** (`this`).
- Since they use **different locks**, they **do not block each other** and can run **concurrently**.

---

(8) **Example**
```java
class Example {
    // Static synchronized method (locks on Example.class)
    public static synchronized void staticSyncMethod() {
        System.out.println("Static synchronized method running...");
    }

    // Instance synchronized method (locks on 'this')
    public synchronized void instanceSyncMethod() {
        System.out.println("Instance synchronized method running...");
    }
}
```

(9) **Scenario:**
1. **Thread A** calls `Example.staticSyncMethod()` → **acquires the `Example.class` lock**.
2. **Thread B** calls `exampleObj.instanceSyncMethod()` → **acquires the `exampleObj` lock**.
3. **Both methods run in parallel** because they lock **different objects**.


(10) **Key Points**
- **Static and instance locks are independent.**
- **No thread contention** occurs between them.
- This is why `ConcurrentHashMap` uses **separate locks for different segments** (similar concept).


(11) **When Would They Block Each Other?**
- If two threads call **the same type of synchronized method**:
    - Two `static synchronized` methods → **block each other** (same class lock).
    - Two `instance synchronized` methods → **block each other** (same object lock).



-  **Answer:** 

   Yes, they can run in parallel because they use **different locks**. Whereas, if two objects of the same class, both call on the (same/different) static synchronized method
then, they will be blocked. they won't run in parallel. 





