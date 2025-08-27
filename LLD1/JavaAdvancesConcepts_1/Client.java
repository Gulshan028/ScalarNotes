package LLD1.JavaAdvancesConcepts_1;

import java.util.ArrayList;
import java.util.List;

public class Client<m,n> {
    List<? extends Animal> animal1 = new ArrayList<>();
    public static void main(String[] args) {
        Calculator<String, String> strCalculator = new Calculator<>("Gulshan", "Meshram");
        strCalculator.print("Gulshan", "Meshram");
        Calculator<Boolean, Boolean> booleanCalculator  = new Calculator<>(true, true);
/*
        List<?> allList = new ArrayList<>();
        List<? extends Animal> AnimalAndChildren = new ArrayList<>();
        List<? super Animal> AnimalNPArent = new ArrayList<>();
        allList.add(new Animal());
        AnimalNPArent.add(new LivingBeings());
        AnimalAndChildren.add(new Cat());
        AnimalNPArent.add(new Animal());
        AnimalAndChildren.add(new Animal());
        AnimalNPArent.add(new Cat());

 */





    }
}
