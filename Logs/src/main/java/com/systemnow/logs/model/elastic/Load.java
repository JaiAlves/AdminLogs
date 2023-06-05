package com.systemnow.logs.model.elastic;

import com.systemnow.logs.model.FileLoad;
import com.systemnow.logs.model.LoadStep;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "load_idx")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Load {
    @Id
    private String id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<FileLoad> fileLoads;
    private List<LoadStep> loadSteps;
}
