package com.example.fusalmatching.repository;

import com.example.fusalmatching.domain.Field;
import com.example.fusalmatching.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, String> {
}
