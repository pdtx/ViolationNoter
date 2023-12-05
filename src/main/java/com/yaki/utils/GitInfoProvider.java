package com.yaki.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcs.log.VcsUser;
import git4idea.GitUserRegistry;
import git4idea.GitUtil;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
}
