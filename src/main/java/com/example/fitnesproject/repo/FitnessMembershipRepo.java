package com.example.fitnesproject.repo;

import com.example.fitnesproject.domain.FitnessMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FitnessMembershipRepo extends JpaRepository<FitnessMembership, Long> {
    List<FitnessMembership> findAllByNameContainingIgnoreCase(String name);

}
