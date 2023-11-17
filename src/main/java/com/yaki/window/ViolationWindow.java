package com.yaki.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.yaki.data.DataCenter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        issueTable.setEnabled(true);

        commentTable.setModel(DataCenter.VIOLATION_COMMENT_MODEL);

        // step1: 登录

        // step2: 选择指定版本指定文件
        // TODO

        // step3: 获取缺陷和评论信息
        // TODO

        // step4: 获取commit message
        // TODO

        // step5: 标记缺陷数据
        // TODO

        // step6: 关联评论/commit message
        // TODO

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
