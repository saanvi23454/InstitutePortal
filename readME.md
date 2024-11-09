# HOW TO RUN CODE
- Download the zip folder provided and extract all files.
- In IntelliJ, open a New Project, set it up.
- Replace the `src` directory in this project with the above downloaded `src` directory.
- Compile and run the `Main` class.

## ASSUMPTIONS
1. All admins have the same password, i.e., `admin@123`.
2. Login can only be done if an account already exists, and the password matches the one in record.
3. If a course is deleted by Admin, it is also dropped for all students who have registered for that course in the current semester.
4. If a Professor or Admin assigns a grade to a student, they have to ensure that the grade has not been assigned earlier for that course.
5. In complaints, local date-time of the system will be used for sorting.
6. When adding a course, the semester provided is valid, and the credit provided is 2 or 4.
7. Default enrollment limit is 600.
8. A Professor will only add grades for his/her own Course and Students.
9. No field called "roll number" for a Student; it is contained in the email itself.
10. Only valid emails are signed up (no separate checking).
11. Student's semester is between 1 and 8.
12. A student can only drop a Course if a grade has not been assigned yet.
13. After 8 semesters, the student becomes frozen so no new semester can be added.
14. A student will not register for the same course multiple times in the same semester.
15. A student can register for any course in a previous or earlier semester.
16. A student will not drop a course once a grade is added for any other course as well.
17. EmailID for each person is unique, and each person will sign up only once; otherwise, the previous entry is overwritten.
18. Professor and Admin will always enter valid email IDs of each other and students when performing tasks (equivalent to dropdown menus where typos are not possible).
19. Admin should not delete a course for which a grade has been assigned to any student in the current semester.
20. To keep it simple, logging activities like timestamp of signup, login, sign-out, etc., are not maintained.
21. Other basic logical assumptions like students cannot change their semester, etc., are applicable.
22. Students should register after the professor has been assigned. In case a student registers for a course before a professor is assigned, previous standing prerequisites and other course details will be followed.
23. It makes sense for the student to give feedback on the course that is currently being studied, so feedback is only allowed for current courses.

**Note**: In UML (Compact) version, `+`, `-` symbols are used as bullet points and do not convey their actual meaning. Getters and setters methods of all classes are omitted to enhance readability. In the UML (Expanded) version, private attributes are omitted, as per convention (courtesy: IntelliJ).

## CONCEPTS ENFORCED
1. **Class**  
   - Defined multiple classes like `Complaint`, `Contact`, `User`, `Student`, etc.
2. **Interface**  
   - Defined an interface `Page`, which is implemented by `Home`, `Login`, and `signUP` pages.
3. **Polymorphism**  
   - In `Complaint` class implementation, `sender` is declared as `User` (can be either Professor or Student) and its attribute `email` is used (which is in `User` class, so both Professor and Student have it).
4. **Abstraction**  
   - Using driver function, available functionalities are provided as a menu.
5. **Encapsulation**  
   - All attributes are made either private or protected so that they cannot be accessed outside the package.
6. **Inheritance**  
   - `Admin`, `Student`, and `Professor` inherit functionalities from `User` (use of `super()` as well).
7. **Overloading**  
   - Used in `User` to either `addContactInfo` as name, number, address or only name; `ShowComplaint` based on date or status.
8. **Static**  
   - Used in most classes to define a static list of all the instantiations done. Used in `Admin`, `Student`, and `Professor` to keep a list of emails and passwords. Used in `Course`, `Complaint` to keep track of all instances.
9. **Abstract**  
   - `User` is an abstract class, because it doesn't need to be instantiated.
10. **Final**  
    - `Complaint` list declared as final, so it cannot be reassigned but can be mutable.
11. **Access Modifiers**  
    - Public, private, protected, and default used as needed.
12. **Dependency**  
    - `Login`, `signUP` classes depend on the `DataBase` class as they request login/signup services from it.
13. **Association**  
    - `Student` and `Complaint` have association; `Professor` and `Course` have association; all compositions are associations (aggregation).
14. **Composition**  
    - `User` is composed of `Contact`; `Course` is composed of `TimeTable`.
15. **Generic**  
    - Used to define a `Pair` class which has the first value as `String` and the second as a generic type (declared upon instantiation).
16. **Object**  
    - Methods like `toString`, `compareTo`, etc., are used.
17. **Overriding**  
    - Methods like `toString`, etc., are overridden from `Object`.

## NEWLY ADDED FEATURES

### Generic Feedback System
- A class for `FeedBack` has been created.
- It can store both Integer Ratings and String Comments in the same variable depending on its initialization.
- Students are tasked with providing feedback and can choose between Rating or Comment.
- Also records the type of feedback and the student who submitted it.
- Students can give feedback for currently registered courses.

### Enhanced User Role Management with Object Class
- Added a role of `TA`.
- A student, while signing up, can register as a `TA-cum-Student`.
- TA can apply for `TAShip` in any course.
- Professor of a course can accept, reject, or remove TAs from the course.
- Once accepted, TAs can add and view a student's grade for their course.

### Robust Exception Handling for Course Registration and Login
- `InvalidLoginException` thrown and caught in the login page if a user gives an incorrect username or password.
- `CourseFullException` thrown and caught in the course registration method of `Student` if the enrollment limit has been reached.
- `DropDeadlinePassedException` thrown and caught in the drop course method of `Student`, and delete course method of `Admin`, if the pre-fixed drop deadline has passed.
- `IOExceptions` collectively handled on the home page.
