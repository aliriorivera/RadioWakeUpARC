package dev.alirio.radiowakeuparc.pojos;

/**
 * RadioStation represents a radio station
 * author: Alirio Rivera
 */
public class RadioStation {

    private String id;
    private String name;
    private String country;
    private String tags;
    private String url;

    public RadioStation() {
    }

    //TODO Add icon to screen list

    /**
     * Constructor of the POJO class
     * @param id id of the radiostation in the REST API
     * @param name name of the radio station
     * @param country country of the radio station
     * @param tags tags of the radio station
     * @param url url of the radio station
     */
    public RadioStation(String id, String name, String country, String tags, String url) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.tags = tags;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
