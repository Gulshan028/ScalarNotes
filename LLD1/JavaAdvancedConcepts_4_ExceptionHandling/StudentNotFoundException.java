package LLD1.JavaAdvancedConcepts_4_ExceptionHandling;

//we may not always use the exception provided by java. We can make our own exceptions. They are called as Custom Exceptions.
//when we write "extends Exception" keywords, then only the below class becomes a "Custom Exception".

public class StudentNotFoundException extends Exception{

    //it comes in the category of "Checked Exception" now, as it directly extends the `Exception` class.

//while throwing the custom exception, we just create the object of the custom exception class and pass the message.
// Hence, in the current class Constructor, we simply call the Constructor of the parent class.

    public StudentNotFoundException(String message) {
        super(message);
    }
}
