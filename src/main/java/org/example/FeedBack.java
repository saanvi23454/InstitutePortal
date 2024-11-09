package org.example;

public class FeedBack <T>{
    private final T content;
    private final Student student;
    private final String type;
    public FeedBack(T content, Student student, String type) {
        this.content = content;
        this.student = student;
        this.type = type;
    }
    public String toString(){
        return (type + " " + content.toString());
    }

    private Student getStudent(){
        return student;
    }
}
