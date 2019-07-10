package com.szkaminski.backend.repositories;

import com.szkaminski.backend.model.PageAnaliticsSingleton;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnaliticsRepository  extends JpaRepository<PageAnaliticsSingleton, Long> {

}
