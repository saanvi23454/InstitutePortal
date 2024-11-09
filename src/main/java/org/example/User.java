package org.example;

public abstract class User { //don't want to instantiate

    String name = "";
    String email;
    String password;
    Contact contact = new Contact();
    //String type;

    public User(String _email, String _password){
        email = _email;
        password = _password;
    }

    public void addContactInfo(String _name, String number, String address){
        contact.addInfo(number, address);
        name = _name;
    }

    public void addContactInfo(String _name){
        name = _name;
    }

    public void getContactInfo(){
        contact.getInfo();
    }

    public abstract String toString();

    public void viewCourseCatalog(){
        for (int i = 1; i < 9; i++){
            System.out.println("Semester " + i + ": ");
            int j = 1;
            for (Course crs : Course.courseList.get(i-1)){
                System.out.println(j + ". " + crs.getCourseCode() + " - " + crs.getTitle());
                j++;
            }
            System.out.println();
        }
    }
}
