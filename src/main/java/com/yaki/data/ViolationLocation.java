package com.yaki.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationLocation {
    private int startLine;
    private int endLine;
    private int startToken;
    private int endToken;
    private String filePath;
    private String anchorName;
    private String code;
    private int offset;
}
