# Lecture | Backend LLD: OOP-2: Access Modifiers and Constructors

## CONTENTS: Deepak Sir
1. Encapsulation
   - Access Modifier
   - Constructors
     - Manual/Parameterised Constructor
     - Constructor Overloading
     - Constructor Signature

---

## CONTENTS: Sandeep Sir
1. Note
2. Short breakdown of JDK, JVM, and JRE
3. More on Constructors
4. Copy Constructor
5. Pass by Value vs Pass by reference in Java
6. Destructor

---

## I] Encapsulation :- 
   Encapsulation is achieved via 2 ways viz. 
   1. `class` to combine attributes(also called *Data Binding*) and 
   2. `access modifiers` to give restricted access.

---

### 1.1) Access Modifier

#### 1) Default

-    It is also called as the `Package` class access modifier, as it allows access within the same package and restricts access outside the package.

---

### 1.2) Constructors

#### 1) Manual/Parameterised Constructor:
-    Look, with default constructor, we had to set the values of all the attributes. So, if a class has 4 attributes then for creating 100 objects, we need to write 400 
     extra lines. We can avoid this with the help of `Parameterised Constructor`,since for each object, we just require one line of code, since, we pass values of the 
     attributes  as the arguments in the **Parametrised Constructor**.
-    e.g.
```java
public class Student {
   private String name;
   private int age;

   public Student(String name, int age) {
      this.name = name;
      this.age = age;
   }
}
```
---

#### 2) Constructor Overloading:

-    Constructor overloading means defining multiple constructors in a `class` with different Constructor signatures.

-    **Key Points:** Same name as the class but different parameter lists (types, order, or count).

-    **Purpose:** Provide different ways to initialize objects.

-    The class cannot have more than one constructor with the same **Constructor Signature**.

-    e.g. `Student(String, int)` and `Student(int, String)` are 2 different **Constructor Signature** as the order of arguments is different.

-    In a `class`, we can have any number of **Constructors** provided they are having different **Constructor Signature**.

---

##### (2.1) Constructor Signature

-    It is the parameter list (types and order) of a constructor. It is like Method Signature.
-    This helps the **Compiler** differentiate the different **Constructors**.
-    for ex: the **Constructor Signature** for above Student parameterised constructor is, 
      `Student(String, int)`
-    Key Rules:
     - **For Overloading:** Constructors must have different parameter lists (i.e. different types, order or count)
     - e.g. 
```java
class Rectangle {
   Rectangle() { ... }                   // Signature: Rectangle()
   Rectangle(int w, int h) { ... }       // Signature: Rectangle(int, int)
   Rectangle(Point p, int w, int h) { ... } // Signature: Rectangle(Point, int, int)
}
```

---

# Sandeep Sir's class

## I] Note:
-   There should be no error in any part of the code in an application else intellij idea gives error. As, entire application code is compiled before running the code.

## II] Here’s a short breakdown of JDK, JVM, and JRE:

### 2.1) JVM (Java Virtual Machine)
-   **What?** A virtual machine that executes Java bytecode.

-   **Role:** Provides platform independence ("Write Once, Run Anywhere").

### 2.2) JRE (Java Runtime Environment)
-   **What?** A package to run Java programs (end-users need this).

-   **Includes:**

    - JVM (to execute bytecode).

    - Libraries (e.g., java.lang, java.util) needed at runtime.

-   **Excludes:** Compiler or development tools.

### 2.3) JDK (Java Development Kit)
-   **What?** A developer toolkit to write, compile, and debug Java code.

-   **Includes:**

    - JRE (to run programs).

    - Compiler (javac), debugger (jdb), and other tools (javadoc, jar).

### 2.4) Key Difference
-    JDK = JRE + Development Tools

-    JRE = JVM + Runtime Libraries

-    Developers need JDK, while end-users only need JRE.

---

## III] More on Constructors

3.1) Yes! You can absolutely write extra instructions inside a constructor, such as:

- Print statements (e.g., logging initialization).

- Calculations or validations.

- Method calls (e.g., initializing helper objects).

- Conditional logic (e.g., parameter checks).

3.2) Example: Constructor with Extra Instructions
```java
public class Student {
    private String name;
    private int age;

    // Constructor with extra logic
    public Student(String name, int age) {
        // 1. Validate parameters
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative!");
        }

        // 2. Assign fields
        this.name = name;
        this.age = age;

        // 3. Print a log message
        System.out.println("Student created: " + name + ", Age: " + age);

        // 4. Call a helper method
        initializeStudentId(); 
    }

    private void initializeStudentId() {
        System.out.println("Generating student ID...");
        // Logic to generate ID...
    }
}
```
3.3) Key Points
- Constructors are like methods — they can contain any valid Java code.

