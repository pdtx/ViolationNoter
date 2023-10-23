package com.yaki.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.EditorTextField;
import com.yaki.data.DataCenter;
import com.yaki.data.DataConvert;
import com.yaki.data.NoteData;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class AddNoteDialog extends DialogWrapper {
    private EditorTextField  tfTitle;
    private EditorTextField tfMark;
    public AddNoteDialog() {
        super(true);
        setTitle("Add Note");
        setSize(400, 250);
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        //new BorderLayout布局，还有其他布局方式
        JPanel panel = new JPanel(new BorderLayout());

        JPanel panel_title = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Please enter the title of the note:");
        tfTitle= new EditorTextField();
        tfTitle.setPlaceholder("note title");
        panel_title.add(label1, BorderLayout.NORTH);
        panel_title.add(tfTitle, BorderLayout.CENTER);

        JPanel panel_content = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Please enter the content of the note:");
        tfMark = new EditorTextField();
        tfMark.setPlaceholder("note content");
        tfMark.setPreferredSize(new Dimension(200,100));
        panel_content.add(label2, BorderLayout.NORTH);
        panel_content.add(tfMark, BorderLayout.CENTER);

        panel.add(panel_title,BorderLayout.NORTH);
        panel.add(panel_content,BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("Add to NoteList");
        button.addActionListener(e -> {
            String title = tfTitle.getText();
            String mark = tfMark.getText();
            //获取文件后缀
            String fileType = DataCenter.FILE_NAME.substring(DataCenter.FILE_NAME.lastIndexOf(".")+1);
            NoteData noteData = new NoteData(title, mark, DataCenter.SELECT_TXT, DataCenter.FILE_NAME, fileType);
            DataCenter.NOTE_LIST.add(noteData);
            DataCenter.TABLE_MODEL.addRow(DataConvert.convert(noteData));
            //添加笔记成功弹窗
            Messages.showMessageDialog("Add successfully","Result",Messages.getInformationIcon());


            AddNoteDialog.this.dispose();

        });
        panel.add(button);
        return panel;
    }
}
