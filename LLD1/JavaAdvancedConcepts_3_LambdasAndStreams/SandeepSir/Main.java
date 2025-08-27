package LLD1.JavaAdvancedConcepts_3_LambdasAndStreams.SandeepSir;

public class Main {
    public static void main(String[] args) {
        //First of all, I create `validator` functional interface with one `validate()` method.
        //now, I create StatusNodeValidator class which implements validator interface.

        //let's use this class to create the object
        StatusNodeValidator statusNodeValidator = new StatusNodeValidator();
        statusNodeValidator.validate(); //run the Main class

        //now, say, this class is not used again since its logic is very unique. so, we create a new class for our other use,
        //and use its object as below.

        ReviewNodeValidator reviewNodeValidator = new ReviewNodeValidator();
        reviewNodeValidator.validate();  //run the Main class

        /*
        the problem is that even this `class` is of no further use to us and creating so many single use classes will
        make our `class-loader` more heavy, and we don't want that.
        Here comes Anonymous class. Let's see how to use it.
         */
        Validator loginNodeValidator = new Validator() {
            @Override
            public boolean validate() {
                System.out.println("loginNOde validator");
                return true;
            }
        };
        //by above statement, my anonymous class is ready to use along with the object, i.e. we have directly created the
        //object with the interface name `Validator` reference.
        //since, we cannot create an object of an interface. Here, this anonymous class allows us to create the same.
        //however, we just need to provide the implementation of the abstract methods.

        //let's directly use the object

        loginNodeValidator.validate();

        //the moment, the method is over, the object will be collected by Garbage Collector.
        //So, there is no extra memory overhead and the class-loader will not get heavy


    }
}
