# Lecture | Backend LLD: Concurrency-1: Introduction to Processes and Threads

Read GitHub notes from Scalar first.

## CONTENTS
1. Computer Systems and fundamentals
   - Processor 
   - Core
   - Threads
   - Process Control Block (PCB)
   - Operating System (OS)
2. Concurrency vs Parallelism
3. Writing Multi-Threaded code in Java
4. Java Threads Basics
5. From Assignment

---

## I] Computer Systems and fundamentals:

### 1.1) Processor: 
1. **Processor (CPU)** = The "Brain" of the Computer.

   **What it is:** A hardware chip that executes instructions (binary code) to run software.

2. **Key Functions:**

- Fetch-Decode-Execute Cycle: Retrieves, interprets, and processes instructions.

- Manages Hardware: Coordinates RAM, storage, and I/O devices.

3. **Components:**

- Cores (independent processing units).

- Control Unit (CU) (orchestrates operations).

- ALU (performs calculations).

- Cache (fast memory for frequently used data).

--- 

### 1.2) Core

1. **Core** = The "Mini-Processor" Inside the CPU.

   **What it is:** The smallest complete processing unit within a CPU.

2. **Key Features:**

   - Independent Execution: Each core can run its own thread (sequence of instructions).

   - Parallel Processing: Multi-core CPUs handle multiple tasks simultaneously.

3. **Contains:**

   - ALU, registers, L1/L2 cache, and a dedicated control unit.

4. **Why it matters:** 
   
   More cores = better multitasking and performance in parallel workloads (e.g., gaming, video editing).

5. **Processor vs Core:** 
   
   A 'processor' (CPU) is the full computing unit, while a 'core' is its fundamental building block.

6. Each `Process` gets the memory(RAM) allocated. 

7.  Suppose, the speed of the Core is 1 GHz. It means, it can perform 10<sup>9</sup> operations in 1 second of time.
    Say, 1 thread requires 10<sup>6</sup> operations. Still, it will be done in 1 millisecond of time.

---

### 1.3) Threads:

1. Thread is the smallest unit of Processing i.e. of CPU execution (and remember, not of a Processor).
2. `Program` is a **compiled** and **executable** code. It is, say, lying/stored in the computer as an `application`. 
    
    But, a `Program` in execution is called a `Process`. And, `Process` is executed by `Threads`.  
    This depends on how the `program` is written. It can be a **Single-Threaded** application, or it can be a **Multi-Threaded** application.
3. `Thread` gets assigned to the `Core`, and it is the `Core`, which does the actual work.

---

### ** 1.4) Process Control Block (PCB)**

#### **(1) Where Programs Live**
- Programs are stored on the **disk** (like your hard drive, SSD). They are the `executable` file like `.exe`, `.apk`, etc.
- When you **run a program**, the Operating System loads its **code and data into RAM** (memory). Then, it becomes a `Process`.
  (As CPU cannot talk to the hard disk or SSD directly because of huge speed difference between them and, 
  CPU and RAM operate at comparable speed hence RAM is preferred). 

#### **(2) What Happens When a Program Runs?**
- The OS creates a **Process Control Block (PCB)**—a small data structure that tracks the process. 
  The Process Control Block (PCB) is stored within the operating system (OS).
- The **PCB does NOT store the actual code/data**—it just **points** to where they are in RAM.

#### **(3) What’s Inside the PCB?**
- **Process ID (PID)** → Unique number identifying the process.
- **Process State** → Is it running, waiting, or ready?
- **Saved CPU Registers** → When the OS switches processes, it saves the current state (like a bookmark).
- **Memory Pointers** → Tells where the process’s code, data, and stack are in RAM.
- **File & I/O Info** → Keeps track of open files, devices, etc.
- Threads are not stored in PCB. 

#### **(4) Threads vs. Processes**
- A **process can have multiple threads**.
- **Each thread has:**
    - Its own **stack** (local variables).
    - Its own **program counter** (keeps track of where it is in the code).
- **All threads of the same Process share:**
    - The **same code, data, and resources** (files, memory) from the parent process.

