package org.cloudcraft.coursemanagementservice.repository;


import org.cloudcraft.coursemanagementservice.module.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEntityRepo extends JpaRepository<FileEntity,Long> {
}
