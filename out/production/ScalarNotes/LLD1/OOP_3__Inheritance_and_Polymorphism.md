# Lecture | Backend LLD: OOP-3: Inheritance and Polymorphism

First read GitHub notes provided by Scalar and then start this notes.

## CONTENTS: Sandeep Sir
1. Inheritance
2. Getters and Setters
3. Polymorphism
   - Method Overloading
   - Method Overriding

---

## CONTENTS: Deepak Kasera Sir
1. Shallow vs Deep Copy Using Copy Constructors
2. Constructor Chaining for Default Constructor
3. `super` vs `this` in Java: Key Differences

---

## I] Inheritance

### 1.1) Key Points about Inheritance:
1. Inheritance is a fundamental concept in **Object-Oriented Programming (OOP)** that allows one class, known as the child or subclass, 
     to inherit properties and behaviors (i.e., methods and attributes) from another class, known as the parent or superclass. 
     This process helps to promote code reusability and create a hierarchical class structure

2. While creation of object of the child class, initialising the attributes inherited from the parent class,
     requires the constructor of the parent class. 

   For this to happen, the compiler first goes to the respective constructor of the child class. Using `super()` keyword,
     it then goes to the respective constructor of the parent class. Hence, `super()` keyword must be always the first line of the 
     child constructor.

3. To sum up, we can say that, initialising the parent attributes is the responsibility of the parent constructor, 
     whereas, initialising only the child attributes is the responsibility of the child class.

4. Example:
```java
   class Car{
        int noOfWheels;
        String brakelight;

        public Car(int noOfWheels, String brakelight){
                  this.noOfWheels = noOfWheels;
                  this.brakelight = brakelight;
        }
   }

   class ElectricCar extends Car{
        int batteryCapacity;

        public ElectricCar(int batteryCapacity, int noOfWheels, String brakelight){
                super(noOfWheels, brakelight);
                this.batteryCapacity = batteryCapacity;
        }
   }
```

### 1.2) Key Concepts of Inheritance:
1. **Parent-Child Relationship:** In inheritance, we represent relationships between classes as **parent-child** or **superclass-subclass** relations. 
                                  This hierarchy enables classes to share common methods and attributes.

2. **Extends Keyword:** In Java, the `extends` keyword is used to inherit a `class`. For example, if `class B extends class A`, `class B` inherits the attributes and methods of `class A`.

3. **Code Reusability:** Inheritance allows derived classes (subclasses) to use the fields and methods of their base class (superclass). This reduces code redundancy.

4. **Overriding Variables:** While child classes inherit methods and attributes from the parent class, they can also have their own specific attributes and methods.

5. **Initialization and the `super` Keyword:** In Java, when a subclass object is created, the constructor calls move from the subclass up through each superclass to the root, 
                                               which initializes the object properly. The `super()` keyword is used to call the constructor of the parent class.

### 1.3) Why `super()` is Needed Even Though Child Class Inherits Parent's Properties? 

The reason is **Constructor Initialization**:

- Constructors are not inherited (unlike methods and fields).

- The child class must ensure the parent class is properly initialized before it initializes itself.

- `super()` calls the parent class constructor.

---

## II] Getters and Setters

1) Refer Github notes and then come here.
2) Basically, they provide controlled access to the `fields` of the class, provided those fields have `private` access.
3) Before setting the attribute, we can validate certain conditions.
4) In Inheritance, if parent class attributes are private then even the child class cannot access those attributes, in 
   such cases, getters and setters are useful. (try using with and without super() keyword).

---

## III] Polymorphism

### 3.1) Introduction to Polymorphism:

1. Polymorphism allows the expression of one interface with multiple implementations. It plays a crucial role in allowing method overriding and overloading:

   - **Dynamic Method Dispatch:** In Java, polymorphism is implemented through method overriding. This is referred to as dynamic method dispatch where the method to be invoked is determined at runtime.

   - **Flexible Code Structure:** Polymorphism contributes to code flexibility and reusability. It allows methods to do different things based on the object that invokes them.

   - **Types of Polymorphism:** Java supports two types of polymorphism—compile-time (method overloading) and runtime (method overriding). 
       Compile-time polymorphism is achieved through method overloading and allows methods in the same class to have the same name but different parameters.

