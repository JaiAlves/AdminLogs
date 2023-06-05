package com.systemnow.logs.controller.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ConfigLogResponse {
    private String groupName;
    private String startPodName;
    private String pathLog;
    private String exceptStartPodName;
    private String logName;
    private String nameSpace;
    private Integer enumStatusId;
}
