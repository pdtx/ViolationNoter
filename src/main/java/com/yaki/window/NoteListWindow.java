package com.yaki.window;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.yaki.data.DataCenter;
import com.yaki.data.NoteData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static com.yaki.data.DataCenter.sourceNoteData;

public class NoteListWindow {
    private JButton btnCreate;
    private JButton btnClear;
    private JButton btnClose;
    private JTable tbContent;
    private JLabel tfTopic;
    private JPanel contentPanel;
    private JTextField textField1;

    List<NoteData> noteList = DataCenter.NOTE_LIST;
    private void init(){
        tbContent.setModel(DataCenter.TABLE_MODEL);
        tbContent.setEnabled(true);
    }

    public NoteListWindow(Project project, ToolWindow toolWindow) {
        init();
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String topic = textField1.getText();
                String fileName = topic+".md";
                if(topic==null||"".equals(topic)){
                    Messages.showMessageDialog("Title is empty", "Result",Messages.getWarningIcon());
                    return;
                }

                //获取用户选择的路径
                VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileDescriptor(),project,project.getBaseDir());
                if(virtualFile!=null){
                    String path = virtualFile.getPath();
                    String fileFullPath = path+"/"+fileName;


                    try {
                        Path real_path = Paths.get(fileFullPath);
                        // 使用newBufferedWriter创建文件并写文件
                        // 这里使用了try-with-resources方法来关闭流，不用手动关闭
                        try (BufferedWriter writer =
                                     Files.newBufferedWriter(real_path, StandardCharsets.UTF_8)) {
                            writer.write("## "+topic+"\n[TOC]");
//                            writer.write("Hello World -创建文件!!");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        //追加写模式
                        try (BufferedWriter writer =
                                     Files.newBufferedWriter(real_path,
                                             StandardCharsets.UTF_8,
                                             StandardOpenOption.APPEND)){

                            for (NoteData note : noteList) {
                                writer.write("\n### " + note.getTitle());
                                writer.write("\n- " + note.getMark());
                                writer.write("\n- " + note.getFileName());
                                writer.write("\n```" + note.getFileType() + "\n");
                                writer.write(note.getContent()  + "\n```");
                            }
                        }

                        //生成文件成功弹窗
                        Messages.showMessageDialog(project,"Save successfully", "Result",Messages.getInformationIcon());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                }
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                DataCenter.reset();
            }
        });
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                toolWindow.hide();
            }
        });
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}

//public class NoteListWindow {
//    private JButton btnCreate;
//    private JButton btnClear;
//    private JButton btnClose;
//    private JTable tbContent;
//    private JLabel tfTopic;
//    private JPanel contentPanel;
//    private JTextField textField1;
//
//    List<NoteData> noteList = DataCenter.NOTE_LIST;
//    private void init(){
//        tbContent.setModel(DataCenter.TABLE_MODEL);
//        tbContent.setEnabled(true);
//    }
//
//    public NoteListWindow(Project project, ToolWindow toolWindow) {
//        init();
//        btnCreate.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                String topic = textField1.getText();
//                String fileName = topic+".md";
//                if(topic==null||"".equals(topic)){
//                    Messages.showMessageDialog("文档标题没有输入", "操作结果",Messages.getInformationIcon());
//                    return;
//                }
//
//                //获取用户选择的路径
//                VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFileDescriptor(),project,project.getBaseDir());
//                if(virtualFile!=null){
//                    String path = virtualFile.getPath();
//                    String fileFullPath = path+"/"+fileName;
//
//
//                    try {
//                        Path real_path = Paths.get(fileFullPath);
//                        // 使用newBufferedWriter创建文件并写文件
//                        // 这里使用了try-with-resources方法来关闭流，不用手动关闭
//                        try (BufferedWriter writer =
//                                     Files.newBufferedWriter(real_path, StandardCharsets.UTF_8)) {
//                            writer.write("## "+topic+"\n[TOC]");
////                            writer.write("Hello World -创建文件!!");
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//
//                        //追加写模式
//                        try (BufferedWriter writer =
//                                     Files.newBufferedWriter(real_path,
//                                             StandardCharsets.UTF_8,
//                                             StandardOpenOption.APPEND)){
//
//                            for (NoteData note : noteList) {
//                                writer.write("\n### " + note.getTitle());
//                                writer.write("\n- " + note.getMark());
//                                writer.write("\n- " + note.getFileName());
//                                writer.write("\n```" + note.getFileType() + "\n");
//                                writer.write(note.getContent()  + "\n```");
//                            }
//                        }
//
//                        //生成文件成功弹窗
//                        Messages.showMessageDialog(project,"生成文档成功", "操作结果",Messages.getInformationIcon());
//                    } catch (Exception exception) {
//                        exception.printStackTrace();
//                    }
//
//                }
//            }
//        });
//        btnClear.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                DataCenter.reset();
//            }
//        });
//        btnClose.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                toolWindow.hide();
//            }
//        });
//    }
//
//    public JPanel getContentPanel() {
//        return contentPanel;
//    }
//}
