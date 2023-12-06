package com.yaki.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationIssue {
    private String violationUuid;
    private String type;
    private String category;
    private String producer;
    private String produceCommit;
    private String produceCommitTime;
    private String solver;
    private String solverCommit;
    private String solverCommitTime;
    private String lastCommit;
    private String lastCommitTime;
    private String status;
    private String versions;
    private List<ViolationLocation> locations;
    private String currCommit;
    private String currFilePath;
    private String currRawIssueUuid;
    private String currRawIssueDetail;
    private String markIds;
    private Boolean isTruePositiveAggregated;
    private Boolean isActionableAggregated;
    private Integer priorityAggregated;
    private String truePositiveConsensusScore;
    private String actionableConsensusScore;
    private String priorityConsensusScore;
    private String updatedAt;
}
