package com.project.musicwebbe.controller.auth;

import com.project.musicwebbe.entities.Album;
import com.project.musicwebbe.entities.Artist;
import com.project.musicwebbe.entities.Song;
import com.project.musicwebbe.service.album.impl.AlbumService;
import com.project.musicwebbe.service.song.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/auth/albums")
public class AlbumAuthRestController {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private SongService songService;


    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.findAll();
        if (albums.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(albums);
    }

    @PostMapping
    public ResponseEntity<?> createAlbum(@RequestBody Album album) {
        album.setDateCreate(LocalDateTime.now());
        albumService.save(album);

        return ResponseEntity.ok(200);
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<?> updateAlbum(@PathVariable Long albumId, @RequestBody Album album) {
        var albumEntity = albumService.findById(albumId);
        if (albumEntity == null) {
            return ResponseEntity.notFound().build();
        }
        albumService.save(album);
        return ResponseEntity.ok(200);
    }
}
