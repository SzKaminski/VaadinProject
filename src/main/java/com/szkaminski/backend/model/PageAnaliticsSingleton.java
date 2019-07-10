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
public class PageAnaliticsSingleton {

    private static PageAnaliticsSingleton INSTANCE;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public static PageAnaliticsSingleton getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new PageAnaliticsSingleton();
        }
        return INSTANCE;
    }

    private int visitCounter;
    private int likeCounter;
    private int notLikeCounter;
}
