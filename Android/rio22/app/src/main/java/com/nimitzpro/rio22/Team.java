package com.nimitzpro.rio22;

import java.io.Serializable;

public class Team implements Serializable {

    private String name, region, desc, winnings, created, image, url;

    public Team(String name, String region, String desc, String winnings, String created, String image, String url) {
        this.name = name;
        this.region = region;
        this.desc = desc;
        this.winnings = winnings;
        this.created = created;
        this.image = image;
        this.url = url;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getRegion(){
        return region;
    }
    public void setRegion(String region){
        this.region = region;
    }

    public String getDesc(){
        return desc;
    }
    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getWinnings() {
        return winnings;
    }
    public void setWinnings(String winnings) {
        this.winnings = winnings;
    }

    public String getCreated() {
        return created;
    }
    public void setCreated(String created) { this.url = url; }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

}
