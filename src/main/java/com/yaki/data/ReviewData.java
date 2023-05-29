package com.yaki.data;

public class ReviewData {
    //code review数据
    private String nodeId;
    private String author;
    private String date;
    private String message;
    private String url;
    private String details;

    public ReviewData(String nodeId, String author, String date, String message, String url) {
        this.nodeId = nodeId;
        this.author = author;
        this.date = date;
        this.message = message;
        this.url = url;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
