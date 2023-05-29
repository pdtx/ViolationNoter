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
        setTitle("添加笔记");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        //new BorderLayout布局，还有其他布局方式
        JPanel panel = new JPanel(new BorderLayout());
        tfTitle= new EditorTextField("笔记标题");
        tfMark = new EditorTextField("笔记内容");
        //设置框的大小
        tfMark.setPreferredSize(new Dimension(200,100));
        panel.add(tfTitle,BorderLayout.NORTH);
        panel.add(tfMark,BorderLayout.CENTER);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("添加到笔记列表");
        button.addActionListener(e -> {
            String title = tfTitle.getText();
            String mark = tfMark.getText();
            //获取文件后缀
            String fileType = DataCenter.FILE_NAME.substring(DataCenter.FILE_NAME.lastIndexOf(".")+1);
            NoteData noteData = new NoteData(title, mark, DataCenter.SELECT_TXT, DataCenter.FILE_NAME, fileType);
            DataCenter.NOTE_LIST.add(noteData);
            DataCenter.TABLE_MODEL.addRow(DataConvert.convert(noteData));
            //添加笔记成功弹窗
            Messages.showMessageDialog("添加成功","操作结果",Messages.getInformationIcon());


            AddNoteDialog.this.dispose();

        });
        panel.add(button);
        return panel;
    }
}
