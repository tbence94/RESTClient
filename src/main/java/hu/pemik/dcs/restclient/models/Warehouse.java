package hu.pemik.dcs.restclient.models;

import hu.pemik.dcs.restclient.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse extends Model {

    public String address;

    public int capacity;

    public List<Integer> storage = new ArrayList<>();

    public List<Integer> used = new ArrayList<>();

    public List<Integer> free = new ArrayList<>();

    public Warehouse(String address, int capacity) {
        this.id = 1; // This class acts as a singleton
        this.address = address;
        this.capacity = capacity;
        this.storage = new ArrayList<>(Collections.nCopies(capacity, 0));
        this.used = new ArrayList<>();

        for (int i = 1; i <= this.capacity; i++) {
            this.free.add(i);
        }
    }

    @Override
    public String toString() {
        return "Warehouse [ id=" + id + ", address='" + address + "', capacity=" + capacity + "]";
    }

}
