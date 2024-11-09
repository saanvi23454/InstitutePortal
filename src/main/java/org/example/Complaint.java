package org.example;
import java.time.LocalDateTime;
import java.util.*;

public class Complaint implements Comparable<Complaint> {
    static int serial = 0;

    static HashMap<Integer, Complaint> complaintList = new HashMap<>();
    int PID;
    String status;
    String description;
    LocalDateTime dateOfCreation;
    LocalDateTime dateOfResolution;

    User sender; //polymorphism
    Admin resolver;

    public Complaint(String desc, User _sender){
        serial++;
        PID = serial;
        description = desc;
        status = "PENDING";
        dateOfCreation = LocalDateTime.now();
        complaintList.put(PID, this);
        sender = _sender;
    }

    public void resolve(Admin _resolver){
        status = "RESOLVED";
        dateOfResolution = LocalDateTime.now();
        resolver = _resolver;
    }

    public int compareTo(Complaint other){
        return this.dateOfCreation.compareTo(other.dateOfCreation);
    } //sorts based on date of creation;

    public String toString() {
        return ("PID " + PID + " : " + status + "\n" + description + "\n\t\tSubmitted by " + sender.email); //polymorphism
    }
}
