/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.Date;
import util.MyToys;

/**
 *
 * @author Thiep Ngo
 */
public class Injection implements Serializable{

    private String injectionID;
    private String injectionPlace1st;
    private String injectionPlace2nd;
    private Date injectionDate1st;
    private Date injectionDate2nd;
    private String studentID;
    private String vaccineID;

    public Injection(String injectionID, String injectionPlace1st, String injectionPlace2nd, Date injectionDate1st, Date injectionDate2nd, String studentID, String vaccineID) {
        this.injectionID = injectionID;
        this.injectionPlace1st = injectionPlace1st;
        this.injectionPlace2nd = injectionPlace2nd;
        this.injectionDate1st = injectionDate1st;
        this.injectionDate2nd = injectionDate2nd;
        this.studentID = studentID;
        this.vaccineID = vaccineID;
    }

    public String getInjectionID() {
        return injectionID;
    }

    public void setInjectionID(String injectionID) {
        this.injectionID = injectionID;
    }

    public String getInjectionPlace1st() {
        return injectionPlace1st;
    }

    public void setInjectionPlace1st(String injectionPlace1st) {
        this.injectionPlace1st = injectionPlace1st;
    }

    public String getInjectionPlace2nd() {
        return injectionPlace2nd;
    }

    public void setInjectionPlace2nd(String injectionPlace2nd) {
        this.injectionPlace2nd = injectionPlace2nd;
    }

    public Date getInjectionDate1st() {
        return injectionDate1st;
    }

    public void setInjectionDate1st(Date injectionDate1st) {
        this.injectionDate1st = injectionDate1st;
    }

    public Date getInjectionDate2nd() {
        return injectionDate2nd;
    }

    public void setInjectionDate2nd(Date injectionDate2nd) {
        this.injectionDate2nd = injectionDate2nd;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

    @Override
    public String toString() {
        return "Injection{" + "injectionID=" + injectionID + ", injectionPlace1st=" + injectionPlace1st + ", injectionPlace2nd=" + injectionPlace2nd + ", injectionDate1st=" + injectionDate1st + ", injectionDate2nd=" + injectionDate2nd + ", studentID=" + studentID + ", vaccineID=" + vaccineID + '}';
    }

    public void showInformation() {
        System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", injectionID, injectionPlace1st,
                MyToys.convertDateToString(injectionDate1st), injectionPlace2nd,  MyToys.convertDateToString(injectionDate2nd), studentID, vaccineID);
    }
}
