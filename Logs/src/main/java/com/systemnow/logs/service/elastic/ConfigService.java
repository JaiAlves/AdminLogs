package com.systemnow.logs.service.elastic;

import com.systemnow.logs.model.elastic.Config;
import com.systemnow.logs.repository.elastic.ConfigElasticRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {
    private final ConfigElasticRepository configElasticRepository;

    public ConfigService(ConfigElasticRepository configElasticRepository) {
        this.configElasticRepository = configElasticRepository;
    }

    public Config getConfig() {
        Config config =null;
        try {
            config =configElasticRepository.findAll().iterator().next();
        } catch (Exception e) {
            if (config==null) {
                config = new Config();
                config.setNameSpace("phxl");
                config = configElasticRepository.save(config);
            }
        }
        return config;
    }

    public Config getById(String id) {
        return configElasticRepository.findById(id).get();
    }
}
