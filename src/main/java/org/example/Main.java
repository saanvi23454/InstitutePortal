package org.example;
import java.util.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        Course.initialize();
        prepopulate();
        Home home = new Home();
        home.welcomeMSG();
        home.leavingMSG();
    }

    public static void prepopulate(){
        suppressOutput(() -> {
            Student myS1 = new Student("saanvi@iiitd.ac.in", "123");
            Student myS2 = new Student("abc@iiitd.ac.in", "123");
            Student myS3 = new Student("xyz", "123");

            Professor myP1 = new Professor("prof1@iiitd.ac.in", "123");
            Professor myP2 = new Professor("prof2", "123");

            Admin myA1 = new Admin("admin");

            Course myC1 = new Course("Linear Algebra", 1, "MTH101", 4);
            Course myC2 = new Course("Probability and Statistics", 2, "MTH201", 4);
            Course myC3 = new Course("Self Growth", 3, "SG", 2);
            Course myC4 = new Course("Advanced Programming", 3, "CSE202", 4);
            Course myC5 = new Course("Data Structures", 2, "CSE201", 4);
            Course myC6 = new Course("Intro to Programming", 1, "CSE101", 4);

            myA1.assignProf(1, 1, "prof2");
            myA1.assignProf(1, 2, "prof1@iiitd.ac.in");

            myP1.setOfficeHours("Monday 1330 to 1430");
            myP1.updateSyllabus("David C Lay Chapter 1-5");
            myP1.changeEnrollmentLimit(650);
            List<Pair<String>> myL = new ArrayList<>();
            myL.add(new Pair<String>("Monday", "1200 to 1300"));
            myL.add(new Pair<String>("Tuesday", "1600 to 1730"));
            myP1.updateTT(myL, "C201");

            try {
                myS3.registerCourse(1, 1);
                myS3.registerCourse(1, 2);
                myS3.addContactInfo("Saanvi", "9009080080", "New Delhi");
                myS3.submitComplaint("Unable to view all courses...");
                myS2.registerCourse(1, 1);
                myS2.registerCourse(1, 2);
            }
            catch (CourseFullException e){
                System.out.println(e.getMessage());
            }

            myA1.resolveComplaint(1);

            myP1.addStudentGrade("xyz", 1, 10);
            myP2.addStudentGrade("abc@iiitd.ac.in", 2, 8);
        });
    }


    // REFERENCES for suppressing output stream: https://stackoverflow.com/questions/8363493
    private static void suppressOutput(Runnable runnable) {
        // Save the original System.out
        PrintStream originalOut = System.out;
        // Create a dummy OutputStream
        OutputStream dummyOutputStream = new OutputStream() {
            @Override
            public void write(int b) {
                // No-op
            }
        };
        // Redirect System.out to the dummy OutputStream
        System.setOut(new PrintStream(dummyOutputStream));
        try {
            // Execute the runnable with suppressed output
            runnable.run();
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }
}