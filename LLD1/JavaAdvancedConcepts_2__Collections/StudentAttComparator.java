package LLD1.JavaAdvancedConcepts_2__Collections;

import java.util.Comparator;

public class StudentAttComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return (int)(o1.getAttendance() - o2.getAttendance());
    }

}
