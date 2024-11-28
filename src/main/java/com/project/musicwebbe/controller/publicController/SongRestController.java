package com.project.musicwebbe.controller.publicController;

import com.project.musicwebbe.dto.albumDTO.AlbumDTO;
import com.project.musicwebbe.dto.songDTO.AlbumOfSongDTO;
import com.project.musicwebbe.dto.songDTO.ArtistOfSongDTO;
import com.project.musicwebbe.dto.songDTO.SongDTO;
import com.project.musicwebbe.entities.*;
import com.project.musicwebbe.service.song.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/public/songs")
public class SongRestController {
    @Autowired
    private SongService songService;

    @GetMapping
    public ResponseEntity<List<SongDTO>> getAllSongs() {
        List<Song> songs = songService.findAll();
        if (songs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SongDTO> songDTOS = songs.stream()
                .map(this::convertToSongDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(songDTOS);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<SongDTO>> getAllPageSongs() {
        PageRequest pageRequest = PageRequest.of(0, 9, Sort.by(Sort.Direction.DESC, "dateCreate"));
        Page<Song> songs = songService.findAll(pageRequest);
        Page<SongDTO> songDTOS = songs.map(this::convertToSongDTO);
        return ResponseEntity.ok(songDTOS);
    }

    @GetMapping("/top-song")
    public ResponseEntity<Page<SongDTO>> getTopSongs(@RequestParam(name = "national", defaultValue = "") String national,
                                                     @RequestParam(name = "size", defaultValue = "100") int size
    ) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<Song> songs = songService.findAllTopSongByNational(national, pageRequest);
        Page<SongDTO> songDTOS = songs.map(this::convertToSongDTO);
        return ResponseEntity.ok(songDTOS);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<SongDTO> getSongById(@PathVariable Long songId) {
        Song song = songService.findById(songId);
        if (song == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToSongDTO(song));
    }

    private SongDTO convertToSongDTO(Song song) {
        SongDTO songDTO = new SongDTO();
        songDTO.setSongId(song.getSongId());
        songDTO.setTitle(song.getTitle());
        songDTO.setDateCreate(song.getDateCreate());
        songDTO.setDuration(song.getDuration());
        songDTO.setCoverImageUrl(song.getCoverImageUrl());
        songDTO.setLyrics(song.getLyrics());
        songDTO.setSongUrl(song.getSongUrl());
        songDTO.setListens(song.getSongListens().stream().mapToInt(SongListen::getTotal).sum());
        if (song.getAlbum() != null) {
            AlbumOfSongDTO album = new AlbumOfSongDTO(song.getAlbum().getAlbumId(), song.getAlbum().getTitle());
            songDTO.setAlbum(album);
        }
        if (song.getArtists() != null) {

            List<ArtistOfSongDTO> artistOfSongDTOS = song.getArtists().stream()
                    .map(artist -> ArtistOfSongDTO.builder()
                            .artistId(artist.getArtistId())
                            .artistName(artist.getArtistName())
                            .build())
                    .toList();
            songDTO.setArtists(artistOfSongDTOS);
        }
        if (song.getGenres() != null) {
            songDTO.setGenres(song.getGenres());
        }
        // Chuyển đổi các entity liên quan sang DTO tương ứng nếu cần
        return songDTO;
    }

}
