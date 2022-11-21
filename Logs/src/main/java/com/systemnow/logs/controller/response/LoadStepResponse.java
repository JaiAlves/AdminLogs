package com.systemnow.logs.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class LoadStepResponse {
    private String step;
    private String status;
    private LocalDateTime startDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime endDate;
}
