# 1) Programming paradigms:
- “Programming paradigms define the style of writing code. Broadly, they are classified into imperative and declarative. In imperative programming, we explicitly tell the system how to perform a task using step-by-step commands. In declarative programming, we describe what we want, not how to achieve it. For example, traditional loops in Java are imperative, while Java Streams are declarative because we express the operation, not the control flow.”
- “Common paradigms include Imperative, Procedural, Object-Oriented, Functional, and Reactive.
   - In imperative programming, we write step-by-step instructions for the system to execute.
   - In procedural programming, we organize these instructions into reusable functions, making the code modular. For example, instead of writing all logic in main, we create a sum(a, b) function and call it.”
   - “Object-Oriented Programming models real-world entities as objects. We use classes as blueprints to create objects. Each object encapsulates state (properties/fields) and behavior (methods). This separation of concerns improves modularity, reusability, and maintainability.”
   - “Both functional and reactive paradigms are declarative. In functional programming, we provide the input once, the function processes it, returns the output, and the computation ends. It is a one-time transformation. Reactive programming, on the other hand, is continuous and event-driven. We subscribe once, and whenever new data or events arrive, the system automatically reacts and processes them.”

# 2) Object-Oriented Programming (OOP) concepts:
- “A class is a blueprint that defines fields, methods, and constructors. An object is an instance created using the constructors of that class. The object represents a real-world entity and holds its own data in the fields. Using that data, it can invoke the class’s methods to perform actions.”
- OOP has four pillars: Abstraction, Encapsulation, Inheritance, and Polymorphism.
- Abstraction is the concept of hiding complex implementation details and exposing only the necessary parts. It focuses on what an object does, not how it does it. In Java, we achieve abstraction using interfaces and abstract classes—similar to how APIs expose behavior while hiding internal logic.
- Encapsulation is about hiding the internal state of an object and allowing controlled access through public methods. It protects data and enforces boundaries using access modifiers.
- Inheritance allows a child class to acquire the fields and methods of its parent class. Constructors are not inherited, but the child can invoke them using super. This promotes code reuse and establishes an is-a relationship.
- Polymorphism is the ability of an object to take multiple forms. In Java, it appears as method overloading (same method name, different parameters) and method overriding (same method, different behavior at runtime).

# 3) Access Modifiers:
- The access modifiers in Java specifies the accessibility or scope of a field, method, constructor, or class. We can change the access level of fields, constructors, methods, and class by applying the access modifier on it.
- There are four access modifiers in Java:
   - Public: The member is accessible from any other class.
   - Private: The member is accessible only within its own class.
   - Protected: The member is accessible within its own package and by subclasses.
   - Default (no modifier): The member is accessible only within its own package.

# 4) Getters and Setters:                   
- Getters and setters are public methods used to provide controlled access to private fields. Getters read the value, and setters update it. Setters can also contain validation logic, ensuring the object’s state remains valid.

# 5) Constructors:
- Constructors are special methods used to initialize an object’s state. If a class defines no constructor, the compiler provides a default no-argument constructor that initializes fields to default values. A parameterized constructor allows setting fields at the time of object creation. Once we define any constructor, the default one is not generated automatically, so it must be written explicitly. Constructors can contain validation, but should remain lightweight.
- `new` is responsible for allocating memory in the heap. The constructor initializes the object’s fields. Finally, the `new` keyword returns the reference of that newly created object, which is assigned to the variable.
- As soon as an object is created using `new`, memory is allocated in the heap. All fields are first initialized to their default values. Then, the constructor’s code executes to set meaningful values for those fields.
- Constructor overloading means defining multiple constructors in a class with different parameter lists. It allows creating objects in different ways. The compiler differentiates them using the constructor signature, which depends on the number, type, and order of parameters.
- Shallow copy vs Deep copy:
   - Shallow copy duplicates only the reference, so both variables point to the same object in heap memory. Any change through one reference affects the same underlying object.
     Deep copy creates a completely new object along with copies of all its nested objects, so changes in one do not affect the other.

# 6) JVM, JRE, and JDK:
- JVM executes Java bytecode, which enables platform independence—hence ‘write once, run anywhere.’
  JRE = JVM + core runtime libraries(`java.lang`, `java.util`, etc.) required to run Java programs.
  JDK = JRE + developer tools like javac, debugger, etc.

End users need JRE to run applications, while developers need JDK to build them.

# 7) Pass by Value vs Pass by Reference:
- Java is strictly pass-by-value. For primitives, the actual value is passed. For objects, a copy of the reference is passed. This allows methods to modify the object’s state, but reassignment inside the method does not affect the original reference.

