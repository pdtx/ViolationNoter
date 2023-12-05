package com.yaki.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationComment {
    private String uuid;
    private String repoUuid;
    private String violationUuid;
    private String url;
    private String developer;
    private String commentCommitId;
    private String body;
    private String filePath;
    private String createdAt;
    private String updateAt;
    private int startLine;
    private int endLine;
    private int realLine;
}
