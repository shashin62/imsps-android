
package com.proschoolonline.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({
    "self",
    "collection",
    "about",
    "up",
    "https://api.w.org/post_type"
})
public class LinksCategory implements Serializable {

    @JsonProperty("self")
    private List<Self> self = new ArrayList<Self>();
    @JsonProperty("collection")
    private List<Collection> collection = new ArrayList<Collection>();
    @JsonProperty("about")
    private List<About> about = new ArrayList<About>();
    @JsonProperty("up")
    private List<Up> up = new ArrayList<Up>();
    @JsonProperty("https://api.w.org/post_type")
    private List<HttpsApiWOrgPostType> httpsApiWOrgPostType = new ArrayList<HttpsApiWOrgPostType>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The self
     */
    @JsonProperty("self")
    public List<Self> getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    @JsonProperty("self")
    public void setSelf(List<Self> self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The collection
     */
    @JsonProperty("collection")
    public List<Collection> getCollection() {
        return collection;
    }

    /**
     * 
     * @param collection
     *     The collection
     */
    @JsonProperty("collection")
    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    /**
     * 
     * @return
     *     The about
     */
    @JsonProperty("about")
    public List<About> getAbout() {
        return about;
    }

    /**
     * 
     * @param about
     *     The about
     */
    @JsonProperty("about")
    public void setAbout(List<About> about) {
        this.about = about;
    }

    /**
     * 
     * @return
     *     The up
     */
    @JsonProperty("up")
    public List<Up> getUp() {
        return up;
    }

    /**
     * 
     * @param up
     *     The up
     */
    @JsonProperty("up")
    public void setUp(List<Up> up) {
        this.up = up;
    }

    /**
     * 
     * @return
     *     The httpsApiWOrgPostType
     */
    @JsonProperty("https://api.w.org/post_type")
    public List<HttpsApiWOrgPostType> getHttpsApiWOrgPostType() {
        return httpsApiWOrgPostType;
    }

    /**
     * 
     * @param httpsApiWOrgPostType
     *     The https://api.w.org/post_type
     */
    @JsonProperty("https://api.w.org/post_type")
    public void setHttpsApiWOrgPostType(List<HttpsApiWOrgPostType> httpsApiWOrgPostType) {
        this.httpsApiWOrgPostType = httpsApiWOrgPostType;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
