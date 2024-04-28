package com.esprit.cloudcraft.repository;


import com.esprit.cloudcraft.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileEntityRepo extends JpaRepository<FileEntity,Long> {
}
