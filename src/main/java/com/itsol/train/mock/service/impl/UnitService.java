package com.itsol.train.mock.service.impl;

import com.itsol.train.mock.domain.Unit;
import com.itsol.train.mock.repo.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(rollbackFor = Exception.class)
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    public void save(Unit unit) {
        unitRepository.save(unit);
    }
}
