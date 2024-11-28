package com.project.musicwebbe.controller.auth;

import com.project.musicwebbe.entities.Song;
import com.project.musicwebbe.service.song.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for managing song-related operations.
 * Author: KhangDV
 */
@RestController
@RequestMapping("/api/auth/songs")
public class SongAuthRestController {
    @Autowired
    private SongService songService;

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        List<Song> songs = songService.findAll();
        if (songs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(songs);
    }

    @PostMapping
    public ResponseEntity<Song> createSong(@Validated @RequestBody Song song) {
        System.out.println(song);
        song.setDateCreate(LocalDateTime.now());
        songService.save(song);
        return ResponseEntity.ok(song);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<Song> updateSong(@PathVariable Long songId, @RequestBody Song song) {
        var songEntity = songService.findById(songId);
        if (songEntity == null) {
            return ResponseEntity.notFound().build();
        }
        songService.save(song);
        return ResponseEntity.ok(song);
    }

}
