package com.yaki.window;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.yaki.data.DataCenter;
import com.yaki.dialog.CommitInfoDialog;
import com.yaki.dialog.LoginDialog;
import com.yaki.utils.GitInfoProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

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
            toolWindow.hide();
            // 弹出登录弹窗，登录（获取git用户名和邮箱-->调用接口注册-->本地缓存uuid）
            String username = GitInfoProvider.getGitUserName(project);
            String email = GitInfoProvider.getGitUserEmail(project);
            if(username != null && email != null){
                LoginDialog loginDialog = new LoginDialog(username, email);
                loginDialog.show();
            }
        }else {
            // 获取当前打开文件的数据
            FileEditorManager fileEditorManager = FileEditorManager.getInstance(project);
            VirtualFile virtualFile = Objects.requireNonNull(fileEditorManager.getSelectedEditor()).getFile();
            String url = virtualFile.getPath();
            String projectBasePath = Objects.requireNonNull(project).getBasePath();
            assert projectBasePath != null;
            DataCenter.file_path = url.substring(url.indexOf(projectBasePath)+projectBasePath.length()+1);
            DataCenter.repoName = projectBasePath.substring(projectBasePath.lastIndexOf("/")+1);
            DataCenter.commitId = GitInfoProvider.getGitCommit(project);

            if (DataCenter.file_path.endsWith(".java")) {
                CommitInfoDialog commitInfoDialog = new CommitInfoDialog(project);
                commitInfoDialog.show();
            }
        }
    }
}
