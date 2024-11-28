package com.project.musicwebbe.controller.publicController;

import com.project.musicwebbe.dto.albumDTO.ArtistOfAlbumDTO;
import com.project.musicwebbe.dto.albumDTO.AlbumDTO;
import com.project.musicwebbe.dto.albumDTO.SongOfAlbumDTO;
import com.project.musicwebbe.dto.songDTO.ArtistOfSongDTO;
import com.project.musicwebbe.entities.Album;
import com.project.musicwebbe.service.album.impl.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/public/albums")
public class AlbumRestController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<Page<AlbumDTO>> getAllAlbums(
            @RequestParam(name = "title", defaultValue = "") String title,
            @RequestParam(name = "artistName", defaultValue = "") String artistName,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        if (page<0) {
            page = 0;
        }
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "date_create"));
        Page<Album> albums = albumService.searchAllByTitleAndArtistName(title, artistName, pageRequest);
        if (albums.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Chuyển đổi từ Album sang AlbumDTO
        Page<AlbumDTO> albumDTOs = albums.map(this::convertToAlbumDTO);
        return ResponseEntity.ok(albumDTOs);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AlbumDTO>> getAllAlbums(){
        List<Album> albums = albumService.findAll();
        if (albums.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<AlbumDTO> albumDTOs = albums.stream()
                .map(this::convertToAlbumDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(albumDTOs);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AlbumDTO>> getAllPageAlbums() {
        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by(Sort.Direction.DESC, "dateCreate"));
        Page<Album> albums = albumService.findAll(pageRequest);
        Page<AlbumDTO> albumDTOs = albums.map(this::convertToAlbumDTO);
        return ResponseEntity.ok(albumDTOs);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable Long albumId) {
        Album album = albumService.findById(albumId);
        if (album == null) {
            return ResponseEntity.notFound().build();
        }
        AlbumDTO albumDTO = convertToAlbumDTO(album);
        return ResponseEntity.ok(albumDTO);
    }

    private AlbumDTO convertToAlbumDTO(Album album) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setAlbumId(album.getAlbumId());
        albumDTO.setTitle(album.getTitle());
        albumDTO.setDateCreate(album.getDateCreate());
        albumDTO.setCoverImageUrl(album.getCoverImageUrl());
        albumDTO.setProvide(album.getProvide());
        if (album.getSongs() != null) {
            List<SongOfAlbumDTO> songOfAlbumDTOS = album.getSongs().stream()
                    .map(song -> SongOfAlbumDTO.builder()
                            .songId(song.getSongId())
                            .title(song.getTitle())
                            .dateCreate(song.getDateCreate())
                            .lyrics(song.getLyrics())
                            .songUrl(song.getSongUrl())
                            .duration(song.getDuration())
                            .coverImageUrl(song.getCoverImageUrl())
                            .artists(song.getArtists().stream()
                                    .map(artist -> ArtistOfSongDTO.builder()
                                            .artistId(artist.getArtistId())
                                            .artistName(artist.getArtistName())
                                            .build()).toList())
                            .build()).toList();
            albumDTO.setSongs(songOfAlbumDTOS);
        }
        if (album.getArtists() != null){
            List<ArtistOfAlbumDTO> artistOfAlbumDTOS = album.getArtists().stream()
                    .map(artist -> {
                        return ArtistOfAlbumDTO.builder()
                                .artistId(artist.getArtistId())
                                .artistName(artist.getArtistName())
                                .avatar(artist.getAvatar())
                                .build();
                    }).collect(Collectors.toList());
            albumDTO.setArtists(artistOfAlbumDTOS);
        }

        // Chuyển đổi các entity liên quan sang DTO tương ứng nếu cần
        return albumDTO;
    }
}
