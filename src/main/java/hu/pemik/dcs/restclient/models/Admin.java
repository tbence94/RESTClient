package hu.pemik.dcs.restclient.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hu.pemik.dcs.restclient.ApplicationMain;
import hu.pemik.dcs.restclient.Console;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class Admin extends Worker {

    public Admin() {
    }

    public Admin(String name, String email) {
        super(name, email);
        this.setRole(User.ROLE_ADMIN);
    }

    @Override
    public Map<String, String> getMenu() {
        Map<String, String> menu = super.getMenu();

        menu.put("Show logs", "showLogs");
        menu.put("List workers", "listWorkers");
        menu.put("Create new worker", "createWorker");

        return menu;
    }


    /**
     * ACTIONS
     */

    public void showLogs() {
        List<Log> logs = ApplicationMain.access().request("logs/all").get(new GenericType<List<Log>>() {});

        if (logs.size() == 0) {
            Console.info("No results found.");
            return;
        }

        logs.forEach(System.out::println);
    }


    public void listWorkers() {
        List<Worker> workers = ApplicationMain.access().request("workers/all").get(new GenericType<List<Worker>>() {});

        if (workers.size() == 0) {
            Console.info("No results found.");
            return;
        }

        workers.forEach(System.out::println);
    }


    public void createWorker() {
        String name = Console.getInput("Name: ");
        String email = Console.getInput("Email: ");

        WorkerCreation worker = new WorkerCreation();
        worker.setName(name);
        worker.setEmail(email);

        Response response = ApplicationMain.access().request("workers/worker").post(Entity.json(worker));

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            Console.action("Failed to store worker");
            System.out.print(response.toString());
            return;
        }

        Console.info("Success");
    }

}


@JsonIgnoreProperties(ignoreUnknown = true)
class WorkerCreation {

    String name;

    String email;

    public WorkerCreation() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}