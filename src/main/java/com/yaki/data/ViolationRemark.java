package com.yaki.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViolationRemark {
    private String userUuid;
    private String violationUuid;
    private Boolean isTruePositive;
    private Boolean isActionable;
    private Integer priority;
    private String reason;
}