### 3.2) Method Overloading

1. Method overloading allows a single method to have multiple signatures. The primary distinctions in overloading are:
   - **Different Parameter Lists:** Methods are differentiated by the number or type of parameters.
   - **Compile-Time Polymorphism:** Method overloading is an example of compile-time polymorphism, where the decision of which method to invoke is made during compile time.
2. Method Signature is same as Constructor signature.
3. Key Rules
   - Overloading depends on:
        - Parameter types
        - Parameter order
        - Parameter count
   - Ignored for overloading:
        - Return type
        - Access modifiers (e.g., public/private)

### 3.3) Method Overriding

1. Method overriding occurs when a subclass provides a specific implementation of a method that is already defined in its superclass. The key points about method overriding are:

   - **Same Method Signature:** The method in the child class must have the same name and parameter list as the method in the parent class.

   - **Differences in Logic:** Although the method name and parameters are the same, the logic within the method can differ, providing specific implementations appropriate for the subclass.

   - **Annotations:** The `@Override` annotation in Java is used to indicate that a method is meant to override a method declared in a superclass. It enhances the readability and understanding of the code

---


# Deepak Sir's Class

## I] Shallow vs Deep Copy Using Copy Constructors in Java (with Multi-Level Objects)

### 1.1) Shallow Copy Using Copy Constructor

A shallow copy constructor only copies primitive values and references to objects (not the objects themselves).

```java
class Student {
    String name;
    School school;  // School is another object
    
    // Shallow copy constructor
    public Student(Student original) {
        this.name = original.name;
        this.school = original.school;  // Shares the same School object reference
    }
}
```

**Problem:** If you modify `student1.school`, it will affect `student2.school` because they reference the same School object.

---

### 1.2) Deep Copy Using Copy Constructor (Multi-Level)

1) For a proper deep copy, we need copy constructors at each level:

#### Level 1: Address Class
```java
class Address {
    String city;
    String street;
    
    //Manual Constructor
     public Address(String city, String street){
         this.city = city;
         this.street = street;
     }
    
    // Copy constructor for Address
    public Address(Address original) {
        this.city = original.city;
        this.street = original.street;
    }
}
```

#### Level 2: School Class
```java
class School {
    String name;
    Address address;  // Address is another object

     //Manual Constructor
     public School(String name, Address address){
          this.name = name;
          this.address = address;
     }
    
    // Deep copy constructor for School
    public School(School original) {
        this.name = original.name;
        this.address = new Address(original.address);  // Creates new Address object
    }
}
```

#### Level 3: Student Class
```java
class Student {
    String name;
    int age;
    School school;  // School contains Address

     //Manual Constructor
     public Student(String name, int age, School school){
          this.name = name;
          this.age = age;
          this.school = school;
     }
     
    // Deep copy constructor for Student
    public Student(Student original) {
        this.name = original.name;
        this.age = original.age;
        this.school = new School(original.school);  // Creates new School (which creates new Address)
    }
}
```

### Complete Example with Usage

```java
public class DeepCopyExample {
    public static void main(String[] args) {
        // Create original student
        Address originalAddress = new Address("New York", "123 Main St");
        School originalSchool = new School("XYZ High", originalAddress);
        Student originalStudent = new Student("John", 16, originalSchool);
        
        // Create deep copy
        Student copiedStudent = new Student(originalStudent);
        
        // Modify the copy
        copiedStudent.school.name = "ABC High";
        copiedStudent.school.address.city = "Boston";
        
        // Original remains unchanged
        System.out.println(originalStudent.school.name);  // Output: "XYZ High"
        System.out.println(originalStudent.school.address.city);  // Output: "New York"
    }
}
```
---

