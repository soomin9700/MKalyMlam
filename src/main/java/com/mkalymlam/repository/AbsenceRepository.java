package com.mkalymlam.repository;

import com.mkalymlam.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEmployeId(Long employeId);
}