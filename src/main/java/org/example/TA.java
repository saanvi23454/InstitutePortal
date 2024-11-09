package org.example;
import java.util.*;

// TA can also view grade, add the feature
// limit to number of courses that can be added

public class TA extends Student{

    private List<Course> courseTAList;

    public TA(String _email, String _password){
        super(_email, _password);
        DataBase.TAList.put(_email, new Pair<TA>(_password, this));
        courseTAList = new ArrayList<>();
    }

    public String toString(){
        return name + " [" + email + "] " + "...CGPA = " + getCGPA() + "\n" + "TA of : " + courseTAList;
    }


    public void addTACourse(Course crs){
        courseTAList.add(crs);
    }

    public void removeTACourse(Course crs){
        int i = 0;
        for (Course c : courseTAList){
            if (c == crs){
                courseTAList.remove(i);
                break;
            }
            i++;
        }
    }

    @Override
    protected void addSemester(int num){ //used by admin
        // semester is between 1 and 8
        super.addSemester(num);
        if (courseTAList != null){
            for (Course c : courseTAList){
                c.removeTA(this.email);
            }
            courseTAList.clear();
        }
    }

    public void applyForTAShip(int sem, int crs){
        if (sem <= 0 || sem > 8){
            System.out.println("Invalid semester");
            return;
        }
        if (crs <= 0 || crs > Course.courseList.get(sem-1).size()){
            System.out.println("Invalid course");
            return;
        }
        (Course.courseList.get(sem-1).get(crs-1)).applyTA(this);
        System.out.println("Applied successfully!");
    }

    public void viewMyTACourses(){
        int i = 1;
        for (Course c : courseTAList){
            System.out.println(i + ". " + c);
            i++;
        }
    }

    public void addStudentGrade(int crsidx, String studentEmail, float _grade){
        if (crsidx <= 0 || crsidx > courseTAList.size()){
            System.out.println("Invalid: Course Index out of range");
            return;
        }
        addNewGrade(studentEmail, _grade, courseTAList.get(crsidx-1));
    }

    public void viewEnrolledStudents(int crsidx){
        if (crsidx <= 0 || crsidx > courseTAList.size()){
            System.out.println("Invalid: Course Index out of range");
            return;
        }
        System.out.println(courseTAList.get(crsidx-1).getTitle());
        int i = 1;
        for (Student s : courseTAList.get(crsidx-1).regStudent){
            System.out.println(i + ". " + s);
            i++;
        }
    }

    private void addNewGrade(String studentEmail, float _grade, Course myCourse){
        // ERROR CHECKING
        if (DataBase.studentList.containsKey(studentEmail)){
            Student myS = (DataBase.studentList.get(studentEmail).val2);
            if (myCourse.regStudent.contains(myS)) {
                int i = 1;
                for (Course c : myS.courseList){
                    if (c == myCourse){
                        break;
                    }
                    i++;
                }
                myS.addGrade(i, _grade);
                System.out.println("Student grade added!");
            }
            else{
                System.out.println("No such student registered for your course!");
            }
        }
        else{
            System.out.println("No such student found!");
        }
    }

    public void viewTASchedule(){
        int i = 1;
        for (Course c : courseTAList){
            System.out.print(i + ". ");
            c.getTT();
        }
        System.out.println();
    }

}