- Order matters: Field assignments usually come first, but logic can be added anywhere.

3.4) Common uses:

- Input validation (throw exceptions for invalid data).

- Logging (debugging/tracking object creation). //don't worry, if you don't understand this point as of now.

- Complex initialization (e.g., starting background threads). //don't worry, if you don't understand this point as of now.

3.5) When to Avoid Extra Logic //don't worry, if you don't understand these points as of now.
- Keep constructors simple if possible (avoid heavy computations/I/O).

- For complex setups, use factory methods or builder patterns.

- Constructors are your object’s "setup crew"—use them wisely! 🛠️

---

## IV] Copy Constructor

   Here,defining the attributes is necessary, i.e. `this.name = obj.name;` , inside Constructor body.

---

## V] Pass by Value vs Pass by reference in Java


### **Java is Strictly "Pass by Value"**
Java **does not** support "pass by reference." Instead, it always uses **"pass by value"**, but the behavior differs depending on whether the argument is a **primitive type** or an **object reference**.

### 5.1) **Pass by Value (Primitive Types)**
When you pass a **primitive type** (e.g., `int`, `double`, `char`) to a method, a **copy of the value** is passed. Changes inside the method **do not** affect the original variable.

**Example:**
```java
public class Main {
    public static void modifyValue(int x) {
        x = 20; // This change is local to the method
    }

    public static void main(String[] args) {
        int num = 10;
        modifyValue(num);
        System.out.println(num); // Output: 10 (unchanged)
    }
}
```
- The original `num` remains `10` because `x` is a copy.

### 5.2) **Pass by Value (Object References)**
When you pass an **object** (e.g., `String`, `ArrayList`, custom class instances(i.e. object)), the **value of the reference** (memory address) is passed, not the object itself. This means:
- If you modify the object's state (e.g., change a field), it affects the original object.
- If you reassign the reference inside the method, it does **not** affect the original reference.

**Example 1: Modifying Object State (Affects Original)**
```java
class Person {
    String name;
    Person(String name) { this.name = name; }
}

public class Main {
    public static void changeName(Person p) {
        p.name = "Alice"; // Modifies the original object
    }

    public static void main(String[] args) {
        Person person = new Person("Bob");
        changeName(person);
        System.out.println(person.name); // Output: "Alice"
    }
}
```
- The `Person` object is modified because `p` refers to the same object.

**Example 2: Reassigning Reference (Does Not Affect Original)**
```java
public class Main {
    public static void reassign(Person p) {
        p = new Person("Charlie"); // p now points to a new object
    }

    public static void main(String[] args) {
        Person person = new Person("Bob");
        reassign(person);
        System.out.println(person.name); // Output: "Bob" (unchanged)
    }
}
```
- The original `person` still refers to `"Bob"` because the method's `p` was just a copy of the reference.

### 5.3) **Why Java is Not "Pass by Reference"**
- In true "pass by reference" (like in C++), modifying the parameter inside the function would modify the original variable. Java does **not** allow this.
  refer Deepak Sir's handwritten note of class oops-3 for C++ example.
- Java passes **references by value**, meaning you can modify the object but not the original reference itself.

### 5.4) **Final Note**
- **Primitives:** Copies are passed; changes inside methods are local.
- **Objects:** References (memory addresses) are passed as values; object state can be modified, but reassigning the reference does nothing outside.

This distinction is crucial for understanding Java's behavior in method calls! 🚀

---

## VI] Destructor

- A destructor is a special method (just like Constructor()) in object-oriented programming (OOP) that is automatically invoked when an object is destroyed or 
  deallocated from memory. Its primary purpose is to perform cleanup tasks.
- Java doesn't contain Destructor(). It has a Garbage Collector.

---

NOTE: Read GitHub notes provided by Scalar and also make the game given in that notes.

---

## VII] Assignment:
1. What function should we override in Java for clean-up activities before JVM reclaims the memory assigned to an object?
   (option A):->Final    (option B):-> Finally     (option C):-> Finalize      (option D):-> Destructor
ANSWER:  **Finalize**

Here's why:
- `finalize()` is a method defined in the `Object` class that the garbage collector calls on an object when it determines there are no more references to the object
- You can override this method to perform cleanup operations like closing files, releasing network connections, or other resource cleanup
- However, note that `finalize()` is deprecated since Java 9 and should generally be avoided in favor of try-with-resources or explicit cleanup methods

The other options:
- **Final** - A keyword used to declare constants, prevent inheritance, or prevent method overriding
- **Finally** - A block used with try-catch for cleanup code that always executes
- **Destructor** - Java doesn't have destructors like C++; it uses garbage collection and the `finalize()` method (though this approach is now discouraged)

---





   
   
