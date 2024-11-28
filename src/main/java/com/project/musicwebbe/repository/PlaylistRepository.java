package com.project.musicwebbe.repository;

import com.project.musicwebbe.entities.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> searchAllByPlaylistNameContaining(String playlistName, Pageable pageable);

}