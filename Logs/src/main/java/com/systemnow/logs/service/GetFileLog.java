package com.systemnow.logs.service;

import com.systemnow.logs.model.ConfigLog;
import com.systemnow.logs.model.Load;

import java.io.IOException;
import java.util.List;

public interface GetFileLog {
    void doGetFile(Load load, List<ConfigLog> configLogs) throws IOException;
}
