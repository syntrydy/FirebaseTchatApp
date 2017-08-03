package cm.app.it.gasmyr.letsay.core;

import java.util.List;

/**
 * Created by gasmyr.mougang on 7/31/17.
 */

public class Conversation {
    private String name;
    private List<Message> users;

    public Conversation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getUsers() {
        return users;
    }

    public void setUsers(List<Message> users) {
        this.users = users;
    }
}
