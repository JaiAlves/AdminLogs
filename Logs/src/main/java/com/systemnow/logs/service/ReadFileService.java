package com.systemnow.logs.service;

import com.systemnow.logs.model.Load;

import java.io.IOException;
import java.util.List;

public interface ReadFileService {
    List<String> doReadFiles(Load load) throws IOException;
}
