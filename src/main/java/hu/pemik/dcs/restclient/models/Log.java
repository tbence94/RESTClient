package hu.pemik.dcs.restclient.models;

import hu.pemik.dcs.restclient.Console;
import hu.pemik.dcs.restclient.Model;

import java.sql.Timestamp;

public class Log extends Model {

    String user;

    String message;

    Timestamp date;

    public Log() {}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[ " + Console.numberString(date.toString()) + " ] #" + id + " - " + user + ": " + Console.infoString(message);
    }
}
