package com.yaki.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaki.data.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;


//用于发送请求获取数据
public class ContentUtils {
    public static JSONObject sendPost(String urlParam, JSONObject requestData) throws IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(urlParam);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        postMethod.addRequestHeader("Content-Type", "application/json");
        // 将JSON对象转换为字符串，并设置为请求主体
        StringRequestEntity requestEntity = new StringRequestEntity(requestData.toJSONString(), "application/json", "UTF-8");
        postMethod.setRequestEntity(requestEntity);

        httpClient.executeMethod(postMethod);

        BufferedReader reader = new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream()));
        StringBuilder stringBuffer = new StringBuilder();
        String str = "";
        while((str = reader.readLine())!=null){
            stringBuffer.append(str);
        }

        String result = stringBuffer.toString();
        postMethod.releaseConnection();
        return new JSONObject(JSON.parseObject(result));
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
        httpClient.executeMethod(getMethod);

        BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));
        StringBuilder stringBuffer = new StringBuilder();
        String str = "";
        while((str = reader.readLine())!=null){
            stringBuffer.append(str);
        }

        String result = stringBuffer.toString();
        getMethod.releaseConnection();
        return result;
    }

    //缺陷信息
    public static void setFileData(String url) throws IOException {
        JSONObject jsonObject = new JSONObject(JSON.parseObject(sendGet(url)));
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

    //注册成为标记员
    public static void register(String url, String username, String email) throws Exception {
        JSONObject requestData = new JSONObject();
        requestData.put("userName", username);
        requestData.put("email", email);

        JSONObject response = sendPost(url, requestData);

        System.out.println("registerResponse:"+response);
        // TODO: 记录uuid
        if ((Integer) response.get("code") == 200) {
            DataCenter.userUuid = "5ef2082a-b23d-309d-a0dc-0c07a48ee32d";
//            DataCenter.userUuid = (String) response.get("data");
            DataCenter.login = true;
        } else {
            DataCenter.login = true;
            throw new RuntimeException(String.valueOf(response));
        }
    }

    //获取文件相关缺陷
    public static void getIssueData() throws IOException {
        System.out.println(DataCenter.issueUrl+"?repo_uuid="+DataCenter.repoUuid+"&commit_id="+DataCenter.commitId+"&file_path="+DataCenter.file_path);
        // TODO: 由于repoUuid目前只有一个，暂时mock数据
        DataCenter.repoUuid = "afd03026-3369-3404-b1be-eaff4ea8bba9";
        DataCenter.commitId = "36a72d9c509a796d9e2bc112fab9356148a13f76";
        DataCenter.file_path = "curator-framework/src/main/java/org/apache/curator/framework/imps/CuratorFrameworkImpl.java";

        String url = DataCenter.issueUrl+"?repo_uuid="+DataCenter.repoUuid+"&commit_id="+DataCenter.commitId+"&file_path="+DataCenter.file_path;
        JSONObject response = new JSONObject(JSON.parseObject(sendGet(url)));
        System.out.println(response);

        if ((Integer) response.get("code") == 200) {
            JSONObject data = (JSONObject) response.get("data");
            JSONArray rows = (JSONArray) data.get("rows");
            DataCenter.violationIssues.clear();
            List<ViolationIssue> violationIssuesList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            for(Object row:rows){
                ViolationIssue violationIssue = objectMapper.convertValue(row, ViolationIssue.class);
                violationIssuesList.add(violationIssue);
            }
            DataCenter.violationIssues.addAll(violationIssuesList);
            DataConvert.convert_issue(DataCenter.violationIssues);
        } else {
            throw new RuntimeException(String.valueOf(response));
        }
    }

    //获取文件相关评论
    public static void getCommentData() throws IOException {
        System.out.println(DataCenter.commentUrl+"?repo_uuid="+DataCenter.repoUuid+"&commit_id="+DataCenter.commitId+"&file_path="+DataCenter.file_path);
        // TODO: 由于repoUuid目前只有一个，暂时mock数据
        DataCenter.repoUuid = "afd03026-3369-3404-b1be-eaff4ea8bba9";
        DataCenter.commitId = "36a72d9c509a796d9e2bc112fab9356148a13f76";
        DataCenter.file_path = "curator-framework/src/main/java/org/apache/curator/framework/imps/CuratorFrameworkImpl.java";

        String url = DataCenter.commentUrl+"?repo_uuid="+DataCenter.repoUuid+"&commit_id="+DataCenter.commitId+"&file_path="+DataCenter.file_path;
        JSONObject response = new JSONObject(JSON.parseObject(sendGet(url)));

        if ((Integer) response.get("code") == 200) {
            JSONObject data = (JSONObject) response.get("data");
            JSONArray rows = (JSONArray) data.get("rows");
            DataCenter.violationComments.clear();
            List<ViolationComment> violationCommentList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            for(Object row:rows){
                ViolationComment violationComment = objectMapper.convertValue(row, ViolationComment.class);
                violationCommentList.add(violationComment);
            }
            DataCenter.violationComments.addAll(violationCommentList);
            DataConvert.convert_comment(DataCenter.violationComments);
        } else {
            throw new RuntimeException(String.valueOf(response));
        }
    }

    //issue信息
    public static void setIssue(String url) throws IOException {
        String result = sendGet(url);
        System.out.println("issues:"+result);
        if (result.startsWith("{")){
            throw new IOException(result);
        }
        JSONArray jsonArray = JSONArray.parseArray(result);
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
