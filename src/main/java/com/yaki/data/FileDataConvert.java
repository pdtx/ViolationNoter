package com.yaki.data;

public class FileDataConvert {
    //文件的code review信息
    public static String[] convert_commit(ReviewData reviewData){
        String[] row = new String[5];
        row[0] = reviewData.getUrl();
        row[1] = reviewData.getMessage();
        row[2] = reviewData.getAuthor();
        row[3] = reviewData.getDate();
        row[4] = reviewData.getDetails();
        return row;
    }
    //文件的issue
    public static String[] convert_issue(IssueData issueData){
        //一行
        String[] row = new String[6];
        row[0] = issueData.getIssueUrl();
        row[1] = issueData.getTitle();
        row[2] = issueData.getUser();
        row[3] = issueData.getState();
        row[4] = issueData.getDate();
        row[5] = issueData.getComment();

        return row;
    }
    //文件的缺陷基础信息
    public static String[] convert_info(FileData fileData){
        //一行
        String[] row = new String[6];
        //文件路径，行号，类型，缺陷详情
        row[0] = fileData.getTitle();
        row[1] = fileData.getLine();
        row[2] = fileData.getCategory();
        row[3] = fileData.getType();
        return row;
    }


}
