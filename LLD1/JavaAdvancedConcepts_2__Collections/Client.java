package LLD1.JavaAdvancedConcepts_2__Collections;

import java.util.*;

public class Client {
    public static void main(String[] args) {
        Student s1 = new Student(21, "Taposhii", 91.00, 99.50);
        Student s2 = new Student(22, "Sonakshi", 92.87, 95.36);
        Student s3 = new Student(23, "Soumya", 93.89, 92.85);
        Student s4 = new Student(24, "Dolly", 92.87, 85.36);

        List<Student> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);

//// Sorting based on COMPARABLE INTERFACE:

        Collections.sort(list);
        // we have sorted the list based on psp in increasing order using above `Collections.sort(list);`
        for(Student student: list){
            System.out.println(student.getName()+" "+student.getPsp());
        }
        //// Sorting based on COMPARATOR INTERFACE:
        Collections.sort(list, new StudentAttComparator());
        for(Student student: list){
            System.out.println(student.getName()+" "+student.getPsp()+" "+student.getAttendance());
        }

        Collections.sort(list, new StudentComparator());
        for(Student student: list){
            System.out.println(student.getName()+" "+student.getPsp()+" "+student.getAttendance());
        }

        //// PRIORITYQUEUE

        System.out.println("-------PRIORITYQUEUE PART---------------------------------------------------------------------------------------------");
       Queue<Integer> pq = new PriorityQueue<>();
        pq.add(1);
        pq.add(7);
        pq.add(-96);
        pq.add(51);
        pq.add(85);

        while(!pq.isEmpty()){
            System.out.println(pq.poll());
        }
        /*
        above is the case when Integers are the element whose sorting and comparison is known to java hence things happened smoothly.
         */

        //let's make a Priority Queue of Student objects which implements Comparable interface:
        Queue<Student> pq1 = new PriorityQueue<>();
        pq1.add(s1);
        pq1.add(s2);
        pq1.add(s3);
        pq1.add(s4);
        while(!pq1.isEmpty()){
            Student st = pq1.poll();
            System.out.println(st.getName()+" "+st.getPsp());
        }
        //for this case too, things happen smoothly and we get the output because the class `Student` implements the `Comparable` interface
        //and hence has a natural order of sorting. Thus, the `Priority queue` can compare the objects of `Student` class


        Student1 s11 = new Student1(21, "Taposhii", 91.00, 99.50);
        Student1 s22 = new Student1(22, "Sonakshi", 92.87, 95.36);
        Student1 s33 = new Student1(23, "Soumya", 93.89, 92.85);
        Student1 s44 = new Student1(24, "Dolly", 92.87, 85.36);

        //NOw, we make PriorityQueue of Student1 class which doesn't implement Comparable interface and hence the Student1 class doesn't have natural order of sorting
        //as a result, when poll() command is used, the PriorityQueue cannot compare objects and give out the minimum object.
        //so we get error.
        //LEARNING: for all of this to happen, it is important that `Student1` class should implement a `Comparable` interface


        Queue<Student1> pq10 = new PriorityQueue<>();

        pq10.add(s11);
        pq10.add(s22);
        pq10.add(s33);
        pq10.add(s44);
        while(!pq10.isEmpty()){
            Student1 st = pq10.poll();
            System.out.println(st.getName()+" "+st.getPsp());
            //here, we get error
        }



        //if natural sorting isn't present  or if we want custom sorting then we can use comparator interface too.
        //lets see the example as follows
        //so, we need to pass the comparator object in the PriorityQueue constructor parentheses
        Queue<Student1> pq11 = new PriorityQueue<>(new STU1Comparator());
        pq11.add(s11);
        pq11.add(s22);
        pq11.add(s33);
        pq11.add(s44);
        System.out.println("with comparataor");
        while(!pq11.isEmpty()){
            Student1 st = pq11.poll();
            System.out.println(st.getName()+" "+st.getPsp()+" "+st.getAttendance());
            //here, pq11 is printed
        }

        //// SET
        System.out.println("-------SET PART---------------------------------------------------------------------------------------------");
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new LinkedHashSet<>();
        Set<Integer> set3 = new TreeSet<>();

        set1.add(80);set1.add(200000);set1.add(1);set1.add(556);set1.add(53);
        set2.add(80);set2.add(200000);set2.add(1);set2.add(556);set2.add(53);
        set3.add(80);set3.add(200000);set3.add(1);set3.add(556);set3.add(53);

        System.out.println(set1);
        System.out.println(set2);
        System.out.println(set3);
        /*
        on printing the three sets, you will see that the order of HashSet is random w.r.t. to the order of insertion.
        the order of LinkedHashSet is the same as the order of insertion.
        the order of TreeSet is sorted.
        that's the difference which we wanted to show.

         */











    }
}
