package com.frege.java.analyzer.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "repository_java_statistics")
public class RepositoryJavaStatistics {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "repository_id")
    private String repositoryId;
    @Column(name = "filename")
    private String filename;
    //TODO add statistics
}
