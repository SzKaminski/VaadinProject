package com.szkaminski.backend.service;


import com.szkaminski.backend.model.PageAnaliticsSingleton;
import com.szkaminski.backend.repositories.AnaliticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnaliticsService {

    @Autowired
    private AnaliticsRepository analiticsRepository;

    public PageAnaliticsSingleton updateAnalitics(){
        analiticsRepository.save(PageAnaliticsSingleton.getINSTANCE());
        return PageAnaliticsSingleton.getINSTANCE();
    }

    public Optional<PageAnaliticsSingleton> getAnalitics(){
        return analiticsRepository.findById(PageAnaliticsSingleton.getINSTANCE().getId());
    }

    public int getLikeCounter(){
        return getAnalitics().get().getLikeCounter();
    }

    public int getNotLikeCounter(){
        return getAnalitics().get().getNotLikeCounter();
    }
}
