
package com.proschoolonline.model;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({
    "id",
    "date",
    "date_gmt",
    "guid",
    "modified",
    "modified_gmt",
    "slug",
    "type",
    "link",
    "title",
    "content",
    "excerpt",
    "author",
    "featured_media",
    "comment_status",
    "ping_status",
    "sticky",
    "format",
    "categories",
    "tags",
    "_links",
    "acf"
})
public class NewsData implements Serializable {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("date")
    private String date;
    @JsonProperty("date_gmt")
    private String dateGmt;
    @JsonProperty("guid")
    private Guid guid;
    @JsonProperty("modified")
    private String modified;
    @JsonProperty("modified_gmt")
    private String modifiedGmt;
    @JsonProperty("slug")
    private String slug;
    @JsonProperty("type")
    private String type;
    @JsonProperty("link")
    private String link;
    @JsonProperty("title")
    private Title title;
    @JsonProperty("content")
    private Content content;
    @JsonProperty("excerpt")
    private Excerpt excerpt;
    @JsonProperty("author")
    private Integer author;
    @JsonProperty("featured_media")
    private Integer featuredMedia;
    @JsonProperty("comment_status")
    private String commentStatus;
    @JsonProperty("ping_status")
    private String pingStatus;
    @JsonProperty("sticky")
    private Boolean sticky;
    @JsonProperty("format")
    private String format;
    @JsonProperty("categories")
    private List<Integer> categories = new ArrayList<Integer>();
    @JsonProperty("tags")
    private List<Object> tags = new ArrayList<Object>();
    @JsonProperty("_links")
    private Links links;
    @JsonProperty("acf")
    private Object acf;

    boolean bookmarked;

    String counter;

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The date
     */
    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The dateGmt
     */
    @JsonProperty("date_gmt")
    public String getDateGmt() {
        return dateGmt;
    }

    /**
     * 
     * @param dateGmt
     *     The date_gmt
     */
    @JsonProperty("date_gmt")
    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    /**
     * 
     * @return
     *     The guid
     */
    @JsonProperty("guid")
    public Guid getGuid() {
        return guid;
    }

    /**
     * 
     * @param guid
     *     The guid
     */
    @JsonProperty("guid")
    public void setGuid(Guid guid) {
        this.guid = guid;
    }

    /**
     * 
     * @return
     *     The modified
     */
    @JsonProperty("modified")
    public String getModified() {
        return modified;
    }

    /**
     * 
     * @param modified
     *     The modified
     */
    @JsonProperty("modified")
    public void setModified(String modified) {
        this.modified = modified;
    }

    /**
     * 
     * @return
     *     The modifiedGmt
     */
    @JsonProperty("modified_gmt")
    public String getModifiedGmt() {
        return modifiedGmt;
    }

    /**
     * 
     * @param modifiedGmt
     *     The modified_gmt
     */
    @JsonProperty("modified_gmt")
    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    /**
     * 
     * @return
     *     The slug
     */
    @JsonProperty("slug")
    public String getSlug() {
        return slug;
    }

    /**
     * 
     * @param slug
     *     The slug
     */
    @JsonProperty("slug")
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The link
     */
    @JsonProperty("link")
    public String getLink() {
        return link;
    }

    /**
     * 
     * @param link
     *     The link
     */
    @JsonProperty("link")
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public Title getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The content
     */
    @JsonProperty("content")
    public Content getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    @JsonProperty("content")
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The excerpt
     */
    @JsonProperty("excerpt")
    public Excerpt getExcerpt() {
        return excerpt;
    }

    /**
     * 
     * @param excerpt
     *     The excerpt
     */
    @JsonProperty("excerpt")
    public void setExcerpt(Excerpt excerpt) {
        this.excerpt = excerpt;
    }

    /**
     * 
     * @return
     *     The author
     */
    @JsonProperty("author")
    public Integer getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    @JsonProperty("author")
    public void setAuthor(Integer author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The featuredMedia
     */
    @JsonProperty("featured_media")
    public Integer getFeaturedMedia() {
        return featuredMedia;
    }

    /**
     * 
     * @param featuredMedia
     *     The featured_media
     */
    @JsonProperty("featured_media")
    public void setFeaturedMedia(Integer featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    /**
     * 
     * @return
     *     The commentStatus
     */
    @JsonProperty("comment_status")
    public String getCommentStatus() {
        return commentStatus;
    }

    /**
     * 
     * @param commentStatus
     *     The comment_status
     */
    @JsonProperty("comment_status")
    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    /**
     * 
     * @return
     *     The pingStatus
     */
    @JsonProperty("ping_status")
    public String getPingStatus() {
        return pingStatus;
    }

    /**
     * 
     * @param pingStatus
     *     The ping_status
     */
    @JsonProperty("ping_status")
    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    /**
     * 
     * @return
     *     The sticky
     */
    @JsonProperty("sticky")
    public Boolean getSticky() {
        return sticky;
    }

    /**
     * 
     * @param sticky
     *     The sticky
     */
    @JsonProperty("sticky")
    public void setSticky(Boolean sticky) {
        this.sticky = sticky;
    }

    /**
     * 
     * @return
     *     The format
     */
    @JsonProperty("format")
    public String getFormat() {
        return format;
    }

    /**
     * 
     * @param format
     *     The format
     */
    @JsonProperty("format")
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 
     * @return
     *     The categories
     */
    @JsonProperty("categories")
    public List<Integer> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    @JsonProperty("categories")
    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    /**
     * 
     * @return
     *     The tags
     */
    @JsonProperty("tags")
    public List<Object> getTags() {
        return tags;
    }

    /**
     * 
     * @param tags
     *     The tags
     */
    @JsonProperty("tags")
    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    /**
     * 
     * @return
     *     The links
     */
    @JsonProperty("_links")
    public Links getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The _links
     */
    @JsonProperty("_links")
    public void setLinks(Links links) {
        this.links = links;
    }

    @JsonProperty("acf")
    public Object getAcf() {
        return acf;
    }

    @JsonProperty("acf")
    public void setAcf(Object acf) {
        this.acf = acf;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public boolean equals(Object obj) {
        NewsData other=(NewsData) obj;
        return this.id==other.id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
