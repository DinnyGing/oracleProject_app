package com.my.oracleproject.repos;

import com.my.oracleproject.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}