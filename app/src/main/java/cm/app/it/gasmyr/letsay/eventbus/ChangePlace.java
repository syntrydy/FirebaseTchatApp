package cm.app.it.gasmyr.letsay.eventbus;

/**
 * Created by gasmyr.mougang on 7/19/17.
 */

public class ChangePlace {
    private String newPlace;
    private String user;

    public ChangePlace(String newPlace) {
        this.newPlace = newPlace;
    }

    public ChangePlace(String newPlace, String user) {
        this.newPlace = newPlace;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNewPlace() {
        return newPlace;
    }

    public void setNewPlace(String newPlace) {
        this.newPlace = newPlace;
    }
}
