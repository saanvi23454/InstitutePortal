package org.example;

import java.util.*;

public class DataBase {

    protected static HashMap<String, Admin> adminList = new HashMap<>(); //static list
    protected static HashMap<String, Pair<Student>> studentList = new HashMap<>();
    protected static HashMap<String, Pair<Professor>> professorList = new HashMap<>();
    protected static HashMap<String, Pair<TA>> TAList = new HashMap<>();


    public static boolean checkProfPWD(String _email, String _pwd){ //helps in login
        if (DataBase.professorList.get(_email) != null) {
            return (DataBase.professorList.get(_email).val1).equals(_pwd);
        }
        return false;
    }

    public static Professor getProfessor(String _email, String _pwd){ //helps after login
        if (checkProfPWD(_email, _pwd)){
            return (DataBase.professorList.get(_email).val2);
        }
        return null;
    }

    public static boolean checkStudPWD(String _email, String _pwd){ //helps in login
        if (DataBase.studentList.get(_email) != null) {
            return (DataBase.studentList.get(_email).val1).equals(_pwd);
        }
        return false;
    }
    public static Student getStudent(String _email, String _pwd){ //helps after login
        if (checkStudPWD(_email, _pwd)){
            return (DataBase.studentList.get(_email).val2);
        }
        return null;
    }

    public static boolean checkAdmPWD(String _email, String _pwd){ //helps in login
        if (DataBase.adminList.containsKey(_email)){
            if (_pwd.equals(Admin.keyPass)){
                return true;
            }
        }
        return false;
    }

    public static Admin getAdmin(String _email, String _pwd){ //helps after login
        if (checkAdmPWD(_email, _pwd)){
            return (DataBase.adminList.get(_email));
        }
        return null;
    }

    public static TA getTA(String _email, String _pwd){ //helps after login
        if (TAList.containsKey(_email)){
            if (checkStudPWD(_email, _pwd)){
                return (DataBase.TAList.get(_email).val2);
            }
        }
        return null;
    }

}
