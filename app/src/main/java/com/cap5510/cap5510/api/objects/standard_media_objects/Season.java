package com.cap5510.cap5510.api.objects.standard_media_objects;

/**
 * http://docs.trakt.apiary.io/#introduction/standard-media-objects
 */
public class Season extends StandardMediaObject {
    private int number;
    private SeasonIDs ids;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public SeasonIDs getIds() {
        return ids;
    }

    public void setIds(SeasonIDs ids) {
        this.ids = ids;
    }
}
