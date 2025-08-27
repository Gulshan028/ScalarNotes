package LLD1.JavaAdvancedConcepts_3_LambdasAndStreams.DeepakSir;

public class Student {
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

    public int getAge() {
        return age;
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
}
