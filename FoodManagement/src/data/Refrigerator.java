package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Validation;

/**
 *
 * @author Thiep Ngo
 */
public class Refrigerator {

    private ArrayList<Food> foodList = new ArrayList();

    File w = new File("food.dat");

    public void addANewFood() {
        String id, name, type, place;
        Date expiredDate;
        double weight;
        int pos;
        System.out.println("====== User inputs new food's information ======");
        do {
            id = Validation.getID("Enter the ID of this food: ", "ERROR. Please try again !!!");
            pos = searchFoodByID(id);
            if (pos >= 0) {
                System.out.println("This ID already exists. Please inupt another one!");
            }
        } while (pos != -1);
        name = Validation.getString("Enter the name of this food: ", "ERROR. Please try again !!!");
        weight = Validation.getAnDouble("Enter the weight of this food: ", "ERROR. Plese try again !!!");
        type = Validation.getString("Enter the type of this food: ", "ERROR. Please try again !!!");
        place = Validation.getString("Enter the place(where your put this"
                + "food in refrigerator) of this food: ", "ERROR. Please try again !!!");
        expiredDate = Validation.getValidDate("Enter the expired date of this food (dd/mm/yyyy): ", "ERROR.  Please try again !!!");
        foodList.add(new Food(id, name, weight, type, place, expiredDate));
        System.out.println("A new food is added sucessfully in the refrigerator.");
    }

    public int searchFoodByID(String foodID) {
        if (foodList.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < foodList.size(); i++) {
            if (foodList.get(i).getId().equalsIgnoreCase(foodID)) {
                return i;
            }
        }
        return -1;
    }

