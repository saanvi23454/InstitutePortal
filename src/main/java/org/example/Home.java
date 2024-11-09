package org.example;
import java.io.IOException;
import java.util.*;

public class Home implements Page {//driver function
    static Scanner scanner = new Scanner(System.in);
    public void welcomeMSG(){
        try{while (true) {
            System.out.println("Hello User, Welcome to University Course Registration System...");

            System.out.println("Enter" +
                    "\n(1) for Login" +
                    "\n(2) for SignUp" +
                    "\n(0) for Terminate Program");
            int choice = scanner.nextInt();
            scanner.nextLine();
            while (choice != 1 && choice != 2 && choice != 0) {
                System.out.println("Invalid choice! Enter again...");
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            if (choice == 0) {
                break;
            }
            if (choice == 2) {
                new signUP().welcomeMSG();
            } else {
                Login login = new Login();
                login.welcomeMSG();
                System.out.println("Enter" +
                        "\n(1) for Student" +
                        "\n(2) for Professor" +
                        "\n(3) for Admin");
                choice = scanner.nextInt();
                scanner.nextLine();
                while (choice != 1 && choice != 2 && choice != 3) {
                    System.out.println("Invalid choice! Enter again...");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                }
                try {
                    if (choice == 1) {
                        login.login_as_student();
                    } else if (choice == 2) {
                        login.login_as_professor();
                    } else {
                        login.login_as_admin();
                    }
                }
                catch (InvalidLoginException e) {
                    System.out.println(e.getMessage());
                }
                login.leavingMSG();
            }
            System.out.println("Back to Home Page!");
        }}
        catch(InputMismatchException i) {
            System.out.println("Unexpected input!");
        }
        catch(NullPointerException n){
            System.out.println("Null pointer exception...");
        }
    }
    public void leavingMSG(){
        System.out.println("Terminating the program...All changes will be lost.");
    }
}

class signUP implements Page {
    static Scanner scanner = new Scanner(System.in);

    public void welcomeMSG() {
        System.out.println("Hello User, Welcome to Signup Page...");
        System.out.println("Enter" +
                "\n(1) for Student" +
                "\n(2) for Professor");
        int choice = scanner.nextInt();
        scanner.nextLine();
        while (choice != 1 && choice != 2) {
            System.out.println("Invalid choice! Enter again...");
            choice = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.println("Enter email id: ");
        String email = scanner.nextLine();
        System.out.println("Enter password: ");
        String pwd = scanner.nextLine();
        if (choice == 2) {
            new Professor(email, pwd);
        } else {
            System.out.println("Enter " +
                    "\n(1) for Non-TA" +
                    "\n(2) for TA");
            choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {
                new Student(email, pwd);
            }
            else if (choice == 2){
                new TA(email, pwd);
            }
            else{
                System.out.println("Invalid choice!");
            }
        }
        System.out.println("Account created!");
        leavingMSG();
    }
    public void leavingMSG(){
        System.out.println("Returning to Home...");
    }
}

class Login implements Page {
    static Scanner scanner = new Scanner(System.in);

    public void welcomeMSG() {
        System.out.println("Hello User, Welcome to Login Page...");
    }

    public void login_as_student() throws InvalidLoginException{
        String email, pwd;

        System.out.println("Enter email: ");
        email = scanner.nextLine();
        System.out.println("Enter password: ");
        pwd = scanner.nextLine();

        if (!DataBase.checkStudPWD(email, pwd)){
            throw new InvalidLoginException("Login attempt failed...username or password incorrect. ");
        };

        if (DataBase.TAList.containsKey(email)){
            TA ME = DataBase.getTA(email, pwd);
            assert (ME != null);
            TAMenu(ME);
        }
        else{
            Student ME = DataBase.getStudent(email, pwd);
            assert (ME != null);
            studentMENU(ME);
        }

        System.out.println("Logging out..");

    }

