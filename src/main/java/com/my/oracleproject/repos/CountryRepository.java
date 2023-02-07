package com.my.oracleproject.repos;

import com.my.oracleproject.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}