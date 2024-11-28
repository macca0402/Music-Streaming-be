package com.project.musicwebbe.repository;

import com.project.musicwebbe.entities.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> searchAllByTitleContaining(String title);

    @Query("SELECT s FROM Song s JOIN s.genres g JOIN s.songListens sl " +
            "WHERE g.genreName LIKE %:national% AND sl.dateCreate >= :startOfWeek " +
            "GROUP BY s.songId " +
            "ORDER BY SUM(sl.total) DESC")
    Page<Song> findAllTopSongsByNational(@Param("national")String national,
                                         @Param("startOfWeek") LocalDate startOfWeek,
                                         Pageable pageable);


}