package com.systemnow.logs.controller.v2;

import com.systemnow.logs.controller.response.LoadResponse;
import com.systemnow.logs.converter.LoadConvert;
import com.systemnow.logs.model.elastic.Load;
import com.systemnow.logs.service.elastic.LoadElasticService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/v2/log")
public class LoadControllerV2 {
    private final LoadElasticService loadElasticService;
    private final LoadConvert loadConvert;

    public LoadControllerV2(LoadElasticService loadElasticService, LoadConvert loadConvert) {
        this.loadElasticService = loadElasticService;
        this.loadConvert = loadConvert;
    }


    /**
     * Create load
     * @param groupName
     * @return created Load
     */
    @ApiOperation(value = "Create load, Param: Group name ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Load.class),
            @ApiResponse(code = 500, message = "Internal server error")})
    @PostMapping("/load/create")
    public ResponseEntity<?> createLoadByGroupName(@RequestParam(required = false) String groupName) {
        log.info("createLoadByGroupName START");

        Load load = loadElasticService.startLoad(groupName);
        LoadResponse loadResponse = loadConvert.to(load);
        String loadName= load.getName();

        log.info("createLoadByGroupName END, load: {}", load);

        return new ResponseEntity<>(loadResponse, null, HttpStatus.OK);
    }

}
