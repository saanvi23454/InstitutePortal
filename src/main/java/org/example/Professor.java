package org.example;

import java.util.*;

public class Professor extends User{

    private final List<Complaint> myComplaint = new ArrayList<>(); //can only be assigned one, but can be modified

    private Course myCourse = null;
    private String officeHours = "";

    public Professor(String _email, String _password){
        super(_email, _password);
        DataBase.professorList.put(_email, new Pair<Professor>(_password, this));
    }

    public void setCourse(Course crs){
        myCourse = crs;
        // professor assigned to course in Course methods itself
    }

    public void display(){
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        contact.getInfo();
        System.out.println("Course: "+ myCourse);
        System.out.println("Office Hours: " + officeHours);
    }

    public String toString(){
        return email + " [Office hours : " + officeHours + "]";
    }

    // MANAGE MY COURSE

    public void updateSyllabus(String _syllabus){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        myCourse.addSyllabus(_syllabus);
        System.out.println("Syllabus updated successfully!");
    }

    public void updateTT(List<Pair<String>> _timings, String _venue){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        myCourse.setTT(_timings, _venue);
        System.out.println("TimeTable updated successfully!");
    }

    public void changeCredit(int _credit){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        myCourse.setCredit(_credit);
        System.out.println("Credit updated successfully!");
    }

    public void changeEnrollmentLimit(int el){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        myCourse.setEnrollmentLimit(el);
        System.out.println("Enrollment limit updated!");
    }

    public void addPreReq(int semester, int courseIdx){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        if (semester > 8 || semester <= 0){
            System.out.println("Invalid semester!");
            return;
        }
        if (courseIdx <= 0 || courseIdx > Course.courseList.get(semester-1).size()){
            System.out.println("Invalid index!");
            return;
        }
        myCourse.addPreReq((Course.courseList.get(semester-1)).get(courseIdx-1));
        System.out.println("Prerequisite added successfully!");
    }

    // MANAGE ENROLLED STUDENTS

    public void addStudentGrade(String studentEmail, int _courseIdx, float _grade){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        // ERROR CHECKING
        if (DataBase.studentList.containsKey(studentEmail)){
            Student myS = (DataBase.studentList.get(studentEmail).val2);
            if (myCourse.regStudent.contains(myS)) {
                myS.addGrade(_courseIdx, _grade);
            }
            else{
                System.out.println("No such student registered for your course!");
            }
        }
        else{
            System.out.println("No such student found!");
        }
    }

    public void viewStudent(String studentEmail){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        Student myS = DataBase.studentList.get(studentEmail).val2;
        if (myCourse.regStudent.contains(myS)){
            myS.display();
        }
        else{
            System.out.println("Student not registered in your course...");
        }
    }

    public void viewEnrolledStudents(){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        System.out.println("Viewing enrolled students...");
        if (myCourse!=null){
            int i = 1;
            for (Student s : myCourse.regStudent){
                System.out.println(i + ". " + s);
                i++;
            }
        }
    }

    // COMPLAINTS

    public void submitComplaint(String desc){
        myComplaint.add(new Complaint(desc, this));
        System.out.println("Complaint submitted successfully!");
    }

    public void viewComplaint(){
        int i = 1;
        System.out.println("Viewing complaints...");
        for (Complaint c : myComplaint){
            System.out.println(i + ". " + c);
            if ((c.status).equals("RESOLVED")){
                System.out.println("\t\tResolved by " + c.resolver.email);
            }
            i++;
        }
    }

    // FEEDBACK
    public void viewFeedBack(){
        if (myCourse == null){
            System.out.println("No course assigned to you...");
            return;
        }
        myCourse.viewFeedBackList();
    }

    // TA

    public void viewTAList(){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        myCourse.viewTAList();
    }

    public void viewAppliedTAList(){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        myCourse.viewAppliedTA();
    }

    public void addTA(String _email){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        myCourse.acceptTA(_email);
    }

    public void removeTA(String _email){
        if (myCourse == null){
            System.out.println("No course assigned to you yet.");
            return;
        }
        myCourse.removeTA(_email);
        System.out.println("TA removed successfully!");
    }

    // setters
    public void setMyCourse(Course crs){
        myCourse = crs;
    }
    public void setOfficeHours(String ofh){
        officeHours = ofh;
        System.out.println("Office hours updated successfully!");
    }

    // getters
    public String getOfficeHours(){
        return officeHours;
    }
    public Course getMyCourse(){
        return myCourse;
    }

}
