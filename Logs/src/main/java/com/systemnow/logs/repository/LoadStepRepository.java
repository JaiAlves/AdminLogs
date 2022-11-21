package com.systemnow.logs.repository;

import com.systemnow.logs.model.LoadStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoadStepRepository extends JpaRepository<LoadStep, Long> {
}
