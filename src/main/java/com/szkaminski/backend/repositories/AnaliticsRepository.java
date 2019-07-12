package com.szkaminski.backend.repositories;

import com.szkaminski.backend.model.PageAnaliticsSingleton;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnaliticsRepository  extends JpaRepository<PageAnaliticsSingleton, Long> {

    @Query(value = "SELECT SUM(visit_counter) FROM analitics", nativeQuery = true)
    int sumAllVisit();
}
