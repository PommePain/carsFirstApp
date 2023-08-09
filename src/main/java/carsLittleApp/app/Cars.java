package carsLittleApp.app;

import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cars {
    private String name;
    private String brand;
    private String dateCreation;
    private Integer price = 0;
    private Integer speed = 0;
    private int id;

    private List<String> colors = new ArrayList<>();

    // Get

    public String getName() { return this.name; }

    public String getBrand() { return this.brand; }

    public int getPrice() { return this.price; }

    public int getSpeed() { return this.speed; }

    public List<String> getColors() { return this.colors; }

    public String getDateCreation() { return this.dateCreation; }

    public int getId() { return this.id; }

    public String getInfosString() {
        return this.brand + " " + this.name + "\nPrix : " + this.price + "  | Vitesse : " + this.speed +
                "\nCouleurs : " + this.colors.toString();
    }

    // Setter

    public void setName (String name) { this.name = name; }

    public void setBrand (String brand) { this.brand = brand; }

    public void setDateCreation (String dateCreation) { this.dateCreation = dateCreation; }

    public void setPrice (Integer price) { this.price = price; }

    public void setSpeed (Integer speed) { this.speed = speed; }

    public void setColors (List<String> colors) { this.colors = colors; }

    public void setId (int id) { this.id = id; }

    // Methods
    public Cars() {};

    public Cars(String name, String brand, int price, int speed, String dateCreation, List<String> colors) {
        this.speed = speed;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.dateCreation = dateCreation;
        this.colors = colors;
    }

    public Cars(String name, String brand, int price, int speed) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.speed = speed;
    }

    public Cars(String name, String brand, int price) {
        this.name = name;
        this.price = price;
        this.brand = brand;
    }

    public Cars(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }
}
