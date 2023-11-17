package com.yaki.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.EditorTextField;
import com.yaki.data.DataCenter;
import com.yaki.utils.ContentUtils;
import com.yaki.utils.GitInfoProvider;
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Objects;

public class LoginDialog extends DialogWrapper {
    private String username = "";
    private String email = "";
    public LoginDialog(String username, String email) {
        super(true);
        setTitle("Login");
        this.username = username;
        this.email = email;
        init();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        //new BorderLayout布局，还有其他布局方式
        JPanel panel = new JPanel(new BorderLayout());

        if (Objects.equals(username, "") || Objects.equals(email, "")) {
            JLabel idLabel1 = new JLabel("Fail to get user information");
            JLabel idLabel2 = new JLabel("git username or email is empty");
            panel.add(idLabel1,BorderLayout.NORTH);
            panel.add(idLabel2,BorderLayout.CENTER);
        }else {
            JLabel idLabel1 = new JLabel("Git Username: "+username);
            JLabel idLabel2 = new JLabel("Git Email: "+email);
            panel.add(idLabel1,BorderLayout.NORTH);
            panel.add(idLabel2,BorderLayout.CENTER);
        }

        setSize(400, 100);
        return panel;
    }

    @Override
    protected JComponent createSouthPanel() {
        if (Objects.equals(username, "") || Objects.equals(email, "")) {
            return null;
        }
        JPanel panel = new JPanel();
        JButton button = new JButton("Register");
        button.addActionListener(e -> {
            try {
                ContentUtils.register(DataCenter.registerUrl, username, email);
                //添加成功弹窗
                Messages.showMessageDialog("Register successfully!","Result",Messages.getInformationIcon());
                LoginDialog.this.dispose();
            } catch (Exception ex) {
                Messages.showMessageDialog("Register fail!","Error",Messages.getErrorIcon());
                ex.printStackTrace();
            }
        });
        panel.add(button);
        return panel;
    }

}
