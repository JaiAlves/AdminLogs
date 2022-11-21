package com.systemnow.logs.converter;

import com.systemnow.logs.controller.response.LoadResponse;
import com.systemnow.logs.controller.response.LoadStepResponse;
import com.systemnow.logs.model.Load;
import com.systemnow.logs.model.enums.EnumStatus;
import com.systemnow.logs.model.enums.EnumStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoadConvert {

    public Page<LoadResponse> to(Page<Load> loadPage) {
        if (loadPage==null) {
            return null;
        }
        List<LoadResponse> loadResponseList = to(loadPage.getContent());

        Pageable pageableFilter =PageRequest.of(loadPage.getNumber(), loadPage.getSize());

        return new PageImpl(loadResponseList, pageableFilter, loadPage.getTotalElements());
    }

    public List<LoadResponse> to(List<Load> loadList) {
        if (loadList==null) {
            return new ArrayList<>();
        }
        List<LoadResponse> loadResponseList = new ArrayList<>();

        loadList.forEach(l-> loadResponseList.add(to(l)));

        return loadResponseList;
    }

    public LoadResponse to(Load load) {
        if (load==null) {
            return null;
        }

        return LoadResponse.builder().
                steps(getLoadStepResponseList(load)).
                startDate(load.getStartDate()).
                endDate(load.getEndDate()).
                name(load.getName()).
                status(load.getEndDate()!=null?"FINISHED":"EXECUTING").
                build();
    }

    private List<LoadStepResponse> getLoadStepResponseList(Load load) {
        if (load.getLoadSteps()!=null && !load.getLoadSteps().isEmpty()) {
            List<LoadStepResponse> loadStepResponseList = new ArrayList<>();

            load.getLoadSteps().forEach(s ->
                    loadStepResponseList.add(LoadStepResponse.builder().
                            status(EnumStatus.getById(s.getStatus())).
                            step(EnumStep.getById(s.getStep())).
                            startDate(s.getStartDate()).
                            endDate(s.getEndDate()).build()));

            return loadStepResponseList;
        }

        return new ArrayList<>();
    }
}
