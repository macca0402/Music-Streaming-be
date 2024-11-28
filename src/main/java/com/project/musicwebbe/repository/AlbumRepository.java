package com.project.musicwebbe.repository;

import com.project.musicwebbe.entities.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> searchAllByTitleContaining(String title);

    @Query(value = "SELECT a.album_id, a.title, a.date_create, a.cover_image_url, a.provide " +
            "FROM albums a JOIN artists ar ON a.album_id = ar.artist_id " +
            "WHERE a.title like %:title% OR ar.artist_name like %:artistName%", nativeQuery = true)
    Page<Album> searchAllByTitleAndArtistName(@Param("title") String title,
                                              @Param("artistName") String artistName,
                                              Pageable pageable);
}