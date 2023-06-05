package com.systemnow.logs.model.elastic;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;



@Document(indexName = "config_idx")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Config {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "name_space")
    private String nameSpace;
}
