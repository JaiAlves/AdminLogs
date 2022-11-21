package com.systemnow.logs.service;

import com.systemnow.logs.model.Load;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface LoadService {
    void executeLoad(Load load, String groupName) throws Exception;
    void justGetFiles(Load load,String groupName) throws IOException;
    void justReadFiles(String loadName) throws Exception;
    Load getByName(String name);
    List<Load> findAll();
    Page<Load> findPageLoad(int page, int size);
    Load startLoad(String groupName);
}
