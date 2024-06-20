package com.test.proj.repository;

import com.test.proj.entities.CancellationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelRepository extends JpaRepository<CancellationRequest, Long> {
}
