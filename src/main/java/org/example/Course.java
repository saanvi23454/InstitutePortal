package org.example;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

public class Course {
    protected static List<List<Course>> courseList = new ArrayList<>();
    public static LocalDateTime dropDeadline = LocalDateTime.of(2024, Month.OCTOBER, 15, 23,59);

    public static void initialize(){
        for (int i = 0; i < 8; i++) {
            courseList.add(new ArrayList<>());
        }
    }

    protected HashSet<Student> regStudent = new HashSet<>(); // set of registered students for this course
    protected HashSet<Course> preReqs = new HashSet<>();
    protected TimeTable timeTable = new TimeTable();
    protected List<TA> TAList = new ArrayList<>();
    protected HashSet<String> AppliedTA = new HashSet<>();
    private List<FeedBack> feedBackList = new ArrayList<>();

    private String title;
    private int semester;
    private String courseCode;
    private Professor professor = null;
    private int credit;
    private String syllabus = "";
    private int enrollment_limit = 600;
    private int current_enrolled = 0;

    public Course(String _title, int _semester, String _crsCode, int _credit){
        // assume -> semester is valid and credit is 2 or 4
        title = _title;
        semester = _semester;
        courseCode = _crsCode;
        credit = _credit;
        Course.courseList.get(_semester-1).add(this);
    }

    public void assignProf(Professor prof){
        professor = prof;
        prof.setMyCourse(this);
    }

    public void addPreReq(Course crs){
        preReqs.add(crs);
    }

    public void setTT(List<Pair<String>> _timings, String _venue){
        timeTable.setTimings(_timings);
        timeTable.setVenue(_venue);
    }

    public void getTT(){
        System.out.println("Course Code : " + courseCode);
        System.out.println("Course Title : " + title);
        System.out.println("Professor : " + professor);
        System.out.println("Credits: " + credit);
        System.out.println("Prerequisites : " + preReqs);
        System.out.println("Time Table : \n" + timeTable);
    }

    public void addStudent(Student std){
        regStudent.add(std);
        current_enrolled += 1;
    }

    public void removeStudent(Student std){
        regStudent.remove(std);
        current_enrolled -=1;
    }

    public void addSyllabus(String _syllabus){
        syllabus = _syllabus;
    }

    public boolean isFull(){
        // checks if enrollment limit reached
        return current_enrolled == enrollment_limit;
    }

    public void addFeedBack(FeedBack feedback){
        feedBackList.add(feedback);
    }

    public void addTA(TA ta){
        ta.addTACourse(this);
        TAList.add(ta);
    }

    public void applyTA(TA ta){
        AppliedTA.add(ta.email);
    }

    public void viewTAList(){
        int i = 1;
        for (TA ta: TAList){
            System.out.println(i + ". " + ta);
            i++;
        }
    }

    public void viewAppliedTA(){
        int i = 1;
        for (String ta : AppliedTA){
            System.out.println(i + ". " + ta);
            i++;
        }
    }

    public void acceptTA(String _email){
        if (AppliedTA.contains(_email)){
            addTA(DataBase.TAList.get(_email).val2);
            AppliedTA.remove(_email);
            System.out.println("TA accepted to the course!");
        }
    }

    public void removeTA(String _email){
        int i = 1;
        for (TA ta: TAList){
            if (ta.email.equals(_email)){
                break;
            }
            i++;
        }
        if (i == TAList.size()+1){
            System.out.println("No such TA found!");
            return;
        }
        TAList.get(i-1).removeTACourse(this);
        TAList.remove(i-1);
    }

    public String toString(){
        return title + " [" + courseCode + "] = " + credit + " credits";
    }

    // setters
    public void setTitle(String _title){
        title = _title;
    }
    public void setSemester(int sem){
        semester = sem;
    }
    public void setCourseCode(String crsc){
        courseCode = crsc;
    }
    public void setProfessor(Professor prof){
        professor = prof;
    }
    public void setCredit(int c){
        credit = c;
    }
    public void setSyllabus(String syl){
        syllabus = syl;
    }
    public void setEnrollmentLimit(int limit){
        enrollment_limit = limit;
    }
    public void setCurrentEnrolled(int en){
        current_enrolled = en;
    }

    //getters
    public String getTitle(){
        return title;
    }
    public int getSemester(){
        return semester;
    }
    public String getCourseCode(){
        return courseCode;
    }
    public Professor getProfessor(){
        return professor;
    }
    public int getCredit(){
        return credit;
    }
    public String getSyllabus(){
        return syllabus;
    }
    public int getEnrollmentLimit(){
        return enrollment_limit;
    }
    public int getCurrentEnrolled(){
        return current_enrolled;
    }
    public void viewFeedBackList() {
        int i = 1;
        System.out.println(courseCode + " : " + title);
        for (FeedBack fb : feedBackList){
            System.out.println(i + ". " + fb);
            i++;
        }
    }
}

