package com.systemnow.logs.service.impl;

import com.systemnow.logs.model.ConfigLog;
import com.systemnow.logs.model.Load;
import com.systemnow.logs.model.LoadStep;
import com.systemnow.logs.model.enums.EnumStatus;
import com.systemnow.logs.model.enums.EnumStep;
import com.systemnow.logs.repository.ConfigLogRepository;
import com.systemnow.logs.repository.LoadRepository;
import com.systemnow.logs.service.GetFileLog;
import com.systemnow.logs.service.LoadService;
import com.systemnow.logs.service.ReadFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoadServiceImpl implements LoadService {
    private final GetFileLog getFileLog;
    private final ReadFileService readFileService;
    private final LoadRepository loadRepository;
    private final ConfigLogRepository configLogRepository;
    private final JdbcTemplate jdbcTemplate;
    List<ConfigLog> configLogs;

    @Override
    @Async
    public void executeLoad(Load load, String groupName) throws Exception {
        if (groupName!=null && !groupName.isEmpty()) {
            configLogs = configLogRepository.findAllByEnumStatusIdAndGroupName(1, groupName);
        } else {
            configLogs = configLogRepository.findAllByEnumStatusId(1);
        }

        try {
            getFileLog.doGetFile(load, configLogs);
            this.closeStep(load, EnumStep.GET_FILES, EnumStatus.SUCCESS);
            load = loadRepository.save(load);
        } catch (Exception e) {
            this.closeStep(load, EnumStep.GET_FILES, EnumStatus.ERROR);
            load.setEndDate(LocalDateTime.now());
            loadRepository.save(load);

            log.error("Error doGetFiles");
            e.printStackTrace();

            throw new Exception("Error doGetFiles");
        }

        try {
            this.openStep(load, EnumStep.READ_FILES);
            load = loadRepository.save(load);

            List<String> fileNames= readFileService.doReadFiles(load);

            this.closeStep(load, EnumStep.READ_FILES, EnumStatus.SUCCESS);
        } catch (Exception e) {
            this.closeStep(load, EnumStep.READ_FILES, EnumStatus.ERROR);

            load.setEndDate(LocalDateTime.now());
            loadRepository.save(load);

            log.error("Error doReadFiles");
            e.printStackTrace();

            throw new Exception("Error doReadFiles");

        }

        load.setEndDate(LocalDateTime.now());
        loadRepository.save(load);
    }

    @Override
    @Async
    public void justGetFiles(Load load, String groupName) throws IOException {
        if (groupName!=null && !groupName.isEmpty()) {
            configLogs = configLogRepository.findAllByEnumStatusIdAndGroupName(1, groupName);
        } else {
            configLogs = configLogRepository.findAllByEnumStatusId(1);
        }

        getFileLog.doGetFile(load, configLogs);
        this.closeStep(load, EnumStep.GET_FILES, EnumStatus.SUCCESS);
        load.setEndDate(LocalDateTime.now());

        loadRepository.save(load);
    }

    @Override
    @Async
    public void justReadFiles(String loadName) throws Exception {
        //remove all contents files by loadId
        String strSql = "delete from file_content where file_id in (select id from file where load_id = (select id from load where name='"+loadName+"'))";
        log.info("cleaning file_content, strSql : {}", strSql);
        jdbcTemplate.execute(strSql);

        //remove all files by loadId
        strSql ="delete from file where load_id=(select id from load where name='"+loadName+"')";
        log.info("cleaning file, strSql : {}", strSql);
        jdbcTemplate.execute(strSql);

        //remove all steps
        strSql ="delete from load_step where load_id=(select id from load where name='"+loadName+"')";
        log.info("cleaning load_step, strSql : {}", strSql);
        jdbcTemplate.execute(strSql);

        Load load = loadRepository.findByName(loadName);
        if (load==null) {
            load= new Load();
            load.setName(loadName);
        }
        load.setStartDate(LocalDateTime.now());
        load.setEndDate(null);
        load.setFileLoads(new ArrayList<>());
        load.setLoadSteps(new ArrayList<>());
        load = loadRepository.save(load);

        try {
            this.openStep(load, EnumStep.READ_FILES);
            load = loadRepository.save(load);

            List<String> fileNames= readFileService.doReadFiles(load);

            this.closeStep(load, EnumStep.READ_FILES, EnumStatus.SUCCESS);

            load.setEndDate(LocalDateTime.now());

            loadRepository.save(load);
        } catch (Exception e) {
            this.closeStep(load, EnumStep.READ_FILES, EnumStatus.ERROR);

            load.setEndDate(LocalDateTime.now());
            loadRepository.save(load);

            log.error("Error doReadFiles");
            e.printStackTrace();

            throw new Exception("Error doReadFiles");
        }
    }

    @Override
    public Load getByName(String name) {
        return loadRepository.findByName(name);
    }

    @Override
    public List<Load> findAll() {
        return loadRepository.findAll();
    }

    @Override
    public Page<Load> findPageLoad(int page, int size) {
        return loadRepository.findAll(PageRequest.of(page, size));
    }

    private void closeStep(Load load, EnumStep enumStep, EnumStatus enumStatus) {
        LoadStep loadStep = load.getByStep(enumStep);
        if (loadStep!=null) {
            loadStep.setStatus(enumStatus.getId());
            loadStep.setEndDate(LocalDateTime.now());
        }
    }

    private void openStep(Load load, EnumStep enumStep) {
        LoadStep loadStep = new LoadStep();
        loadStep.setStep(enumStep.getId());
        loadStep.setStartDate(LocalDateTime.now());
        loadStep.setLoad(load);

        if (load.getLoadSteps()==null) {
            load.setLoadSteps(new ArrayList<>());
        }
        load.getLoadSteps().add(loadStep);
    }

    @Override
    public Load startLoad(String groupName) {
        String uuid=UUID.randomUUID().toString();
        String name =groupName!=null?groupName+uuid:uuid;
        Load load = new Load();
        load.setFileLoads(new ArrayList<>());
        load.setName(name);
        load.setStartDate(LocalDateTime.now());
        this.openStep(load, EnumStep.GET_FILES);

        return loadRepository.save(load);
    }
}
