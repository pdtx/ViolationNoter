package com.yaki.utils;

import com.intellij.openapi.project.Project;
import com.intellij.vcs.log.VcsUser;
import git4idea.GitUserRegistry;
import git4idea.GitVcs;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;

import java.util.List;
import java.util.Objects;

public class GitInfoProvider {
    public static String getGitUserName(Project project) {
        GitRepositoryManager repositoryManager = GitRepositoryManager.getInstance(project);
        List<GitRepository> gitRepositories = repositoryManager.getRepositories();
        GitUserRegistry registry = GitUserRegistry.getInstance(project);
        VcsUser vcsUser = registry.getOrReadUser(gitRepositories.get(0).getRoot());
        if (vcsUser != null) return vcsUser.getName();
        return null;
    }

    public static String getGitUserEmail(Project project) {
        GitRepositoryManager repositoryManager = GitRepositoryManager.getInstance(project);
        List<GitRepository> gitRepositories = repositoryManager.getRepositories();
        GitUserRegistry registry = GitUserRegistry.getInstance(project);
        VcsUser vcsUser = registry.getOrReadUser(gitRepositories.get(0).getRoot());
        if (vcsUser != null) return vcsUser.getEmail();
        return null;
    }
}
