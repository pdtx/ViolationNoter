package com.yaki.data;

import com.yaki.processor.SourceNoteData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataCenter {
    public static boolean login;//登录状态
    public static String repoName;
    public static String file_path;
    public static String commitId;//commitId
    public static String preTracker = "http://10.176.34.96:8105/issue/tracker-file?repo_uuid=";//github api前缀
    public static String preUrl = "https://api.github.com/repos/apache/";//github api前缀
    public static FileData fileData;


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

    /**
     * 基础的文件缺陷信息
     */
    public static List<FileData> fileDataList = new LinkedList<>();
    //表头

    public static String[] COLUMN={"FilePath","Line","Type","Message","引入TP/FP","修复TP/FP","Actionable","Priority","Repair Details","Remark"};

    public static DefaultTableModel SCANFILE_MODEL = new DefaultTableModel(null,COLUMN);


    public static DefaultListModel<String> DETAIL_MODEL = new DefaultListModel<>();
    public static List<String> detailList = new LinkedList<>();
    public static List<IssueData> issueList = new LinkedList<>();
    public static List<ReviewData> reviewList = new LinkedList<>();
    public static String issueStr="";
}
