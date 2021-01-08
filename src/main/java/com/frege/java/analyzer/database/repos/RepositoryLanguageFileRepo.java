package com.frege.java.analyzer.database.repos;

import com.frege.java.analyzer.database.model.RepositoryLanguageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryLanguageFileRepo extends JpaRepository<RepositoryLanguageFile, String> {
    @Query("SELECT * FROM repository_language_file RLF WHERE RLF.repository_language_id = ?1")
    RepositoryLanguageFile findPath(Long id);
}
