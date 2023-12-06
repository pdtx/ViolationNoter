package com.yaki.data;

import com.yaki.processor.SourceNoteData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DataCenter {
    //用户相关
    public static boolean login;//登录状态
    public static String registerUrl = "http://10.176.34.85:8155/annotator";
    public static String userUuid;

    //文件相关
    public static String repoName;
    public static String file_path;
    public static String commitId;//commitId
    public static String repoUuid;

    //数据相关
    public static String issueUrl = "http://10.176.34.85:8155/violation/tracker-file";
    public static String commentUrl = "http://10.176.34.85:8155/violation/comment/tracker-file";
    public static String remarkUrl = "http://10.176.34.85:8155/annotate-violation";
    public static List<ViolationIssue> violationIssues = new LinkedList<>();
    public static List<ViolationComment> violationComments = new LinkedList<>();
    public static HashMap<String, ViolationRemark> remarkHashMap = new HashMap<>();

    public static String preTracker = "http://10.176.34.96:8105/issue/tracker-file?repo_uuid=";//github api前缀
    public static String preUrl = "https://api.github.com/repos/apache/";//github api前缀


    public static String SELECT_TXT;
    public static String FILE_NAME;
    //笔记列表
    public static List<NoteData> NOTE_LIST = new LinkedList<NoteData>();

    public static SourceNoteData sourceNoteData;
    public static String[] HEAD={"Title","Content","Filename","Code"};

    //[][]data,[]columnNames,即数据和表头
    public static DefaultTableModel TABLE_MODEL = new DefaultTableModel(null,HEAD);

    public static void reset(){
        NOTE_LIST.clear();
        TABLE_MODEL.setDataVector(null,HEAD);
    }

    //表头
    public static String[] COLUMN_COMMIT={"Url","Message","Author","Date","Details"};
    public static DefaultTableModel COMMIT_MODEL = new DefaultTableModel(null,COLUMN_COMMIT);
    //-------------------------------------------------------------------------------
    //表头
    public static String[] COLUMN_ISSUE={"Url","Title","Author","State","Date","Comments"};
    public static DefaultTableModel ISSUE_MODEL = new DefaultTableModel(null,COLUMN_ISSUE);
    //-------------------------------------------------------------------------------
    //表头
    public static String[] COLUMN={"FilePath","Line","Type","Message","引入TP/FP","修复TP/FP","Actionable","Priority","Repair Details","Remark"};
    public static DefaultTableModel SCANFILE_MODEL = new DefaultTableModel(null,COLUMN);
    //-------------------------------------------------------------------------------

    //表头
    public static String[] COLUMN_VIOLATION_ISSUE={"Type","Category","Status","Locations","TP/FP","Actionable","Priority","Reason"};
    public static DefaultTableModel VIOLATION_ISSUE_MODEL = new DefaultTableModel(null,COLUMN_VIOLATION_ISSUE) {
        @Override
        public boolean isCellEditable(int row, int column) {
            if (column == 4){
                return true;
            }else if (column == 5 && getValueAt(row, 4) != null && getValueAt(row, 4).equals("TP")){
                return true;
            }else if (column == 6 && getValueAt(row, 4) != null && getValueAt(row, 4).equals("TP") && getValueAt(row, 5) != null && getValueAt(row, 5).equals("Actionable")){
                return true;
            }else return column == 7;
        }
    };
    //-------------------------------------------------------------------------------
    //表头
    public static String[] COLUMN_VIOLATION_COMMENT={"Url","Developer","Comment","CreateTime","UpdateTime","StartLine","EndLine","RealLine"};
    public static DefaultTableModel VIOLATION_COMMENT_MODEL = new DefaultTableModel(null,COLUMN_VIOLATION_COMMENT) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * 基础的文件缺陷信息
     */
    public static List<FileData> fileDataList = new LinkedList<>();
    public static DefaultListModel<String> DETAIL_MODEL = new DefaultListModel<>();
    public static List<String> detailList = new LinkedList<>();
    public static List<IssueData> issueList = new LinkedList<>();
    public static List<ReviewData> reviewList = new LinkedList<>();
    public static String issueStr="";
}