    public int searchFoodByKeyword(String keyword) {
        if (foodList.isEmpty()) {
            return -1;
        } else {
            for (int i = 0; i < foodList.size(); i++) {
                if ((foodList.get(i).getName().toLowerCase().contains(keyword))) {
                    return i;
                }
            }
        }
        return -1;
    }
    public int searchFoodByDate(Date date) {
        if (foodList.isEmpty()) {
            return -1;
        } else {
            for (int i = 0; i < foodList.size(); i++) {
                if ((foodList.get(i).getExpiredDate().compareTo(date) == 0)) {
                    return i;
                }
            }
        }
        return -1;
    }
    public int searchFoodByWeight(double weight) {
        if (foodList.isEmpty()) {
            return -1;
        } else {
            for (int i = 0; i < foodList.size(); i++) {
                if (foodList.get(i).getWeight() == weight) {
                    return i;
                }
            }
        }
        return -1;
    }
    public int searchFoodByWeightV2(double weight) {
        int count = 0;
          if (foodList.isEmpty()) {
            return -1;
        } else {
            for (int i = 0; i < foodList.size(); i++) {
                if (foodList.get(i).getWeight() == weight) {
                    count++;
                }
            }
        }
        return count;
    }
    public int searchFoodByWeightV3(double x) {
          if (foodList.isEmpty()) {
            return -1;
        } else {
            for (int i = 0; i < foodList.size(); i++) {
                if (foodList.get(i).getWeight() >= x && foodList.get(i).getWeight() < x + 1) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public void searchWeight() {
        double x = Validation.getAnDouble("AS", "asd");
        int pos = searchFoodByWeightV3(x);
        if(pos != -1) {
             for (int i = 0; i < foodList.size(); i++) {
                if (foodList.get(i).getWeight() >= x && foodList.get(i).getWeight() < x + 10) {
                    foodList.get(i).showProfile();
                }
            }
        } else {
            System.out.println("Weight is not exist");
        }
    }
     public int searchFoodByDateV2(Date date) {
        int count = 0;
         if (foodList.isEmpty()) {
            return -1;
        } else {
            for (int i = 0; i < foodList.size(); i++) {
                if ((foodList.get(i).getExpiredDate().compareTo(date) == 0)) {
                    count ++;
                }
            }
        }
        return count;
    }
    

    public void searchFoodByName() {
        int pos, input;
        int result = 0;
        String keyword;
        if (foodList.isEmpty()) {
            System.out.println("The refrigerator is empty. Please "
                    + "go to back to the main menu and add a new food.");
            Validation.getEnter();
            return;
        }
        do {
            System.out.println("Here is the food list");
            for (Food x : foodList) {
                x.showProfile();
            }
            System.out.println("============================= Have " + foodList.size() + " rows in here ============================== ");

            keyword = Validation.getString("Enter a keyword you want to search: ",
                    "ERROR. Please try again !!!").toLowerCase();
            System.out.printf("|%-3s|%-20s|%-8s|%-15s|%-15s|%-12s|\n", "ID", "Name", "Weight", "Type", "Place", "Expired Date");
            pos = searchFoodByKeyword(keyword);
            if (pos != -1) {
                for (int i = 0; i < foodList.size(); i++) {
                    if (foodList.get(i).getName().toLowerCase().contains(keyword)) {
                        foodList.get(i).showProfile();
                        result++;
                    }
                }
                System.out.println("============================= Have " + result + " rows in here ============================== ");
            } else {
                System.out.println("This food does not exist");
            }
            input = Validation.isContinue("Do you really want to continue searching another food (y/n)?: ",
                    "You need enter: y (Yes) or n (No) !!!");
            if (input == -1) {
                Validation.getEnter();
                break;
            } else {
                result = 0;
            }
        } while (input == 1);
    }

    public void removeFood() {
        String id;
        int pos;
        if (foodList.isEmpty()) {
            System.out.println("The refrigerator is empty. Please "
                    + "go to back to the main menu and add a new food.");
            Validation.getEnter();
        }
        id = Validation.getID("Enter ID of the food that you want to remove: ", "ERROR. Please try again !!!");
        pos = searchFoodByID(id);
        System.out.println("==================================== WAITING ====================================");
        if (pos == -1) {
            System.out.println("This id food does not exist !!!");
            System.out.println("Detele failed !!!");
            Validation.getEnter();
        } else {
            int input = Validation.isContinue("Do you really want to remove this id food (y/n) ?: ",
                    "You need enter: y (Yes) or n (No) !!!");
            switch (input) {
                case 1:
                    foodList.remove(pos);
                    System.out.println("The selected food is removed successfully !!!");
                    System.out.println("Id food: " + id + " removed.");
                    Validation.getEnter();
                    break;
                case -1:
                    System.out.println("Detele failed !!! Please try again....");
                    Validation.getEnter();
                    break;
            }
        }
    }
    
     public void removeFoodByDate() {
        Date date;
        int pos, count = 0;
        if (foodList.isEmpty()) {
            System.out.println("The refrigerator is empty. Please "
                    + "go to back to the main menu and add a new food.");
            Validation.getEnter();
        }
        date = Validation.getValidDate("Enter Date that you want to remove: ", "ERROR. Please try again !!!");
        pos = searchFoodByDate(date);
        count = searchFoodByDateV2(date);
        System.out.println("==================================== WAITING ====================================");
        if (pos == -1) {
            System.out.println("This id food does not exist !!!");
            System.out.println("Detele failed !!!");
            Validation.getEnter();
        } else {
            int input = Validation.isContinue("Do you really want to remove this id food (y/n) ?: ",
                    "You need enter: y (Yes) or n (No) !!!");
            switch (input) {
                case 1:
                    do {
                    for (int i = 0; i < foodList.size(); i++) {
                        if(foodList.get(i).getExpiredDate().compareTo(date) == 0) {
                            count--;
                           foodList.remove(i);
                        }
                    }
                    } while(count != 0);
                    System.out.println("The selected food is removed successfully !!!");
                    System.out.println("Date: " + Validation.convertDateToString(date) + " removed.");
                    Validation.getEnter();
                    break;
                case -1:
                    System.out.println("Detele failed !!! Please try again....");
                    Validation.getEnter();
                    break;
            }
        }
    }
     
      public void removeFoodByWeight() {
        double weight;
        int pos, count = 0;
        if (foodList.isEmpty()) {
            System.out.println("The refrigerator is empty. Please "
                    + "go to back to the main menu and add a new food.");
            Validation.getEnter();
        }
        weight = Validation.getAnDouble("Enter weight that you want to remove: ", "ERROR. Please try again !!!");
        pos = searchFoodByWeight(weight);
        count = searchFoodByWeightV2(weight);
        System.out.println("==================================== WAITING ====================================");
        if (pos == -1) {
            System.out.println("This weight food does not exist !!!");
            System.out.println("Detele failed !!!");
            Validation.getEnter();
        } else {
            int input = Validation.isContinue("Do you really want to remove this id food (y/n) ?: ",
                    "You need enter: y (Yes) or n (No) !!!");
            switch (input) {
                case 1:
                    do {
                    for (int i = 0; i < foodList.size(); i++) {
                        if(foodList.get(i).getWeight() == weight) {
                            count--;
                           foodList.remove(i);
                        }
                    }
                    } while(count != 0);
                    System.out.println("The selected food is removed successfully !!!");
                    System.out.println("Weight: " + weight + " removed.");
                    Validation.getEnter();
                    break;
                case -1:
                    System.out.println("Detele failed !!! Please try again....");
                    Validation.getEnter();
                    break;
            }
        }
    }
    

    public void printTheFood() {
        if (foodList.isEmpty()) {
            System.out.println("The refrigerator is empty. Nothing to print. Please "
                    + "go to back to the main menu and add a new food.");
            Validation.getEnter();
            return;
        }
        System.out.println("This is the list of food before desending by expired date.");
        System.out.printf("|%-3s|%-20s|%-8s|%-15s|%-15s|%-12s|\n", "ID", "Name", "Weight", "Type", "Place", "Expired Date");
        for (int i = 0; i < foodList.size(); i++) {
            foodList.get(i).showProfile();
        }
        
         for (int i = 0; i < foodList.size() - 1; i++) {
            for (int j = i + 1; j < foodList.size(); j++) {
                if(foodList.get(i).getName().compareTo(foodList.get(j).getName()) > 0) {
                    Food tmp = foodList.get(i);
                    foodList.set(i, foodList.get(j));
                    foodList.set(j, tmp);
                }
            }
            }
        
        System.out.println("================================= WAITING =================================");
        System.out.println("This is the list of food after desending by expired date.");
        System.out.printf("|%-3s|%-20s|%-8s|%-15s|%-15s|%-12s|\n", "ID", "Name", "Weight", "Type", "Place", "Expired Date");
        for (int i = 0; i < foodList.size(); i++) {
            foodList.get(i).showProfile();
        }
        System.out.println("============================= Have " + foodList.size() + " rows in here ============================= ");
        int choice = Validation.isContinue("Do you want to save all information of the food"
                + "in the refrigerator to the file(y/n) ?: ", "You need enter: y (Yes) or n (No) !!!");
        if (choice == 1) {
            saveFile();
        } else {
            Validation.getEnter();
        }
    }

    public void saveFile() {
        String inputMsg = Validation.getString("Input your file name(<file_name>.dat) you want"
                + "to save: ", "ERROR !!!. Please input again");
        if (inputMsg.contains(".dat") && !inputMsg.contains(" ")) {
            File f = new File(inputMsg);
            if (f.exists()) {
                System.out.println("\nThis file is already in the folder, here are some suggestions for you");
                System.out.println("1. REPLACE IT");
                System.out.println("2. CREATE A NEW FILE NAME");
                int choice = Validation.getAnInteger("Enter your choice: ", "You are required to choose the option 1 or 2", 1, 2);
                switch (choice) {
                    case 1:
                    try {
                        FileOutputStream fos = new FileOutputStream(inputMsg);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        for (int i = 0; i < foodList.size(); i++) {
                            oos.writeObject(foodList.get(i));
                        }
                        oos.close();
                        fos.close();
                    } catch (IOException e) {

                    }
                    break;
                    case 2:
                        String newFileName;
                        boolean checkName = true;
                        do {
                            newFileName = Validation.getFileName("Enter new file name that you want to save: ", "File name is required");
                            if (newFileName.equalsIgnoreCase(inputMsg)) {
                                System.out.println("This file has the same name, please choose a different name");
                                checkName = false;
                            } else {
                                checkName = true;
                            }
                        } while (checkName == false);
                        try {
                            FileOutputStream fos = new FileOutputStream(newFileName);
                            ObjectOutputStream oos = new ObjectOutputStream(fos);
                            for (int i = 0; i < foodList.size(); i++) {
                                oos.writeObject(foodList.get(i));
                            }
                            oos.close();
                            fos.close();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                }
            } else {
                System.out.println("...................Saving...................");
                try {
                    FileOutputStream fos = new FileOutputStream(f);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    for (int i = 0; i < foodList.size(); i++) {
                        oos.writeObject(foodList.get(i));
                    }
                    oos.close();
                    fos.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Refrigerator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Refrigerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("            Save sucessfully !!!");
            System.out.println("=================================================================");
        } else {
            System.out.println("               Save failed !!!!");
            System.out.println("==================================================================");
        }
    }

    public void showFiveFood() {
        if (foodList.isEmpty()) {
            readFiveFood();
        } else {
            System.out.printf("|%-3s|%-20s|%-8s|%-15s|%-15s|%-12s|\n", "ID", "Name", "Weight", "Type", "Place", "Expired Date");
            for (int i = 0; i < foodList.size(); i++) {
                foodList.get(i).showProfile();
            }
            System.out.println("============================= Have " + foodList.size() + " rows in here =============================");
            Validation.getEnter();
        }
    }

    public void readFiveFood() {
        if (w.exists()) {
            try {
                FileInputStream fis = new FileInputStream(w);
                ObjectInputStream oos = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    Food f = (Food) oos.readObject();
                    foodList.add(f);
                }
                fis.close();
                oos.close();

            } catch (Exception e) {
            }
            for (int i = 0; i < foodList.size(); i++) {
                foodList.get(i).showProfile();
            }
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(w);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String dateString1 = "24/12/2021";
                String dateString2 = "11/03/2022";
                String dateString3 = "08/03/2021";
                String dateString4 = "24/10/2021";
                String dateString5 = "15/02/2022";
                Date dateObject1 = sdf.parse(dateString1);
                Date dateObject2 = sdf.parse(dateString2);
                Date dateObject3 = sdf.parse(dateString3);
                Date dateObject4 = sdf.parse(dateString4);
                Date dateObject5 = sdf.parse(dateString5);
                foodList.add(new Food("1", "Chicken", 50.25, "Fast Food", "2nd Shelf", dateObject1));
                foodList.add(new Food("2", "Hamburger", 124.35, "Fast Food", "2nd Shelf", dateObject2));
                foodList.add(new Food("3", "Muffins", 9.7, "Fast Food", "3rd Shelf", dateObject3));
                foodList.add(new Food("4", "Hot dog", 53.5, "Fast Food", "4th Shelf", dateObject4));
                foodList.add(new Food("5", "Pizza", 92.4, "Fast Food", "5th Shelf", dateObject5));
                for (Food vc : foodList) {
                    oos.writeObject(vc);
                }
                oos.close();
                fos.close();
            } catch (Exception e) {

            }
            System.out.printf("|%-3s|%-20s|%-8s|%-15s|%-15s|%-12s|\n", "ID", "Name", "Weight", "Type", "Place", "Expired Date");
            for (Food x : foodList) {
                x.showProfile();
            }
            System.out.println("============================= Have " + foodList.size() + " rows in here =============================");
            Validation.getEnter();
        }
    }

}
