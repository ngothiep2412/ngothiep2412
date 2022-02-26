package dao;

import data.Injection;
import data.Student;
import data.Vaccine;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import util.MyToys;

public class VaccineDao implements InjectionInteface {

    static int lastStudentID;
    static int lastVaccineID;
    static int lastInjectionID;

    private List<Injection> injectionList = new ArrayList();

    Student student[];
    Vaccine vaccine[];
    File f = new File("student.dat");
    File q = new File("vaccine.dat");
    File w = new File("injection.dat");

    public VaccineDao() {
        student = new Student[10];
        vaccine = new Vaccine[5];
        lastStudentID = 0;
        if (f.exists()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    student[lastStudentID++] = (Student) ois.readObject();
                }
                ois.close();
                fis.close();
            } catch (Exception e) {

            }
        }
        lastVaccineID = 0;
        if (q.exists()) {
            try {
                FileInputStream fis = new FileInputStream(q);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    vaccine[lastVaccineID++] = (Vaccine) ois.readObject();
                }
                ois.close();
                fis.close();
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void addInjection() {
        int count = 0;
        boolean injectionDate2nd_2;
        String injectionID, injectionPlace1st, injectionPlace2nd = "";
        Date injectionDate1st, injectionDate2nd = null;
        String studentID = "", vaccineID;
        int pos1, pos2, pos3;

        do {
            injectionID = MyToys.getID("Enter the ID of injection: ", "Please try again !!!");
            pos1 = searchInjectionID(injectionID);
            if (pos1 != -1) {
                System.out.println("This ID already exists. Please inupt another one !");
            }
        } while (pos1 != -1);

        System.out.println("This is the list student:");
        System.out.printf("|%-10s|%-15s\n", "Student ID", "Name Student");
        for (int i = 0; i < student.length; i++) {
            student[i].showInformation();
        }
        System.out.println("========== Have " + student.length + " rows in here ==========");
        do {
            do {
                String id = MyToys.getString("Enter the ID of student you want to add in Injection List: ", "Please try again!!!");
                count = searchStudentIDV2(id);
                pos2 = searchStudentID(id);
                if (pos2 == -1) {
                    System.out.println("This Student ID has not exsits.");
                }
            } while (pos2 == -1);
            if (count == -1) {
                System.out.println("This student has not injected before.");
                studentID = student[pos2].getStudentID();
            } else {
                System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                        "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
                injectionList.get(count).showInformation();
                if (injectionList.get(count).getInjectionDate2nd() == null
                        || injectionList.get(count).getInjectionPlace2nd().equalsIgnoreCase("NULL")) {
                    System.out.println("Student had injected 1 time");
                    System.out.println("If you want to update Student's 2nd place and 2nd date.");
                    System.out.println("Please return to the menu and select update function.");
                    System.out.println("Please select another student.");
                } else {
                    System.out.println("This student has completed 2 injections.");
                    System.out.println("Please select another student.");
                }
            }
        } while (count != -1);

        System.out.println("This is the list vaccine:");
        System.out.printf("|%-11s|%-12s\n", "Vaccine ID", "Name Vaccine");
        for (int i = 0; i < vaccine.length; i++) {
            vaccine[i].showInformation();
        }
        System.out.println("========== Have " + vaccine.length + " rows in here ==========");
        do {
            String vc = MyToys.getString("Enter the ID of vaccine you want to add in Injection List: ", "Please try again!!!");
            pos3 = searchVaccineID(vc);
            if (pos3 == -1) {
                System.out.println("This vaccine ID has not exsits.");
            }
        } while (pos3 == -1);
        vaccineID = vaccine[pos3].getVaccineID();

        injectionPlace1st = MyToys.getString("Enter 1st injection place: ", "Please try again!!!");
        injectionDate1st = MyToys.getValidDate("Enter 1st injection date(dd/MM/yyyy): ", "Please try again!!!");

        int input1 = MyToys.isContinue("Would you like to record 2nd information (place and date) right now?(y/n) ?: ", "Please input y(Y) or n(N) !!!");
        if (input1 == -1) {
            injectionPlace2nd = "NULL";
            injectionDate2nd = null;
        }
        if (input1 == 1) {
            injectionPlace2nd = MyToys.getString("Enter 2nd injection place: ", "Please try again!!!");
            Date injectionDate2nd_1 = MyToys.getValidDate("Enter 2nd injection date(dd/MM/yyyy): ",
                    "Please try again!!!");
            injectionDate2nd_2 = checkDate(injectionDate1st, injectionDate2nd_1);
            if (injectionDate2nd_2) {
                injectionDate2nd = injectionDate2nd_1;
            } else {
                while (injectionDate2nd_2 != true) {
                    System.out.println("Vaccine must be give 4 to 12 weeks after the first injection!!!");
                    injectionDate2nd = MyToys.getValidDate("Enter 2nd injection date(dd/MM/yyyy): ",
                            "Please try again!!!");
                    injectionDate2nd_2 = checkDate(injectionDate1st, injectionDate2nd);
                }
            }
        }
        Injection inj = new Injection(injectionID, injectionPlace1st, injectionPlace2nd, injectionDate1st, injectionDate2nd, studentID, vaccineID);
        injectionList.add(inj);
        System.out.println("A new injection is added sucessfully.");
    }

    public int searchStudentIDV2(String inputID) {
        if (injectionList.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getStudentID().equalsIgnoreCase(inputID)) {
                return i;
            }
        }
        return -1;
    }

    public int searchVaccineIDV2(String inputID) {
        if (injectionList.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getVaccineID().equalsIgnoreCase(inputID)) {
                return i;
            }
        }
        return -1;
    }

    public int searchDate(Date date) {
        if (injectionList.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getInjectionDate1st().compareTo(date) == 0) {
                return i;
            } else {
                if (injectionList.get(i).getInjectionDate2nd() != null
                        && injectionList.get(i).getInjectionDate2nd().compareTo(date) == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int searchInjectionByDate(Date date) {
        int count = 0;
        if (injectionList.isEmpty()) {
            return count;
        }
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getInjectionDate1st().compareTo(date) == 0) {
                count++;
            } else {
                if (injectionList.get(i).getInjectionDate2nd() != null
                        && injectionList.get(i).getInjectionDate2nd().compareTo(date) == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    public int searchStudentName(String studentName) {
        if (student.length == 0) {
            return -1;
        }
        for (int i = 0; i < student.length; i++) {
            if (student[i].getName().equalsIgnoreCase(studentName)) {
                return i;
            }
        }
        return -1;
    }

    public int searchStudentNameV2(String studentName) {
        int count = 0;
        if (injectionList.isEmpty()) {
            return -1;
        } else {

            for (int i = 0; i < student.length; i++) {
                if (student[i].getName().equalsIgnoreCase(studentName)) {
                    for (int j = 0; j < injectionList.size(); j++) {
                        if (injectionList.get(j).getStudentID().equalsIgnoreCase(student[i].getStudentID())) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public int searchPlace(String place) {
        if (injectionList.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getInjectionPlace1st().equalsIgnoreCase(place)) {
                return i;
            } else {
                if (injectionList.get(i).getInjectionPlace2nd().compareTo("NULL") != 0
                        && injectionList.get(i).getInjectionPlace2nd().equalsIgnoreCase(place)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int searchInjectionByPlace(String place) {
        int count = 0;
        if (injectionList.isEmpty()) {
            return count;
        }
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getInjectionPlace1st().equalsIgnoreCase(place)) {
                count++;
            } else {
                if (injectionList.get(i).getInjectionPlace2nd().equalsIgnoreCase(place)) {
                    count++;
                }
            }
        }

        return count;
    }

    public int searchVaccineName(String vaccineName) {
        if (vaccine.length == 0) {
            return -1;
        }
        for (int i = 0; i < vaccine.length; i++) {
            if (vaccine[i].getName().equalsIgnoreCase(vaccineName)) {
                return i;
            }
        }
        return -1;
    }

    public int searchVaccineNameV3(String vaccineName) {
        if (vaccine.length == 0) {
            return -1;
        }
        for (int i = 0; i < vaccine.length; i++) {
            if (vaccine[i].getName().equalsIgnoreCase(vaccineName)) {
                return i;
            }
        }
        return -1;
    }

    public int searchInjectionByVaccineName(String vaccineName) {
        int count = 0;
        if (injectionList.isEmpty()) {
            return count;
        }
        for (int i = 0; i < vaccine.length; i++) {
            if (vaccine[i].getName().equalsIgnoreCase(vaccineName)) {
                for (int j = 0; j < injectionList.size(); j++) {
                    if (injectionList.get(j).getVaccineID().equalsIgnoreCase(vaccine[i].getVaccineID())) {
                        injectionList.get(j).showInformation();
                        count++;
                    }
                }

            }
        }
        return count;
    }

    public int searchVaccineID(String inputID) {
        if (vaccine.length == 0) {
            return -1;
        } else {
            for (int i = 0; i < vaccine.length; i++) {
                if (vaccine[i].getVaccineID().toLowerCase().equalsIgnoreCase(inputID)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int searchInjectionByVaccineID(String vaccineID) {
        int count = 0;
        if (injectionList.isEmpty()) {
            return count;
        }
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getVaccineID().equalsIgnoreCase(vaccineID)) {
                count++;
            }

        }
        return count;
    }

    public int searchStudentID(String inputID) {
        if (student.length == 0) {
            return -1;
        } else {
            for (int i = 0; i < student.length; i++) {
                if (student[i].getStudentID().toLowerCase().equalsIgnoreCase(inputID)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int searchInjectionID(String inputID) {
        if (injectionList.isEmpty()) {
            return -1;
        } else {
            for (int i = 0; i < injectionList.size(); i++) {
                if (injectionList.get(i).getInjectionID().equalsIgnoreCase(inputID)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean checkDate(Date firstDate, Date secondDate) {
        String date1 = MyToys.convertDateToString(firstDate);
        String date2 = MyToys.convertDateToString(secondDate);
        LocalDate d1 = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate d2 = LocalDate.parse(date2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
        long diffDays = diff.toDays();
        if (diffDays >= 28 && diffDays <= 84) {
            return true;
        }
        return false;
    }

    public long getDifferenceDays(Date d1, Date d2) {
        long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff;
    }

    public int getDateBy30Days() {
        long days;
        int count = 0;
        if (injectionList.isEmpty()) {
            return count;
        }
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getInjectionDate2nd() != null) {
                days = getDifferenceDays(injectionList.get(i).getInjectionDate1st(),
                        injectionList.get(i).getInjectionDate2nd());
                if (days > 30) {
                    injectionList.get(i).showInformation();
                    count++;
                }
            }

        }
        return count;
    }

    public int getDateCurrentDate() {
        Date currentDate = new Date();
        long days;
        int count = 0;
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getInjectionDate2nd() == null) {
                days = getDifferenceDays(injectionList.get(i).getInjectionDate1st(),
                        currentDate);
                if (days > 20 && injectionList.get(i).getInjectionDate1st().after(currentDate) == true) {
                    injectionList.get(i).showInformation();
                    count++;
                }
            } else {
                days = getDifferenceDays(injectionList.get(i).getInjectionDate2nd(),
                        currentDate);
                if (days > 20 && injectionList.get(i).getInjectionDate2nd().after(currentDate) == true) {
                    injectionList.get(i).showInformation();
                    count++;
                }
            }

        }
        return count;
    }

    public boolean searchVaccineIDV1(String inputIDVaccine) {
        for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getVaccineID().equalsIgnoreCase(inputIDVaccine)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateInjection() {
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");
            System.out.println("Please add a new injection to use this selection.");

        } else {
            int pos1;
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");
            String chocie = MyToys.getID("Enter the injection ID you want to update: ", "Please input again !!!");
            int pos = searchInjectionID(chocie);
            if (pos != -1) {
                for (int i = 0; i < injectionList.size(); i++) {
                    if (injectionList.get(i).getInjectionID().equalsIgnoreCase(chocie)) {
                        System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                                "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
                        injectionList.get(i).showInformation();
                        if (injectionList.get(pos).getInjectionDate2nd() != null
                                || injectionList.get(pos).getInjectionPlace2nd().equalsIgnoreCase("NULL") == false) {
                            System.out.println("Student has completed 2 injections!");
                            int choice = MyToys.isContinue("Do you really to change new information in 2nd injection place and date (y/n)?: ",
                                    "Please input y(Y) / n(N)");
                            if (choice == 1) {
                                String updatePlace = MyToys.getStringV1("Enter a new information "
                                        + "in 2nd injection place you want to update: ");
                                if (updatePlace.isEmpty() || updatePlace.length() == 0) {

                                } else {
                                    int input = MyToys.isContinue("Do you really to change new information in 2nd injection place (y/n)?",
                                            "Please input y(Y) / n(N)");
                                    if (input == 1) {
                                        injectionList.get(i).setInjectionPlace2nd(updatePlace);
                                    }
                                }
                                Date injectionDate2nd_1 = MyToys.getValidDate("Enter a new information "
                                        + "in 2nd injection date you want to update: ",
                                        "Please try again!!!");
                                boolean injectionDate2nd_2 = checkDate(injectionList.get(i).getInjectionDate1st(), injectionDate2nd_1);
                                if (injectionDate2nd_2) {
                                    choice = MyToys.isContinue("Do you really to change new information in 2nd injection date (y/n)?: ",
                                            "Please input y(Y) / n(N)");
                                    if (choice == 1) {
                                        injectionList.get(i).setInjectionDate2nd(injectionDate2nd_1);
                                    }
                                } else {
                                    while (injectionDate2nd_2 != true) {
                                        System.out.println("Vaccine must be give 4 to 12 weeks after the first injection!!!");
                                        injectionDate2nd_1 = MyToys.getValidDate("Enter a new information "
                                                + "in 2nd injection date you want to update: ",
                                                "Please try again!!!");
                                        injectionDate2nd_2 = checkDate(injectionList.get(i).getInjectionDate1st(), injectionDate2nd_1);
                                    }
                                    choice = MyToys.isContinue("Do you really to change new information in 2nd injection place (y/n)?: ",
                                            "Please input y(Y) / n(N)");
                                    if (choice == 1) {
                                        injectionList.get(i).setInjectionDate2nd(injectionDate2nd_1);
                                    }
                                }
                                System.out.println("The result of updating....");
                                System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                                        "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
                                injectionList.get(i).showInformation();
                                if (injectionList.get(pos).getInjectionDate2nd() != null
                                        || injectionList.get(pos).getInjectionPlace2nd().equalsIgnoreCase("NULL") == false) {
                                    System.out.println("Upadte call sucessfully");
                                    System.out.println("Student has completed 2 injections!");
                                } else {
                                    System.out.println("Update call cancel !");
                                }
                            } else {
                                System.out.println("Update call cancel");
                                MyToys.getEnter();
                            }
                        } else {
                            String confirm;
                            do {
                                do {
                                    confirm = MyToys.getString("Enter a 2nd vaccine id: ", "Please try aagin!!!");
                                    pos1 = searchVaccineID(confirm);
                                    if (pos1 == -1) {
                                        System.out.println("This vaccine ID has not exsits.");
                                    }
                                } while (pos1 == -1);
                                if (injectionList.get(pos).getVaccineID().equalsIgnoreCase(confirm) == false) {
                                    System.out.println("This student has a one injection.");
                                    System.out.println("Please enter same type vaccine.");
                                }
                            } while (injectionList.get(pos).getVaccineID().equalsIgnoreCase(confirm) != true);

                            String updatePlace = MyToys.getStringV1("Enter a new information "
                                    + "in 2nd injection place you want to update: ");
                            if (updatePlace.isEmpty() || updatePlace.length() == 0) {

                            } else {
                                int input = MyToys.isContinue("Do you really to change new information in 2nd injection place (y/n)?",
                                        "Please input y(Y) / n(N)");
                                if (input == 1) {
                                    injectionList.get(i).setInjectionPlace2nd(updatePlace);
                                }
                            }
                            Date injectionDate2nd_1 = MyToys.getValidDate("Enter a new information "
                                    + "in 2nd injection date you want to update: ",
                                    "Please try again!!!");
                            boolean injectionDate2nd_2 = checkDate(injectionList.get(i).getInjectionDate1st(), injectionDate2nd_1);
                            if (injectionDate2nd_2) {
                                int choice = MyToys.isContinue("Do you really to change new information in 2nd injection date (y/n)?: ",
                                        "Please input y(Y) / n(N)");
                                if (choice == 1) {
                                    injectionList.get(i).setInjectionDate2nd(injectionDate2nd_1);
                                }
                            } else {
                                while (injectionDate2nd_2 != true) {
                                    System.out.println("Vaccine must be give 4 to 12 weeks after the first injection!!!");
                                    injectionDate2nd_1 = MyToys.getValidDate("Enter a new information "
                                            + "in 2nd injection date you want to update: ",
                                            "Please try again!!!");
                                    injectionDate2nd_2 = checkDate(injectionList.get(i).getInjectionDate1st(), injectionDate2nd_1);
                                }
                                int choice = MyToys.isContinue("Do you really to change new information in 2nd injection date (y/n)?: ",
                                        "Please input y(Y) / n(N)");
                                if (choice == 1) {
                                    injectionList.get(i).setInjectionDate2nd(injectionDate2nd_1);
                                }
                            }
                            System.out.println("The result of updating....");
                            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
                            injectionList.get(i).showInformation();
                            if (injectionList.get(pos).getInjectionDate2nd() != null
                                    || injectionList.get(pos).getInjectionPlace2nd().equalsIgnoreCase("NULL") == false) {
                                System.out.println("Student has completed 2 injections!");
                            } else {
                                System.out.println("Update call cancel !");
                            }
                            MyToys.getEnter();
                        }
                    }
                }
            } else {
                System.out.println("Injection ID does not exits.");
                MyToys.getEnter();
            }
        }
    }

    @Override
    public void deleteInjection() {
        int pos, input;
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");
            System.out.println("Please add a new injection to use this selection.");

        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");
            String choice = MyToys.getString("Enter the injection ID you want to delete: ", "Please input again !!!");
            pos = searchInjectionID(choice);
            if (pos != -1) {
                input = MyToys.isContinue("Do you really want to remove this Injection ID(y/n) ?: ",
                        "You need enter: y (Yes) or n (No)!!!");
                switch (input) {
                    case 1:
                        injectionList.remove(pos);
                        System.out.println("The selected injection is removed successfully !!!");
                        System.out.println("Injection ID: " + choice + " removed.\n");
                        MyToys.getEnter();
                        break;
                    case -1:
                        System.out.println("Detele failed !!! Please try again....\n");
                        MyToys.getEnter();
                        break;
                }
            } else {
                System.out.println("This Injection ID does not exist !!!");
                System.out.println("Detele failed !!!\n");
                MyToys.getEnter();
            }
        }
    }
    @Override
    public void searchInjectionByStudentID() {
        int pos;
        int result = 0;
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");

        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");
            Date date = MyToys.getValidDate("Enter Date you want to search: ", "Please try agagi!!!");
            pos = searchDate(date);
            if (pos != -1) {
                System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                        "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");

                for (int i = 0; i < injectionList.size(); i++) {
            if (injectionList.get(i).getInjectionDate1st().compareTo(date) == 0) {
                    injectionList.get(i).showInformation();
            } else {
                if (injectionList.get(i).getInjectionDate2nd() != null
                        && injectionList.get(i).getInjectionDate2nd().compareTo(date) == 0) {
                     injectionList.get(i).showInformation();
                }
            }
        }
                System.out.println("======================================================== Have " + result + " rows in here =========================================");
            } else {
                System.out.println("This Date does not exist.");
                MyToys.getEnter();
            }
        }
    }

    public void deleteInjectionByDate() {
        int pos, input, count;
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");
            System.out.println("Please add a new injection to use this selection.");

        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");
            Date date = MyToys.getValidDate("Enter the Date you want to delete: ", "Please input again !!!");
            pos = searchDate(date);
            count = searchInjectionByDate(date);
            if (pos != -1) {
                for (int i = 0; i < injectionList.size(); i++) {
                    if (injectionList.get(i).getInjectionDate1st().compareTo(date) == 0) {
                        injectionList.get(i).showInformation();
                    } else {
                        if (injectionList.get(i).getInjectionDate2nd() != null
                                && injectionList.get(i).getInjectionDate2nd().compareTo(date) == 0) {
                            injectionList.get(i).showInformation();
                        }
                    }
                }
                System.out.println("======================================================== Have " + count + " rows in here =========================================");

                input = MyToys.isContinue("Do you really want to remove this Date(y/n) ?: ",
                        "You need enter: y (Yes) or n (No)!!!");
                switch (input) {
                    case 1:
                        do {
                            for (int i = 0; i < injectionList.size(); i++) {
                                if (injectionList.get(i).getInjectionDate1st().compareTo(date) == 0) {
                                    count--;
                                    injectionList.remove(i);
                                } else {
                                    if (injectionList.get(i).getInjectionDate2nd() != null
                                            && injectionList.get(i).getInjectionDate2nd().compareTo(date) == 0) {
                                        count--;
                                        injectionList.remove(i);
                                    }
                                }
                            }
                        } while (count != 0);
                        System.out.println("The selected injection is removed successfully !!!");
                        System.out.println("Date: " + MyToys.convertDateToString(date) + " removed.\n");
                        MyToys.getEnter();
                        break;
                    case -1:
                        System.out.println("Detele failed !!! Please try again....\n");
                        MyToys.getEnter();
                        break;
                }
            } else {
                System.out.println("This date does not exist !!!");
                System.out.println("Detele failed !!!\n");
                MyToys.getEnter();
            }
        }
    }

    public void deleteInjectionByDateWith30Days() {
        int input, count = 0;
        long days = 0;
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");
            System.out.println("Please add a new injection to use this selection.");

        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");
            for (int i = 0; i < injectionList.size(); i++) {
                if (injectionList.get(i).getInjectionDate2nd() != null) {
                    days = getDifferenceDays(injectionList.get(i).getInjectionDate1st(),
                            injectionList.get(i).getInjectionDate2nd());
                    if (days > 30) {
                        injectionList.get(i).showInformation();
                        count++;
                    }
                }

            }
        }
        if (injectionList.isEmpty()) {
            System.out.println("This date does not exist !!!");
            System.out.println("Detele failed !!!\n");
            MyToys.getEnter();
        } else {
            input = MyToys.isContinue("Do you really want to remove this Date(y/n) ?: ",
                    "You need enter: y (Yes) or n (No)!!!");
            switch (input) {
                case 1:
                    do {
                        for (int i = 0; i < injectionList.size(); i++) {
                            if (injectionList.get(i).getInjectionDate2nd() != null) {
                                days = getDifferenceDays(injectionList.get(i).getInjectionDate1st(),
                                        injectionList.get(i).getInjectionDate2nd());
                                if (days > 30) {
                                    count--;
                                    injectionList.remove(i);
                                }
                            }
                        }
                    } while (count != 0);
                    System.out.println("The selected injection is removed successfully !!!");
                    System.out.println("Removed sucessfully.\n");
                    MyToys.getEnter();
                    break;
                case -1:
                    System.out.println("Detele failed !!! Please try again....\n");
                    MyToys.getEnter();
                    break;
            }
        }

    }

    public void deleteInjectionAfterDate() {
        int input, count = 0;
        long days = 0;
        Date currentDate = new Date();
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");
            System.out.println("Please add a new injection to use this selection.");

        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");

            count = getDateCurrentDate();
            if (injectionList.isEmpty()) {
                System.out.println("This date does not exist !!!");
                System.out.println("Detele failed !!!\n");
                MyToys.getEnter();
            } else {
                input = MyToys.isContinue("Do you really want to remove this Date(y/n) ?: ",
                        "You need enter: y (Yes) or n (No)!!!");
                switch (input) {
                    case 1:
                        do {
                            for (int i = 0; i < injectionList.size(); i++) {
                                if (injectionList.get(i).getInjectionDate2nd() == null) {
                                    days = getDifferenceDays(injectionList.get(i).getInjectionDate1st(),
                                            currentDate);
                                    if (days > 20 && injectionList.get(i).getInjectionDate1st().after(currentDate)) {
                                        count--;
                                        injectionList.remove(i);
                                    }
                                } else {
                                    days = getDifferenceDays(injectionList.get(i).getInjectionDate2nd(),
                                            currentDate);
                                    if (days > 20 && injectionList.get(i).getInjectionDate2nd().after(currentDate)) {
                                        count--;
                                        injectionList.remove(i);
                                    }
                                }
                            }
                        } while (count != 0);
                        System.out.println("The selected injection is removed successfully !!!");
                        System.out.println("Removed sucessfully.\n");
                        MyToys.getEnter();
                        break;
                    case -1:
                        System.out.println("Detele failed !!! Please try again....\n");
                        MyToys.getEnter();
                        break;
                }
            }

        }

    }

    public void deleteInjectionByPlace() {
        int pos, input, count;
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");
            System.out.println("Please add a new injection to use this selection.");

        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");
            String place = MyToys.getString("Enter the place you want to delete: ", "Please input again !!!");
            pos = searchPlace(place);
            count = searchInjectionByPlace(place);
            if (pos != -1) {
                for (int i = 0; i < injectionList.size(); i++) {
                    if (injectionList.get(i).getInjectionPlace1st().equalsIgnoreCase(place)) {
                        injectionList.get(i).showInformation();
                    } else {
                        if (injectionList.get(i).getInjectionDate2nd() != null
                                && injectionList.get(i).getInjectionPlace2nd().equalsIgnoreCase(place)) {
                            injectionList.get(i).showInformation();
                        }
                    }
                }
                System.out.println("======================================================== Have " + count + " rows in here =========================================");

                input = MyToys.isContinue("Do you really want to remove this Date(y/n) ?: ",
                        "You need enter: y (Yes) or n (No)!!!");
                switch (input) {
                    case 1:
                        do {
                            for (int i = 0; i < injectionList.size(); i++) {
                                if (injectionList.get(i).getInjectionPlace1st().equalsIgnoreCase(place)) {
                                    count--;
                                    injectionList.remove(i);
                                } else {
                                    if (injectionList.get(i).getInjectionDate2nd() != null
                                            && injectionList.get(i).getInjectionPlace2nd().equalsIgnoreCase(place)) {
                                        count--;
                                        injectionList.remove(i);
                                    }
                                }
                            }
                        } while (count != 0);
                        System.out.println("The selected injection is removed successfully !!!");
                        System.out.println("Place: " + place + " removed.\n");
                        MyToys.getEnter();
                        break;
                    case -1:
                        System.out.println("Detele failed !!! Please try again....\n");
                        MyToys.getEnter();
                        break;
                }
            } else {
                System.out.println("This place does not exist !!!");
                System.out.println("Detele failed !!!\n");
                MyToys.getEnter();
            }
        }
    }

    public void deleteInjectionByVaccineID() {
        int pos, input, count;
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");
            System.out.println("Please add a new injection to use this selection.");

        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");
            String vaccineID = MyToys.getString("Enter the Vaccine ID you want to delete: ", "Please input again !!!");
            pos = searchVaccineIDV2(vaccineID);
            count = searchInjectionByVaccineID(vaccineID);
            if (pos != -1) {
                for (int i = 0; i < injectionList.size(); i++) {
                    if (injectionList.get(i).getVaccineID().equalsIgnoreCase(vaccineID)) {
                        injectionList.get(i).showInformation();
                    }
                }
                System.out.println("======================================================== Have " + count + " rows in here =========================================");

                input = MyToys.isContinue("Do you really want to remove this Date(y/n) ?: ",
                        "You need enter: y (Yes) or n (No)!!!");
                switch (input) {
                    case 1:
                        do {
                            for (int i = 0; i < injectionList.size(); i++) {
                                if (injectionList.get(i).getVaccineID().equalsIgnoreCase(vaccineID)) {
                                    count--;
                                    injectionList.remove(i);
                                }

                            }
                        } while (count != 0);
                        System.out.println("The selected injection is removed successfully !!!");
                        System.out.println("Vaccine ID: " + vaccineID + " removed.\n");
                        MyToys.getEnter();
                        break;
                    case -1:
                        System.out.println("Detele failed !!! Please try again....\n");
                        MyToys.getEnter();
                        break;
                }
            } else {
                System.out.println("This Vaccine ID does not exist !!!");
                System.out.println("Detele failed !!!\n");
                MyToys.getEnter();
            }
        }

    }

    public void deleteInjectionByVaccineName() {

        int pos, input, count = 0;
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");

        } else {
            System.out.printf("|%-11s|%-12s|\n", "Vaccine ID", "Name");
            for (int i = 0; i < vaccine.length; i++) {
                vaccine[i].showInformation();
            }
            System.out.println("======================================================== Have " + vaccine.length + " rows in here =========================================");
            String name = MyToys.getString("Enter Vaccine Name you want to search: ", "Please try agagi!!!");
            pos = searchVaccineNameV3(name);
            count = searchInjectionByVaccineName(name);
            if (pos != -1) {
                input = MyToys.isContinue("Do you really want to remove this Date(y/n) ?: ",
                        "You need enter: y (Yes) or n (No)!!!");
                switch (input) {
                    case 1:
                        do {
                            for (int i = 0; i < vaccine.length; i++) {
                                if (vaccine[i].getName().equalsIgnoreCase(name)) {
                                    for (int j = 0; j < injectionList.size(); j++) {
                                        if (injectionList.get(j).getVaccineID().equalsIgnoreCase(vaccine[i].getVaccineID())) {
                                            count--;
                                            injectionList.remove(j);
                                        }
                                    }
                                }
                            }
                        } while (count != 0);
                        System.out.println("The selected injection is removed successfully !!!");
                        System.out.println("Vaccine Name: " + name + " removed.\n");
                        MyToys.getEnter();
                        break;
                    case -1:
                        System.out.println("Detele failed !!! Please try again....\n");
                        MyToys.getEnter();
                        break;
                }
            } else {
                System.out.println("This Vaccine ID does not exist !!!");
                System.out.println("Detele failed !!!\n");
                MyToys.getEnter();
            }
        }
    }

    public void saveFileInjection() {
        if (injectionList.isEmpty()) {
            System.out.println("Injection List is empty");
            System.out.println("Please add a new injection to use this selection.");
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(w);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (int i = 0; i < injectionList.size(); i++) {
                    oos.writeObject(injectionList.get(i));
                }
                oos.close();
                fos.close();
            } catch (Exception e) {
            }
            System.out.println("Saving......");
            System.out.println("Save sucessfully!!!\n");
        }
    }

    public void saveFileStudent() {
        try {
            String fileName = "student.dat";
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            List<Student> list = new ArrayList<>();
            list.add(new Student("SE151074", "Xuan Thiep"));
            list.add(new Student("SE151046", "Hoang Tan"));
            list.add(new Student("SE151056", "Dinh Thai"));
            list.add(new Student("SE151066", "Phuoc Thanh"));
            list.add(new Student("SE151101", "Nha Thy"));
            list.add(new Student("SE151054", "Minh Thai"));
            list.add(new Student("SE150413", "Ngoc Ngan"));
            list.add(new Student("SE151086", "Duc Thinh"));
            list.add(new Student("SE151125", "Minh Tri"));
            list.add(new Student("SE151143", "Lam Truong"));
            for (Student vc : list) {
                oos.writeObject(vc);
            }
            oos.close();
            fos.close();
        } catch (Exception e) {

        }
    }

    public void readFileInjectionV1() {
        if (injectionList.isEmpty()) {
            readFileInjection();
        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");

            MyToys.getEnter();
        }
    }

    public void printInformation() {
        if (injectionList.isEmpty()) {
            System.out.println("Injection is empty");
        } else {
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");

            MyToys.getEnter();
        }
    }

    public void printInformationV2() {
        if (injectionList.isEmpty()) {
            System.out.println("Injection is empty");
        } else {
            long days;
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                if (injectionList.get(i).getInjectionDate2nd() != null) {
                    days = getDifferenceDays(injectionList.get(i).getInjectionDate1st(),
                            injectionList.get(i).getInjectionDate2nd());
                    if (days > 30) {
                        System.out.println("Okk");

                    } else {
                        System.out.println("Not");
                    }
                } else {
                    System.out.println("Not");
                }
            }
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");

            MyToys.getEnter();
        }
    }

    public void readFileInjection() {
        if (w.exists()) {
            try {
                FileInputStream fis = new FileInputStream(w);
                ObjectInputStream ois = new ObjectInputStream(fis);
                while (fis.available() > 0) {
                    Injection inj = (Injection) ois.readObject();
                    injectionList.add(inj);
                }
                System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                        "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
                for (int i = 0; i < injectionList.size(); i++) {
                    injectionList.get(i).showInformation();
                }
                fis.close();
                ois.close();

            } catch (Exception e) {

            }
            System.out.println("======================================================== Have " + injectionList.size() + " rows in here =========================================");
            MyToys.getEnter();

        } else {
            try {
                FileOutputStream fos = new FileOutputStream(w);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String dateString1 = "01/09/2021";
                String dateString2 = "08/11/2021";
                String dateString3 = "01/09/2021";
                String dateString4 = "24/12/2021";
                String dateString7 = "01/10/2021";
                String dateString8 = "30/01/2022";
                Date dateObject1 = sdf.parse(dateString1);
                Date dateObject2 = sdf.parse(dateString2);
                Date dateObject3 = sdf.parse(dateString3);
                Date dateObject4 = sdf.parse(dateString4);
                Date dateObject7 = sdf.parse(dateString7);
                Date dateObject8 = sdf.parse(dateString8);
                injectionList.add(new Injection("1", "Tinh Dong Nai", "NULL", dateObject1, null, "SE151074", "Covid-V001"));
                injectionList.add(new Injection("2", "Quan Tan Binh", "NULL", dateObject2, null, "SE151056", "Covid-V002"));
                injectionList.add(new Injection("3", "Tinh Binh Duong", "Tinh Binh Duong", dateObject3, dateObject7, "SE151086", "Covid-V004"));
                injectionList.add(new Injection("4", "Quan 12", "Quan 1", dateObject4, dateObject8, "SE151143", "Covid-V005"));
                for (Injection vc : injectionList) {
                    oos.writeObject(vc);
                }
                oos.close();
                fos.close();
            } catch (Exception e) {

            }
            System.out.printf("|%-13s|%-20s|%-20s|%-19s|%-19s|%-10s|%-10s|\n", "Injection ID", "1st Injection Place",
                    "1st Injection Date", "2nd Injection Place", "2nd Injection Date", "Student ID", "Vaccine ID");
            for (int i = 0; i < injectionList.size(); i++) {
                injectionList.get(i).showInformation();
            }
            System.out.println("========================================================= Have " + injectionList.size() + " rows in here ==========================================");
            MyToys.getEnter();
        }
    }

    public void saveFileVaccine() {
        try {
            String fileName = "vaccine.dat";
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            List<Vaccine> listVaccine = new ArrayList<>();
            listVaccine.add(new Vaccine("Covid-V001", "AstraZeneca"));
            listVaccine.add(new Vaccine("Covid-V002", "SPUTNIK V"));
            listVaccine.add(new Vaccine("Covid-V003", "Vero Cell"));
            listVaccine.add(new Vaccine("Covid-V004", "Pfizer"));
            listVaccine.add(new Vaccine("Covid-V005", "Moderna"));
            for (Vaccine vc : listVaccine) {
                oos.writeObject(vc);
            }
            oos.close();
            fos.close();
        } catch (Exception e) {

        }
    }
}
