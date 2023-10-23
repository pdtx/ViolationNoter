package com.yaki.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.EditorTextField;
import com.yaki.data.DataCenter;
import com.yaki.utils.ContentUtils;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SetCommitIdDialog extends DialogWrapper {
    private EditorTextField  tfTitle;
    public SetCommitIdDialog() {
        super(true);
        setTitle("Commit Id");
        init();

        setSize(400, 200);
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        //new BorderLayout布局，还有其他布局方式
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Please enter the commit id you want to query:");
        tfTitle= new EditorTextField();
        tfTitle.setPlaceholder("enter the commit id");
        //设置框的大小
        panel.add(label, BorderLayout.NORTH);
        panel.add(tfTitle,BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel();
        JButton button_cancel = new JButton("Cancel");
        button_cancel.addActionListener(e -> {
            SetCommitIdDialog.this.dispose();
        });
        JButton button = new JButton("OK");
        button.addActionListener(e -> {
            button.setEnabled(false);
            button_cancel.setEnabled(false);
            button.setText("Loading");

            //获取commitId
            DataCenter.commitId = tfTitle.getText();
            if (DataCenter.commitId.equals("") || DataCenter.commitId.contains(" ")){
                Messages.showMessageDialog("Commit id is empty or invalid!","Warning",Messages.getWarningIcon());
                return;
            }
            System.out.println("扫描缺陷文件");

            String trackerUrl = DataCenter.preTracker + DataCenter.repoName +"&commit_id=" + DataCenter.commitId
                    + "&file_path=" + DataCenter.file_path + "&closed=true";

            String issueUrl = DataCenter.preUrl + DataCenter.repoName + "/issues";

            System.out.println("trackerUrl:"+trackerUrl);
            System.out.println("issueUrl:"+issueUrl);

            try {
                ContentUtils.setFileData(trackerUrl);
                ContentUtils.setIssue(issueUrl);
            } catch (Exception ex) {
                Messages.showMessageDialog("Query failed!","Error",Messages.getErrorIcon());
                button.setEnabled(true);
                button.setText("OK");
                button_cancel.setEnabled(true);
                throw new RuntimeException(ex);
            }

            button.setEnabled(true);
            button.setText("OK");
            button_cancel.setEnabled(true);
            Messages.showMessageDialog("Query ok!","Result",Messages.getInformationIcon());
            SetCommitIdDialog.this.dispose();
        });
        panel.add(button_cancel);
        panel.add(button);
        return panel;
    }

}
