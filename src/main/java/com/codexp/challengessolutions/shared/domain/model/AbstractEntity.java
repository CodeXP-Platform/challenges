package com.codexp.challengessolutions.shared.domain.model;

import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public class AbstractEntity {
    private Date updatedAt;
    private Date createdAt;
}
