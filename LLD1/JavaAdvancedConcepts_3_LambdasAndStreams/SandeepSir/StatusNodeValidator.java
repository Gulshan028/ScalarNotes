package LLD1.JavaAdvancedConcepts_3_LambdasAndStreams.SandeepSir;

public class StatusNodeValidator implements Validator{
    @Override
    public boolean validate() {
        System.out.println("Validating Status node");
        return true;
    }
}
