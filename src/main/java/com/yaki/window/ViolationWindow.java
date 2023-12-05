package com.yaki.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.yaki.data.DataCenter;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public ViolationWindow(Project project, ToolWindow toolWindow) {
        init();
    }

    private void init() {
        issueTable.setModel(DataCenter.VIOLATION_ISSUE_MODEL);

        commentTable.setModel(DataCenter.VIOLATION_COMMENT_MODEL);

        Vector<String> vector1 = new Vector<String>();
        vector1.add("TP");
        vector1.add("FP");

        final JComboBox<String> comboBox1 = new JComboBox<String>(vector1);
        //下拉框监听
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(comboBox1.getSelectedItem());
                }
            }
        });
        issueTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox1));

        // step7: 更新标记结果
        remarkButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO: 调用接口更新标记结果
            }
        });
    }

    public JPanel getViolationPanel() {
        return violationPanel;
    }
}
