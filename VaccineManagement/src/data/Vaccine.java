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
public class Vaccine implements Serializable {
    private String vaccineID;
    private String name;

    public Vaccine(String vaccineID, String name) {
        this.vaccineID = vaccineID;
        this.name = name;
    }

    public String getVaccineID() {
        return vaccineID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Vaccine{" + "vaccineID =" + vaccineID + ", name =" + name + '}';
    }
    
    public void showInformation() {
        System.out.printf("|%-11s|%-12s|\n", vaccineID, name);
    }
    
}
