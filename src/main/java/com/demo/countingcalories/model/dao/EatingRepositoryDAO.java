package com.demo.countingcalories.model.dao;

import com.demo.countingcalories.model.entity.Eating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EatingRepositoryDAO extends JpaRepository<Eating, Long> {

    Page<Eating> findByUserIdAndDateBetween(Long userId, LocalDateTime dateAfter, LocalDateTime dateBefore, Pageable pageable);

    Page<Eating> findByUserId(Long userId, Pageable pageable);

    List<Eating> findByUserIdAndDateBetween(Long userId, LocalDateTime start, LocalDateTime end);

}
