package com.projectinteract.wind;

public class Article {

    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String image_url;
    private String source_id;
    private String[] creator;



    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getSource_id() {
        return source_id;
    }

    public String getCreatorName() {
        if (creator != null && creator.length > 0) {
            return creator[0];
        }
        return "Unknown";
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public void setCreator(String[] creator) {
        this.creator = creator;
    }
}
