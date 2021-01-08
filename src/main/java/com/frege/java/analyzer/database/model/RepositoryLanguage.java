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
@Entity(name = "repository_language")
public class RepositoryLanguage {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "repository_id")
    private String repositoryId;
    @Column(name = "language_id")
    private Integer languageId;
    @Column(name = "present")
    private Boolean present;
    @Column(name = "analyzed")
    private Boolean analyzed;
}
