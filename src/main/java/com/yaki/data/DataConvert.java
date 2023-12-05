package com.yaki.data;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DataConvert {
    public static String[] convert(NoteData noteData){
        //一行
        String[] row = new String[4];
        row[0] = noteData.getTitle();
        row[1] = noteData.getMark();
        row[2] = noteData.getFileName();
        row[3] = noteData.getContent();
        return row;
    }

    public static String[] convert_issue_item(ViolationIssue v) {
        String[] row = new String[8];
        row[0] = v.getType();
        row[1] = v.getCategory();
        row[2] = v.getStatus();
        List<String> locations = v.getLocations().stream().map(l->"["+l.getStartLine()+","+l.getEndLine()+"]").collect(Collectors.toList());
        row[3] = StringUtils.join(locations, ",");
        row[4] = v.getIsTruePositiveAggregated();
        row[5] = v.getActionableConsensusScore();
        row[6] = v.getPriorityAggregated();
        row[7] = "";
        return row;
    }

    public static void convert_issue(List<ViolationIssue> violationIssues) {
        for (ViolationIssue v: violationIssues){
            DataCenter.VIOLATION_ISSUE_MODEL.addRow(convert_issue_item(v));
        }
    }

    public static void convert_comment(List<ViolationComment> violationComments) {
        for (ViolationComment v: violationComments){
            String[] row = new String[8];
            row[0] = v.getUrl();
            row[1] = v.getDeveloper();
            row[2] = v.getBody();
            row[3] = v.getCreatedAt();
            row[4] = v.getUpdateAt();
            row[5] = v.getStartLine()+"";
            row[6] = v.getEndLine()+"";
            row[7] = v.getRealLine()+"";
            DataCenter.VIOLATION_COMMENT_MODEL.addRow(row);
        }
    }
}
