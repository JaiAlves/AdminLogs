package com.systemnow.logs.repository;

import com.systemnow.logs.model.ConfigLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigLogRepository extends JpaRepository<ConfigLog, Long> {
    List<ConfigLog> findAllByEnumStatusId(Integer enumStatusId);
    List<ConfigLog> findAllByEnumStatusIdAndGroupName(Integer enumStatusId, String groupName);
}
