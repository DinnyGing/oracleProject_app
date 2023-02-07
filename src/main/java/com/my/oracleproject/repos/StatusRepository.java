package com.my.oracleproject.repos;

import com.my.oracleproject.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}