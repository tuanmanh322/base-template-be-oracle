package com.itsol.train.mock.repo;

import com.itsol.train.mock.domain.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Unit findByUnitId(Long unitId);
}
