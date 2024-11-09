package org.example;

import java.util.*;

public class Admin extends User{
        // class for ADMIN which inherits functionalities from USER class
        protected static String keyPass = "admin@123";

        public Admin(String _email){
            super (_email, keyPass);  //calls constructor of User
            DataBase.adminList.put(_email, this);
        }

        public void display(){
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            contact.getInfo();
        }

        public String toString(){
            return name + " [" + email + "]";
        }

        // MANAGE COURSES

        public void deleteCourse(int semester, int index){
            if (semester <= 0 || semester > 8){
                System.out.println("Invalid semester!");
                return;
            }
            if (index <= 0 || index > Course.courseList.get(semester-1).size()){
                System.out.println("Invalid index!");
                return;
            }
            Course myC =  Course.courseList.get(semester-1).get(index-1);
            try{
                for (Student s : myC.regStudent){
                    int idx = 0;
                    for (Course c : s.courseList){
                        if (c == myC){
                            break;
                        }
                        idx ++;
                    }
                    s.dropCourse(idx+1);
                }
            }
            catch (DropDeadlinePassedException e){
                System.out.println("Drop deadline ("+ Course.dropDeadline + ") has Passed. Cannot delete the course now!");
                return;
            }
            Course.courseList.get(semester-1).remove(index-1);
            System.out.println("Course deleted successfully!");
        }

        public void addCourse(String _title, int _semester, String _crsCode, int _credit){
            new Course(_title, _semester, _crsCode, _credit);
            System.out.println("Course added successfully!");
        }

        // MANAGE STUDENTS

        public void viewStudentList(){
            System.out.println("Viewing student list...");
            int i = 1;
            for (Map.Entry<String, Pair<Student>> entry : DataBase.studentList.entrySet()){
                System.out.println(i + ". " + entry.getValue().val2.name + " [" + entry.getKey() +"]");
                i++;
            }
        }

        public void viewStudent(String studentEmail){
            if (DataBase.studentList.containsKey(studentEmail)){
                Student myS = DataBase.studentList.get(studentEmail).val2;
                myS.display();
            }
            else{
                System.out.println("No such student found!");
                return;
            }
        }

        public void studentChangeDetails(String studentEmail, String _name, String number, String address){
            if (DataBase.studentList.containsKey(studentEmail)){
                (DataBase.studentList.get(studentEmail).val2).addContactInfo(_name, number, address);
                System.out.println("Details changed successfully!");
            }
            else{
                System.out.println("No such student found!");
                return;
            }
        }

        public void addStudentGrade(String studentEmail, int _courseIdx, float _grade){
            // assume - > grade not assigned already
            if (DataBase.studentList.containsKey(studentEmail)){
                Student myS = (DataBase.studentList.get(studentEmail).val2);
                if (_courseIdx <= 0 || _courseIdx > myS.courseList.size()){
                    System.out.println("No such course found!");
                    return;
                }
                myS.addGrade(_courseIdx, _grade);
            }
            else{
                System.out.println("No such student found!");
                return;
            }
        }

        // MANAGE PROFESSORS

        public void viewProfList(){
            System.out.println("Viewing professor list...");
            int i = 1;
            for (Map.Entry<String, Pair<Professor>> entry : DataBase.professorList.entrySet()){
                System.out.println(i + ". " + entry.getValue().val2.name + " [" + entry.getKey() + "]");
                i++;
            }
        }

        public void assignProf(int _semester, int crsIdx, String profEmail){
            if (_semester <= 0 || _semester > 8){
                System.out.println("Invalid semester!");
                return;
            }
            if (crsIdx <= 0 || crsIdx > Course.courseList.get(_semester-1).size()){
                System.out.println("Invalid index!");
                return;
            }
            if (DataBase.professorList.containsKey(profEmail)){
                Professor myP = DataBase.professorList.get(profEmail).val2;
                if (myP.getMyCourse() != null){
                    System.out.println("Professor already assigned to another course!");
                    return;
                }
                ((Course.courseList.get(_semester-1)).get(crsIdx-1)).assignProf(myP);
                System.out.println("Professor assigned successfully!");
            }
            else{
                System.out.println("No such professor found!");
                return;
            }
        }

        public void unAssignProf(String profEmail){
            if (DataBase.professorList.containsKey(profEmail)) {
                Professor myP = DataBase.professorList.get(profEmail).val2;
                (myP.getMyCourse()).setProfessor(null);
                myP.setCourse(null);
                System.out.println("Professor is now assigned to no course!");
            }
            else{
                System.out.println("No such professor found!");
            }
        }

        // HANDLE COMPLAINTS

        public void showComplaints(int order){
            List<Map.Entry<Integer, Complaint>> entryList = new ArrayList<>(Complaint.complaintList.entrySet());
            Comparator<Map.Entry<Integer, Complaint>> valueComp1 = (e1, e2) -> e1.getValue().compareTo(e2.getValue());
            Comparator<Map.Entry<Integer, Complaint>> valueComp2 = (e1, e2) -> e2.getValue().compareTo(e1.getValue());

            if (order == 1){
                entryList.sort(valueComp1);
            }
            else if (order == -1){
                entryList.sort(valueComp2);
            }

            for (Map.Entry<Integer, Complaint> m: entryList){
                System.out.println (m.getValue());
            }
        }

        public void showComplaints(String status){
            Complaint.complaintList.forEach((pid, complaint) -> {
                if (complaint.status.equals(status)) {
                    System.out.println(complaint);
                }
            });
        }

        public void resolveComplaint(int pID){
            (Complaint.complaintList.get(pID)).resolve(this);
            System.out.println("Complaint resolved!");
        }
}
