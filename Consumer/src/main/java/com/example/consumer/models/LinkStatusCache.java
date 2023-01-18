package com.example.consumer.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CacheStatus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkStatusCache {
    @Id
    private String key;
    private String status;
}
