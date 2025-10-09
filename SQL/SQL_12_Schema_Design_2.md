# Lecture | SQL 13: Schema Design 2

## I] Points to Remember

### Q.1) Which is better among Subquery and Joins?
- Answer: Look, We already saw that both have almost the same Time complexity of around 
  O(N*M). Now, We chose Subquery as it provides better readability. It is easy to understand.

---

## Links:
1. Netflix requirements for Schema design: https://docs.google.com/document/d/1xQbcv-smnV_JY6NUb4gz2owwPaQMWdoWty6PZyFEsq8/edit?tab=t.0#heading=h.54zl5r4gjxxe
2. lld case study: https://github.com/ashishps1/awesome-low-level-design
3. SQL Revision notes: https://docs.google.com/document/d/1jfPCH7Alv-PTV8NDi01uescOt1Nbr9D2F8YdeusNFR4/edit?tab=t.0

Q2. Design Scaler Academy Schema - RT

Tables:

1. students
  - student_id (int, Primary Key, AUTO_INCREMENT)
  - email (String, UNIQUE, NOT NULL)
  - name (String, NOT NULL)
  - phone_number (int, not null, UNIQUE)
  - problem_solve_percentage
  - attendance_percentage
  - batch_id (Foreign key to batches.batch_id, int)
    - Purpose: Here, batch_id denotes current batch of the student, considering   
      student can join multiple batches but one after the other and not simultaneously
      in multiple batches

2. batches
  - batch_id (Primary Key, AUTO_INCREMENT)
  - name

3. lectures
  - lecture_id (Primary Key, AUTO_INCREMENT)
  - topic_name (String, NOT NULL)
  - assignment_id (Foreign Key to assignments.assignment_id)
  - homework_id (Foreign Key to homeworks.homework_id)
  - prelecture_content (NOT NULL)
    -Purpose: since every class needs to have prelecture_content, this field cannot
    be NULL. Also, prelecture_content can be made beforehand. It is not something that
    will become ready during the class, hence it is part of this table.

4. assignments
  - assignment_id (Primary Key, AUTO_INCREMENT)
  - name
5. homeworks
  - homework_id (Primary Key, AUTO_INCREMENT)
  - name

6. problems
  - problem_id (Primary Key, AUTO_INCREMENT)
  - name
  - assignment_id (int, Foreign key -> assignments.assignment_id)
  - homework_id (int, Foreign key -> homeworks.homework_id)

7. classes
  - class_id (Primary Key, AUTO_INCREMENT)
  - instructor_id (Foreign Key to instructors.instructor_id)
  - date *//this shows the timeline of the class which the student can see by joining with this table.*
  - time *//this shows the timeline of the class which the student can see by joining with this table.*
  - lecture_id (Foreign Key to lectures.lecture_id)
  - lecture_notes (NOT NULL) //since every class needs to have prelecture_conten, this field cannot be NULL

8. instructors
  - instructor_id (Primary Key, AUTO_INCREMENT)
  - name

9. students_classes (Mapping table to show Many_to_Many relation between students and classes)
  - student_id (Foreign Key to students.student_id)
  - class_id (Foreign Key to classes.class_id)
  - Primary_Key(student_id, class_id)
  - Purpose: To show all the classes the student has attended and is supposed to attend in the future.

10. problem_status_type
  - problem_status_id (Primary Key, AUTO_INCREMENT)
  - value

11. students_problems
  - student_id (Foreign Key to students.student_id)
  - problem_id (Foreign Key to problems.problem_id)
  - problem_status_id (Foreign Key to problem_status_type.problem_status_id)
  - Primary_Key(student_id, problem_id)

12. instructors_classes (Mapping table for Many_to_Many relation between Instructor and their classes)
  - instructor_id (Foreign Key to instructors.instructor_id)
  - class_id (Foreign Key to classes.class_id)
  - Primary_Key(instructor_id, class_id)
  - Purpose: Instructor should be able to see all their classes.


13. students_batches (Mapping table for Many_to_Many relation between students and their batches)
  - student_id (Foreign Key to students.student_id)
  - batch_id (Foreign Key to batches.batch_id)
  - Primary_Key(student_id, batch_id)
  - Purpose: it has history of all the batches student has been a part of

14. instructors_lectures
  - instructor_id (Foreign Key to instructors.instructor_id)
  - lecture_id (Foreign Key to lectures.lecture_id)
  - Primary_Key(instructor_id, lecture_id)

15. Foreign Keys
  - lectures(assignment_id) refers assignments(assignment_id)
  - lectures(homework_id) refers homeworks(homework_id)
  - assignments(problem_id) refers problems(problem_id)
  - homeworks(homework_id) refers problems(problem_id)
  - classes(instructor_id) refers instructors(instructor_id)
  - classes(lecture_id) refers lectures(lecture_id)
  - students_classes(student_id) refers students(student_id)
  - students_classes(class_id) refers classes(class_id)
  - students_problems(student_id) refers students(student_id)
  - students_problems(problem_id) refers problems(problem_id)
  - students_problems(problem_status_id) refers problem_status_type(problem_status_id)
  - assignments(problem_id) refers problems(problem_id)
  - instructors_classes(instructor_id) refers instructors(instructor_id)
  - instructors_classes(class_id) refers classes(class_id)
  - students_batches(student_id) refers students(student_id)
  - students_batches(batch_id) refers instructors(batch_id)
  - instructors_lectures(instructor_id) refers instructors(instructor_id)
  - instructors_lectures(lecture_id) refers lectures(lecture_id)

16. Cardinality of Relations
  - Between students and
  - Between lectures and assignments ->
  - Between students and
  - Between students and 

