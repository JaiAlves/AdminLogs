package com.systemnow.logs.service.impl;

import com.systemnow.logs.component.ApplicationProperties;
import com.systemnow.logs.model.FileContent;
import com.systemnow.logs.model.FileLoad;
import com.systemnow.logs.model.Load;
import com.systemnow.logs.model.enums.EnumTypeLog;
import com.systemnow.logs.service.ReadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReadServiceImpl implements ReadFileService {
    private final ApplicationProperties applicationProperties;

    public ReadServiceImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public List<String> doReadFiles(Load load) throws IOException {
        List<String> fileNames = new ArrayList<>();

        String pathFileLogs = applicationProperties.getPathLoadFileLogs(load);
        log.info("doReadFiles START, pathFileLogs: {}", pathFileLogs);

        File folder = new File(pathFileLogs);
        for (File file : folder.listFiles()) {
            if (!file.isDirectory()) {
                fileNames.add(file.getName());

                FileLoad fileLoad = readFile(load, file);
                load.getFileLoads().add(fileLoad);
            }
        }
        log.info("doReadFiles END");

        return fileNames;
    }

    private FileLoad readFile(Load load, File file) throws IOException {
        log.info("readFile START , fileName: {}",file.getName());
        log.info("readFile pos StartDate from: {} until {}",applicationProperties.getLayoutIniPosStartDate(), applicationProperties.getLayoutEndPosStartDate());
        log.info("readFile pos Type from: {} until {}",applicationProperties.getLayoutIniPosType(), applicationProperties.getLayoutEndPosType());


        FileLoad fileLoad = new FileLoad();
        fileLoad.setStartDate(LocalDateTime.now());
        fileLoad.setName(file.getName());
        fileLoad.setLoad(load);
        fileLoad.setFileContents(new ArrayList<>());

        FileReader fileReader = new FileReader(applicationProperties.getPathLoadFileLogs(load)+"/"+file.getName());
        BufferedReader lerArq = new BufferedReader(fileReader);

        String readLine = lerArq.readLine(); // read first line
        LocalDateTime lastDate = null;
        int lineNumber =1;
        while (readLine != null) {
            FileContent fileContent = new FileContent();
            String strDate = "";
            String type =  "";

            if (readLine.length()>= applicationProperties.getLayoutEndPosStartDate()) {
                strDate = readLine.substring(applicationProperties.getLayoutIniPosStartDate(), applicationProperties.getLayoutEndPosStartDate());
            }
            if (readLine.length()>=applicationProperties.getLayoutEndPosType()) {
                type =readLine.substring(applicationProperties.getLayoutIniPosType(), applicationProperties.getLayoutEndPosType()).trim();
            }

            LocalDateTime localDateTime = getDateFromStr(strDate);

            if (localDateTime!=null) {
                lastDate = localDateTime;
            } else {
                localDateTime = lastDate;
            }

            fileContent.setDateTime(localDateTime);
            fileContent.setText(readLine);
            fileContent.setType(EnumTypeLog.getByName(type)!=null?EnumTypeLog.getByName(type).getId():0);
            fileContent.setFileLoad(fileLoad);
            fileContent.setLine(lineNumber);

            fileLoad.getFileContents().add(fileContent);

            readLine = lerArq.readLine(); // read next line

            lineNumber++;
        }
        log.info("readFile END, fileName: {}, lineNumber {}",file.getName(), lineNumber);

        fileReader.close();

        fileLoad.setEndDate(LocalDateTime.now());

        return fileLoad;
    }

    private LocalDateTime getDateFromStr(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        try {
            return LocalDateTime.parse(strDate.trim(), formatter);    
        } catch (Exception e) {
            return null;
        }
    }
}
