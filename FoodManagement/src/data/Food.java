package data;

import java.io.Serializable;
import java.util.Date;
import util.Validation;

/**
 *
 * @author Thiep Ngo
 */
public class Food implements Serializable {

    private String id;
    private String name;
    private double weight;
    private String type;
    private String place;
    private Date expiredDate;

    public Food(String id, String name, double weight, String type, String place, Date expiredDate) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.type = type;
        this.place = place;
        this.expiredDate = expiredDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    
    @Override
    public String toString() {
        return String.format("|%-3s|%-20s|%-8.2f|%-15s|%-15s|%-12s|", id, name, weight, type, place, Validation.convertDateToString(expiredDate));
    }

    public void showProfile() {
        System.out.printf("|%-3s|%-20s|%-8.2f|%-15s|%-15s|%-12s|\n", id, name, weight, type, place, Validation.convertDateToString(expiredDate));
    }
}
