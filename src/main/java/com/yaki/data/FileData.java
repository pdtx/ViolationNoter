package com.yaki.data;
//一个文件的缺陷信息
public class FileData {
    private String title;
    private String issueUuid;
    private String status;
    private String type;
    private String category;
    private String producer;
    private String produceCommit;
    private String produceCommitTime;
    private String solver;
    private String solveCommit;
    private String solveCommitTime;
    private String lastCommit;
    private String lastCommitTime;
    private int versions;
    private String currCommit;
    private String currFilePath;
    private String currRawIssueUuid;
    private String currRawIssueDetail;
    private String line;


    public FileData(String issueUuid, String status, String type, String category, String producer, String produceCommit, String produceCommitTime, String solver, String solveCommit, String solveCommitTime, String lastCommit, String lastCommitTime, int versions, String currCommit, String currFilePath, String currRawIssueUuid, String currRawIssueDetail) {
        this.issueUuid = issueUuid;
        this.status = status;
        this.type = type;
        this.category = category;
        this.producer = producer;
        this.produceCommit = produceCommit;
        this.produceCommitTime = produceCommitTime;
        this.solver = solver;
        this.solveCommit = solveCommit;
        this.solveCommitTime = solveCommitTime;
        this.lastCommit = lastCommit;
        this.lastCommitTime = lastCommitTime;
        this.versions = versions;
        this.currCommit = currCommit;
        this.currFilePath = currFilePath;
        this.currRawIssueUuid = currRawIssueUuid;
        this.currRawIssueDetail = currRawIssueDetail;

    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCurrRawIssueDetail() {
        return currRawIssueDetail;
    }

    public void setCurrRawIssueDetail(String currRawIssueDetail) {
        this.currRawIssueDetail = currRawIssueDetail;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSolver(String solver) {
        this.solver = solver;
    }

    public void setSolveCommit(String solveCommit) {
        this.solveCommit = solveCommit;
    }

    public void setSolveCommitTime(String solveCommitTime) {
        this.solveCommitTime = solveCommitTime;
    }

    public void setLastCommit(String lastCommit) {
        this.lastCommit = lastCommit;
    }

    public void setLastCommitTime(String lastCommitTime) {
        this.lastCommitTime = lastCommitTime;
    }


    public void setCurrFilePath(String currFilePath) {
        this.currFilePath = currFilePath;
    }

    public void setCurrRawIssueUuid(String currRawIssueUuid) {
        this.currRawIssueUuid = currRawIssueUuid;
    }

    public String getStatus() {
        return status;
    }

    public String getSolver() {
        return solver;
    }

    public String getSolveCommit() {
        return solveCommit;
    }

    public String getSolveCommitTime() {
        return solveCommitTime;
    }

    public String getLastCommit() {
        return lastCommit;
    }

    public String getLastCommitTime() {
        return lastCommitTime;
    }

    public int getVersions() {
        return versions;
    }

    public void setVersions(int versions) {
        this.versions = versions;
    }

    public String getCurrCommit() {
        return currCommit;
    }

    public void setCurrCommit(String currCommit) {
        this.currCommit = currCommit;
    }

    public String getCurrFilePath() {
        return currFilePath;
    }

    public String getCurrRawIssueUuid() {
        return currRawIssueUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssueUuid() {
        return issueUuid;
    }

    public void setIssueUuid(String issueUuid) {
        this.issueUuid = issueUuid;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProduceCommit() {
        return produceCommit;
    }

    public void setProduceCommit(String produceCommit) {
        this.produceCommit = produceCommit;
    }

    public String getProduceCommitTime() {
        return produceCommitTime;
    }

    public void setProduceCommitTime(String produceCommitTime) {
        this.produceCommitTime = produceCommitTime;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "title='" + title + '\'' +
                ", issueUuid='" + issueUuid + '\'' +
                ", producer='" + producer + '\'' +
                ", produceCommit='" + produceCommit + '\'' +
                ", produceCommitTime='" + produceCommitTime + '\'' +
                ", status='" + status + '\'' +
                ", solver='" + solver + '\'' +
                ", solveCommit='" + solveCommit + '\'' +
                ", solveCommitTime='" + solveCommitTime + '\'' +
                ", lastCommit='" + lastCommit + '\'' +
                ", lastCommitTime='" + lastCommitTime + '\'' +
                ", versions='" + versions + '\'' +
                ", currFilePath='" + currFilePath + '\'' +
                ", currRawIssueUuid='" + currRawIssueUuid + '\'' +
                '}';
    }
}
