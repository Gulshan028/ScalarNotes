package LLD1.JavaAdvancesConcepts_1;

public class Calculator<X,Y> {

    private X x;
    private Y y;

    public Calculator(X x, Y y){
        this.x = x;
        this.y = y;
    }

    public void print(X x, Y y){
        System.out.print(x+" ");
        System.out.println(y);
    }
}
