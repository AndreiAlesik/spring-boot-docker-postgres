package com.kaluzny.demo.domain;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AutomobileRepository extends JpaRepository<Automobile, Long> {
//    List<Automobile> findByName(String name);
//
//    List<Automobile> findByColor(String color);
//
//    List<Automobile> findByNameAndColor(String name, String color);
//
//    List<Automobile> findByColorStartsWith(String colorStartWith, Pageable page);

    @Query(value = """
            SELECT * FROM automobile WHERE deleted IS FALSE
            """, nativeQuery = true)
    List<Automobile> findAllExists();

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE automobile SET deleted = TRUE WHERE id = ?1", nativeQuery = true)
    void markRemoved(UUID id);

    @Query(value = """
            UPDATE automobile SET name = :name, color = :color, is_original_color = :originalColor WHERE id = :id
            """, nativeQuery = true)
    Automobile updateAutomobile(String name, String color, Boolean originalColor, UUID id);

    @Query(value = "SELECT * FROM automobile WHERE name = :name", nativeQuery = true)
    List<Automobile> findByName(String name);

    @Query(value = "SELECT * FROM automobile WHERE color = :color", nativeQuery = true)
    List<Automobile> findByColor(String color);

    @Query(value = "SELECT * FROM automobile WHERE name = :name AND color = :color", nativeQuery = true)
    List<Automobile> findByNameAndColor(String name, String color);

    @Query(value = "SELECT * FROM automobile WHERE color LIKE :colorStartWith", nativeQuery = true)
    List<Automobile> findByColorStartsWith(String colorStartWith, Pageable page);

    @Query(value = "SELECT * FROM automobile WHERE id = :id", nativeQuery = true)
    Optional<Automobile> findById(UUID id);
}
