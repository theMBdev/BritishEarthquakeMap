//
// Name                 Martin Brown
// Student ID           S1718539
// Programme of Study   Computing
//

package com.example.britishearthquakemap;

public class Item {

    public String title;
    public String description;
    public String link;
    public String pubDate;
    public String category;
    public String geoLat;
    public String geoLong;

    public String location;
    public String depth;
    public String magnitude;
    public String dateTime;
    public String day;


    public Item(){}
    public Item(String title, String description, String link, String pubDate, String category, String geoLat, String geoLong, String location, String depth, String magnitude, String dateTime, String day) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.category = category;
        this.geoLat = geoLat;
        this.geoLong = geoLong;
        this.location = location;
        this.depth = depth;
        this.magnitude = magnitude;
        this.dateTime = dateTime;
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    public String getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(String geoLong) {
        this.geoLong = geoLong;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
