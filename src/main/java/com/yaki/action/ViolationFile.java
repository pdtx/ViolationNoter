package com.yaki.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.yaki.data.DataCenter;
import com.yaki.dialog.CommitInfoDialog;
import com.yaki.utils.GitInfoProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ViolationFile extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile();
        String url = virtualFile.getPath();
        String projectBasePath = Objects.requireNonNull(project).getBasePath();
        assert projectBasePath != null;
        DataCenter.file_path = url.substring(url.indexOf(projectBasePath)+projectBasePath.length()+1);
        DataCenter.repoName = projectBasePath.substring(projectBasePath.lastIndexOf("/")+1);
        DataCenter.commitId = GitInfoProvider.getGitCommit(project);

        CommitInfoDialog commitInfoDialog = new CommitInfoDialog(project);
        commitInfoDialog.show();
    }
}
