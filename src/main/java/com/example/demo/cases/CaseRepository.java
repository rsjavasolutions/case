package com.example.demo.cases;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CaseRepository extends JpaRepository<CaseEntity, Long> {

    Set<CaseEntity> findAllByContractualVerificationDateBetween(LocalDate from, LocalDate to);

    Optional<CaseEntity> findByUuid(String uuid);

    void deleteByUuid(String uuid);
}
