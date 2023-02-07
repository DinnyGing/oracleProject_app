package com.my.oracleproject.repos;

import com.my.oracleproject.models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}