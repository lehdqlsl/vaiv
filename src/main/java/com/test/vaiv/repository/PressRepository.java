package com.test.vaiv.repository;

import com.test.vaiv.domain.Press;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PressRepository extends JpaRepository<Press, Long> {
    List<Press> findByDateIsBetween(LocalDate start, LocalDate end);
}
