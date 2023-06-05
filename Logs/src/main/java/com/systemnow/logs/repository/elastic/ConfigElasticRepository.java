package com.systemnow.logs.repository.elastic;

import com.systemnow.logs.model.elastic.Config;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ConfigElasticRepository extends ElasticsearchRepository<Config, String> {
}
