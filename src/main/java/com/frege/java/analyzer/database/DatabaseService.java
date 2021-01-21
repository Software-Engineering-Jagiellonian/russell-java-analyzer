package com.frege.java.analyzer.database;

import com.frege.java.analyzer.database.model.RepositoryJavaStatistics;
import com.frege.java.analyzer.database.model.RepositoryLanguage;
import com.frege.java.analyzer.database.repos.RepositoryJavaStatisticsRepo;
import com.frege.java.analyzer.database.repos.RepositoryLanguageFileRepo;
import com.frege.java.analyzer.database.repos.RepositoryLanguageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DatabaseService {
    private final RepositoryLanguageRepo repositoryLanguageRepo;
    private final RepositoryLanguageFileRepo repositoryLanguageFileRepo;
    private final RepositoryJavaStatisticsRepo repositoryJavaStatisticsRepo;

    @Autowired
    public DatabaseService(RepositoryLanguageRepo repositoryLanguageRepo, RepositoryLanguageFileRepo repositoryLanguageFileRepo, RepositoryJavaStatisticsRepo repositoryJavaStatisticsRepo) {
        this.repositoryLanguageRepo = repositoryLanguageRepo;
        this.repositoryLanguageFileRepo = repositoryLanguageFileRepo;
        this.repositoryJavaStatisticsRepo = repositoryJavaStatisticsRepo;
    }

    public String getPath(Long repositoryLanguageId) {
        return repositoryLanguageFileRepo.findPath(repositoryLanguageId).getPath();
    }

    public Long getRepositoryLanguageIdByRepositoryId(String repositoryId) {
        return repositoryLanguageRepo.findByRepositoryId(repositoryId).getId();
    }

    public void updateAnalyzed(Long id) {
        RepositoryLanguage repositoryLanguage = repositoryLanguageRepo.getOne(id.toString());
        repositoryLanguage.setAnalyzed(true);
        repositoryLanguageRepo.save(repositoryLanguage);
    }

    public void saveStatistics(List<RepositoryJavaStatistics> statistics) {
        repositoryJavaStatisticsRepo.saveAll(statistics);
    }
}
