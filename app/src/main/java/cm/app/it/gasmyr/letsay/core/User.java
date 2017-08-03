package cm.app.it.gasmyr.letsay.core;

/**
 * Created by gasmyr.mougang on 7/15/17.
 */

public class User {
    private String name;
    private String phone;
    private String avatarUrl;
    private boolean online;
    public User(){

    }

    public User(String name, String phone,boolean online) {
        this.name = name;
        this.phone = phone;
        this.online = online;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }


}
