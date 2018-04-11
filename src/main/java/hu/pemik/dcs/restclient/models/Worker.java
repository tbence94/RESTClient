package hu.pemik.dcs.restclient.models;

import hu.pemik.dcs.restclient.ApplicationMain;
import hu.pemik.dcs.restclient.Console;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
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
}
