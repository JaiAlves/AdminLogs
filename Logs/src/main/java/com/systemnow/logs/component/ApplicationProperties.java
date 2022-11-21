package com.systemnow.logs.component;

import com.systemnow.logs.model.Load;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
    @Value("${path.file.logs}")
    private String  pathFileLogs;

    @Value("${layout.ini.pos.start.date}")
    private int layoutIniPosStartDate;

    @Value("${layout.end.pos.start.date}")
    private int layoutEndPosStartDate;

    @Value("${layout.ini.pos.type}")
    private int layoutIniPosType;

    @Value("${layout.end.pos.type}")
    private int layoutEndPosType;

    public String getPathFileLogs() {
        return pathFileLogs;
    }

    public String getPathLoadFileLogs(Load load) {
        return pathFileLogs+"/"+load.getName();
    }

    public int getLayoutIniPosStartDate() {
        return layoutIniPosStartDate;
    }

    public int getLayoutEndPosStartDate() {
        return layoutEndPosStartDate;
    }

    public int getLayoutIniPosType() {
        return layoutIniPosType;
    }

    public int getLayoutEndPosType() {
        return layoutEndPosType;
    }
}
