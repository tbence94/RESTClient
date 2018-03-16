package hu.pemik.dcs.restclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author pekmil
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {

    public int id;
    public String name;
    public String description;
    public String userName;

    public Todo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
