package com.cap5510.cap5510.api.objects;

import com.cap5510.cap5510.api.Type;
import java.util.ArrayList;

/**
 * Created by Sanjay on 3/20/2016.
 */
public class WatchlistItem {

    private Type type;
    private String title;
    private ArrayList<Integer> episodes;
    private int season;
    private int year;
    private int traktID;
    private String slug;
    private String imdbID;

    public WatchlistItem(){
        this.type = null;
        this.title = "";
        this.episodes = new ArrayList<Integer>();
        this.season = -1;
        this.year = -1;
        this.traktID = -1;
        this.slug = "";
        this.imdbID = "";
    }

    public WatchlistItem(Type type){
        this.type = type;
        this.title = "";
        this.episodes = new ArrayList<Integer>();
        this.season = -1;
        this.year = -1;
        this.traktID = -1;
        this.slug = "";
        this.imdbID = "";
    }


    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    private String posterURL;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Integer>  getEpisodes() {
        return episodes;
    }

    public void setEpisodes(ArrayList<Integer>  number) {
        this.episodes = number;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTraktID() {
        return traktID;
    }

    public void setTraktID(int traktID) {
        this.traktID = traktID;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }
}
