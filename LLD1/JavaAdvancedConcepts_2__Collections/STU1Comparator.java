package LLD1.JavaAdvancedConcepts_2__Collections;

import java.util.Comparator;

public class STU1Comparator implements Comparator<Student1> {
    @Override
    public int compare(Student1 o1, Student1 o2) {
       return (int)(o1.getAttendance() - o2.getAttendance());
    }
}
