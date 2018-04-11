package hu.pemik.dcs.restclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.pemik.dcs.restclient.ApplicationMain;
import hu.pemik.dcs.restclient.Console;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Worker extends User {

    public Worker() {
    }

    public Worker(String name, String email) {
        super(name, email);
        this.setRole(User.ROLE_WORKER);
    }


    @Override
    public Map<String, String> getMenu() {
        Map<String, String> menu = super.getMenu();

        menu.put("Store product", "storeProduct");
        menu.put("Get product", "takeProduct");
        menu.put("Show warehouse stat", "showWarehouseStat");
        menu.put("Modify customer capacity", "modifyCustomerCapacity");

        return menu;
    }

    public List<String> getAccessList() {
        return new ArrayList<String>() {{
            add(HttpMethod.GET + ": products/all");
            add(HttpMethod.POST + ": products/product");
            add(HttpMethod.DELETE + ": products/product");
        }};
    }


    public void storeProduct() {
        String name = Console.getInput("Name: ");
        String description = Console.getInput("Description: ");
        int quantity = Console.getIntInput("Quantity: ");
        boolean cooled = Console.getInput("Cooled: ").equals("y");
        int customerId = Console.getIntInput("CustomerId: ");

        Product product = new Product(name, description, quantity, cooled, customerId);
        Response response = ApplicationMain.access().request("products/product").post(Entity.json(product));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            Console.action("Failed to store product");
            System.out.print(response.toString());
            return;
        }

        Console.info("Success");
    }

    public void takeProduct() {
        int productId = Console.getIntInput("Product ID: ");

        Response response = ApplicationMain.access().target("products/product/{id}")
                .resolveTemplate("id", productId)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            Console.action("Failed to remove product");
            System.out.print(response.toString());
            return;
        }

        Console.info("Success");
    }

    public void showWarehouseStat() {
        Warehouse warehouse = ApplicationMain.access().request("warehouse/info").get(new GenericType<Warehouse>() {});

        Console.log(warehouse.toString());
        Console.waitForEnter();
    }

    public void modifyCustomerCapacity() {
        int customerId = Console.getIntInput("Customer ID:");
        int capacity = Console.getIntInput("Requested capacity:");

        ContractUpdate contractUpdate = new ContractUpdate();
        contractUpdate.setCapacity(capacity);

        Response response = ApplicationMain.access().target("customers/customer/{id}")
                .resolveTemplate("id", customerId)
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(contractUpdate));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            Console.action("Failed to update contract");
            System.out.print(response.toString());
            return;
        }

        Console.info("Success");
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ContractUpdate {

    int capacity;

    public ContractUpdate() {}

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


}