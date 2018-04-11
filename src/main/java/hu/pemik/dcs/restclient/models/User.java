package hu.pemik.dcs.restclient.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.pemik.dcs.restclient.ApplicationMain;
import hu.pemik.dcs.restclient.Console;
import hu.pemik.dcs.restclient.Model;

import javax.ws.rs.core.GenericType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class User extends Model {

    public static final String ROLE_ADMIN = "admin";

    public static final String ROLE_WORKER = "worker";

    public static final String ROLE_CUSTOMER = "customer";

    @JsonIgnore
    public String[] uniqueKeys = {"id", "email"};

    public String name;

    public String email;

    public String role = "guest";

    public List<String> accessList;

    public String company;

    public int capacity;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Map<String, String> getMenu() {
        Map<String, String> menu = new LinkedHashMap<>();

        menu.put("List products", "listProducts");

        return menu;
    }

    /**
     * @param methodName name of the method
     * @throws Exception anything
     */
    public void call(String methodName) throws Exception {

        try {

            // (Route methods must be public void without any args)
            Method method = this.getClass().getMethod(methodName);
            method.invoke(this);

        } catch (InvocationTargetException e) {

            // Forward exceptions inside the invoked method
            try {
                throw e.getCause();
            } catch (Exception exception) {
                throw exception;
            } catch (Throwable ignored) {  }

        } catch (Exception e) {
            Console.alert("User: '" + name + "' cannot perform this action: '" + methodName + "'");
        }

    }

    public List<String> getAccessList() {
        return accessList;
    }

    public void setAccessList(List<String> accessList) {
        this.accessList = accessList;
    }

    public final boolean isAuthorized(String method, String path) {
        return this.getAccessList().contains(method + ": " + path);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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

    @Override
    public String toString() {
        return "User [ id=" + id + ", name='" + name + "', email='" + email + "', role=" + role + "]";
    }

    /**
     * ACTIONS
     */

    public void listProducts() {
        List<Product> products = ApplicationMain.access().request("products/all").get(new GenericType<List<Product>>() {});

        if (products.size() == 0) {
            Console.info("No results found.");
            return;
        }

        products.forEach(System.out::println);
    }

}
