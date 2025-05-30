# Lecture | Backend LLD: OOP-1: Intro to OOP

## Programming Paradigm

### 1] Procedural Programming
   1.1) It is mainly organised, using the functions/methods. One function can call other functions. 
        Operations start from the main() method.
   1.2) It has a 'struct' (short form for 'structure') which is user defined datatype. 
        ex:- struct Student{
                 int id;
                 String name;
                 int class;
             };
   1.3) Struct  has no methods like a class in java has. Also, there is no concept of 'public', 'private'        hence the variables inside the struct are visible to everyone.
   1.4) Consider an operation:
        ```java
        printStudent(struct Student){
                print(Student.id);
                print(Student.name);
                print(Student.class);
        };
        ```
        here, you can see that 'printStudent', which resembles a verb in general world is acting on 
        'Student', which resembles a subject in real world. Whereas, in real world, we see that a 
        subject acts on a verb. This thing becomes difficult to scale.
### 2] Object Oriented Programming
    2.1) Here, the user defined data-type is called a 'class'. It has 'variables' as well as 'methods' 
         inside it. for ex:- 
                      ```java 
                      class Student {
                             int id;
                             String name;

                             public printStudent(){
                                    print(id);
                                    print(name);
                             }

                       }
                       ```
    2.2) from main(), we create student object and call printStudent() method on that object as, 
         ```java
         Student st = new Student();
         st.print();
         ```
### 3] Cons of Procedural Programming
       Since, in struct, the data is there and methods aren't there. So, some external method acts on
       the data. Hence, this practice makes, 
       1) difficult to design complex systems
       2) understanding is difficult
       3) difficult to debug
       4) becomes a maggi code
### 4] Doubt
    Know the difference between Entity and Object.
### 5] Abstraction 
       Java has 1 Principle(viz.Abstraction) and 3 pillars(viz.Encapsulation, Polymorphism, Inheritance).
       The 3 pillars are used to achieve one principal. 
    5.1) Abstraction in Java is a core principle of object-oriented programming (OOP) that focuses on 
         hiding complex implementation details and exposing only necessary information to the user. It 
         simplifies programming by allowing developers to concentrate on an object's functionality rather         than its underlying implementation. Ex- Driver focuses on driving the car rather than its 
         internal functionality.
    5.2) 
