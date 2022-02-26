/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Thiep Ngo
 */
public class MyToys {

    private static Scanner sc = new Scanner(System.in);

    public static int getAnInteger(String inputMsg, String errorMsg, int min, int max) {
        int tmp, n;
        if (min > max) {
            tmp = min;
            min = max;
            max = tmp;
        }
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Integer.parseInt(sc.nextLine());
                if (n < min || n > max) {
                    throw new Exception();
                }
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static double getAnDouble(String inputMsg, String errorMsg) {
        double n;
        while (true) {
            try {
                System.out.print(inputMsg);
                n = Double.parseDouble(sc.nextLine());
                return n;
            } catch (Exception e) {
                System.out.println(errorMsg);
            }
        }
    }

    public static String getID(String inputMsg, String errorMsg) {
        String id;
        while (true) {
            System.out.print(inputMsg);
            id = sc.nextLine().trim().toLowerCase();
            if (id.length() == 0 || id.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return id;
            }
        }
    }

    public static String getString(String inputMsg, String errorMsg) {
        String string;
        while (true) {
            System.out.print(inputMsg);
            string = sc.nextLine().trim();
            if (string.length() == 0 || string.isEmpty()) {
                System.out.println(errorMsg);
            } else {
                return string;
            }
        }
    }

    public static String getStringV1(String inputMsg) {
        String string;
        System.out.print(inputMsg);
        string = sc.nextLine().trim();
        return string;
    }

    public static Date getValidDate(String inMsg, String errMsg) {
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        boolean check = false;
        Date date = null;
        String time = null;
        d.setLenient(false);
        do {
            do {
                try {
                    System.out.print(inMsg);
                    time = sc.nextLine();
                    if (!time.matches("[0-9]{1,2}[/][0-9]{1,2}[/][0-9]{4}")) {
                        throw new Exception();
                    }
                    check = true;
                } catch (Exception e) {
                    System.out.println(errMsg);
                }
            } while (check == false);
            try {
                date = d.parse(time);
            } catch (Exception e) {
                System.out.println(errMsg);;
                check = false;
            }
        } while (check == false);
        return date;
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (date == null) {
            return String.format("NULL", date);
        }
        return df.format(date);
    }

    public static int isContinue(String inputMsg, String errorMsg) {
        boolean n;
        int result = 0;
        do {
            System.out.print(inputMsg);
            String answer = sc.nextLine();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
                result = 1;
                n = false;
            } else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no")) {
                result = -1;
                n = false;
            } else {
                System.out.println(errorMsg);
                n = true;
            }
        } while (n);
        return result;
    }

    public static void getEnter() {
        System.out.println("Press Enter to get back to the main menu...");
        do {
            if (sc.nextLine().trim().isEmpty()) {
                break;
            }
        } while (true);
    }

}
