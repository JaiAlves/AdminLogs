package com.systemnow.logs.controller;

import com.systemnow.logs.controller.response.ConfigLogResponse;
import com.systemnow.logs.controller.response.LoadResponse;
import com.systemnow.logs.converter.ConfigLogConvert;
import com.systemnow.logs.model.ConfigLog;
import com.systemnow.logs.model.elastic.Config;
import com.systemnow.logs.service.GetConfigLog;
import com.systemnow.logs.service.GetFileLog;
import com.systemnow.logs.service.elastic.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/log")
@Slf4j
public class ConfigController {
    private final ConfigService configService;
    private final GetConfigLog getConfigLog;
    private final ConfigLogConvert configLogConvert;

    public ConfigController(ConfigService configService, GetFileLog getFileLog, GetConfigLog getConfigLog, ConfigLogConvert configLogConvert) {
        this.configService = configService;
        this.getConfigLog = getConfigLog;
        this.configLogConvert = configLogConvert;
    }

    @GetMapping("/config")
    public ResponseEntity<Config> getConfig() {
        Config config = configService.getConfig();

        log.info("Config: {}", config);

        return new ResponseEntity<>(config, null, HttpStatus.OK);
    }

    @GetMapping("/config/log")
    public ResponseEntity<Page<ConfigLogResponse>> getConfigLog(@RequestParam(required = false, defaultValue = "0") int page,
                                                                @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("get config/log by pageable");

        Page<ConfigLog> configLogPage = getConfigLog.getAll(page, size);
        Page<ConfigLogResponse> configLogResponsePage = configLogConvert.to(configLogPage);

        if (configLogResponsePage==null || configLogResponsePage.isEmpty()) {
            return new ResponseEntity<Page<ConfigLogResponse>>(null, null, HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Page<ConfigLogResponse>>(configLogResponsePage, null, HttpStatus.OK);

    }

    //sbpTpIQB3Jo9XrpC3q7z
    @GetMapping("/config/{id}")
    public ResponseEntity<Config> getConfigById(@PathVariable String id) {
        Config config = configService.getById(id);

        log.info("Config by id: {}, config: {}", id, config);

        if (config==null) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(config, null, HttpStatus.OK);
    }
}
