package com.androidrion.movierealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieModel extends RealmObject {
    @PrimaryKey
    private Integer id;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    private String original_title, release_date, overview, poster_path;
}
