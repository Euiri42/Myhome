package com.youngeun.myhome.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String client;
    private String institution;

//    @ManyToOne
//    @JoinColumn(name = "")


}
