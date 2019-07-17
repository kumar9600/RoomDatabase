package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {
    @Expose
    private String profileImage;
    @SerializedName("class")
    @Expose
    private String _class;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("student_token")
    @Expose
    private Integer studentToken;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Integer getStudentToken() {
        return studentToken;
    }

    public void setStudentToken(Integer studentToken) {
        this.studentToken = studentToken;
    }
}
