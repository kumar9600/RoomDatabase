package com.example.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("session_id")
    @Expose
    private String sessionId;
    @SerializedName("dp")
    @Expose
    private String dp;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("nry_verify")
    @Expose
    private Boolean nryVerify;
    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("children")
    @Expose
    private List<Child> children = null;
    @SerializedName("login_type")
    @Expose
    private String loginType;
    @SerializedName("user_token")
    @Expose
    private Integer userToken;
    @SerializedName("stories")
    @Expose
    private List<StoriesDbModel> stories = null;

    public List<StoriesDbModel> getStories() {
        return stories;
    }

    public void setStories(List<StoriesDbModel> stories) {
        this.stories = stories;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getNryVerify() {
        return nryVerify;
    }

    public void setNryVerify(Boolean nryVerify) {
        this.nryVerify = nryVerify;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public Integer getUserToken() {
        return userToken;
    }

    public void setUserToken(Integer userToken) {
        this.userToken = userToken;
    }
}
