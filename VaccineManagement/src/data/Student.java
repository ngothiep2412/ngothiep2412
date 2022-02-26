/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;

/**
 *
 * @author Thiep Ngo
 */
public class Student implements Serializable{

    private String name;
    private String studentID;

    public Student(String studentID, String name) {
        this.name = name;
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    @Override
    public String toString() {
        return "Student{" + "name =" + name + ", studentID =" + studentID + '}';
    }
    
     public void showInformation() {
        System.out.printf("|%-10s|%-11s\n", studentID, name);
    }

}
