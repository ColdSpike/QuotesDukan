package com.inspiration.makrandpawar.quotesdukan.model;

import io.realm.RealmObject;

public class QuoteListRealmObject extends RealmObject {
    private int id;
    private String body;
    private String author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
