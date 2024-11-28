package com.project.musicwebbe.controller.auth;

import com.project.musicwebbe.entities.Album;
import com.project.musicwebbe.entities.Artist;
import com.project.musicwebbe.entities.Genre;
import com.project.musicwebbe.entities.Song;
import com.project.musicwebbe.service.artist.impl.ArtistService;
import com.project.musicwebbe.service.song.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth/artists")
public class ArtistAuthRestController {

    @Autowired
    private ArtistService artistService;
    @Autowired
    private ISongService songService;

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.findAll();
        if (artists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(artists);
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody Artist artist) {
        Artist savedArtist = artistService.saveD(artist);

        return ResponseEntity.ok(savedArtist);
    }


    @PutMapping("/{artistId}")
    public ResponseEntity<?> updateAlbum(@PathVariable Long artistId, @RequestBody Artist artist) {
        var artistEntity = artistService.findById(artistId);
        if (artistEntity == null) {
            return ResponseEntity.notFound().build();
        }
        artistService.save(artist);
        return ResponseEntity.ok(200);
    }
}