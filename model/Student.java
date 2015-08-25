package com.example.adri4n.databaseex1.model;

/**
 * Created by adri4n on 17.03.2015.
 */
public class Student {
    private int id;
    private String name;
    private String email;
    private String address;
    private double grade;

    public Student() {

    }

    public Student(int id, String name, String email, String address, double grade) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