    public void login_as_professor() throws InvalidLoginException {
        String email, pwd;

        System.out.println("Enter email: ");
        email = scanner.nextLine();
        System.out.println("Enter password: ");
        pwd = scanner.nextLine();

        if (!DataBase.checkProfPWD(email, pwd)){
            throw new InvalidLoginException("Login attempt failed...username or password incorrect. ");
        };

        Professor ME = DataBase.getProfessor(email, pwd);
        assert (ME != null);

        // MENU
        int choice = 0;

        do {
            switch (choice) {
                case 0:
                    break;
                case 1:
                    System.out.println("Enter" +
                            "\n(1) to View Profile" +
                            "\n(2) to Edit Profile");
                    do {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    while (choice != 1 && choice != 2 && checkCondition());
                    if (choice == 1) {
                        ME.display();
                    } else {
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Number: ");
                        String number = scanner.nextLine();
                        System.out.println("Address: ");
                        String address = scanner.nextLine();
                        ME.addContactInfo(name, number, address);
                    }
                    break;
                case 2:
                    System.out.println("Enter" +
                            "\n(1) to Update Syllabus" +
                            "\n(2) to Update Class Timings" +
                            "\n(3) to Update Credits" +
                            "\n(4) to Add PreRequisite" +
                            "\n(5) to Edit Enrollment Limit" +
                            "\n(6) to Update Office Hours" +
                            "\n(0) to Back");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    String entry, entry2, entry3;
                    switch (choice){
                        case 0:
                            break;
                        case 1:
                            System.out.println("Enter Syllabus: ");
                            entry = scanner.nextLine();
                            ME.updateSyllabus(entry);
                            break;
                        case 2:
                            System.out.println("Number of classes: ");
                            int j = 0;
                            int k = scanner.nextInt();
                            scanner.nextLine();
                            List<Pair<String>> myL = new ArrayList<>();
                            while (j < k){
                                System.out.println("Enter Day: ");
                                entry3 = scanner.nextLine();
                                System.out.println("Enter Timings: ");
                                entry2 = scanner.nextLine();
                                myL.add(new Pair<String>(entry3, entry2));
                                j++;
                            }
                            System.out.println("Enter venue: ");
                            entry = scanner.nextLine();
                            ME.updateTT(myL, entry);
                            break;
                        case 3:
                            System.out.println("Enter credits (2 or 4): ");
                            ME.changeCredit(scanner.nextInt());
                            scanner.nextLine();
                            break;
                        case 4:
                            ME.viewCourseCatalog();
                            System.out.println("Enter semester: ");
                            int sem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enter course index: ");
                            int idx = scanner.nextInt();
                            scanner.nextLine();
                            ME.addPreReq(sem, idx);
                            break;
                        case 5:
                            System.out.println("Enter enrollment limit: ");
                            ME.changeEnrollmentLimit(scanner.nextInt());
                            scanner.nextLine();
                            break;
                        case 6:
                            System.out.println("Enter office hours: ");
                            ME.setOfficeHours(scanner.nextLine());
                            break;
                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                    break;
                case 3:
                    ME.viewEnrolledStudents();
                    break;
                case 4:
                    ME.viewEnrolledStudents();
                    System.out.println("Enter student email: ");
                    String emailid = scanner.nextLine();
                    ME.viewStudent(emailid);
                    System.out.println("Enter course index: ");
                    int crsidx = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter grade (out of 10.0): ");
                    float grade = scanner.nextFloat();
                    scanner.nextLine();
                    ME.addStudentGrade(emailid, crsidx, grade);
                    break;
                case 5:
                    ME.viewCourseCatalog();
                    break;
                case 6:
                    System.out.println("Enter description: ");
                    ME.submitComplaint(scanner.nextLine());
                    break;
                case 7:
                    ME.viewComplaint();
                    break;
                case 8:
                    ME.viewFeedBack();
                    break;
                case 9:
                    System.out.println("Enter" +
                            "\n(1) to View TA Applications" +
                            "\n(2) to Accept TA" +
                            "\n(3) to View Course TA List" +
                            "\n(4) to Remove TA");
                    do {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    // CHECK WHILE CONDITION
                    while (choice != 1 && choice != 2 && choice != 3 && choice!=4 && checkCondition());
                    if (choice == 1) {
                        ME.viewAppliedTAList();
                    }
                    else if (choice == 2){
                        ME.viewAppliedTAList();
                        System.out.println("Enter TA email: ");
                        ME.addTA(scanner.nextLine());
                    }
                    else if (choice == 3){
                        ME.viewTAList();
                    }
                    else if (choice == 4){
                        ME.viewTAList();
                        System.out.println("Enter TA email: ");
                        String _email = scanner.nextLine();
                        ME.removeTA(_email);
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
            printMSG2();
            choice = scanner.nextInt();
            scanner.nextLine();
        }while (choice!=0);
        System.out.println("Logging out..");
    }

    public void login_as_admin() throws InvalidLoginException{
        String email, pwd;

        System.out.println("Enter email: ");
        email = scanner.nextLine();
        System.out.println("Enter password: ");
        pwd = scanner.nextLine();

        if (!DataBase.checkAdmPWD(email, pwd)){
            throw new InvalidLoginException("Login attempt failed...username or password incorrect. ");
        };

        Admin ME = DataBase.getAdmin(email, pwd);
        assert (ME != null);

        // MENU
        int choice = 0;
        do {
            switch (choice){
                case 0:
                   break;
                case 1:
                    System.out.println("Enter" +
                            "\n(1) to View Profile" +
                            "\n(2) to Edit Profile");
                    do {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    while (choice != 1 && choice != 2 && checkCondition());
                    if (choice == 1) {
                        ME.display();
                    } else {
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Number: ");
                        String number = scanner.nextLine();
                        System.out.println("Address: ");
                        String address = scanner.nextLine();
                        ME.addContactInfo(name, number, address);
                    }
                    break;
                case 2:
                    System.out.println("Enter" +
                            "\n(1) to Add Course" +
                            "\n(2) to Delete Course");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice){
                        case 1:
                            String title, crscode;
                            int credit, semester;
                            System.out.println("Enter Course Title: ");
                            title = scanner.nextLine();
                            System.out.println("Enter Course Code: ");
                            crscode = scanner.nextLine();
                            System.out.println("Enter Semester: ");
                            semester = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enter Credits (2 or 4): ");
                            credit = scanner.nextInt();
                            scanner.nextLine();
                            ME.addCourse(title, semester, crscode, credit);
                            break;
                        case 2:
                            ME.viewCourseCatalog();
                            System.out.println("Enter semester: ");
                            int sem = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enter course index: ");
                            int idx = scanner.nextInt();
                            scanner.nextLine();
                            ME.deleteCourse(sem, idx);
                            break;
                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Enter" +
                            "\n(1) to View All Students" +
                            "\n(2) to View Specific Student" +
                            "\n(3) to Change Student Details" +
                            "\n(4) to Assign Grade to Student");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice){
                        case 1:
                            ME.viewStudentList();
                            break;
                        case 2:
                            System.out.println("Enter student email ID: ");
                            String temp = scanner.nextLine();
                            ME.viewStudent(temp);
                            break;
                        case 3:
                            System.out.println("Enter student email ID: ");
                            String emailID = scanner.nextLine();
                            System.out.println("Enter Name: ");
                            String name = scanner.nextLine();
                            System.out.println("Enter Number: ");
                            String number = scanner.nextLine();
                            System.out.println("Enter Address: ");
                            String address = scanner.nextLine();
                            ME.studentChangeDetails(emailID, name, number, address);
                            break;
                        case 4:
                            ME.viewStudentList();
                            System.out.println("Enter student email: ");
                            String emailid = scanner.nextLine();
                            ME.viewStudent(emailid);
                            System.out.println("Enter course index: ");
                            int crsidx = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Enter grade (out of 10.0): ");
                            float grade = scanner.nextFloat();
                            scanner.nextLine();
                            ME.addStudentGrade(emailid, crsidx, grade);
                            break;
                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                    break;
                case 4:
                    ME.viewProfList();
                    System.out.println("Enter professor email ID: ");
                    String emailid = scanner.nextLine();
                    ME.viewCourseCatalog();
                    System.out.println("Enter semester: ");
                    int sem = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter course index: ");
                    int crsidx = scanner.nextInt();
                    scanner.nextLine();
                    ME.assignProf(sem, crsidx, emailid);
                    break;
                case 5:
                    ME.viewProfList();
                    System.out.println("Enter professor email ID: ");
                    String emailID = scanner.nextLine();
                    ME.unAssignProf(emailID);
                    break;
                case 6:
                    System.out.println("Enter" +
                            "\n(1) to Show Complaints" +
                            "\n(2) to Resolve Complaints");
                    choice = scanner.nextInt();
                    scanner.nextLine();
                    switch (choice){
                        case 1:
                            System.out.println("Enter" +
                                    "\n(1) to Sort by Date -> Ascending" +
                                    "\n(2) to Sort by Date -> Descending" +
                                    "\n(3) to Filter by Status -> PENDING" +
                                    "\n(4) to Filter by Status -> RESOLVED");
                            choice = scanner.nextInt();
                            scanner.nextLine();
                            switch (choice){
                                case 1:
                                    ME.showComplaints(1);
                                    break;
                                case 2:
                                    ME.showComplaints(-1);
                                    break;
                                case 3:
                                    ME.showComplaints("PENDING");
                                    break;
                                case 4:
                                    ME.showComplaints("RESOLVED");
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                                    break;
                            }
                            break;
                        case 2:
                            ME.showComplaints(-1);
                            System.out.println("Enter complaint PID: ");
                            int pid = scanner.nextInt();
                            scanner.nextLine();
                            ME.resolveComplaint(pid);
                            break;
                        default:
                            System.out.println("Invalid choice!");
                            break;
                    }
                    break;
                case 7:
                    ME.viewCourseCatalog();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
            printMSG3();
            choice = scanner.nextInt();
            scanner.nextLine();
        } while (choice!=0);
        System.out.println("Logging out..");
    }

    public boolean checkCondition() {
        System.out.println("Invalid, try again...");
        return true;
    }

    public void printMSG1() {
        System.out.println("Enter" +
                "\n(1) to My Profile" +
                "\n(2) to View Available Courses" +
                "\n(3) to Register for Courses" +
                "\n(4) to View Schedule" +
                "\n(5) to Track Academic Progress" +
                "\n(6) to Drop Courses" +
                "\n(7) to Submit Complaints" +
                "\n(8) to View Complaints" +
                "\n(9) to Add Course FeedBack" +
                "\n(0) to SIGN OUT");
    }

    public void printMSG2(){
        System.out.println("Enter" +
                "\n(1) to My Profile" +
                "\n(2) to Manage Course" +
                "\n(3) to View Enrolled Students" +
                "\n(4) to Add Student Grade" +
                "\n(5) to View Course Catalog" +
                "\n(6) to Submit Complaint" +
                "\n(7) to View Complaint" +
                "\n(8) to View Course FeedBack" +
                "\n(9) to Manage TAs" +
                "\n(0) to SIGN OUT");
    }

    public void printMSG3(){
        System.out.println("Enter" +
                "\n(1) to My Profile" +
                "\n(2) to Manage Course Catalog" +
                "\n(3) to Manage Student Records" +
                "\n(4) to Assign Professor to Course" +
                "\n(5) to Unassign Professor" +
                "\n(6) to Handle Complaints" +
                "\n(7) to View Course Catalog" +
                "\n(0) to SIGN OUT");
    }

    public void printMSG4() {
        System.out.println("Enter" +
                "\n(1) to My Profile" +
                "\n(2) to View Available Courses" +
                "\n(3) to Register for Courses" +
                "\n(4) to View Schedule" +
                "\n(5) to Track Academic Progress" +
                "\n(6) to Drop Courses" +
                "\n(7) to Submit Complaints" +
                "\n(8) to View Complaints" +
                "\n(9) to Add Course FeedBack" +
                "\n(10) to Manage Course as TA" +
                "\n(11) to Apply for TAShip" +
                "\n(0) to SIGN OUT");
    }

    public void studentMENU(Student ME){
        // MENU
        int choice = 0;

        do {
            switch (choice) {
                case 0:
                    break;
                case 1:
                    System.out.println("Enter" +
                            "\n(1) to View Profile" +
                            "\n(2) to Edit Profile");
                    do {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    while (choice != 1 && choice != 2 && checkCondition());
                    if (choice == 1) {
                        ME.display();
                    } else {
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Number: ");
                        String number = scanner.nextLine();
                        System.out.println("Address: ");
                        String address = scanner.nextLine();
                        ME.addContactInfo(name, number, address);
                    }
                    break;
                case 2:
                    ME.viewCourseList();
                    break;
                case 3:
                    System.out.println("Note: You can register for a course of any " +
                            "semester before and including your current semester.");
                    ME.viewCourseCatalog();
                    System.out.println("Enter Semester: ");
                    int sem = scanner.nextInt();
                    System.out.println("Enter Course Index: ");
                    int idx = scanner.nextInt();
                    scanner.nextLine();

                    try{
                        ME.registerCourse(sem, idx);
                    }
                    catch (CourseFullException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 4:
                    ME.viewSchedule();
                    break;
                case 5:
                    System.out.println("Enter" +
                            "\n(1) to Check SGPA" +
                            "\n(2) to Check CGPA" +
                            "\n(3) to View GradeList");
                    do {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    // CHECK WHILE CONDITION
                    while (choice != 1 && choice != 2 && choice != 3 && checkCondition());
                    if (choice == 1) {
                        System.out.println("Enter Semester Number: ");
                        ME.viewSGPA(scanner.nextInt());
                        scanner.nextLine();
                    } else if (choice == 2) {
                        System.out.println(ME.getCGPA());
                    }
                    else{
                        ME.viewGradeList();
                    }
                    break;
                case 6:
                    ME.myCourseList();
                    System.out.println("Enter Course Index: ");

                    try{
                        ME.dropCourse(scanner.nextInt());
                    }
                    catch(DropDeadlinePassedException e){
                        System.out.println(e.getMessage());
                    }
                    scanner.nextLine();
                    break;
                case 7:
                    System.out.println("Enter description: ");
                    ME.submitComplaint(scanner.nextLine());
                    break;
                case 8:
                    ME.viewComplaint();
                    break;
                case 9:
                    ME.myCourseList();
                    System.out.println("Enter Course Index: ");
                    int crsidx = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter (1) for Rating or (2) for Comment: ");
                    switch (scanner.nextInt()){
                        case 1:
                            scanner.nextLine();
                            System.out.println("Enter Rating on a Scale of 1 to 5: ");
                            ME.addFeedBackRating(crsidx, scanner.nextInt());
                            scanner.nextLine();
                            break;
                        case 2:
                            scanner.nextLine();
                            System.out.println("Enter Comment: ");
                            ME.addFeedBackComment(crsidx, scanner.nextLine());
                            break;
                        default:
                            scanner.nextLine();
                            System.out.println("Invalid choice!");
                            break;
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }

            printMSG1();
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        while (choice != 0);
    }

    public void TAMenu(TA ME){
        // MENU
        int choice = 0;
        do {
            switch (choice) {
                case 0:
                    break;
                case 1:
                    System.out.println("Enter" +
                            "\n(1) to View Profile" +
                            "\n(2) to Edit Profile");
                    do {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    while (choice != 1 && choice != 2 && checkCondition());
                    if (choice == 1) {
                        ME.display();
                    } else {
                        System.out.println("Name: ");
                        String name = scanner.nextLine();
                        System.out.println("Number: ");
                        String number = scanner.nextLine();
                        System.out.println("Address: ");
                        String address = scanner.nextLine();
                        ME.addContactInfo(name, number, address);
                    }
                    break;
                case 2:
                    ME.viewCourseList();
                    break;
                case 3:
                    System.out.println("Note: You can register for a course of any " +
                            "semester before and including your current semester.");
                    ME.viewCourseCatalog();
                    System.out.println("Enter Semester: ");
                    int sem = scanner.nextInt();
                    System.out.println("Enter Course Index: ");
                    int idx = scanner.nextInt();
                    scanner.nextLine();

                    try{
                        ME.registerCourse(sem, idx);
                    }
                    catch (CourseFullException e){
                        System.out.println(e.getMessage());
                    }

                    break;
                case 4:
                    ME.viewSchedule();
                    break;
                case 5:
                    System.out.println("Enter" +
                            "\n(1) to Check SGPA" +
                            "\n(2) to Check CGPA" +
                            "\n(3) to View GradeList");
                    do {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    // CHECK WHILE CONDITION
                    while (choice != 1 && choice != 2 && choice != 3 && checkCondition());
                    if (choice == 1) {
                        System.out.println("Enter Semester Number: ");
                        ME.viewSGPA(scanner.nextInt());
                        scanner.nextLine();
                    } else if (choice == 2) {
                        System.out.println(ME.getCGPA());
                    }
                    else{
                        ME.viewGradeList();
                    }
                    break;
                case 6:
                    ME.myCourseList();
                    System.out.println("Enter Course Index: ");
                    try{
                        ME.dropCourse(scanner.nextInt());
                    }
                    catch (DropDeadlinePassedException e){
                        System.out.println(e.getMessage());
                    }
                    scanner.nextLine();
                    break;
                case 7:
                    System.out.println("Enter description: ");
                    ME.submitComplaint(scanner.nextLine());
                    break;
                case 8:
                    ME.viewComplaint();
                    break;
                case 9:
                    ME.myCourseList();
                    System.out.println("Enter Course Index: ");
                    int crsidx = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter (1) for Rating or (2) for Comment: ");
                    switch (scanner.nextInt()){
                        case 1:
                            scanner.nextLine();
                            System.out.println("Enter Rating on a Scale of 1 to 5: ");
                            ME.addFeedBackRating(crsidx, scanner.nextInt());
                            scanner.nextLine();
                            break;
                        case 2:
                            scanner.nextLine();
                            System.out.println("Enter Comment: ");
                            ME.addFeedBackComment(crsidx, scanner.nextLine());
                            break;
                        default:
                            scanner.nextLine();
                            System.out.println("Invalid choice!");
                            break;
                    }
                    break;
                case 10:
                    System.out.println("Enter" +
                            "\n(1) to View Enrolled Students" +
                            "\n(2) to Add Student Grade" +
                            "\n(3) to View Schedule");
                    do {
                        choice = scanner.nextInt();
                        scanner.nextLine();
                    }
                    // CHECK WHILE CONDITION
                    while (choice != 1 && choice != 2 && choice != 3 && checkCondition());
                    if (choice == 1) {
                        ME.viewMyTACourses();
                        System.out.println("Enter course index: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        ME.viewEnrolledStudents(choice);
                    }
                    else if (choice == 2){
                        ME.viewMyTACourses();
                        System.out.println("Enter course index: ");
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        ME.viewEnrolledStudents(choice);
                        System.out.println("Enter student email: ");
                        String choice2 = scanner.nextLine();
                        System.out.println("Enter student grade: ");
                        ME.addStudentGrade(choice, choice2, scanner.nextFloat());
                        scanner.nextLine();
                    }
                    else if (choice == 3){
                        ME.viewTASchedule();
                    }
                    break;
                case 11:
                    ME.viewCourseCatalog();
                    System.out.println("Enter Semester: ");
                    int seme = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter Course Index: ");
                    int crsid = scanner.nextInt();
                    scanner.nextLine();
                    ME.applyForTAShip(seme, crsid);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }

            printMSG4();
            choice = scanner.nextInt();
            scanner.nextLine();
        }
        while (choice != 0);
    }
    public void leavingMSG(){
        System.out.println("Returning to Home...");
    }
}