package com.systemnow.logs.controller;

import com.systemnow.logs.controller.response.LoadResponse;
import com.systemnow.logs.converter.LoadConvert;
import com.systemnow.logs.model.Load;
import com.systemnow.logs.service.LoadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

@EnableAsync
@Slf4j
@RestController
@RequestMapping(path = "/log")
public class LogController {
    private final LoadService loadService;
    private final LoadConvert loadConvert;

    public LogController(LoadService loadService, LoadConvert loadConvert) {
        this.loadService = loadService;
        this.loadConvert = loadConvert;
    }

    /**
     * Get file logs from pods
     * @param groupName
     * @return name of created Load
     */
    @PostMapping("/getFiles")
    public ResponseEntity<String> getFiles(@RequestParam(required = false) String groupName) {
        log.info("getFiles START");
        Load load = loadService.startLoad(groupName);
        String loadName= load.getName();
        try {
            loadService.justGetFiles(load, groupName);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("ERROR, load: "+loadName, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("getFiles END");

        return new ResponseEntity<>("load : "+loadName+ " executing!", null, HttpStatus.OK);
    }

    /**
     * Read file from folder name and save to database.
     * @param name
     * @return name of load
     */
    @PostMapping("/readFiles")
    public ResponseEntity<String> doReadFiles(@RequestParam String name) {
        log.info("doReadFiles START");

        try {
            loadService.justReadFiles(name);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("ERROR, load: "+name, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("doReadFiles END");

        return new ResponseEntity<>("load: "+name +" executing!", null, HttpStatus.OK);
    }

    /**
     * Do the complete load (get files, read files and save content to database)
     * @param groupName
     * @return name of created Load
     */
    @PostMapping("/load")
    public ResponseEntity<String> completeLoad(@RequestParam(required = false) String groupName) {
        log.info("Load START");
        String loadName=null;
        Load load = loadService.startLoad(groupName);
        loadName = load.getName();
        try {
            loadService.executeLoad(load, groupName);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>("ERROR, load : "+loadName, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Load END");

        return new ResponseEntity<>("load : "+loadName +" executing!", null, HttpStatus.OK);
    }

    @GetMapping("/load/{name}")
    public ResponseEntity<LoadResponse> getLoad(@PathVariable String name) {
        log.info("getLoad by loadName: {}", name);
        Load load = loadService.getByName(name);
        LoadResponse loadResponse = loadConvert.to(load);

        if (loadResponse==null) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(loadResponse, null, HttpStatus.OK);
    }

    @GetMapping("/load")
    public ResponseEntity<Page<LoadResponse>> getAllLoad(@RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        log.info("getAllLoad");

        Page<Load> loadPage = loadService.findPageLoad(page, size);
        Page pageLoadResponse = loadConvert.to(loadPage);

        if (loadPage==null || loadPage.isEmpty()) {
            return new ResponseEntity<Page<LoadResponse>>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Page<LoadResponse>>(pageLoadResponse, null, HttpStatus.OK);
    }

}
