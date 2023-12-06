package com.yaki.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.history.VcsRevisionNumber;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcs.log.*;
import com.intellij.vcs.log.data.VcsLogData;
import com.yaki.data.CommitInfo;
import git4idea.GitUserRegistry;
import git4idea.GitUtil;
import git4idea.history.GitHistoryUtils;
import git4idea.history.GitLogUtil;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GitInfoProvider {
    public static String getGitUserName(Project project) {
        GitRepositoryManager repositoryManager = GitUtil.getRepositoryManager(project);
        List<GitRepository> gitRepositories = repositoryManager.getRepositories();
        GitUserRegistry registry = GitUserRegistry.getInstance(project);
        if (gitRepositories.size() == 0) return null;
        VcsUser vcsUser = registry.getOrReadUser(gitRepositories.get(0).getRoot());
        if (vcsUser != null) return vcsUser.getName();
        return null;
    }

    public static String getGitUserEmail(Project project) {
        GitRepositoryManager repositoryManager = GitUtil.getRepositoryManager(project);
        List<GitRepository> gitRepositories = repositoryManager.getRepositories();
        GitUserRegistry registry = GitUserRegistry.getInstance(project);
        if (gitRepositories.size() == 0) return null;
        VcsUser vcsUser = registry.getOrReadUser(gitRepositories.get(0).getRoot());
        if (vcsUser != null) return vcsUser.getEmail();
        return null;
    }

    public static String getGitCommit(Project project){
        GitRepositoryManager manager = GitUtil.getRepositoryManager(project);
        List<GitRepository> gitRepositories = manager.getRepositories();
        if (gitRepositories.size() == 0) return null;
        GitRepository repository = gitRepositories.get(0);

        return repository.getCurrentRevision();
    }

    public static CommitInfo getGitMessage(Project project) {
        GitRepositoryManager manager = GitUtil.getRepositoryManager(project);
        List<GitRepository> gitRepositories = manager.getRepositories();
        if (gitRepositories.size() == 0) return null;
        GitRepository repository = gitRepositories.get(0);

        VirtualFile root = repository.getRoot();
        String commitHash = repository.getCurrentRevision(); // 获取当前 commit 的哈希值

        try {
            List<? extends VcsCommitMetadata> metaData = GitHistoryUtils.collectCommitsMetadata(project, root, commitHash);
            if (metaData == null) return null;
            CommitInfo commitInfo = new CommitInfo();
            commitInfo.setCommitId(commitHash);
            commitInfo.setCommitMessage(metaData.get(0).getFullMessage());
            commitInfo.setAuthorName(metaData.get(0).getAuthor().getName());
            commitInfo.setAuthorEmail(metaData.get(0).getAuthor().getEmail());
            commitInfo.setCommitTime(metaData.get(0).getCommitTime());
            return commitInfo;
        } catch (VcsException e) {
            e.printStackTrace();
        }

        return null;
    }
}
