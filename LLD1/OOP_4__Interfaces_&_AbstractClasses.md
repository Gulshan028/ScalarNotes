# Lecture | Backend LLD: OOP-4: Interfaces, Abstract Classes

## CONTENTS: Deepak Sir's class
1. Polymorphism
2. Static Keyword
3. Interfaces
4. Tight Coupling in Backend Projects: ProductController Example
5. Different Methods in Interface
6. Multiple Inheritance in Java
7. Abstract Class
8. Backward Compatibility
9. The “final” Keyword in Java
10. Example of a Class Implementing Multiple Interfaces and Extending Another Class
11. Assignments

---

## I] Polymorphism

### 1.1) General Understanding about Polymorphism

1) Consider an Inheritance relation as: `User class` is having 4 child classes as `Student` class, `Mentor` class, 
    `TA` class, `Instructor` class. Then, we can say that,
    - Student IS-A User. (i.e. Student is a type of User)
    - Mentor IS-A User.
    - TA IS-A User.
    - Instructor IS-A User.
   
   To conclude, we can say that, `User` has many forms.

2) Consider the method, `changePassword()`. Now, each of the 4 child class will have same implementation of above method.
   So, why will I write this method separately for each of the 4 child classes. I will directly write the method in the 
   `User` class and then each of the child classes can access this method. This suggest that this method can take any 
    form of the `User`, whether `Student`, `TA`, `Mentor`, etc.
    It means the method is stored at `User` reference but any form of the `User` can access it.
    
    I can write it as:
```java
User user = new Student();      //because every Student is a User
User user = new TA();           //because every TA is a User
User user = new Mentor();       //because every Mentor is a User
User user = new Instructor();   //because every Instructor is a User
```
3) **Learning:** We can store the object of any child class in the parent class reference (as shown above) but the 
                 vice versa is NOT possible(as shown below).
4) `Student st = new User();`  //compilation error because every user is not a student. It can be mentor, TA, Instructor, etc
5) The benefit of this is that: Consider we have a method named, `printNames()`, which will print names of all the users, i.e.
   the user base then we can make the method as 
```java
   printNames(List<User> users){
        ----------
        ----------
   }
```
Here, in this list `users`, we can add all the users, whether `Student`, `TA`, `Mentor`, `Instructors`, etc. as
```java
users.add(new Student());
users.add(new Mentor());
//etc
```
so, rather than having 4 different lists for 4 different child classes. We are having just one list. The users' `List` 
of `User` type, which is the parent class, and we are able to store any child class object in this parent class reference.

---

### 1.2) Polymorphism Example – Using Animal and Dog Classes

#### 1.2.1) Class Structure

```java
class Animal {
    void walk() {}
}

class Dog extends Animal {
    void bark() {}
}
```

---

#### 1.2.2) Code Scenarios:

1. **Case 1:**

   ```java
   Animal a = new Animal();
   a.walk();   // ✅ Allowed
   a.bark();   // ❌ Error (bark() not in Animal)
   ```

2. **Case 2:**

   ```java
   Dog d = new Dog();
   d.bark();   // ✅ Allowed
   d.walk();   // ✅ Inherited from Animal
   ```

3. **Case 3:**

```java
   Dog d = new Animal();  // ❌ Compilation Error
   // Not allowed: Can't store parent object in child class reference as every Animal is not Dog

    Dog d1 = (Dog) new Animal();
   d1.walk(); //❌ Runtime Error
   d1.bark(); //❌ Runtime Error
```
Now, this code is compiled successfully at compile time as we have typecast Animal object into Dog but at runtime when the actual object is 
created, we get runtime error. As Dog must be having more methods and attributes which are not part of Animal. 
**So, Java doesn't allow DownCasting.**

4. **Case 4:**

   ```java
   Animal a = new Dog();  // ✅ Allowed (Polymorphism)
   a.walk();   // ✅ Allowed (method in Animal)
   a.bark();   // ❌ Compile-time error (Animal reference doesn't "know" bark())
   ```
  - In this 4th case, `a.bark();` isn't allowed because `a` is an `animal reference` which has no idea about `bark()` method.
    Also, objects are created at runtime and compiler is only aware of reference at compile time. It isn't aware of what objects
    are created at runtime. Hence, it doesn't know, what methods are present at the object level.
  - At runtime, the JVM knows `a` is a Dog, but this doesn’t matter for method accessibility—the compiler’s restrictions apply first.
  - This is `Upcasting` also known as `Implicit casting`, where reference is of parent type and at runtime the object created is of 
     child type. However, the parent reference variable is only aware of the properties and methods present in the parent object hence 
     it can only call the methods and properties, which are present in the parent reference variable. However, if child has overridden 
     any method then at runtime that overridden method will be run.