### 1.3) Key Points

1. **Shallow Copy Constructor:**
     - Only copies primitive values directly
     - Copies references to objects (shared between original and copy)

2. **Deep Copy Constructor:**
     - Copies primitive values
     - Creates new objects for all non-primitive fields
     - Requires copy constructors at each level of object hierarchy
     - For 3-level objects (Student → School → Address), each class needs proper copy constructor

3. **Multi-Level Deep Copy:**
     - Each object in the hierarchy must properly implement deep copy
     - Changes at any level (School or Address) in the copy won't affect the original

This approach ensures complete isolation between the original and copied objects at all levels.

---

### 1.4) Note: **Deep Copy from Scaler Companion**
-   A deep copy creates a new object and also recursively copies all objects referenced by the original object.
-   Each level of nested objects within the original object is cloned, ensuring that the two objects do not share any references.
-  Challenges: Creating deep copies can be challenging and often impractical for deeply nested objects

---

### 1.5) Conclusion:
-   At production level, each class has hundreds of objects and then each nested class will have further nested objects.
    Hence, doing deep copy using copy constructor manually is impractical. While possible in simple cases, 
    production systems typically rely on serialization-based methods or specialized libraries instead of manual deep copy 
    implementations.

---


## II] Constructor Chaining

### 2.1) For Default Constructor

1) Let `class C extends class B` and `class B extends class A`, i.e. `C` is the child class of `B` and `B` is the 
     child class of `A`. Now, When we create an object of `class C` as `C c = new C();`, using the default Constructor.
     Then, first of all, Default Constructor of `C` automatically calls default constructor of its immediate parent class 
     `B` and then `B` also first of all call the default constructor of its immediate parent class `A`, automatically. 
     So, the order of execution is, first the default Constructor of `A` is executed and then the default constructor of 
     `B` is executed and lastly the default constructor of `C` is executed. (Provided all the respective default constructors 
      are accessible, i.e. they aren't `private`).

2) Here's a slightly expanded explanation with the same clear code example:

When creating an object in Java inheritance, constructors are called in a specific order from parent to child:

```java
class A {
    A() {
        System.out.println("A's constructor"); // 1st to execute
    }
}

class B extends A {
    B() {
        // Compiler automatically inserts super() here
        System.out.println("B's constructor"); // 2nd to execute
    }
}

class C extends B {
    C() {
        // Compiler automatically inserts super() here
        System.out.println("C's constructor"); // 3rd to execute
    }
}
```

```java
Output:
A's constructor
B's constructor
C's constructor
```

3) Key points:
   - **Automatic super() call**: Each constructor implicitly calls `super()` as its first statement (unless explicitly written)
   - **Execution order**: Parent constructors complete before child constructors
   - **Access requirement**: All constructors in the chain must be at least `protected` (not `private`)

When you do `new C()`, the flow is:
1. `C()` calls `B()`
2. `B()` calls `A()`
3. `A()` executes first
4. Then `B()` executes
5. Finally `C()` completes

This ensures proper initialization from the root parent class downward.

This process of one Constructor calling their parent constructors is called **Constructor Chaining**.
This is done with  `this()` and `super()` keywords. 
It ensures that the parent constructor is called before the child constructor.

4)
```java
class A {
int a;

    // Parameterized constructor
    A(int a) {
        this.a = a;
        System.out.println("A's constructor with a = " + a);
    }
}

class B extends A {
int b;

    // Parameterized constructor
    B(int b) {
        this.b = b;
        System.out.println("B's constructor with b = " + b);
    }
}

class C extends B {
int c;

    // Parameterized constructor
    C(int c) {
        this.c = c;
        System.out.println("C's constructor with c = " + c);
    }
}

public class Main {
    public static void main(String[] args) {
        C c = new C(30);
    }
}
```
In this code, since, Parameterized constructor of `C` cannot call either default or Parameterized constructor of its immediate 
parent, hence, this code gives compilation error. 
Because, child class attributes cannot be initialised before the attributes of the parent class.
So, to make it work, we need to have default constructors in class `B` and class `A`. So that, compiler can call it automatically.
Then, variables `a` and `b` would have gotten default values.
**Learning:** If you don't write super() explicitly in a subclass constructor, Java automatically inserts a call to the 
  no-argument constructor of the superclass (super() as the first line ) — but only if the superclass has a no-arg constructor.

