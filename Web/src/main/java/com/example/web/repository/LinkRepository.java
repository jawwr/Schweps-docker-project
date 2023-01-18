package com.example.web.repository;

import com.example.web.models.LinkDbModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<LinkDbModel, Integer> {
    Optional<LinkDbModel> findById(Integer id);
    @Query(value = "UPDATE links SET status = :#{#status} WHERE id = :#{#id}", nativeQuery = true)
    void updateLinkById(Integer id, String status);
}
