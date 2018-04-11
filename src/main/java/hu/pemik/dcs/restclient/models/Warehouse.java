package hu.pemik.dcs.restclient.models;

import hu.pemik.dcs.restclient.Console;
import hu.pemik.dcs.restclient.Model;

public class Warehouse extends Model {

    public String address;

    public int capacity;

    public int used;

    public int free;

    public Warehouse() {}

    public Warehouse(String address, int capacity) {
        this.address = address;
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    @Override
    public String toString() {
        String result = Console.titleString("Warehouse:\n") + Console.lineString();

        result+= "\n"+Console.highlightString("Address:  ") + address;
        result+= "\n"+Console.highlightString("Capacity: ") + capacity;
        result+= "\n"+Console.highlightString("Used:     ") + used;
        result+= "\n"+Console.highlightString("Free:     ") + free;

        return result;
    }

}
