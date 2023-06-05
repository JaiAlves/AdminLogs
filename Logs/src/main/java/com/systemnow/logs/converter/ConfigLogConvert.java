package com.systemnow.logs.converter;

import com.systemnow.logs.controller.response.ConfigLogResponse;
import com.systemnow.logs.controller.response.LoadResponse;
import com.systemnow.logs.model.ConfigLog;
import com.systemnow.logs.model.Load;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigLogConvert {
    public Page<ConfigLogResponse> to(Page<ConfigLog> loadPage) {
        if (loadPage==null) {
            return null;
        }
        List<ConfigLogResponse> configLogResponses = to(loadPage.getContent());

        Pageable pageableFilter = PageRequest.of(loadPage.getNumber(), loadPage.getSize());

        return new PageImpl(configLogResponses, pageableFilter, loadPage.getTotalElements());
    }

    public List<ConfigLogResponse> to(List<ConfigLog> list) {
        if (list==null) {
            return new ArrayList<>();
        }

        List<ConfigLogResponse> configLogResponseList = new ArrayList<>();

        list.forEach(l-> configLogResponseList.add(to(l)));

        return configLogResponseList;
    }

    public ConfigLogResponse to(ConfigLog configLog) {
        if (configLog==null) {
            return null;
        }

        return ConfigLogResponse.builder().
                logName(configLog.getLogName()).
                groupName(configLog.getGroupName()).
                nameSpace(configLog.getNameSpace()).
                pathLog(configLog.getPathLog()).
                startPodName(configLog.getStartPodName()).
                exceptStartPodName(configLog.getExceptStartPodName()).
                enumStatusId(configLog.getEnumStatusId()).
                build();
    }
}
