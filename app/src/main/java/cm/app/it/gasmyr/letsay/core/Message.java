package cm.app.it.gasmyr.letsay.core;

import java.util.Date;

/**
 * Created by gasmyr.mougang on 7/15/17.
 */

public class Message {
    private String id;
    private String content;
    private String from;
    private String to;
    private Date date;

    public Message(){

    }

    public Message(String content, Date date) {
        this.content = content;
        this.date = date;
    }

    public Message(String content, String from, String to, Date date) {
        this.content = content;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMine(String me)
    {
        if(me.equalsIgnoreCase(from)){
            return  true;
        }else{
            return false;
        }
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
