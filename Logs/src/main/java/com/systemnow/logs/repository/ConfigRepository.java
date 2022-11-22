package com.systemnow.logs.repository;

import com.systemnow.logs.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<Config, Long> {
}