#### **(5) Context Switching (How the OS Juggles Processes/Threads)**
- The OS **pauses** one thread/process, **saves its state in the PCB**, and **switches to another**.
- Later, it can **restore the saved state** and continue where it left off.
-  Multicore CPUs reduce context switching by running threads in parallel across cores, while single-core CPUs must constantly 
   switch between tasks. Fewer context switches mean less overhead (saving/reloading states), so more time is spent executing 
   tasks. Even at the same clock speed, a multicore CPU completes workloads faster because threads run simultaneously without 
   interruptions. For example, 4 threads on 4 cores finish with zero switching, while a single core wastes cycles switching. 
   **Less switching = higher efficiency = better performance.**

#### **(6) Key Takeaways**
✅ **PCB = Process’s ID Card** → Tracks info about the process, but **not the actual code/data**.  
✅ **Threads = Workers in a Process** → Each has its own stack and program counter but shares the process’s resources.  
✅ **Context Switching = Task Swapping** → The OS saves/loads states using the PCB to multitask efficiently.

#### **(7) Some Extra Points**
1. ✅ A centralized OS scheduler manages both processes and threads, but threads are the entities that the CPU cores execute.
2.  In multi-core CPUs,
    - ✅ the scheduler still assigns threads (not processes) to each core, mixing threads from different processes freely.
    - ✅ True parallelism happens because multiple threads run simultaneously on different cores.
    - ✅ Threads from the same process can run on different cores, sharing memory but executing independently.

---

### 1.5) Operating System (OS):
There are thousands of operations being performed in the system. But, CPU has only around 16 to 32 cores, at max. So, at any instant of
time, it can run only 16 or 32 operations in parallel. But, these operations are switched so fast that we feel that these thousands of operations
are being performed simultaneously. So, this is job of `OS` that how fast it switches among the operations. The faster it does, the
faster is the processing power of the OS.

---

## II] Concurrency vs Parallelism:

#### **2.1) Concurrency**
- **"Doing multiple tasks alternately."** It uses **Context Switching**.
- Tasks **appear** to run at the same time, but the CPU switches between them rapidly (e.g., time-slicing on a single core).
- **Example:** A single-core CPU running a browser and music player **by switching** between them.
- At any given instant of time, only one Thread is being executed remaining threads are paused and waiting.

#### **2.2) Parallelism**
- **"Doing multiple tasks simultaneously."**
- Tasks **actually** run at the same time using multiple cores/processors.
- **Example:** A 4-core CPU running 4 threads **truly in parallel** at the same instant of time.

---

## III] Writing Multi-Threaded code in Java:

1. Always prefer implementing runnable interface over extending the Thread class. 
2. The scaler companion and GitHub explanation is good enough.
3. Java, by default is Single-threaded, which is `main` thread. Java creates it by default. As we require at least one thread to 
   run the process.
4. `Thread.currentThread().getName()`; it is used to get the name of the current executing thread. By default, Threads are numbered from 0, ex: `Thread-0`.
5. `Thread` is a class in `java`.

---

## IV] Java Threads Basics

### 4.1) Thread Implementation:
- **Implementing Runnable Interface:** To create a `thread`, a `class` should implement the <<`Runnable`>> interface. This mandates the 
  implementation of the `run()` method, where the code for the task to be executed in a separate `thread` resides .

- **Starting a Thread:** After creating a `thread` object, the `start()` method on that `thread` object is called to initiate the `thread`. The `start()` method, 
  unlike `run()`, creates a new thread of execution.

- Example Code:
```java
class HelloWorldPrinter implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello World from " + Thread.currentThread().getName());
    }
}

// In Main or any other class
public class Main {
    public static void main(String[] args) {
        HelloWorldPrinter task = new HelloWorldPrinter();
        Thread thread = new Thread(task);
        thread.start(); // This will start the new thread
    }
}
```

### 4.2) Multi-Threading in Practice
-   **Thread Execution Order:**
    When threads are created and started, the execution order is not guaranteed (Threads can get executed in any order), displaying 
    the concurrent nature where the CPU scheduler decides which thread runs at any particular time .

---

## V] From Assignment

1. Each thread or process that runs in parallel needs its own program counter to track its execution. So, multiple program 
   counters are required when you have multiple threads/processes running in parallel.
2. Multithreading can occur even on a single-core CPU using time-slicing (context switching). Having multiple cores improves 
   the performance of multithreading but is not a requirement.

---

Read about Thread interruption and related exception. It is must.