# 8) Static vs Instance:
- When you add the static keyword to a variable, it means that the variable is no longer tied to an instance of the class. This means that only one instance of that static member is created which is shared across all instances of the class. 
- Static variables are stored in the Method Area (or Metaspace in Java 8+), not in the heap. This is a separate memory region from where instance variables are stored. Instance variables reside in the heap as part of object instances, while static variables exist independently of any object instance.
- Static variables are initialized when the class is loaded and can be accessed without creating an instance of the class.
- Static methods are accessed using the class name and the dot operator. You don’t need to create an instance of the class to access the static method.
- Static methods can only access static variables and other static methods. They can not access non-static variables and methods by default. If you try to access a non-static variable or method from a static method, the compiler will throw an error.
- Static methods can access non-static members only through an object reference, as they need an instance context.

# 9) Inheritance:
- Inheritance allows a child class to inherit fields and methods from a parent class using the `extends` keyword. It helps in code reuse.
- For example, in an institute, all users—students, instructors, TAs, Mentors—share common data like name, email, and phone. We can put these in a `User` parent class, and each specific type can extend it.
- Java does not support multiple inheritance with classes because of the diamond problem—if two parent classes have the same method, Java cannot decide which one to use. But we can achieve this using interfaces.
- Java supports single, multilevel (grandparent → parent → child), and hierarchical inheritance (one parent, many children).
- Initialising the parent attributes is the responsibility of the parent constructor, whereas, initialising only the child attributes is the responsibility of the child class. Since constructors are not inherited, Java uses constructor chaining. `super()` must be the first statement in the child constructor—either explicitly or implicitly—so that the parent part of the object is constructed first. This guarantees that the parent’s attributes are initialised before the child’s attributes, maintaining a valid object state.
- In Inheritance, if parent class attributes are private then even the child class cannot access those attributes, in such cases, getters and setters are useful. But those attributes can surely be initialised using `super()` keyword in the child class constructor.
- In Java, the `extends` clause must come before the `implements` clause

# 10) Polymorphism:
- Polymorphism allows the expression of one interface with multiple implementations. At runtime, the JVM decides which implementation to execute based on the actual object, which is realized through method overriding in subclasses. This enables dynamic behavior and loose coupling in design.
- Polymorphism means ‘many forms’. In Java, it is mainly of two types: method overloading and method overriding.
   - Method overloading means having multiple methods with the same name but different parameter lists. This is compile-time polymorphism.
   - Method overriding happens when a child class provides its own implementation of a method inherited from the parent class. The method name and parameters remain the same, but the behavior changes. This is runtime polymorphism and depends on the actual object.
   - Upcasting also known as Implicit casting, where reference is of parent type and at runtime the object created is of child type. However, the parent reference variable is only aware of the properties and methods present in the parent object hence it can only call the methods and properties, which are present in the parent class. However, if child has overridden any method then at runtime that overridden method will be run.
   - Downcasting is converting a parent-type reference into a child-type reference, where the parent reference is already pointing to a child object. This is NOT allowed directly and requires explicit casting. It passes compilation, but at runtime the JVM checks the actual object type. If the object is not an instance of that child class, a ClassCastException is thrown.
   - ```java
    Animal a = new Dog();  // ✅ Upcasting (Implicit)

  Dog d = (Dog) a;   // ✅ Downcasting (Explicit)
  Dog d = new Animal();  // ❌ Compilation Error
  // Not allowed: Can't store parent object in child class reference as every Animal is not Dog

  Dog d1 = (Dog) new Animal();     //compiles successfully but fails at runtime
  d1.walk(); //❌ Runtime Error
  d1.bark(); //❌ Runtime Error
   // Not allowed: ClassCastException at runtime. As Dog must be having more methods and attributes which are not part of Animal. So, Java doesn't allow DownCasting
  ```

# 11) What is "Method Hiding"?
- Method hiding is a feature in Java where a subclass defines a `static` method with the same signature (name and parameter list) as a `static` method in its superclass. Since static methods belong to the class and not to the object, they are not overridden but hidden.
- You cannot use the `@Override` annotation for a hiding method. If you do, the compiler will throw an error because it's not actually overriding an instance method.
- **Execution is Determined by Reference Type (Not Object Type):** This is the most crucial difference from method overriding.
   - Overriding (instance methods): The JVM decides which version of an instance method to call at runtime based on the actual type of the object (new SubClass()).
   - Hiding (static methods): The compiler decides which version of a static method to call at compile time based on the reference type (SuperClass ref;).
- Best Practice: Because method hiding does not exhibit polymorphic behavior and is often confusing, it is generally considered bad practice. It can easily lead to bugs, as the output of the program changes based on the reference type, not the object type.
   - Recommendation: Avoid hiding static methods. If you need a static method in a subclass, give it a different name to prevent any confusion with the superclass method.

