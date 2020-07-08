package com.itsol.train.mock.repo;

import com.itsol.train.mock.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
