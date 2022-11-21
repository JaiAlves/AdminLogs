package com.systemnow.logs.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoadResponse {
    private String name;
    private LocalDateTime startDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime endDate;
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<LoadStepResponse> steps;
}