The other option is to call the parameterized constructors using the keyword called `super()`. Let's see it in lines below.

Here's the same code with explicit `super()` calls in the constructors:

```java
class A {
    int a;
    
    // Parameterized constructor
    A(int a) {
        this.a = a;
        System.out.println("A's constructor with a = " + a);
    }
}

class B extends A {
    int b;
    
    // Parameterized constructor
    B(int a, int b) {
        super(a);  // Explicit call to parent constructor
        this.b = b;
        System.out.println("B's constructor with b = " + b);
    }
}

class C extends B {
    int c;
    
    // Parameterized constructor
    C(int a, int b, int c) {
        super(a, b);  // Explicit call to parent constructor
        this.c = c;
        System.out.println("C's constructor with c = " + c);
    }
}

public class Main {
    public static void main(String[] args) {
        C c = new C(10, 20, 30);
    }
}
```

Output (same as before):
```
A's constructor with a = 10
B's constructor with b = 20
C's constructor with c = 30
```


1. The `super()` call must be the first statement in each constructor as parent class' attributes must be initialised before 
   child class' attributes (remember, vice versa will give compilation error).
2. `super()` keyword refers to the constructor of the immediate parent class only.

---

## III] Assignments

1. Static methods can be overloaded but not cannot be overridden.
2. Java doesn't support 'Operator Overloading'.

---

##  IV] `super` vs `this` in Java: Key Differences

### 4.1) `this` Keyword
- **Refers to the current object instance**
- Used to:
    - Access current class's instance variables (especially when shadowed by parameters)
    - Invoke current class's methods
    - Call one constructor from another constructor in the same class
    - Pass the current object as a parameter

```java
class MyClass {
    int value;
    
    MyClass(int value) {
        this.value = value; // Differentiates instance var from parameter
    }
    
    void display() {
        System.out.println(this.value); // Explicit reference
    }
}
```
---

### 4.2) `super` Keyword
- **Refers to the parent class**
- Used to:
    - Access parent class's fields/methods (when overridden in child)
    - Call parent class's constructor
    - Resolve naming conflicts between parent and child class members

```java
class Parent {
    String message = "Parent message";
}

class Child extends Parent {
    String message = "Child message";
    
    void display() {
        System.out.println(super.message); // Accesses parent's message
        System.out.println(this.message);  // Accesses child's message
    }
}
```
---

### 4.3) Key Differences

| Feature        | `this`                          | `super`                          |
|---------------|--------------------------------|--------------------------------|
| **Reference**  | Current instance               | Parent class instance          |
| **Variables**  | Current class fields           | Parent class fields            |
| **Methods**    | Current class methods          | Parent class methods           |
| **Constructor**| Calls current class constructor| Calls parent class constructor |
| **Usage**      | Within any instance method/constructor | Only in child class context |

---

### 4.4) Constructor Chaining Example

```java
class Vehicle {
    int wheels;
    
    Vehicle(int wheels) {
        this.wheels = wheels;
    }
}

class Car extends Vehicle {
    String model;
    
    Car(int wheels, String model) {
        super(wheels); // Calls Vehicle constructor
        this.model = model;
    }
}
```
---

### 4.5) When to Use Each

**Use `this` when:**
- You need to resolve name conflicts between instance variables and parameters
- You want to explicitly show you're working with current class members
- Implementing constructor chaining within the same class

**Use `super` when:**
- You need to access overridden parent class members
- Implementing constructor chaining from child to parent
- You specifically need parent class functionality despite having overrides

Both keywords are resolved at compile time and cannot be used in static contexts.

---

     