# 12) Interfaces:
- In object-oriented programming, interfaces define contracts. They declare a set of methods that a class must implement without providing the implementation. This enforces a common behavior across unrelated classes and enables loose coupling, making the system more flexible, testable, and extensible.
- Interfaces define behavior, not state. We cannot create objects of an interface, and an interface can extend multiple interfaces, enabling multiple inheritance of type, though it cannot extend any class. Interfaces do not have constructors because they are not responsible for object initialization. As a result, they do not hold instance attributes; any variable declared in an interface is implicitly `public`, `static`, and `final`, and must be initialized at the time of declaration.
- Interfaces can also contain static and default methods. Static methods belong to the interface itself and cannot be overridden. Default methods provide a concrete implementation and can be overridden by implementing classes or further extended by other interfaces. This allows interfaces to evolve without breaking existing implementations.
- Interfaces primarily have abstract methods, which contain only the method signature and no body, and end with a semicolon. In interfaces, these methods are implicitly `public` and `abstract`, so there is no need to specify the `abstract` keyword. These methods define a contract, ensuring that all implementing classes have consistent method name while allowing each class to have flexible implementation.
- **Default methods:-** Before Java 8, interfaces could contain only abstract methods. This created a design problem—adding a new method to an interface would break all existing implementations with compilation errors. To solve this, Java introduced default methods. Default methods are public, include an implementation, and are declared using the default keyword. All implementing classes automatically inherit them, and they may choose to override them if custom behavior is needed. This allows interfaces to evolve without breaking existing code.
- From Java 8 onwards, interfaces can also have:-
   - Static Methods: These methods belong to the interface itself and can be called using the interface name. They should not be inherited or overridden by implementing classes.
- From Java 9 onwards, interfaces can also have:
   - Private Methods: These methods allow internal code reuse between default and static methods while keeping helper logic hidden from implementers. They cannot be accessed outside the interface.
- In short, `default methods` add behavior to implementers, `static methods` provide interface-level utilities, and `private methods` support clean internal implementation without exposing details.
- we can’t use the `final` keyword in the interface definition, as it will result in a compiler error. All interface declarations should have the `public` or `default` access modifier; the `abstract` modifier will be added automatically by the compiler. 
- We use interfaces to add certain behavioral functionality that can be used by unrelated classes. For instance, Comparable, Comparator, and Cloneable are Java interfaces that can be implemented by unrelated classes.
- Remember, Program to Interface, not to implementation to ensure loose coupling and flexibility in design.

# 13) Abstract Classes:
- Abstract classes are blueprints for other classes. They can contain both abstract methods (without implementation) and concrete methods (with implementation). Abstract classes cannot be instantiated directly; they must be subclassed
- Abstract classes are used when multiple related classes share common behavior and state, but still require some methods to have flexible, class-specific implementations. In such cases, we place the common fields and concrete behavior in an abstract class, and declare the varying behavior as abstract methods.
- An abstract class cannot be instantiated, but it can have constructors, fields, concrete methods, and abstract methods. The abstract keyword is mandatory on the class definition, and also on any method that does not provide an implementation. This allows child classes to inherit shared logic while being forced to provide their own implementation for the incomplete behavior.
- "Abstract classes doesn't need to necessarily have abstract methods". An abstract class is declared using the `abstract` keyword, and one of the purpose of an abstract class is to prevent instantiation (i.e., you cannot create an object of an abstract class). Even if it has no abstract methods, marking it abstract ensures it must be subclassed.
- If a class has at least one abstract method, it must be declared abstract.

# 14) Backward Compatibility in Java:
- Backward compatibility is one of the core reasons behind Java’s longevity. It means that code written for older Java versions continues to compile and run on newer Java runtimes. This gives developers and enterprises confidence that their systems will not break with platform upgrades. Since large organizations evolve slowly and run long-lived systems, this trust is critical and encourages gradual adoption of newer Java versions.
- Even when APIs become obsolete, they are first marked as deprecated rather than being removed outright, giving developers sufficient time to migrate to better alternatives. It is important to note that Java guarantees backward compatibility, but not forward compatibility—code written for a newer version is not expected to run on older runtimes.

# 15) `final` keyword:
- The final keyword in Java can be applied to variables, methods, classes, and even method parameters. When applied to a variable, it means the variable can be assigned only once—either at declaration or in the constructor. For reference types, final locks the reference, not the object’s internal state.
- A final method cannot be overridden, which is useful when we want to prevent changes to critical behavior. A final class cannot be extended, meaning it cannot have subclasses and cannot be inherited.
- If a method parameter is declared final, its value cannot be reassigned inside the method, ensuring that the input remains unchanged throughout the method’s execution.