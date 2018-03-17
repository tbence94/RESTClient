package hu.pemik.dcs.restclient.models;

import hu.pemik.dcs.restclient.Console;
import hu.pemik.dcs.restclient.Model;

public class Product extends Model {

    public String name;

    public String description;

    public int quantity;

    public boolean cooled;

    public int customerId;

    public int locationId;

    public Product() {
    }

    public Product(String name, String description, int quantity, boolean cooled, int customerId, int locationId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.cooled = cooled;
        this.customerId = customerId;
        this.locationId = locationId;
    }

    public Product(String name, String description, int quantity, boolean cooled, int customerId) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.cooled = cooled;
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isCooled() {
        return cooled;
    }

    public void setCooled(boolean cooled) {
        this.cooled = cooled;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        String result = "";

        result += "\n" + Console.numberString("#" + id) + " - " + Console.infoString(name) + "\n";
        result += Console.lineString() + "\n";
        result += "Quantity:    " + Console.numberString(quantity) + "\n";
        result += "Cooled:      " + Console.highlightString((cooled) ? "Yes" : "No") + "\n";
        result += "Customer ID: " + Console.numberString(customerId) + "\n";
        result += "Location ID: " + Console.numberString(locationId) + "\n";
        result += "Description: " + Console.highlightString(description) + "\n";
        result += Console.lineString() + "\n";

        return result;
    }

}
