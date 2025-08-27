# Lecture | Backend LLD: OOP-1: Intro to OOP

## CONTENTS: (Sandeep Sir)
1. Programming Paradigms
   - Procedural Programming
   - Object-Oriented Programming
2. OOPS -> Object-Oriented Programming
3. Java's Principle and its 3 Pillars:
   - Abstraction
   - Encapsulation

---

## CONTENTS: (Deepak Kasera Sir)
1. Classes and Objects

---

## I] Programming Paradigm
A programming paradigm is a fundamental style of writing code, defined by how it structures and solves problems—such as imperative (step-by-step commands), etc.

### 1.1) Procedural Programming

1) It is mainly organised using functions/methods. One function can call other functions.  
Operations start from the `main()` method.

2) It has a `struct` (short form for 'structure'), which is a user-defined data type.  
Example:

```java
struct Student {
    int id;
    String name;
    int classRoom;
}
```

3) Struct has no methods like a `class` in Java. Also, there is no concept of `public` or `private`,  
   so the variables inside the struct are visible to everyone.

4) Consider an operation:

```java
printStudent(struct Student) {
    print(Student.id);
    print(Student.name);
    print(Student.classRoom);
}
```

Here, you can see that `printStudent` (a verb) is acting on `Student` (a subject).  
But in the real world, we expect a **subject to act on a verb**.  
This mismatch makes procedural code harder to scale.

---

### 1.2) Object-Oriented Programming

1) In OOP, the user-defined data type is a `class`. It has **variables** and **methods**.  
Example:

```java
class Student {
    int id;
    String name;

    public void printStudent() {
        print(id);
        print(name);
    }
}
```

- Look, `String` is itself an object of *String class*. So, the actual *name* is stored in separate memory called `String Pool`.
  And, the attribute `name` holds the address of the location in the `String Pool` where the name is stored.

2) From `main()`, we create a `Student` object and call the method:

```java
public class Main{
    public static void main(String[] args) {
        Student st = new Student();
        st.printStudent();
    }
}
```
   Here, you can see that, the subject is executing a verb. 

---

### 1.3) Cons of Procedural Programming

Since structs only store data and no methods, external functions manipulate the data.  
This leads to:

- Difficulty in designing complex systems  (because of subject-verb analogy)
- Poor understandability  
- Harder debugging  
- "Maggi" code (spaghetti code)

---

## II] OOPS - Object Oriented Programming
### 2.1) Entities vs. Regular Objects:
1) Entities are special Java objects that:-
   - Persist in a database (survive program termination). 
   - Require a unique identifier (e.g., `@Id`).

2) Methods belong to the entity's `class` and execute in memory, but only field values are stored in the database.

3) Regular objects exist only in memory and are destroyed when the program ends.

### 2.2) Key Point:
Entities = Persistent objects with database-backed storage.

---

## III] Java's Principle and its 3 Pillars:
### 3.1) Abstraction

Java has **1 Principle** — `Abstraction`, and **3 Pillars** — `Encapsulation`, `Polymorphism`, `Inheritance`.  
These 3 pillars help achieve the principle (ex: living healthy life :- Principle; Diet, exercise, sound sleep are pillars).

1) *Abstraction* in Java hides complex implementation and shows only necessary details.  
It helps focus on *what* an object does rather than *how*.  
Example: A driver uses a car without understanding how the engine works.

### 3.2) **Encapsulation:**
- Just like the *capsule* of a medicine serves 2 tasks(holds different chemicals together and protects from outer surrounding).
-  In the same way, Encapsulation in java serves 2 purposes.
   - `class` holds different attributes together.
   -  `Access Modifier` protects by giving limited access.

---

# Deepak Kasera sir's class

## I] Classes and Objects:

### 1.1) Class :
1) `Class` is a blueprint for creating an object. It isn't the actual object. It doesn't occupy the space in the heap memory.
      It just gives the structure to the object. It has `attributes` and `method`.
ex:
```java
class Student {
    int id;
    String name;

    public void printStudent() {
        print(id);
        print(name);
    }
}
```
2) Lets create an object of class Student.
```java
Student st  = new Student();
```
- So, here, LHS **Student** defines the type of the object. 
- **st** is the *reference* as it holds the address of the object in the heap memory. 
- On the RHS, entire part is the actual object.  
- `new` keyword is responsible for allocating memory to the object. The amount of memory allotted depends on the type of the 
   object (i.e. as per the number and kinds of the attributes).

3) In above object *st*, *st* will hold the address (say, @7820) at which the object is created in the heap memory.
     Therefore, st = @7820.

4) 
```java
st.id = 1;
st.name = "Gulshan";
```
This means that go at @7820 location and set id = 1. So, it is like, @7820.id = 1;
Similarly for st.name

5)
```java
st.printStudent();
```
In this way, *st* can call methods also.
The thing to notice here is that the **subject st** is acting on **verb/action printStudent()**.

---

### 1.2) Objects
1)
```java
Student st = new Car();
```
This is not allowed and throws compilation error. Reference of type `st` cannot store address of object of `Car`.

2) In Java, `Objects` are created at runtime. i.e. When we run the program then only the memory is allocated to the object 
in `Heap memory`.

3) The reference variables are stored in the `Stack memory`.

4) REVISION: Arraylist are called `Dyanamic Array` because Arraylist is nothing but array. When an arraylist is initialised 
     for the first time, then, its size is 16. There is a term called "Load factor". So, as we fill the arraylist and 
     if the load factor = 0.75, As the 75% of the arraylist is filled then the arraylist doubles its size. This same concept 
     goes with Hashtable, HashMap, etc.

---

NOTE: Read GitHub notes given by Scalar.


