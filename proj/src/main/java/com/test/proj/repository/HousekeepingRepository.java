package com.test.proj.repository;

import com.test.proj.compositekeys.Housekey;
import com.test.proj.entities.Housekeeping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HousekeepingRepository extends JpaRepository<Housekeeping, Housekey> {
}
