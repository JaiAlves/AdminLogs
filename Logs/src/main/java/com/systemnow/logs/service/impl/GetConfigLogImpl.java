package com.systemnow.logs.service.impl;

import com.systemnow.logs.model.ConfigLog;
import com.systemnow.logs.model.Load;
import com.systemnow.logs.repository.ConfigLogRepository;
import com.systemnow.logs.service.GetConfigLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class GetConfigLogImpl implements GetConfigLog {
    private final ConfigLogRepository configLogRepository;

    public GetConfigLogImpl(ConfigLogRepository configLogRepository) {
        this.configLogRepository = configLogRepository;
    }

    @Override
    public Page<ConfigLog> getAll(int page, int size) {
        return configLogRepository.findAll(PageRequest.of(page, size));
    }

    /*
    ConfigLogConvert
     */
}
