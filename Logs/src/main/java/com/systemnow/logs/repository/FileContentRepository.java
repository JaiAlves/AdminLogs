package com.systemnow.logs.repository;

import com.systemnow.logs.model.FileContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileContentRepository extends JpaRepository<FileContent, Long> {
}
