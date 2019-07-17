package com.example.myapplication.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Medias {

    @PrimaryKey(autoGenerate = true)
    private int mediaId;
    @ColumnInfo(name = "storyId")
    private int storyId;
    private String image;

    public Medias(int mediaId, String image) {
        this.mediaId = mediaId;
        this.image = image;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStoryId() {
        return storyId;
    }

    public void setStoryId(int storyId) {
        this.storyId = storyId;
    }

    @NonNull
    @Override
    public String toString() {
        return image;
    }
}
