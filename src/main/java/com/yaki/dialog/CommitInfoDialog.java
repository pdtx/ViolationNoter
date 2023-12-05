package com.yaki.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.EditorTextField;
import com.yaki.data.DataCenter;
import com.yaki.utils.ContentUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class CommitInfoDialog extends DialogWrapper {
    public CommitInfoDialog(@Nullable Project project) {
        super(project);
        setTitle("Commit Info");
        init();

        setSize(400, 200);
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Current Commit Id:"+DataCenter.commitId);
        JLabel label2 = new JLabel("If you want to change the commit id, please use git checkout manually!");
        //设置框的大小
        panel.add(label1, BorderLayout.NORTH);
        panel.add(label2,BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("OK");
        button.addActionListener(e -> {
            button.setEnabled(false);
            try {
                ContentUtils.getIssueData();
                ContentUtils.getCommentData();
                Messages.showMessageDialog("Query ok!","Result",Messages.getInformationIcon());
                button.setEnabled(true);
                CommitInfoDialog.this.dispose();
            }catch (Exception exception){
                Messages.showMessageDialog("Query failed!","Error",Messages.getErrorIcon());
                button.setEnabled(true);
                exception.printStackTrace();
                throw new RuntimeException(exception);
            }
        });
        panel.add(button);
        return panel;
    }
}
