package org.cloudcraft.coursemanagementservice.repository;

import org.cloudcraft.coursemanagementservice.module.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepo extends JpaRepository<Content,Long> {
}
