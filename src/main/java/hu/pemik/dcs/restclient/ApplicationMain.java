package hu.pemik.dcs.restclient;

import hu.pemik.dcs.restclient.models.Admin;
import hu.pemik.dcs.restclient.models.Customer;
import hu.pemik.dcs.restclient.models.User;
import hu.pemik.dcs.restclient.models.Worker;
import hu.pemik.dcs.restclient.services.AuthRequestFilter;
import hu.pemik.dcs.restclient.services.JsonObjectMapperProvider;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApplicationMain {

    private static ApplicationMain app;

    private static User user;

    private Map<String, String> routes;

    private Client client;

    private AuthRequestFilter authFilter;

    public static ApplicationMain access() {
        return app;
    }

    public static void main(String[] args) {
        app = new ApplicationMain();
        app.initRestClient(args[0]);

        if (app.login()) {
            app.run();
        }
    }

    private void initRestClient(String userName) {
        this.client = ClientBuilder.newBuilder()
                .register(JsonObjectMapperProvider.class)
                .register(JacksonFeature.class)
                .build();

        this.authFilter = new AuthRequestFilter(userName);
    }

    public Builder request(String url) {
        return target(url).request(MediaType.APPLICATION_JSON);
    }

    public WebTarget target(String url) {
        return client.target(Config.REST_SERVER_URL + url).register(authFilter);
    }

    private void run() {
        try {

            while (true) {
                Console.clear();
                Console.header("Hello " + user.name + "!");

                buildMenu();

                // Get user input
                String request = Console.getInput();
                // Exit on 'x'
                if (request.equals("x")) break;

                // Call controller method if the request is valid
                dispatch(request);

                Console.waitForEnter();
            }

        } catch (Exception e) {
            Console.handleException(e);
        }
    }

    private void buildMenu() {
        routes = new HashMap<>();
        Map<String, String> menu = user.getMenu();
        String[] actions = Arrays.copyOf(menu.keySet().toArray(), menu.size(), String[].class);


        for (int i = 1; i <= actions.length; i++) {

            String label = String.valueOf(i);
            String action = actions[i - 1];

            Console.menu(label, action);
            routes.put(label, menu.get(action));

        }

        System.out.println();

        Console.menu("x", "Exit");
    }

    private void dispatch(String request) throws Exception {
        String method = routes.get(request);
        user.call(method);
    }

    private boolean login() {
        user = request("auth/login").get(new GenericType<User>() {});
        int userId = user.id;

        switch (user.role) {
            case "admin":
                user = new Admin(user.name, user.email);
                break;
            case "worker":
                user = new Worker(user.name, user.email);
                break;
            case "customer":
                user = new Customer(user.name, user.email, user.company, user.capacity);
                break;
            default:
                Console.alert("Unknown user: " + user.toString());
                return false;
        }

        user.setId(userId);
        return true;
    }
}