---

### 1.3) Key Learning:

* You **can store** child object in **parent class reference**:

  ```java
  Animal a = new Dog();  // ✅
  ```

* You **cannot store** parent object in **child class reference**:

  ```java
  Dog d = new Animal();  // ❌
  ```
  
* When using parent class reference, only **parent class methods** are accessible, even if the object is of the child class.

---

### 1.4) Summary of Polymorphism:

1. **Definition:** Polymorphism allows for methods to do different things based on the object it is acting upon, even though they share the same name. 
      Derived from "poly" (many) and "morph" (form), polymorphism means one name, many forms.
2. **Types:**
   - 2.1) **Compile-time (Method Overloading):** Achieved when two or more methods in the same class have the same name but different parameters. 
            This allows the compiler to identify which method to execute.
   - 2.2) **Run-time (Method Overriding):** Involves a child class having a method with the same name and parameters as a method in the parent class, 
          allowing dynamic method invocation based on the object's runtime type

--- 

## II] Static Keyword:
1. The static keyword in Java is used for memory management. It is applied to `variables` or `methods` that are common to all instances of a class, rather than belonging to any one instance.
2. **Definition:** Static members belong to the class rather than any object instance. This means that there is only one copy of the static member for all objects of the class.
3. **Static Attributes:** Class-level attributes that are shared across all instances of the class. For example, a university name for all student objects.
4. **Static Methods:** Methods that can be called without creating an instance of the class. These methods can only access `static` data members.
   It cannot refer to `this` or `super` in any way.
5. A static method cannot directly access non-static attributes of the same class. To use them:
   - Create an object inside the static method.
   - Pass an object as a parameter.
