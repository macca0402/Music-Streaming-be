package com.project.musicwebbe.repository;

import com.project.musicwebbe.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> searchAllByArtistNameContaining(String artistName);

    Artist findByArtistNameContaining(String artistName);
}