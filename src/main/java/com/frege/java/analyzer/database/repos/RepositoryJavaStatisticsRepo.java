package com.frege.java.analyzer.database.repos;

import com.frege.java.analyzer.database.model.RepositoryJavaStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryJavaStatisticsRepo extends JpaRepository<RepositoryJavaStatistics, String> {
}
