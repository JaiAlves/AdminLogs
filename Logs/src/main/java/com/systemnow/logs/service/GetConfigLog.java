package com.systemnow.logs.service;

import com.systemnow.logs.model.ConfigLog;
import org.springframework.data.domain.Page;

public interface GetConfigLog {
    Page<ConfigLog> getAll(int page, int size);

}
