package com.systemnow.logs.repository;

import com.systemnow.logs.model.ConfigLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfigLogRepository extends JpaRepository<ConfigLog, Long> {
    List<ConfigLog> findAllByEnumStatusId(Integer enumStatusId);
    List<ConfigLog> findAllByEnumStatusIdAndGroupName(Integer enumStatusId, String groupName);
}
