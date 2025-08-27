package LLD1.JavaAdvancedConcepts_2__Collections;

public class Student implements Comparable<Student> {
    private int age;
    private String name;
    private double psp;
    private double attendance;

    public Student(int age, String name, double psp, double attendance) {
        this.age = age;
        this.name = name;
        this.psp = psp;
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public double getPsp() {
        return psp;
    }

    public double getAttendance() {
        return attendance;
    }



    @Override
    public int compareTo(Student other) {
        /// /Comparison logic
/*        for ascending order:
        if(this.psp < other.psp) return -1; //we can return any negative value
        else if(this.psp > other.psp) return 1; // we can return any positive value
        else return 0;
 */
        //we have replaced all the above lines with this just one line. This style is used in most of the codebases
        return (int) (this.psp - other.psp);
        /*
 // In case, we need to sort based on more than one parameter. Follow as below:

        if(this.psp < other.psp) return -1; //we can return any negative value
        else if(this.psp > other.psp) return 1; // we can return any positive value
        else{ // the case when two students have equal psp

            if(this.attendance < other.attendance) return -1;
            else if (this.attendance > other.attendance) return 1;
            else return 0;
        }

         */

        //descending order


        /*
        if(this.psp < other.psp) return 1; //we can return any negative value
        else if(this.psp > other.psp) return -1; // we can return any positive value
        else return 0;
        OR
        return (int) (other.psp - this.psp);
         */




    }

}