6. ❌ Static methods cannot be overridden (only hidden i.e. Method hiding, which isn't Polymorphism, hence use it cautiously).
7. ✅ Static methods can be overloaded (same name, different parameters).
8. Non-Static method can access both static and Non-Static attributes of the class.
9. **Example of static method:**
   A common example is the `main` method in Java, defined as `public static void main(String[] args)`, which serves as an entry point for the application without requiring an instance of a class.

---

## III] Interfaces:

### 3.1) Key Points about Interfaces:

1. **Definition**: Interfaces are crucial for defining contracts in object-oriented programming. They are blueprint for behaviours i.e. methods().
   **Interfaces specify a set of methods that a class must implement, but they do not provide the method's body**, meaning they do not dictate how the actions should be performed.
2. We cannot create objects of an Interface.
3. Generally, we start name of an Interface with capital 'I' alphabet. ex: ICalculator, etc. This is not a rule.
4. `implements` is the word to be used for a `class` to be able to use an `Interface`. ex: 
    ```java
          public class Calculator implements ICalculator{};
    ```
---

### 3.2) **Can an Interface Extend Another Interface?**
**Yes!** In Java, an **interface can extend one or more other interfaces** (unlike classes, which can extend only one class). This is called **interface inheritance**.

---

### 3.3)  **Key Rules:**
✅ **Single Inheritance:**
```java
interface A {}
interface B extends A {}  // Valid (B extends A)
```  

✅ **Multiple Inheritance (Allowed for Interfaces!):**
```java
interface A {}
interface B {}
interface C extends A, B {}  // Valid (C extends A and B)
```  

❌ **Cannot Extend Classes:**
```java
class X {}
interface Y extends X {}  // ❌ Compile Error (Interfaces can't extend classes)
```  

---

### 3.4) **Example: Interface Extending Another Interface**
```java
// Parent Interface
interface Animal {
    void eat();
}

// Child Interface (extends Animal)
interface Dog extends Animal {
    void bark();
}

// Class implementing Dog (must implement both eat() and bark())
class GermanShepherd implements Dog {
    @Override
    public void eat() {
        System.out.println("German Shepherd eats meat.");
    }

    @Override
    public void bark() {
        System.out.println("German Shepherd barks loudly!");
    }
}

public class Main {
    public static void main(String[] args) {
        GermanShepherd dog = new GermanShepherd();
        dog.eat();  // Output: "German Shepherd eats meat."
        dog.bark(); // Output: "German Shepherd barks loudly!"
    }
}
```

---

### 3.5) **Why Extend Interfaces?**
1. **Code Reusability:** Avoid rewriting common method declarations.
2. **Polymorphism:** A child interface can be used where the parent is expected.
   ```java
   Animal myDog = new GermanShepherd();  // Valid (Dog extends Animal)
   ```  
   - Learning:- ✅ An interface reference can store an object of a class only if the class implements that interface.
3. **Multiple Inheritance:** Unlike classes, interfaces can extend multiple parents.

---

### 3.6) **Multiple Interface Inheritance Example**
```java
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// Duck extends both Flyable and Swimmable
interface Duck extends Flyable, Swimmable {
    void quack();
}

class MallardDuck implements Duck {
    @Override
    public void fly() {
        System.out.println("Mallard Duck flies.");
    }

    @Override
    public void swim() {
        System.out.println("Mallard Duck swims.");
    }

    @Override
    public void quack() {
        System.out.println("Mallard Duck quacks!");
    }
}
```

---

### 3.7) **Key Takeaways**
| **Feature**               | **Interface Inheritance**          |
|--------------------------|-----------------------------------|
| **Extends another interface?** | ✅ Yes (single or multiple) |  
| **Extends a class?**      | ❌ No |  
| **Default methods?**      | ✅ Inherited (unless overridden) |  
| **Static methods?**       | ❌ Not inherited (must be called via interface name) |  

---

### 3.8) **Final Answer**
**Yes, an interface can extend other interfaces.**
- **Single extension:** `interface B extends A {}`
- **Multiple extensions:** `interface C extends A, B {}`

**Example:**
```java
interface Parent { void method1(); }
interface Child extends Parent { void method2(); }
```  
This ensures **all `Child` implementers must define both `method1()` and `method2()`**.

---

### 3.9) **Do Interfaces Have Attributes and Constructors?**

#### **1. Interfaces CAN Have Attributes (But Only Constants)**
✅ **Interfaces can have variables**, but they are **implicitly** `public`, `static`, and `final` (constants).
```java
interface Example {
    int MAX_VALUE = 100;  // Implicitly: public static final int MAX_VALUE = 100;
}
```
- ** Interfaces cannot have instance variables** (non-static attributes).
- **All fields are constants** (must be initialized).

---

#### **2. Interfaces CANNOT Have Constructors**
❌ **No constructors are allowed** in interfaces.
```java
interface Example {
    // Example() {}  // ❌ Compile Error (Interfaces cannot have constructors)
}
```
- **Reason:** Interfaces define **behavior contracts**, not object initialization.

---

### 3. **Why No Constructors?**
- **Interfaces cannot be instantiated** (no `new MyInterface()`).
- **Purpose:** Define **what** a class should do, not **how** it’s created.

---

### 4. **Example: Valid vs. Invalid in Interfaces**
```java
interface Vehicle {
    // ✅ Valid: Constant (implicitly public static final)
    int WHEELS = 4;

    // ✅ Valid: Abstract method (no body)
    void start();

    // ❌ Invalid: Instance variable
    // String model;  // Error!

    // ❌ Invalid: Constructor
    // Vehicle() {}  // Error!
}
```
---

## IV] Tight Coupling in Backend Projects: ProductController Example

### 4.1) The Problem Scenario

In our backend project, we have a `ProductController` class that directly depends on `FakeStoreProductService` class . This creates tight coupling because:

1. **Direct Dependency**: `ProductController` is hardcoded to work only with `FakeStoreProductService`
2. **Implementation Lock-in**: The controller knows specific details about the service implementation
3. **Inflexibility**: Switching to a different service (like `DummyStoreProductService`) requires controller modifications as new ProductService might have different methods, 
                      different return types, etc.
All these things make it difficult to transition from one ProductService to the newer one.

---

### 4.2) The Tight Coupling Example

```java
public class ProductController {
    // Tight coupling - directly using FakeStoreProductService
    private FakeStoreProductService productService = new FakeStoreProductService();
    
    public Product getProduct(Long id) {
        // Controller is aware of the specific service's implementation details
        return productService.getFakeStoreProduct(id);
    }
}
```
---

### 4.3) Why This is Problematic

When we need to switch to `DummyStoreProductService`:
- The new service might have different method signatures (e.g., `fetchProduct()` instead of `getFakeStoreProduct()`)
- Return types might be different
- The entire `ProductController` would need modifications to accommodate the new service
- Testing becomes harder because we can't easily mock the service

---

### 4.4) The Root Cause

This is tight coupling because:
1. **High Dependency**: The controller cannot function without this exact service implementation
2. **Low Flexibility**: Changing services requires changing the controller
3. **Poor Testability**: Unit testing the controller requires the actual service

---

### 4.5) The Solution Approach

We should:
1. **Introduce an Interface**: Create a `ProductService` interface that both services implement
2. **Use Dependency Injection**: Pass the service implementation to the controller rather than letting it create one
3. **Program to Interfaces**: Have the controller depend on the abstraction (interface) rather than the concrete implementation

This transformation would lead to loose coupling, making our system more maintainable and flexible.

---

### 4.6) Important Principle: 
**Program to Interface, not to implementation.**
   - The explanation is simple, the ProductService can be implemented via multiple ways. Say, it can get product info from various ways, say, fakestore, dummystore, database, etc.
     So, productController should program to Interface, Say, `ProductService` Interface, which just declares the method. Now, we will have a `FakeStoreProductService` class
     which will implement this `ProductService` Interface.
   - Now, we want `ProductController` class to be dependent on interface. So, we will create dependency in `ProductController` class as follows,
```java
ProductService ps = new FakeStoreProductService();
```
Here, we are having reference of interface type and object of implementing class type, which resembles parent-child relationship hence `Method Overriding i.e. Run-time Polymorphism`
can be seen. So, method from `FakeStoreProductService` class will be triggered. As we have seen in Run-time Polymorphism, the method to be triggered depends on the object created.
- Now, I want to have a different implementation of the ProductService class. So, I can have the another class implementing the interface. Say, the class name is `DummyStoreProductService`.
- So, now, I just need to store the `DummyStoreProductService` object into the `ps` reference, inside `ProductController` class. As parent reference can store any child object. 
- This is how, with just single change we can have flexibility of several implementations.
- This is called as `Loose Coupling` where we can easily switch from one service to another service.

---

## V] Different Methods in Interface:

### 5.1) Abstract Methods:
#### (1) **Abstract Methods:**
Abstract methods are methods declared **without any implementation** (no body) in an **abstract class** or **interface**. They act as placeholders, forcing subclasses or implementing classes to provide the concrete implementation.

#### (2) **Key Points:**
1. **No Body**: Ends with a semicolon (`;`) instead of curly braces `{}`.
2. **Must be Overridden**: Subclasses (for abstract classes) or implementing classes (for interfaces) must define them.
3. **Used for Polymorphism**: Enforces a consistent structure while allowing different behaviors.

#### (3) **Examples:**
1. **In an Abstract Class:**
   ```java
   abstract class Animal {
       abstract void makeSound(); // Abstract method
   }
   ```  
2. **In an Interface:**
   ```java
   interface Drawable {
       void draw(); // Abstract method
   }
   ```  
---

#### (4) **Why Use Abstract Methods?**
- Ensures **consistent method naming** across implementations.
- Allows **flexibility** in how different classes implement behavior.
- Supports **polymorphism** (e.g., `Animal dog = new Dog(); dog.makeSound();`).

---

### 5.2) Default Methods: 
Like regular interface methods, `default` methods are implicitly `public`; there’s no need to specify the `public` modifier.

Unlike regular interface methods, we declare them with the `default` keyword at the beginning of the method signature, and they provide an implementation.

1. The reason why the Java 8 release included `default` methods is pretty obvious.

In a typical design based on abstractions, where an interface has one or multiple implementations, if one or more methods are added to the interface, 
all the implementations will be forced to implement them too. Otherwise, the design will just break down.

`Default` interface methods are an efficient way to deal with this issue. They allow us to add new methods to an interface that are automatically available 
in the implementations. Therefore, we don’t need to modify the implementing classes.

In this way, backward compatibility is neatly preserved without having to refactor the implementers.

2. The `default methods`, from the `interface` are automatically available in the implementing `class` (similar to parent's methods in Inheritance 
       are invisibly available in child classes).

3. **Summary: Multiple Interface Inheritance with `Default` Methods**

- If a class implements **multiple interfaces with conflicting `default` methods**, it causes a **compilation error** (Diamond Problem).
- The class **must override** the conflicting method and provide its own implementation.
- It can explicitly choose **one interface’s `default` method** using `InterfaceName.super.method()`.
- Alternatively, it can **combine behaviors** from both interfaces in the override.

---

### 5.3) **HOMEWORK:-** 
Study about Static methods in interfaces and add the relevant in this section 5.3

---
## VI] Multiple Inheritance in Java:
### 6.1) Definition:
Multiple inheritance refers to a feature where a class can inherit properties (methods/fields) from more than one parent class simultaneously.

### 6.2) **Short Answer for "Why Java doesn't support Multiple Inheritance?" Because:**
Java doesn’t support **multiple inheritance (for classes)** to avoid the **"Diamond Problem"**, where ambiguity arises if a class inherits the same method from two different parent classes.

---

### 6.3) **Example: The Diamond Problem**
```java
class A {
    void show() { System.out.println("A"); }
}

class B extends A {
    void show() { System.out.println("B"); }
}

class C extends A {
    void show() { System.out.println("C"); }
}

// If Java allowed multiple inheritance:
class D extends B, C {  
    // ❌ Ambiguity: Which show() should run? B's or C's?
}

public class Main {
    public static void main(String[] args) {
        D obj = new D();
        obj.show(); // ❌ Compiler can't decide (Error in languages like C++)
    }
}
```  
**Problem:** If `D` could extend both `B` and `C`, calling `show()` would be ambiguous.

---

### 6.4) **How Java Solves It:**
✅ **Interfaces (with default methods) allow multiple inheritance** safely because:
1. The subclass **must override conflicting methods**.
2. The programmer **explicitly chooses** which default implementation to use.

```java
interface B { default void show() { System.out.println("B"); } }
interface C { default void show() { System.out.println("C"); } }

class D implements B, C {
    @Override
    public void show() {  // Must override to resolve conflict
        B.super.show();  // Explicitly choose B's version
    }
}
```  
Even if there are no default methods in interfaces, then still all the same abstract methods from multiple interfaces must be implemented 
at the class implementing the interfaces. So, there is no ambiguity.

---

### 6.5) **Key Takeaway:**
- **Classes:** No multiple inheritance (to avoid ambiguity).
- **Interfaces:** Multiple inheritance allowed (with rules to handle conflicts).
- Java replaces class-based multiple inheritance with interface-based multiple inheritance for clarity and simplicity.
- If Java allowed multiple inheritance for classes, constructor calls (`super()` calls in Constructor chaining) would become ambiguous. Java avoids this by:

   - Restricting classes to single inheritance.

   - Allowing multiple inheritance only for interfaces (which have no constructors).

   - This keeps object initialization simple and predictable.

---

## VII] Abstract Class:

- **Definition:** Abstract classes are used when classes have common behaviors, but the detail of implementation might differ. 
  They can have both method implementations and method declarations without a body (abstract methods), which the subclasses 
  are required to implement.
- **When to Use:** Abstract classes are useful where some methods have common functionality across the subclasses, but others do not. 
  They allow common functionality to be defined at one place.
- **Differences from Interfaces:** Unlike interfaces, abstract classes can have constructor, member variables(attributes), and defined (concrete) methods.
- Abstract classes cannot have `objects`. They extend child classes then child classes can have objects.
- Abstract classes are beneficial when you want to share code among closely related classes. 
- Also, when you need attributes along with abstract methods to be passed on to the child classes. At such times, we prefer abstract classes over interfaces.
- `abstract` modifier keyword must be defined before class and also with the `abstract` methods of the `abstract class`.

---

## VIII] Backward Compatibility:
### 8.1) **Backward Compatibility in Java**

Backward compatibility in Java means that **code written in an older version of Java should continue to work without modification in newer versions** of the Java platform. This ensures that:

1. **Existing applications** keep running smoothly when users upgrade their Java runtime.
2. **Libraries and frameworks** remain functional across different Java versions.
3. **Developers don’t need to rewrite old code** just to make it work on newer JVMs.

---

### 8.2) **How Java Maintains Backward Compatibility**
1. **No Breaking Changes in Bytecode**
    - Java bytecode (`.class` files) generated by older compilers should still execute on newer JVMs.
    - Example: A `.class` file from Java 8 should run on Java 11, 17, or 21 without recompilation.

2. **Deprecation Instead of Removal**
    - Old APIs are marked as **`@Deprecated`** before removal (if ever), giving developers time to migrate.
    - Example: `Thread.stop()` was deprecated but still exists for compatibility.

3. **Support for Older Language Features**
    - Even when new syntax is introduced, old syntax remains valid.
    - Example: Java 5 introduced generics, but raw types (pre-generics code) still work.

4. **JVM Evolution with Care**
    - New JVM features (e.g., `invokedynamic` in Java 7) don’t break older bytecode.

---

### 8.3) **Exceptions to Backward Compatibility**
While rare, some changes can break compatibility:
- **Security fixes** (e.g., tightening access controls).
- **Bug fixes** that change unintended behavior.
- **Very old, rarely used APIs** might eventually be removed (e.g., `Applet` API in Java 9+).

---

### 8.4) **Example of Backward Compatibility**
```java
// Java 5 code (introduced generics)
List list = new ArrayList(); // Raw type (old style)
list.add("Hello");

// This still compiles and runs in Java 21, even though modern code uses generics:
List<String> modernList = new ArrayList<>();
```

---

### 8.5) **Forward Compatibility?**
Java does **not** guarantee forward compatibility:
- Code compiled with a **newer** Java version (e.g., Java 17) **will not run** on an **older** JVM (e.g., Java 8).

---

### 8.6) **Why Backward Compatibility Matters**
- **Enterprise Stability**: Large systems rely on long-term support.
- **Ecosystem Trust**: Developers trust that their code won’t break with updates.
- **Smooth Upgrades**: Encourages adoption of newer Java versions.

Java’s strong backward compatibility is a key reason for its longevity in the software industry.

---

## IX] The “final” Keyword in Java:


### 1️⃣ **Final Variable**

* Declared with the `final` keyword.
* Must be initialized **once**.
* Value cannot be changed after initialization.

Example:

```java
final int MAX_AGE = 100;
// MAX_AGE = 101;  // Compile error
```

---

### 2️⃣ **Final Method**

* Cannot be overridden by any subclass.

---

### 3️⃣ **Final Class**

* `Classes` marked as final can’t be extended. Any attempt to *inherit* from a *`final` class* will cause a *compiler error*.

---

### 4️⃣ **Final Parameter**

* Input Parameter value cannot be changed inside the method.

Example:

```java
public void process(final int value) {
    // value = value + 1; // Compile error
}
```

---

### 5️⃣ **Blank Final Variable**

* A `final` variable can be left uninitialized and must be assigned exactly once, typically in a constructor.

Example:

```java
public class Book {
    private final String title;

    public Book(String title) {
        this.title = title;
    }
}
```

---

## X] Example of a Class Implementing Multiple Interfaces and Extending Another Class

Here's a Java example demonstrating a class that extends one class and implements multiple interfaces:

```java
// Parent class
class Animal {
    private String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " is eating.");
    }
    
    public String getName() {
        return name;
    }
}

// First interface
interface Swimmable {
    void swim();
}

// Second interface
interface Flyable {
    void fly();
}

// Class extending Animal and implementing two interfaces
class Duck extends Animal implements Swimmable, Flyable {
    public Duck(String name) {
        super(name);
    }
    
    // Implementing Swimmable interface
    @Override
    public void swim() {
        System.out.println(getName() + " is swimming in the pond.");
    }
    
    // Implementing Flyable interface
    @Override
    public void fly() {
        System.out.println(getName() + " is flying through the air.");
    }
    
    // Additional method specific to Duck
    public void quack() {
        System.out.println(getName() + " says: Quack quack!");
    }
}

public class Main {
    public static void main(String[] args) {
        Duck donald = new Duck("Donald");
        
        donald.eat();   // Inherited from Animal
        donald.swim();  // From Swimmable interface
        donald.fly();   // From Flyable interface
        donald.quack(); // Duck's own method
    }
}
```
---

### 10.1) Key Points:

1. **Extends Clause**: The `Duck` class extends `Animal` using `extends Animal`
2. **Implements Clause**: The `Duck` class implements both `Swimmable` and `Flyable` interfaces using `implements Swimmable, Flyable`
3. **Order Matters**: In Java, the extends clause must come before the implements clause
4. **Implementation**: The class must provide concrete implementations for all abstract methods from all interfaces

--- 
### 10.2) Output:
When you run this program, it will output:
```
Donald is eating.
Donald is swimming in the pond.
Donald is flying through the air.
Donald says: Quack quack!
```

This example shows how a class can inherit behavior from a parent class while also promising to provide specific behaviors defined in multiple interfaces.

---

## XI] Assignments:

1) "`Abstract` classes doesn't need to necessarily have `abstract` methods". An `abstract class` is declared using the `abstract` keyword, and one of the purpose of an 
   abstract class is to prevent instantiation (i.e., you cannot create an object of an abstract class). Even if it has no abstract methods, marking it abstract ensures it must be subclassed.
2) If a class has at least one `abstract` method, it must be declared `abstract`.
3) `final` keyword is used to make a non-inheritable class in Java.
4) Data members = Attributes = Fields = Instance variables
   - These are variables declared inside a class (but outside methods).
   - They hold the state/data of an object.
