package com.systemnow.logs.repository;

import com.systemnow.logs.model.Load;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoadRepository extends JpaRepository<Load, Long> {
    Load findByName(String name);
}
