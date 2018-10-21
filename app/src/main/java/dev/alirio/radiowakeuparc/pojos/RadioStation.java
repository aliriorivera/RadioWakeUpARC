package dev.alirio.radiowakeuparc.pojos;

/**
 * RadioStation represents a radio station
 * author: Alirio Rivera
 */
public class RadioStation {

    private String name, country, tags, url;

    public RadioStation() {
    }

    //TODO Add icon to screen list

    /**
     * Constructor of the POJO class
     * @param name name of the radio station
     * @param country country of the radio station
     * @param tags tags of the radio station
     * @param url url of the radio station
     */
    public RadioStation(String name, String country, String tags, String url) {
        this.name = name;
        this.country = country;
        this.tags = tags;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
