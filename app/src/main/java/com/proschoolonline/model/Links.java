
package com.proschoolonline.model;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({
    "self",
    "collection",
    "about",
    "author",
    "replies",
    "version-history",
    "https://api.w.org/attachment",
    "https://api.w.org/term"
})
public class Links implements Serializable {

    @JsonProperty("self")
    private List<Self> self = new ArrayList<Self>();
    @JsonProperty("collection")
    private List<Collection> collection = new ArrayList<Collection>();
    @JsonProperty("about")
    private List<About> about = new ArrayList<About>();
    @JsonProperty("author")
    private List<Author> author = new ArrayList<Author>();
    @JsonProperty("replies")
    private List<Reply> replies = new ArrayList<Reply>();
    @JsonProperty("version-history")
    private List<VersionHistory> versionHistory = new ArrayList<VersionHistory>();
    @JsonProperty("https://api.w.org/attachment")
    private List<HttpsApiWOrgAttachment> httpsApiWOrgAttachment = new ArrayList<HttpsApiWOrgAttachment>();
    @JsonProperty("https://api.w.org/term")
    private List<HttpsApiWOrgTerm> httpsApiWOrgTerm = new ArrayList<HttpsApiWOrgTerm>();
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
     *     The author
     */
    @JsonProperty("author")
    public List<Author> getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    @JsonProperty("author")
    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The replies
     */
    @JsonProperty("replies")
    public List<Reply> getReplies() {
        return replies;
    }

    /**
     * 
     * @param replies
     *     The replies
     */
    @JsonProperty("replies")
    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    /**
     * 
     * @return
     *     The versionHistory
     */
    @JsonProperty("version-history")
    public List<VersionHistory> getVersionHistory() {
        return versionHistory;
    }

    /**
     * 
     * @param versionHistory
     *     The version-history
     */
    @JsonProperty("version-history")
    public void setVersionHistory(List<VersionHistory> versionHistory) {
        this.versionHistory = versionHistory;
    }

    /**
     * 
     * @return
     *     The httpsApiWOrgAttachment
     */
    @JsonProperty("https://api.w.org/attachment")
    public List<HttpsApiWOrgAttachment> getHttpsApiWOrgAttachment() {
        return httpsApiWOrgAttachment;
    }

    /**
     * 
     * @param httpsApiWOrgAttachment
     *     The https://api.w.org/attachment
     */
    @JsonProperty("https://api.w.org/attachment")
    public void setHttpsApiWOrgAttachment(List<HttpsApiWOrgAttachment> httpsApiWOrgAttachment) {
        this.httpsApiWOrgAttachment = httpsApiWOrgAttachment;
    }

    /**
     * 
     * @return
     *     The httpsApiWOrgTerm
     */
    @JsonProperty("https://api.w.org/term")
    public List<HttpsApiWOrgTerm> getHttpsApiWOrgTerm() {
        return httpsApiWOrgTerm;
    }

    /**
     * 
     * @param httpsApiWOrgTerm
     *     The https://api.w.org/term
     */
    @JsonProperty("https://api.w.org/term")
    public void setHttpsApiWOrgTerm(List<HttpsApiWOrgTerm> httpsApiWOrgTerm) {
        this.httpsApiWOrgTerm = httpsApiWOrgTerm;
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
