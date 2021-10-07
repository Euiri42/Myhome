package com.youngeun.myhome.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;
    @NotNull
    @Size(min=2, max=30, message = "2자이상 30자 이하로 작성해주세요.")
    private String title;
    private String content;
}
