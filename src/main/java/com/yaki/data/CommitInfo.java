package com.yaki.data;

import com.intellij.openapi.util.NlsSafe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitInfo {
    private String commitId;
    private String commitMessage;
    private String authorName;
    private String authorEmail;
    private long commitTime;
}
