package com.systemnow.logs.service.elastic.impl;

import com.systemnow.logs.model.elastic.Load;
import com.systemnow.logs.repository.elastic.LoadElasticRepository;
import com.systemnow.logs.service.elastic.LoadElasticService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class LoadElasticServiceImpl implements LoadElasticService {
    private final LoadElasticRepository loadElasticRepository;

    public LoadElasticServiceImpl(com.systemnow.logs.repository.elastic.LoadElasticRepository loadElasticRepository) {
        this.loadElasticRepository = loadElasticRepository;
    }

    @Override
    public Load startLoad(String groupName) {
        String uuid= UUID.randomUUID().toString();
        String name =groupName!=null?groupName+uuid:uuid;
        Load load = new Load();
        load.setFileLoads(new ArrayList<>());
        load.setName(name);
        load.setStartDate(LocalDateTime.now());
        //this.openStep(load, EnumStep.GET_FILES);

        return loadElasticRepository.save(load);
    }

}
