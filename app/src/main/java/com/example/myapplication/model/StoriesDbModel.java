package com.example.myapplication.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.example.myapplication.converters.DateConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

@Entity(indices = @Index(value = {"storyId"}, unique = true))
public class StoriesDbModel {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters(DateConverter.class)
    private Date borrowDate;
    @SerializedName("comments")
    @Expose
    private Integer comments;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("from_designation")
    @Expose
    private String fromDesignation;
    @SerializedName("from_name")
    @Expose
    private String fromName;
    @SerializedName("from_thumpImg")
    @Expose
    private String fromThumpImg;
    @SerializedName("like")
    @Expose
    private Boolean like;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("short_desc")
    @Expose
    private String shortDesc;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("story_id")
    @Expose
    private Integer storyId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("media")
    @Expose
    private List<String> media = null;

    /*@Relation(parentColumn = "id", entityColumn = "storyId", entity = Medias.class)
    private List<Medias> medias;
    @Ignore
    @SerializedName("media")
    @Expose
    private List<String> media = null;*/


    public StoriesDbModel(
            int id,
            Date borrowDate,
            Integer comments,
            String date,
            String fromDesignation,
            String fromName,
            String fromThumpImg,
            Boolean like,
            Integer likes,
            String shortDesc,
            String status,
            Integer storyId,
            String title,
            String to,
            List<String> media) {

        this.id = id;
        this.borrowDate = borrowDate;
        this.comments = comments;
        this.date = date;
        this.fromDesignation = fromDesignation;
        this.fromName = fromName;
        this.fromThumpImg = fromThumpImg;
        this.like = like;
        this.likes = likes;
        this.shortDesc = shortDesc;
        this.status = status;
        this.storyId = storyId;
        this.title = title;
        this.to = to;
        this.media = media;

    }

    /*public List<Medias> getMedias() {
        return medias;
    }

    public void setMedias(List<Medias> medias) {
        this.medias = medias;
    }
*/

    public List<String> getMedia() {
        return media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getFromDesignation() {
        return fromDesignation;
    }

    public void setFromDesignation(String fromDesignation) {
        this.fromDesignation = fromDesignation;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromThumpImg() {
        return fromThumpImg;
    }

    public void setFromThumpImg(String fromThumpImg) {
        this.fromThumpImg = fromThumpImg;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
}
