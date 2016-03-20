package com.cap5510.cap5510.api.objects.standard_media_objects;

/**
 * http://docs.trakt.apiary.io/#introduction/standard-media-objects
 */
public class Person extends StandardMediaObject {
    private String name;
    private PersonIDs ids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonIDs getIds() {
        return ids;
    }

    public void setIds(PersonIDs ids) {
        this.ids = ids;
    }
}
