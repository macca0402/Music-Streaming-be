package com.project.musicwebbe.repository;

import com.project.musicwebbe.entities.SongListen;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SongListenRepository extends JpaRepository<SongListen, Long> {
    SongListen findBySongSongIdAndDateCreate(Long songId, LocalDate date);

}
