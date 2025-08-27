package LLD1.JavaAdvancedConcepts_2__Collections;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        //custom sorting logic which is based on the custom parameter `marks`
        //where 'marks' is not the attribute of the Student class:
        int marks1 = (int) (0.6* o1.getAttendance() + 0.4 * o1.getPsp());
        int marks2 = (int) (0.6* o2.getAttendance() + 0.4 * o2.getPsp());
        return marks1-marks2;
    }
}
