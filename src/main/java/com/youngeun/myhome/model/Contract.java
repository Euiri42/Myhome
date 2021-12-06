package com.youngeun.myhome.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Contract {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String client;
    private String institution;
}
