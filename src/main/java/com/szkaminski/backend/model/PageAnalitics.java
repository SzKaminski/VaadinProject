package com.szkaminski.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "analitics")
public class PageAnalitics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int visitCounter;
    private int likeCounter;
    private int notLikeCounter;
}
