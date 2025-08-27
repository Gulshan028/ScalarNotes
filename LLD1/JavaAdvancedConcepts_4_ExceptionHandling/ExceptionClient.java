package LLD1.JavaAdvancedConcepts_4_ExceptionHandling;

import LLD1.JavaAdvancedConcepts_2__Collections.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;

public class ExceptionClient {
    //we create divide method
    public static int divide(int a, int b){
        return a/b;
    }
    public static void main(String[] args) {
        int a = 10;
        int b = 0;

        ////System.out.println(divide(a,b));

        //here, since we divided by zero, our code abruptly broken as we got `ArithmeticException`:- / by zero
        System.out.println("Hello World!");
        /*
        as soon as we run this much code, we get `ArithmeticException` as follows:
        "C:\Program Files\Java\jdk-17\bin\java.exe" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2025.1\lib\idea_rt.jar=56254" -Dfile.encoding=UTF-8 -classpath C:\Users\gulsh\ScalarNotes\out\production\ScalarNotes LLD1.JavaAdvancedConcepts_4_ExceptionHandling.ExceptionClient
Exception in thread "main" `java.lang.ArithmeticException`: / by zero
	at LLD1.JavaAdvancedConcepts_4_ExceptionHandling.ExceptionClient.divide(ExceptionClient.java:5)
	at LLD1.JavaAdvancedConcepts_4_ExceptionHandling.ExceptionClient.main(ExceptionClient.java:10)

Process finished with exit code 1
         */

        /*

        let's decode this error message: In red colour,
        1. in the first line, exception type (`ArithmeticException` in this case) and its statement ( / by zero) is given.
        2. from the second line:  logs are given in the decreasing order. So, the 2nd line in red colour tells that this is the
                                    line where the exception occurred, line no 5 in this case.
        3. third line in red colour: it tells, above 2nd line was called from which line, line no 10 in this case.
         */
        //so, we can say that root of our exception is line number 10.
        //we can also see that once there was an exception then our code literally just stopped. It didn't execute any lines of code after
        //line number 10, hence line number 23 is not executed.
        /*
        this `ArithmeticException` is an Unchecked exception (i.e. Runtime exception), hence the compiler didn't warn us anything while compilation.
        We got the exception only after running the code.
         */

        //remove `throws FileNotFoundException` from `main()` method signature if present, to understand this part.
        //uncomment below two lines to understand better
        ////File file = new File("abc.txt");
        ////FileReader fileReader = new FileReader(file);

        /*
        so, here, you can see that we got an error when we hover over the red underline showing exception.
        It reads: Unhandled exception: java.io.FileNotFoundException
        It means the file which we are trying to read is not found.

        The great part is that the Compiler is able to throw an exception. It is telling at compile time to handle the exception.
        This is the Checked Exception.
        Now, we can either handle the exception using tyr-catch block or pass the exception to the caller function as explained below.
         So, we are passing to the caller function. Our statement is in the main() method. So, in the method signature of main() method, we are writing
         `throws FileNotFoundException`, which passes the exception to the caller function. Now, you can see that error red underline is gone i.e. error is gone
         */
//COMMENTING THOSE CODE STATEMENTS WHICH CAN CAUSE ISSUE BY 4 SLASH LINES
        //now, we are creating readFile() method, see at the end of this main() function,
        //Now, remove `throws FileNotFoundException` from main() method signature, if present to do below operation.

       //// readFile("abc.txt");


        /*
        we are getting,
        Unhandled exception: java.io.FileNotFoundException

        now, we can either handle it or pass it. if we pass it, then the error red underline will go away. Try it once and undo it.
        But, we will not pass any exception via `main()` method. Because, tyr to understand that main() is the first method to operate,
        then if we pass it via `main()` method, then no `method` will handle that exception, and hence we can say that this
        particular exception won't be resolved or handled.

        POINT TO REMEMBER: Hence, ideally, we should always handle the exception in main() method and not pass it.
        As main() is the first method to be operated. So, if main() method passes any exception, then that exception cannot be handled anywhere else, and
        it will be shown to the user/client.
         */

        //now, let's handle the `divide by zero` exception.

        try{
            int c = divide(a,b);
            System.out.println(c);
        } catch (ArithmeticException e) {
            System.out.println("Please don't divide by zero.");
        }

        System.out.println("Hello World!");

        /*
        on running this much part, we see that, once the exception is caught, then the catch block executes (However, statements in try block after the exception has
        occurred have not executed). Thus, we are sending a graceful message to the user and also the code statements after the catch block have run.
        Thus, we can see that, as soon as we have handled the exception, then the code is not stopping abruptly.
         */

        ////CUSTOM EXCEPTION:
        //we are making one method named `findStudentById()`. See below.
        /*
        using the below try-catch block, the error which was propagated upwards finally comes here and hence we are
        handling it here. If I remove the try-catch block and call the method simply, then I will get the compilation error.
         */

        try {
            findStudentById(10l); // Top-level method that initiates the process
        } catch (StudentNotFoundException e) {
            System.err.println("Student Not Found: " + e.getMessage());
        }

    }

    //// readFile() method:

//in below method signature, I can write `throws Exception` instead of writing multiple exceptions with the `throws`
// keyword. When this `Exception` will be thrown upwards and handled, then if a specific catch block is available, then
    //that particular catch block will be executed, not the generic one. So, our purpose of handling the exception is not impacted,
    //however, still writing multiple exception names with the `throws` keyword is advised as it improves readability.

    public static void readFile(String fileName) throws FileNotFoundException, InterruptedException {

        //here, as shown in this way above, we can throw multiple error and propagate them upwards,

        File file = new File(fileName);
        FileReader fileReader = new FileReader(fileName);
        Thread.sleep(100);

        //this `InterruptedException` error will be shown in main() method when the readFile() method will be called.
        //so, while calling this function, we will need to handle this exception using try-catch block
        /*
        here, we were getting `FileNotFoundException` hence we added `throws FileNotFoundException` in the method signature
        to pass exception to the method, calling this statement.
        So, our exception is now passed to the main() method.
        Now, go inside main() function.
         */

    }
//we have used this type of methods in project module
    public static Student findStudentById(Long StudentId) throws StudentNotFoundException{

        //below lines gives you a student object from student-repository which is found by `id` if it is available. Hence, we are using Optional<object> here.
        //as the student can be available or not available

        ////Optional<Student> optionalStudent = studentRepository.findById(id);

        //let's say, repository passed below object

        Optional<Student> optionalStudent = Optional.empty();

        if(optionalStudent.isEmpty()){
            throw new StudentNotFoundException("Student with the given id doesn't exist.");

            /*
            the above line is red underlined. Because this is a checked exception, and we are throwing exception and not handling it.
            Hence, the compiler is forcing us to handle or pass the exception. So, now we will pass the exception to the caller function.

            Now, as we have added "throws StudentNotFoundException" in the method signature. Thus, the compiler warning is gone now.

            Alternatively, we can make this `StudentNotFoundException` extend `RuntimeException` in its class definition. By this way, it would have become
            an UnChecked Exception. Then, the Compiler won't give us any warning. For trying this, remove "throws StudentNotFoundException"
            from method signature first and then try this alternate solution.

            Thus, ideally, we should make Custom Exceptions as the Checked Exceptions. It helps us because the Compiler gives us warnings.

             */
            /*
            Also, note that here we are using `throw` keyword and not `throws`. So, remember, when we are throwing any
            custom exception, we use `throw` keyword, and
             We use the `throws` keyword in the method signature to propagate the exception upwards in the call stack.
             */
        }

        return null;
    }
}
