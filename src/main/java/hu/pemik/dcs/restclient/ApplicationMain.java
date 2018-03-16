package hu.pemik.dcs.restclient;

import hu.pemik.dcs.restclient.services.AuthRequestFilter;
import hu.pemik.dcs.restclient.services.JsonObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Scanner;

public class ApplicationMain {

    private Client client;

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.initRestClient(args[0]);
        app.start();
    }

    private void initRestClient(String userName) {
        this.client = ClientBuilder.newBuilder()
                .register(JsonObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();

        AuthRequestFilter authFilter = new AuthRequestFilter(userName);
        // this.reverseStringEndpoint = client.target("http://localhost:33333/rest/string/reverse/{stringToReverse}").register(authFilter);
    }

    private void start() {
        String action = getAction();

        while (!action.equals("x")) {
            switch (action) {
                case "1":
                    System.out.println("Execute action 1...");
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
        System.out.println("1: Example action");
        System.out.println("x: Exit");
        System.out.println("==================================");
    }

}
