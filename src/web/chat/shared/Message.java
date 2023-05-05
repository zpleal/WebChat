package web.chat.shared;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    Date date;
    String from;
    String text;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message() {}

    public Message(String from, String text) {
        this.date = new Date();
        this.from = from;
        this.text = text;
    }
}
