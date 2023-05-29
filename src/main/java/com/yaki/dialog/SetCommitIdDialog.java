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
        setTitle("Enter the commit id");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        //new BorderLayout布局，还有其他布局方式
        JPanel panel = new JPanel(new BorderLayout());
        tfTitle= new EditorTextField("commit id");
        //设置框的大小
        panel.add(tfTitle,BorderLayout.NORTH);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("ok");
        button.addActionListener(e -> {
            //获取commitId
            String title = tfTitle.getText();
            DataCenter.commitId = title;
            System.out.println("扫描缺陷文件");

            String trackerUrl = DataCenter.preTracker + DataCenter.repoName +"&commit_id=" + DataCenter.commitId
                    + "&file_path=" + DataCenter.file_path + "&closed=true";

            String issueUrl = DataCenter.preUrl + DataCenter.repoName + "/issues";

            System.out.println("trackerUrl:"+trackerUrl);
            System.out.println("issueUrl:"+issueUrl);


            try {
                ContentUtils.setFileData(trackerUrl);
                ContentUtils.setIssue(issueUrl);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            //添加成功弹窗
            Messages.showMessageDialog("query ok!","Result",Messages.getInformationIcon());
            SetCommitIdDialog.this.dispose();

        });
        panel.add(button);
        return panel;
    }

}
