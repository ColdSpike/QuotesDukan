package com.inspiration.makrandpawar.quotesdukan.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavouriteRealmObject extends RealmObject {
    private String body;
    private String author;

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
