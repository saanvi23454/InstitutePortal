package org.example;

public class Contact {
    private String number = "";
    private String address = "";

    public void addInfo(String _number, String _address){
        number = _number;
        address = _address;
    }
    public void getInfo() {
        System.out.println("Number: " + number + "\nAddress: " + address);
    }
}