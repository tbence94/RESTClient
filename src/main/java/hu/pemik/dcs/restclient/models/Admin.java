package hu.pemik.dcs.restclient.models;

import hu.pemik.dcs.restclient.ApplicationMain;
import hu.pemik.dcs.restclient.Console;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.GenericType;
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

        return menu;
    }

    public List<String> getAccessList() {
        List<String> accessList = super.getAccessList();

        // Extend worker accessList...
        accessList.add(HttpMethod.GET + ": logs/all");

        return accessList;
    }

    public void showLogs() {
        List<Log> logs = ApplicationMain.access().request("logs/all").get(new GenericType<List<Log>>() {});

        if (logs.size() == 0) {
            Console.info("No results found.");
            return;
        }

        logs.forEach(System.out::println);
    }

}
