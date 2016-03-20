package com.cap5510.cap5510.api.objects.standard_media_objects;

/**
 * http://docs.trakt.apiary.io/#introduction/standard-media-objects
 */
public class Show extends StandardMediaObject {
    private String title;
    private int year;
    private ShowIDs ids;

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

    public ShowIDs getIds() {
        return ids;
    }

    public void setIds(ShowIDs ids) {
        this.ids = ids;
    }
}
