package com.yaki.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.yaki.data.DataCenter;
import com.yaki.dialog.LoginDialog;
import com.yaki.utils.GitInfoProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ViolationWindowManagerListener implements ToolWindowManagerListener {
    private final Project project;

    public ViolationWindowManagerListener(Project project) {
        this.project = project;
    }

    // 工具栏窗口被打开展示的时候
    @Override
    public void toolWindowShown(ToolWindow toolWindow) {
        if (!toolWindow.getId().equals("ViolationWindow")) {
            return;
        }

        // 检查是否登录
        if (!DataCenter.login){
            // 弹出登录弹窗，登录（获取git用户名和邮箱-->调用接口注册-->本地缓存uuid）
            String username = GitInfoProvider.getGitUserName(project);
            String email = GitInfoProvider.getGitUserEmail(project);
            LoginDialog loginDialog = new LoginDialog(username, email);
            loginDialog.show();
        }
    }

    // 首次打开的时候进行注册，在注册的时候同步一次数据即可
    @Override
    public void toolWindowsRegistered(List<String> ids, @NotNull ToolWindowManager toolWindowManager) {
        if (ids.contains("ViolationWindow")) {
            // TODO：初始化数据
        }
    }
}
