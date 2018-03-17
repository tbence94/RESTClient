package hu.pemik.dcs.restclient;

import hu.pemik.dcs.restclient.models.Product;
import hu.pemik.dcs.restclient.services.AuthRequestFilter;
import hu.pemik.dcs.restclient.services.JsonObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Scanner;

public class ApplicationMain {

    private Client client;

    private AuthRequestFilter authFilter;

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.initRestClient(args[0]);

        // TODO: Login & fetch user + actions

        app.start();
    }

    private void initRestClient(String userName) {
        this.client = ClientBuilder.newBuilder()
                .register(JsonObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();

        this.authFilter = new AuthRequestFilter(userName);
    }

    private Builder request(String url) {
        return client.target(Config.REST_SERVER_URL + url).register(authFilter).request(MediaType.APPLICATION_JSON);
    }

    private void start() {
        String action = getAction();

        while (!action.equals("x")) {
            switch (action) {
                case "1":
                    storeProduct();
                    break;
                default:
                    System.out.println("Unsupported action: " + action);
            }

            action = getAction();
        }
    }

    private String getAction() {
        Scanner scanner = new Scanner(System.in);
        showMenu();
        System.out.print("Action: ");
        return scanner.nextLine();
    }

    private void showMenu() {
        System.out.println("\n\nAVAILABLE ACTIONS:");
        System.out.println("==================================");
        System.out.println("1: StoreProduct");
        System.out.println("x: Exit");
        System.out.println("==================================");
    }


    /**
     * ======================================================================
     * Product actions
     * ======================================================================
     */

    private void storeProduct() {
        String name = Console.getInput("Name: ");
        String description = Console.getInput("Description: ");
        int quantity = Console.getIntInput("Quantity: ");
        boolean cooled = Console.getInput("Cooled: ").equals("y");
        int customerId = Console.getIntInput("CustomerId: ");

        Product product = new Product(name, description, quantity, cooled, customerId);

        Response response = request("products/product").post(Entity.json(product));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            Console.action("Failed to store product");
            System.out.print(response.toString());
            return;
        }

        Console.info("Success");
    }

}
