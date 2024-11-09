package org.example;
import java.time.LocalDateTime;
import java.util.*;

public class Student extends User {

        protected List<List<Pair<Float>>> gradeList = new ArrayList<>();
        protected List<Course> courseList = new ArrayList<>();
        protected List <Float> SGPA = new ArrayList<>();
        protected List<Complaint> myComplaint = new ArrayList<>(); //can only be assigned one, but can be modified
        protected List<Boolean> addedCommentFeedBack = new ArrayList<>();
        protected List<Boolean> addedRatingFeedBack = new ArrayList<>();


    protected int semester;
        protected int creditCompleted = 0;
        protected float CGPA = 0;

        protected int creditCurrent = 0;
        protected float totalMarksCurrent = 0;
        protected int numberOfGradesFinal = 0; //used by admin


        public Student(String _email, String _password){
            super(_email, _password);
            DataBase.studentList.put(_email, new Pair<Student>(_password, this));
            this.addSemester(1);
        }

        public void display(){
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            contact.getInfo();
            System.out.println("Semester: " + semester);
            System.out.println("CGPA: " + getCGPA());
            System.out.println("Course List: " + courseList);
            System.out.println("Credit Registered: " + creditCurrent);
            System.out.println("Credit Completed: " + creditCompleted);
        }

        public String toString(){
            return name + " [" + email + "] " + "...CGPA = " + getCGPA();
        }

        protected void addSemester(int num){ //used by admin
            // semester is between 1 and 8
            semester = num;
            for (Course c: courseList){
                c.removeStudent(this);
            }
            courseList.clear();
            gradeList.add(new ArrayList<>());
            creditCompleted += creditCurrent;
            creditCurrent = 0;
            totalMarksCurrent = 0;
            numberOfGradesFinal = 0;
            addedRatingFeedBack.clear();
            addedCommentFeedBack.clear();

        }

        public void viewCourseList(){
            int i = 1;
            for (Course c : Course.courseList.get(semester-1)){
                System.out.println(i + ".\n");
                c.getTT();
                System.out.println();
                i++;
            }
        }

        public void viewGradeList(){
            int i = 1;
            for (List<Pair<Float>> myL : gradeList){
                System.out.println("Semester " + i + ": ");
                for (Pair<Float> myP : myL){
                    System.out.println(myP.val1 + " : " + myP.val2);
                }
                i++;
            }
        }

        // MANAGE COURSES

        public void registerCourse(int sem, int index) throws CourseFullException{
            if (sem > semester || sem <= 0){
                System.out.println("Invalid semester!");
                return;
            }
            if (index > Course.courseList.get(sem-1).size() || index <= 0){
                System.out.println("Invalid index!");
                return;
            }
            Course crs = (Course.courseList.get(sem-1)).get(index-1);
            if (creditCurrent + crs.getCredit() > 20){
                System.out.println("Exceeding credit limit, cannot register...");
                return;
            }
            for (Course c : crs.preReqs){
                boolean flag = false;
                for (int j = 0; j < semester-1; j++) {
                    for (Pair<Float> p : gradeList.get(j)) {
                        if ((p.val1).equals(c.getTitle())) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag){
                        break;
                    }
                }
                if (!flag){
                    System.out.println("Prerequisites not completed, cannot register...");
                    return;
                }
            }
            if (crs.isFull()){
                throw new CourseFullException(crs.getTitle() + "course is already full, cannot register.");
            }
            courseList.add(crs);
            addedRatingFeedBack.add(false);
            addedCommentFeedBack.add(false);
            crs.addStudent(this);
            creditCurrent += crs.getCredit();
            System.out.println(crs.getTitle() + " course registered successfully!");
        }

        public void dropCourse(int index)throws DropDeadlinePassedException{
            if (LocalDateTime.now().isAfter(Course.dropDeadline)){
                throw new DropDeadlinePassedException("Drop deadline ("+ Course.dropDeadline + ") has passed; cannot drop the course now.");
            }
            if (index > courseList.size() || index <= 0){
                System.out.println("Invalid");
                return;
            }
            //assume grade not given yet for this course
            creditCurrent -= (courseList.get(index-1)).getCredit();
            (courseList.get(index-1)).removeStudent(this);
            courseList.remove(index-1);
            addedRatingFeedBack.remove(index-1);
            addedCommentFeedBack.remove(index-1);
            System.out.println("Course removed successfully");
        }

        public void myCourseList(){
            int i = 1;
            for (Course c : courseList){
                System.out.println(i + ".\n");
                c.getTT();
                System.out.println();
                i++;
            }
        }

        public void viewSchedule(){
            int i = 1;
            System.out.println("Semester : " + semester);
            for (Course c : courseList){
                System.out.println(i + ".\n");
                c.getTT();
                System.out.println();
                i ++;
            }
        }

        public void addGrade(int _courseIdx, float _grade){
            // assume 1 time only grade is assigned
            if (_courseIdx > courseList.size() || _courseIdx <= 0){
                System.out.println("Invalid");
                return;
            }
            (gradeList.get(semester-1)).add(new Pair<Float>((courseList.get(_courseIdx-1).getTitle()), _grade));
            numberOfGradesFinal += 1;

            totalMarksCurrent += (_grade)*(float)((courseList.get(_courseIdx-1)).getCredit());
            if (numberOfGradesFinal == courseList.size()){
                setSGPA();
                if (semester != 8){
                    addSemester(semester+1);
                }
                else{
                    addSemester(semester+1);
                    System.out.println("B.Tech Completed!");
                }
            }
            System.out.println("Grade added successfully!");
        }


        // TRACK ACADEMIC STANDINGS

        public float getCGPA(){
            return CGPA;
        }

        protected void setSGPA(){
            SGPA.add(totalMarksCurrent/(float)creditCurrent);
            CGPA = ((CGPA*creditCompleted) + (totalMarksCurrent))/(creditCompleted + creditCurrent);
        }

        public float getSGPA(int _semester){
            if (_semester < semester & _semester > 0){
                return SGPA.get(_semester-1);
            }
            else{
                return 0;
            }
        }

        public void viewSGPA(int _semester){
            if (_semester < semester & _semester > 0){
                System.out.println("SGPA for " + _semester + " is " + SGPA.get(_semester-1));
            }
            else if (_semester == semester){
                System.out.println("Grades not finalized yet...");
            }
            else{
                System.out.println("Invalid query");
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
        public void addFeedBackComment(int courseIdx, String comment){
            if (courseIdx > courseList.size() || courseIdx <= 0){
                System.out.println("Invalid");
                return;
            }
            if (addedCommentFeedBack.get(courseIdx - 1)){
                System.out.println("Feedback already submitted...cannot submit more than once!");
                return;
            }
            courseList.get(courseIdx-1).addFeedBack(new FeedBack<String>(comment, this, "Comment"));
            addedCommentFeedBack.set(courseIdx-1, true);
            System.out.println("FeedBack submitted!");
        }

        public void addFeedBackRating(int courseIdx, int rating){
            if (courseIdx > courseList.size() || courseIdx <= 0){
                System.out.println("Invalid");
                return;
            }
            if (addedRatingFeedBack.get(courseIdx - 1)){
                System.out.println("Rating feedback already submitted...cannot submit more than once!");
                return;
            }
            if (rating > 5 || rating <= 0){
                System.out.println("Rating not within the 1 to 5 scale...Failed submission");
                return;
            }
            courseList.get(courseIdx-1).addFeedBack(new FeedBack<Integer>(rating, this, "Rating"));
            addedRatingFeedBack.set(courseIdx-1, true);
            System.out.println("FeedBack submitted!");
        }
}
