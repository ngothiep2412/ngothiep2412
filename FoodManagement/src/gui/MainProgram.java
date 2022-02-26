package gui;

import data.Refrigerator;
import util.Validation;

public class MainProgram {

    public static void main(String[] args) {
        int choice;

        Refrigerator refrigerator = new Refrigerator();
        do {
            System.out.println("Welcome to Food Management - @2021 <SE151074"
                    + "- Ngô Xuân Thiệp>");
            System.out.println("Select the options below");
            System.out.println("0. Five food are already.");
            System.out.println("1. Add a new food ");
            System.out.println("2. Search a food by name ");
            System.out.println("3. Remove the food by ID");
            System.out.println("4. Print the food list in the descending order of expired date");
            System.out.println("5. Quit");
            choice = Validation.getAnInteger("Your choice: ", "You need to choose 0 to 5. Please try again !!! ", 0, 5);
            switch (choice) {
                case 0:
                    System.out.println("You are preparing to add five food.");
                    refrigerator.showFiveFood();
                    break;
                case 1:
                    int input;
                    System.out.println("You are preparing to "
                            + "input a new food information.");
                    refrigerator.addANewFood();
                    do {
                        input = Validation.isContinue("Do you really want to continue adding another food (y/n)?: ",
                                "You need enter: y (Yes) or n (No) !!!");
                        if (input == 1) {
                            refrigerator.addANewFood();
                        } else {
                            Validation.getEnter();
                            break;
                        }
                    } while (input == 1);
                    break;
                case 2:
                    System.out.println("You are preparing to search a food by name.");
                    refrigerator.searchFoodByName();
                    break;
                case 3:
                    System.out.println("You are preparing to remove the food by ID.");
                    refrigerator.removeFoodByWeight();
                    break;
                case 4:
                    System.out.println("You are preparing to print the food list in the"
                            + " descending order of expired date.");
                    refrigerator.printTheFood();
                    break;
                case 5:
                    System.out.println("Bye Bye. See you next time!");
                    break;
            }
        } while (choice != 5);
    }

}
