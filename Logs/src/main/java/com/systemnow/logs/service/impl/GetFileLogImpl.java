package com.systemnow.logs.service.impl;

import com.systemnow.logs.component.ApplicationProperties;
import com.systemnow.logs.model.ConfigLog;
import com.systemnow.logs.model.Load;
import com.systemnow.logs.service.GetFileLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
public class GetFileLogImpl implements GetFileLog {
    private final ApplicationProperties applicationProperties;
    private String defaultNameSpace;

    public GetFileLogImpl(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        //TODO get from Config
        defaultNameSpace="phxl";
    }

    @Override
    public void doGetFile(Load load, List<ConfigLog> configLogs) throws IOException {
        log.info("GetFileLog.execute START");

        createLoadFolder(load);

        if (configLogs!=null && !configLogs.isEmpty()) {
            configLogs.forEach(c-> {
                getFileFromPod(load, c);
            });
        }

        log.info("GetFileLog.execute END");
    }

    private void createLoadFolder(Load load) throws IOException {
        String folderName = applicationProperties.getPathLoadFileLogs(load);
        Path path = Paths.get(folderName);
        Files.createDirectory(path);
    }

    private void getFileFromPod(Load load, ConfigLog configLog) {
        log.info("getFileFromPod START");

        String nameSpace="";
        String pathLog = configLog.getPathLog();
        String logName = configLog.getLogName();
        String startPodName = configLog.getStartPodName();
        String exceptStartPodName = configLog.getExceptStartPodName();

        if (configLog.getNameSpace()!=null && !configLog.getNameSpace().isEmpty()) {
            nameSpace = configLog.getNameSpace();
        } else {
            nameSpace = defaultNameSpace;
        }

        log.info("getFileFromPod nameSpace: {}",nameSpace);
        log.info("getFileFromPod pathLog: {}",pathLog);
        log.info("getFileFromPod logName: {}",logName);
        log.info("getFileFromPod startPodName: {}",startPodName);
        log.info("getFileFromPod exceptStartPodName: {}",exceptStartPodName);

        try {
            int i=1;

            for (String pod: getPodsByListPodsCommand(nameSpace, startPodName, exceptStartPodName)) {
                String command ="";
                if (nameSpace!=null && !nameSpace.isEmpty()) {
                    command= "kubectl cp " + pod + ":"+pathLog + logName + " "+applicationProperties.getPathLoadFileLogs(load)+ "/" + pod + "_" + logName +" -n "+nameSpace;
                } else {
                    command= "kubectl cp " + pod + ":"+pathLog + logName + " "+applicationProperties.getPathLoadFileLogs(load)+ "/" + pod + "_" + logName;
                    //kubectl cp mno-mg-548d5c4d74-gpmvk:/logs/drum_mg/MG_62003143.log /temp/logs/mno-mg-548d5c4d74-gpmvk_MG_62003143.log -n phxl
                }
                log.info(command);

                Process exec = Runtime.getRuntime().exec(command);

                InputStream in = exec.getInputStream();
                Scanner scan = new Scanner(in);
                while (scan.hasNext()) {
                    log.info(scan.nextLine());
                }

                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("getFileFromPod END");
    }

    private List<String> getPodsByListPodsCommand(String nameSpace, String startPodName, String exceptStartPodName) {
        List<String> pods = new ArrayList<>();

        try {
            String command;
            if (nameSpace!=null && !nameSpace.isEmpty()) {
                command= "kubectl get pods -n "+nameSpace;
            } else {
                command= "kubectl get pods";
            }
            log.info("Looking for pods...");

            Process exec = Runtime.getRuntime().exec(command);

            InputStream in = exec.getInputStream();
            Scanner scan = new Scanner(in);
            while (scan.hasNext()) {
                String line = scan.nextLine();
                log.info("line: {}", line);
                if (exceptStartPodName==null || exceptStartPodName.isEmpty()) {
                    if (line.startsWith(startPodName) && line.contains("Running")) {
                        String podName = line.substring(0, 45).trim();
                        log.info("podName: "+podName);
                        pods.add(podName);
                    }
                } else {
                    if (line.startsWith(startPodName) && !line.startsWith(exceptStartPodName) && line.contains("Running")) {
                        String podName = line.substring(0, 45).trim();
                        log.info("podName: "+podName);
                        pods.add(podName);
                    }
                }
            }
            log.info("Finish looking for pods...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("pods.size(): {}", pods.size());

        return pods;
    }
}
