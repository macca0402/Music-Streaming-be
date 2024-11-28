package com.project.musicwebbe.controller.auth;

import com.project.musicwebbe.entities.Album;
import com.project.musicwebbe.entities.Playlist;
import com.project.musicwebbe.service.playlist.impl.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/auth/playlists")
@CrossOrigin("*")
public class PlaylistAuthRestController {

    @Autowired
    private PlaylistService playListService;

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        List<Playlist> playlists = playListService.findAll();
        if (playlists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playlists);
    }


    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody Playlist playlist) {
        System.out.println(playlist);
        String playlistCodeDefault = "MUSIC-";
        String randomLetters = generateRandomString(5);
        playlist.setDateCreate(LocalDateTime.now());
        playlist.setPlaylistCode(playlistCodeDefault + randomLetters);
        playListService.save(playlist);
        return ResponseEntity.ok(200);
    }
    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }

        return result.toString();
    }

    @PutMapping("/{playlistId}")
    public ResponseEntity<?> updatePlaylist(@PathVariable Long playlistId, @RequestBody Playlist playlist) {
        Playlist existingPlaylist = playListService.findById(playlistId);
        if (existingPlaylist == null) {
            return ResponseEntity.notFound().build();
        }
        playListService.save(playlist);
        return ResponseEntity.ok(200);
    }


}
