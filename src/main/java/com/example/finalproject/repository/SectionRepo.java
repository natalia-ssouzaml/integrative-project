package com.example.finalproject.repository;

import com.example.finalproject.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SectionRepo extends JpaRepository<Section, Long> {
    @Query(value = "SELECT * FROM section s WHERE s.category = ? LIMIT 1;", nativeQuery = true)
    Optional<Section> findByCategory(String category);
}
