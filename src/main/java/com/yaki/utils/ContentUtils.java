package com.yaki.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.yaki.data.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


//用于发送请求获取数据
public class ContentUtils {
    public static JSONObject sendPost(String urlParam) throws IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(urlParam);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        postMethod.addRequestHeader("Content-Type", "application/json");

        httpClient.executeMethod(postMethod);

        String result = postMethod.getResponseBodyAsString();
        postMethod.releaseConnection();
        JSONObject res = new JSONObject(JSON.parseObject(result));
//        return result;
        return res;
    }
    public static String sendGetTracker(String urlParam) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建GET请求方法实例对象
        GetMethod getMethod = new GetMethod(urlParam);
        // 设置post请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", "application/json");
        httpClient.executeMethod(getMethod);

        String result = getMethod.getResponseBodyAsString();
        getMethod.releaseConnection();
        return result;
    }
    public static String sendGet(String urlParam) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建GET请求方法实例对象
        GetMethod getMethod = new GetMethod(urlParam);
        // 设置post请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", "application/json");
        getMethod.addRequestHeader("Authorization", "Bearer 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");
        httpClient.executeMethod(getMethod);

        String result = getMethod.getResponseBodyAsString();
        getMethod.releaseConnection();
        return result;
    }

    //缺陷信息
    public static void setFileData(String url) throws IOException {
        JSONObject jsonObject = new JSONObject(JSON.parseObject(sendGetTracker(url)));
        String title = "";//文件名
        JSONObject data = (JSONObject) jsonObject.get("data");
        JSONArray rows = (JSONArray) data.get("rows");
        for(Object row:rows){
            String issueUuid = (String) ((JSONObject)row).get("issueUuid");
            String status = (String) ((JSONObject)row).get("status");
            String type = (String) ((JSONObject)row).get("type");
            String category = (String) ((JSONObject)row).get("category");

            String producer = (String) ((JSONObject)row).get("producer");
            String produceCommit = (String) ((JSONObject)row).get("produceCommit");
            String produceCommitTime = (String) ((JSONObject)row).get("produceCommitTime");
            String solver = (String) ((JSONObject)row).get("solver");
            String solveCommit = (String) ((JSONObject)row).get("solveCommit");
            String solveCommitTime = (String) ((JSONObject)row).get("solveCommit");
            String lastCommit = (String) ((JSONObject)row).get("lastCommit");
            String lastCommitTime = (String) ((JSONObject)row).get("lastCommitTime");
            int versions = (int)((JSONObject)row).get("versions");
            String currCommit = (String) ((JSONObject)row).get("currCommit");
            String currFilePath = (String) ((JSONObject)row).get("currFilePath");
            String currRawIssueUuid = (String) ((JSONObject)row).get("currRawIssueUuid");
            String currRawIssueDetail = (String) ((JSONObject)row).get("currRawIssueDetail");
            JSONArray locations = (JSONArray)((JSONObject)row).get("locations");
            String line = "";//行号
            for (int i = 0; i < locations.size(); i++) {
                JSONObject tmp = (JSONObject)(locations.get(i));
                 line += "("+ tmp.get("startLine")+"," + tmp.get("endLine")+")";
                 title = (String) tmp.get("filePath");
            }

            FileData fileData = new FileData( issueUuid,  status,  type,  category,  producer,  produceCommit,  produceCommitTime,  solver,  solveCommit,  solveCommitTime,  lastCommit,  lastCommitTime,  versions,  currCommit,currFilePath,currRawIssueUuid, currRawIssueDetail);

            fileData.setTitle(title);
            fileData.setLine(line);

            DataCenter.fileDataList.add(fileData);//添加fileData
            DataCenter.SCANFILE_MODEL.addRow(FileDataConvert.convert_info(fileData));//展示基础的缺陷信息


            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("issueUuid:").append(fileData.getIssueUuid()).append("\n")
                    .append("producer:").append(fileData.getProducer()).append("\n")
                    .append("produceCommit:").append(fileData.getProduceCommit()).append("\n")
                    .append("produceCommitTime:").append(fileData.getProduceCommitTime()).append("\n")
                    .append("solver:").append(fileData.getSolver()).append("\n")
                    .append("solveCommit:").append(fileData.getSolveCommit()).append("\n")
                    .append("solveCommitTime:").append(fileData.getSolveCommitTime()).append("\n")
                    .append("currCommit:").append(fileData.getCurrCommit()).append("\n")
                    .append("currFilePath:").append(fileData.getCurrFilePath()).append("\n")
                    .append("currRawIssueUuid:").append(fileData.getCurrRawIssueUuid()).append("\n")
                    .append("currRawIssueDetail:").append(fileData.getCurrRawIssueDetail()).append("\n")
                    .append("lastCommit:").append(fileData.getLastCommit()).append("\n")
                    .append("lastCommitTime:").append(fileData.getLastCommitTime()).append("\n");
            DataCenter.detailList.add(stringBuilder.toString());

            String reviewUrl = DataCenter.preUrl + DataCenter.repoName + "/commits";
            String produceUrl = reviewUrl + "/" + produceCommit;
            String solveUrl = reviewUrl + "/" + solveCommit;
            String lastUrl = reviewUrl + "/" + lastCommit;

            ContentUtils.setReview(produceUrl);
            ContentUtils.setReview(solveUrl);
            ContentUtils.setReview(lastUrl);
        }

    }


    //issue信息
    public static void setIssue(String url) throws IOException {
        JSONArray jsonArray = JSONArray.parseArray(sendGet(url));
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject row = jsonArray.getJSONObject(i) ;
            String issueUrl = (String) row.get("url");
            String commentUrl = (String) row.get("comments_url");
            String title  = (String) row.get("title");
            String user = (String) ((JSONObject)row.get("user")).get("login");
            String state = (String)row.get("state");
            String date = (String)row.get("created_at");
            StringBuilder stringBuilder = new StringBuilder();
            IssueData issueData = new IssueData(issueUrl,title,user,state,date);
            issueData.setComment(setComment(commentUrl));
            DataCenter.issueList.add(issueData);
            DataCenter.ISSUE_MODEL.addRow(FileDataConvert.convert_issue(issueData));//展示issue信息
            DataCenter.issueStr += stringBuilder.toString();
        }
    }

    //issue的comment信息
    public static String setComment(String url) throws IOException {
        System.out.println(url);
        JSONArray jsonArray = JSONArray.parseArray(sendGet(url));
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject row = jsonArray.getJSONObject(i) ;
            String id = row.get("id")+"";
            String body = (String)row.get("body");
            String comment = "comment" + id + ":" + body;
            stringBuilder.append(comment).append("\n");
        }
        return stringBuilder.toString();
    }

    //code review 信息
    public static void setReview(String url) throws IOException {
        JSONObject row = JSONObject.parseObject(sendGet(url));
            String reviewUrl = (String) row.get("url");
            JSONObject commit = (JSONObject) row.get("commit");
            String author = null;
            String date = null;
            String message = null;
            if (commit!=null){
                author = (String) ((JSONObject)commit.get("committer")).get("name");
                date = (String) ((JSONObject)commit.get("committer")).get("date");
                message = (String)commit.get("message");
            }
            String nodeId = (String)row.get("node_id");
            ReviewData reviewData = new ReviewData(nodeId,author,date,message,reviewUrl);
            String stats = row.get("stats").toString();
            reviewData.setDetails(stats);
        DataCenter.reviewList.add(reviewData);
            DataCenter.COMMIT_MODEL.addRow(FileDataConvert.convert_commit(reviewData));//展示review信息
    }


    // 将输出函数单独拎出来
    public static void outputResponseContent(String responseContent, String accept){
        // accept 是接收内容类型，如"JSON"、"XML"、"HTML"、"Text"等，此处自定义输出方法
        if(accept == "JSON"){
            JSONObject jsonObj = JSON.parseObject(responseContent);
            if(jsonObj != null) for (Map.Entry<String, Object> entry : jsonObj.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }
        }
        else{
            System.out.println(responseContent);
        }
    }






}
