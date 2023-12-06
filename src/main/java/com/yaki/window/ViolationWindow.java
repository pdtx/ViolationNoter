package com.yaki.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.ui.JBUI;
import com.yaki.data.CommitInfo;
import com.yaki.data.DataCenter;
import com.yaki.data.ViolationRemark;
import com.yaki.dialog.CommitInfoDialog;
import com.yaki.utils.ContentUtils;
import com.yaki.utils.GitInfoProvider;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class ViolationWindow {
    private JPanel violationPanel;
    private JTabbedPane tabbedPane;
    private JTable issueTable;
    private JTable commentTable;
    private JScrollPane JPIssue;
    private JScrollPane JPComment;
    private JScrollPane JPCommit;
    private JButton remarkButton;
    private JPanel commitPanel;
    private JButton updateButton;

    public ViolationWindow(Project project, ToolWindow toolWindow) {
        init(project);
    }

    private void init(Project project) {
        issueTable.setModel(DataCenter.VIOLATION_ISSUE_MODEL);

        commentTable.setModel(DataCenter.VIOLATION_COMMENT_MODEL);

        // commit信息
        commitPanel.setLayout(new BoxLayout(commitPanel, BoxLayout.Y_AXIS));
        Border padding = JBUI.Borders.empty(20);
        commitPanel.setBorder(padding);
        CommitInfo commitInfo = GitInfoProvider.getGitMessage(project);
        if (commitInfo == null){
            JLabel label = new JLabel("Fail to fetch commit info");
            commitPanel.add(label);
        }else {
            Date date = new Date(commitInfo.getCommitTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String formattedTime = sdf.format(date);
            JLabel label = new JLabel("<html>"
                    +"Current Commit Info<br><br>"
                    +"Commit Id: "+commitInfo.getCommitId()+"<br><br>"
                    +"Commit Message: "+commitInfo.getCommitMessage()+"<br><br>"
                    +"Commit Author: "+commitInfo.getAuthorName()+"&lt;"+commitInfo.getAuthorEmail()+"&gt;<br><br>"
                    +"Commit Time: "+formattedTime
                    +"</html>"
            );
            commitPanel.add(label);
        }

        /**
         * TP/FP
         */
        Vector<String> vector1 = new Vector<String>();
        vector1.add("TP");
        vector1.add("FP");
        final JComboBox<String> comboBox1 = new JComboBox<String>(vector1);
        //下拉框监听
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    int selectedRow = issueTable.getSelectedRow();
                    String violationUuid = DataCenter.violationIssues.get(selectedRow).getViolationUuid();
                    Boolean truePositive = comboBox1.getSelectedItem() == null ? null : comboBox1.getSelectedItem() == "TP";
                    if (DataCenter.remarkHashMap.containsKey(violationUuid)){
                        ViolationRemark violationRemark = DataCenter.remarkHashMap.get(violationUuid);
                        violationRemark.setIsTruePositive(truePositive);
                        DataCenter.remarkHashMap.put(violationUuid, violationRemark);
                    }else {
                        ViolationRemark violationRemark= new ViolationRemark(DataCenter.userUuid, violationUuid, truePositive, null, null, "");
                        DataCenter.remarkHashMap.put(violationUuid, violationRemark);
                    }
                }
            }
        });
        issueTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox1));

        /**
         * 可行性
         */
        Vector<String> vector2 = new Vector<String>();
        vector2.add("Actionable");
        vector2.add("NonActionable");
        final JComboBox<String> comboBox2 = new JComboBox<String>(vector2);
        //下拉框监听
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    int selectedRow = issueTable.getSelectedRow();
                    String violationUuid = DataCenter.violationIssues.get(selectedRow).getViolationUuid();
                    Boolean actionable = comboBox2.getSelectedItem() == null ? null : comboBox2.getSelectedItem() == "Actionable";
                    if (DataCenter.remarkHashMap.containsKey(violationUuid)){
                        ViolationRemark violationRemark = DataCenter.remarkHashMap.get(violationUuid);
                        violationRemark.setIsActionable(actionable);
                        DataCenter.remarkHashMap.put(violationUuid, violationRemark);
                    }else {
                        ViolationRemark violationRemark= new ViolationRemark(DataCenter.userUuid, violationUuid, null, actionable, null, "");
                        DataCenter.remarkHashMap.put(violationUuid, violationRemark);
                    }
                }
            }
        });
        issueTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(comboBox2));

        /**
         * 优先级
         */
        Vector<Integer> vectorPrior = new Vector<Integer>();
        for (int i = 1; i <= 6; i++) {
            vectorPrior.add(i);
        }

        final JComboBox<Integer> comboBoxPrior = new JComboBox<Integer>(vectorPrior);
        //下拉框监听
        comboBoxPrior.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    int selectedRow = issueTable.getSelectedRow();
                    String violationUuid = DataCenter.violationIssues.get(selectedRow).getViolationUuid();
                    Integer priority = comboBoxPrior.getSelectedItem() == null ? null : (Integer) comboBoxPrior.getSelectedItem();
                    if (DataCenter.remarkHashMap.containsKey(violationUuid)){
                        ViolationRemark violationRemark = DataCenter.remarkHashMap.get(violationUuid);
                        violationRemark.setPriority(priority);
                        DataCenter.remarkHashMap.put(violationUuid, violationRemark);
                    }else {
                        ViolationRemark violationRemark= new ViolationRemark(DataCenter.userUuid, violationUuid, null, null, priority, "");
                        DataCenter.remarkHashMap.put(violationUuid, violationRemark);
                    }
                }
            }
        });
        issueTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBoxPrior));

        /**
         * 原因
         */
        JTextField textField = new JTextField();
        issueTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 7) {
                    int selectedRow = issueTable.getSelectedRow();
                    String violationUuid = DataCenter.violationIssues.get(selectedRow).getViolationUuid();
                    String reason = textField.getText();
                    if (DataCenter.remarkHashMap.containsKey(violationUuid)){
                        ViolationRemark violationRemark = DataCenter.remarkHashMap.get(violationUuid);
                        violationRemark.setReason(reason);
                        DataCenter.remarkHashMap.put(violationUuid, violationRemark);
                    }else {
                        ViolationRemark violationRemark= new ViolationRemark(DataCenter.userUuid, violationUuid, null, null, null, reason);
                        DataCenter.remarkHashMap.put(violationUuid, violationRemark);
                    }
                }
            }
        });
        issueTable.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(textField));

        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ContentUtils.getIssueData();
                    ContentUtils.getCommentData();
                    Messages.showMessageDialog("Update data successfully!","Result",Messages.getInformationIcon());
                }catch (Exception exception){
                    Messages.showMessageDialog("Update data failed!","Error",Messages.getErrorIcon());
                    exception.printStackTrace();
                    throw new RuntimeException(exception);
                }
            }
        });

        // 更新标记结果
        remarkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                List<ViolationRemark> remarkList = new ArrayList<>(DataCenter.remarkHashMap.values());
                try {
                    ContentUtils.remark(remarkList);
                    Messages.showMessageDialog("Remark successfully!","Result",Messages.getInformationIcon());
                }catch (Exception exception){
                    Messages.showMessageDialog("Remark failed!","Error",Messages.getErrorIcon());
                    exception.printStackTrace();
                    throw new RuntimeException(exception);
                }
            }
        });
    }

    public JPanel getViolationPanel() {
        return violationPanel;
    }
}
