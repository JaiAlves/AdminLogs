package com.systemnow.logs.controller;

import com.systemnow.logs.controller.response.LoadResponse;
import com.systemnow.logs.model.elastic.Config;
import com.systemnow.logs.service.elastic.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/log")
@Slf4j
public class ConfigController {
    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/config")
    public ResponseEntity<Config> getConfig() {
        Config config = configService.getConfig();

        log.info("Config: {}", config);

        return new ResponseEntity<>(config, null, HttpStatus.OK);
    }

    //sbpTpIQB3Jo9XrpC3q7z
    @GetMapping("/config/{}")
    public ResponseEntity<Config> getConfigById(@RequestParam String id) {
        Config config = configService.getById(id);

        log.info("Config: {}", config);

        if (config==null) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(config, null, HttpStatus.OK);
    }
}
