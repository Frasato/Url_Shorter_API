package com.url.api.repositories;

import com.url.api.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, String> {

    @Query(value = "SELECT * FROM urls WHERE original_url = :url", nativeQuery = true)
    Optional<Url> findByUrl(@Param("url")String url);

    @Query(value = "SELECT * FROM urls WHERE shorted_id = :id", nativeQuery = true)
    Optional<Url> findByShortedId(@Param("id")String id);

}
