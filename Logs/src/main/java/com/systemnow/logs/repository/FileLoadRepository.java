package com.systemnow.logs.repository;

import com.systemnow.logs.model.FileLoad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileLoadRepository extends JpaRepository<FileLoad, Long> {
}
