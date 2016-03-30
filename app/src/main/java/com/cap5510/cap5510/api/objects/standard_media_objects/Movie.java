package com.cap5510.cap5510.api.objects.standard_media_objects;


/**
 * http://docs.trakt.apiary.io/#introduction/standard-media-objects
 */
public class Movie extends StandardMediaObject {
    private String title;
    private int year;
    private MovieIDs ids;
    private int traktID;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public MovieIDs getIds() {
        return ids;
    }

    public void setIds(MovieIDs ids) {
        this.ids = ids;
    }

    public int getTraktID(){return traktID;}

    public Movie(int id){
        traktID = id;
    }
}
