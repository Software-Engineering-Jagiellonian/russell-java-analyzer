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
@Entity(name = "repository_language_file")
public class RepositoryLanguageFile {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "repository_language_id")
    private Long repositoryLanguageId;
    @Column(name = "file_path")
    private String path;
}
