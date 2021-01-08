package com.frege.java.analyzer.database.repos;

import com.frege.java.analyzer.database.model.RepositoryLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryLanguageRepo extends JpaRepository<RepositoryLanguage, String> {
    @Query("SELECT * FROM repository_language RL WHERE RL.repository_id = ?1")
    RepositoryLanguage findByRepositoryId(String repoId);
}
