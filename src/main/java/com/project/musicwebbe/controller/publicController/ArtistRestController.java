package com.project.musicwebbe.controller.publicController;

import com.project.musicwebbe.dto.artistDTO.AlbumOfArtistDTO;
import com.project.musicwebbe.dto.artistDTO.ArtistDTO;
import com.project.musicwebbe.dto.artistDTO.SongOfArtistDTO;
import com.project.musicwebbe.dto.songDTO.ArtistOfSongDTO;
import com.project.musicwebbe.entities.Artist;
import com.project.musicwebbe.entities.Genre;
import com.project.musicwebbe.service.artist.impl.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        List<Artist> artists = artistService.findAll();
        if (artists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<ArtistDTO> artistDTOS = artists.stream()
                .map(this::convertToArtistDTO)
                .toList();
        return ResponseEntity.ok(artistDTOS);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable Long artistId) {
        Artist artist = artistService.findById(artistId);
        if (artist == null) {
            return ResponseEntity.notFound().build();
        }
        ArtistDTO artistDTO = convertToArtistDTO(artist);
        return ResponseEntity.ok(artistDTO);
    }

    @GetMapping("/name/{artistName}")
    public ResponseEntity<ArtistDTO> getArtistByName(@PathVariable String artistName) {
        Artist artist = artistService.findByName(artistName);
        if (artist == null) {
            return ResponseEntity.notFound().build();
        }
        ArtistDTO artistDTO = convertToArtistDTO(artist);
        return ResponseEntity.ok(artistDTO);
    }

    private ArtistDTO convertToArtistDTO(Artist artist) {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setArtistId(artist.getArtistId());
        artistDTO.setArtistName(artist.getArtistName());
        artistDTO.setAvatar(artist.getAvatar());
        artistDTO.setBiography(artist.getBiography());
        if (artist.getGenres() != null) {
            artistDTO.setGenres(artist.getGenres());
        }
        if (artist.getSongs() != null) {
            List<SongOfArtistDTO> songOfArtistDTOS = artist.getSongs().stream()
                    .map(song -> SongOfArtistDTO.builder()
                            .songId(song.getSongId())
                            .title(song.getTitle())
                            .dateCreate(song.getDateCreate())
                            .lyrics(song.getLyrics())
                            .songUrl(song.getSongUrl())
                            .duration(song.getDuration())
                            .coverImageUrl(song.getCoverImageUrl())
                            .artists(song.getArtists().stream()
                                    .map(artist1 -> ArtistOfSongDTO.builder()
                                            .artistId(artist1.getArtistId())
                                            .artistName(artist1.getArtistName())
                                            .build()).toList())
                            .build())
                    .toList();
            artistDTO.setSongs(songOfArtistDTOS);
        }
        if (artist.getAlbums() != null) {
            List<AlbumOfArtistDTO> albumOfArtistDTOS = artist.getAlbums().stream()
                    .map(album -> AlbumOfArtistDTO.builder()
                            .albumId(album.getAlbumId())
                            .title(album.getTitle())
                            .provide(album.getProvide())
                            .dateCreate(album.getDateCreate())
                            .coverImageUrl(album.getCoverImageUrl())
                            .build()).toList();
            artistDTO.setAlbums(albumOfArtistDTOS);
        }

        // Chuyển đổi các entity liên quan sang DTO tương ứng nếu cần
        return artistDTO;
    }
}
