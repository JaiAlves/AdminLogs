package com.systemnow.logs.service.elastic;

import com.systemnow.logs.model.elastic.Load;

public interface LoadElasticService {
    Load startLoad(String groupName);
}
