package com.example.ftest;

import java.io.Serializable;

public class TipInfo implements Serializable {
    private String id;
    private String content;
    private String time;
    private String colour="white";

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getColour() {
        return colour;
    }
}
