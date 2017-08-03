package cm.app.it.gasmyr.letsay.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gasmyr.mougang on 7/31/17.
 */

public class Discussion {
    private String key;
    private String Kname;
    private Message message;
    public String getKey() {
        return key;
    }

    public String getKname() {
        return Kname;
    }

    public void setKname(String kname) {
        Kname = kname;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }



    public void setKey(String key) {
        this.key = key;
    }

}
