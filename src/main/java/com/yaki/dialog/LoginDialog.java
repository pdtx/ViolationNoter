package com.yaki.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.EditorTextField;
import com.yaki.data.DataCenter;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginDialog extends DialogWrapper {
    private EditorTextField idText;
    private EditorTextField passText;
    private JLabel idLabel;
    private JLabel passLabel;
    public LoginDialog() {
        super(true);
        setTitle("Login");
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        //new BorderLayout布局，还有其他布局方式
        JPanel panel = new JPanel(new BorderLayout());

        idText= new EditorTextField("");
        idLabel=new JLabel("id");
        passLabel = new JLabel("pass");
        //设置框的大小
        panel.add(idLabel,BorderLayout.WEST);
        panel.add(idText,BorderLayout.NORTH);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel panel = new JPanel();
        JButton button = new JButton("ok");
        button.addActionListener(e -> {
            //获取id,pass
            String url = "jdbc:mysql://localhost:3306/violation_benchmark/";
            String user = "root";
            String password = "password";
            String id = idText.getText();
            String pass = passText.getText();

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                Statement stmt = conn.createStatement();
                String sql = "SELECT annotator.pass FROM annotator where annotator.annotator_id="+id;
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    if (pass.equals(String.valueOf(rs))){
                        DataCenter.login = true;//登录成功
                    }else{
                        System.out.println("正在为您注册……");
                    }
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException exception) {
                System.out.println("Error querying MySQL database");
                exception.printStackTrace();
            }

            //添加成功弹窗
            Messages.showMessageDialog("query ok!","Result",Messages.getInformationIcon());
            LoginDialog.this.dispose();

        });
        panel.add(button);
        return panel;
    }

}
