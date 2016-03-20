package com.cap5510.cap5510.api.objects.standard_media_objects;

/**
 * http://docs.trakt.apiary.io/#introduction/standard-media-objects
 */
public class Episode extends StandardMediaObject {
    private int season;
    private int number;
    private String title;
    private EpisodeIDs ids;

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EpisodeIDs getIds() {
        return ids;
    }

    public void setIds(EpisodeIDs ids) {
        this.ids = ids;
    }
}
