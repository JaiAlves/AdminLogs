package com.systemnow.logs.repository.elastic;

import com.systemnow.logs.model.elastic.Load;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LoadElasticRepository extends ElasticsearchRepository<Load, String> {
}
