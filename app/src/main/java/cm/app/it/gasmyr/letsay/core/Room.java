package cm.app.it.gasmyr.letsay.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gasmyr.mougang on 7/15/17.
 */

public class Room {
    private String id;
    private String name;
    private List<User> users;
    private User admin;
    private String description;

    public Room(String name, User admin) {
        this.name = name;
        this.admin = admin;
    }

    public Room(String name, List<User> users, User admin) {
        this.name = name;
        this.users = users;
        this.admin = admin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<User> getUsers() {
        if(users!=null)
            return users;
        return new ArrayList<>();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getAdmin() {
            return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}
