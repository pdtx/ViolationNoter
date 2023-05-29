package com.yaki.window;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.yaki.data.DataCenter;
import com.yaki.data.FileData;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class ScanListWindow {
    private JTable tbFileContent;
    private JPanel scanPanel;
    private JTabbedPane tabbedPane;
    private JScrollPane jpCommit;
    private JTable tbCommitContent;
    private JScrollPane jpIssue;
    private JPanel jpInfo;
    private JScrollPane detailPanel;
    private JTextPane detailPane;
    private JTable issueTable;
    private JButton clearButton;
    private JButton saveButton;
    public JLabel issueLabel;
    private JList issueList;
    private JLabel detailLabel;
    private JTextPane repairDetail;
    private JTextPane remark;
    private void init(){

        tbFileContent.setModel(DataCenter.SCANFILE_MODEL);//绑定数据模型
        tbFileContent.setEnabled(true);

        tbCommitContent.setModel(DataCenter.COMMIT_MODEL);//绑定数据模型
        tbCommitContent.setEnabled(true);

        issueTable.setModel(DataCenter.ISSUE_MODEL);//绑定数据模型
        issueTable.setEnabled(true);

        tbFileContent.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int c=tbFileContent.getSelectedColumn();
                int r=tbFileContent.getSelectedRow();
                String key = (String) tbFileContent.getValueAt(0, c);
                if(c!=-1&&r!=-1)
                    //获取鼠标行列
                    System.out.println("行:" + r + ",列:"+c);
                detailPane.setText(DataCenter.detailList.get(r));
            }
        });



        /**
         * 引入TP/FP
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
                    System.out.println(comboBox1.getSelectedItem());
                }
            }
        });
        //表格编辑器
        tbFileContent.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBox1));

        /**
         * 修复TP/FP
         */
        Vector<String> vectorFix = new Vector<String>();
        vectorFix.add("TP");
        vectorFix.add("FP");

        final JComboBox<String> comboBox2 = new JComboBox<String>(vectorFix);
        //下拉框监听
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(comboBox2.getSelectedItem());
                }
            }
        });
        //表格编辑器
        tbFileContent.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(comboBox1));


        /**
         * actionable
         */
        Vector<String> vector2 = new Vector<String>();
        vector2.add("Actionable");
        vector2.add("Non-actionable");

        final JComboBox<String> comboBox = new JComboBox<String>(vector2);
        //下拉框监听
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(comboBox.getSelectedItem());
                }
            }
        });
        //表格编辑器
        tbFileContent.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(comboBox));

        /**
         * 优先级
         */
        Vector<String> vectorPrior = new Vector<String>();
        for (int i = 1; i <= 6; i++) {
            vectorPrior.add(i+"");
        }

        final JComboBox<String> comboBoxPrior = new JComboBox<String>(vectorPrior);
        //下拉框监听
        comboBoxPrior.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println(comboBoxPrior.getSelectedItem());
                }
            }
        });
        //表格编辑器
        tbFileContent.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(comboBoxPrior));

        /**
         * clear
         */
        clearButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                vector1.clear();
                vector2.clear();
                vectorPrior.clear();
            }
        });

        /**
         * 导出为csv格式
         */

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = "export.csv";
                try {
                    // 写入 CSV 文件
                    FileWriter fileWriter = new FileWriter(fileName);
                    BufferedWriter writer = new BufferedWriter(fileWriter);
                    writer.write("FilePath,Line,Type,Message,引入TP/FP,修复TP/FP,Actionable/Non-actionable,Priority,Repair Details,Remark\n");
                    for (int i =0;i<DataCenter.fileDataList.size();i++) {
                        FileData data = DataCenter.fileDataList.get(i);
                        writer.write(data.getCurrFilePath() + "," +data.getLine() + "," +data.getCategory() +"," +data.getType());
                        for(int j=4;j<=9;j++){
                          writer.write(","+tbFileContent.getValueAt(i,j));
                        }
                        writer.write("\n");
                    }
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


    }


    public ScanListWindow(Project project,ToolWindow toolWindow){
        init();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getScanPanel() {
        return scanPanel;
    }
    public JLabel getDetailLabel(){
        return detailLabel;
    }

    public JTable getTbFileContent() {
        return tbFileContent;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }


    public JTable getTbCommitContent() {
        return tbCommitContent;
    }
    public JList getIssueList(){return issueList;}
    public JLabel getIssueLabel(){return issueLabel;}
}
