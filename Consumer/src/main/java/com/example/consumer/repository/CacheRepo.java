package com.example.consumer.repository;

import com.example.consumer.models.LinkStatusCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepo extends CrudRepository<LinkStatusCache, String> {
}
