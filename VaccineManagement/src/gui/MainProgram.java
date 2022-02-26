/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.VaccineDao;
import util.MyToys;

public class MainProgram {

    public static void main(String[] args) {
        int choice;
        
        VaccineDao myManagement = new VaccineDao();
        myManagement.saveFileStudent();
        do {
            System.out.println("Welcome to Vaccine Management - @2021 <SE151074"
                    + "- Ngô Xuân Thiệp>");
            System.out.println("Select the options below");
            System.out.println("0. Print Injection List");
            System.out.println("1. Show information all students have been injected.");
            System.out.println("2. Add student's vaccine injection inforamtion.");
            System.out.println("3. Updating information of student's vaccine injection.");
            System.out.println("4. Delete student vaccine injection information. ");
            System.out.println("5. Search for injection information by StudentID.");
            System.out.println("6. Save information all students have been injection.");
            System.out.println("7. Quit");
            choice = MyToys.getAnInteger("Your choice: ", "You need to choose 0 to 7. Please try again !!! ", 0, 7);
            switch (choice) {
                case 0:
                     System.out.println("You are preparing to print information all students have "
                            + "been injected.");
                    myManagement.printInformation();
                    break;
                case 1:
                    System.out.println("You are preparing to show information all students have "
                            + "been injected.");
                    myManagement.readFileInjectionV1();
                    break;
                case 2:
                    int input;
                    System.out.println("You are preparing to "
                            + "create a new injection information.");
                    myManagement.addInjection();
                    do {
                        input = MyToys.isContinue("Do you really want to continue adding another injection (y/n)?: ",
                                "You need enter: y (Yes) or n (No) !!!");
                        if (input == 1) {
                            myManagement.addInjection();
                        } else {
                            MyToys.getEnter();
                            break;
                        }
                    } while (input == 1);
                    break;
                case 3:
                    System.out.println("You are preparing to upadting information "
                            + "of student's vaccine injection.");
                    myManagement.updateInjection();
                    break;
                case 4:
                    System.out.println("You are preparing to delete student vaccine injection by Injection ID.");
                    myManagement.deleteInjection();
                    break;
                case 5:
                    System.out.println("You are preparing to search for injection information"
                            + "by Stuednt ID.");
                    myManagement.searchInjectionByStudentID();
                    break;
                case 6:
                    System.out.println("You are preaparing to save all data in the injection.dat file. ");
                    myManagement.saveFileInjection();
                    break;
                case 7:
                    System.out.println("Good Bye!!!. See you again.");
                    break;
            }
        } while (choice != 7);
    }
}
