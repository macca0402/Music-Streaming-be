package com.project.musicwebbe.controller.publicController;


import com.project.musicwebbe.dto.playlistDTO.PlaylistDTO;
import com.project.musicwebbe.dto.playlistDTO.SongOfPlaylistDTO;
import com.project.musicwebbe.dto.playlistDTO.UserOfPlaylistDTO;
import com.project.musicwebbe.dto.songDTO.ArtistOfSongDTO;
import com.project.musicwebbe.dto.songDTO.SongDTO;
import com.project.musicwebbe.entities.Playlist;
import com.project.musicwebbe.service.playlist.impl.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public/playlists")
@CrossOrigin("*")
public class PlaylistRestController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping(value = "/getAll")
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylist(){
        List<Playlist> playlists = playlistService.findAll();
        if (playlists.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<PlaylistDTO> playlistDTOS = playlists.stream()
                .map(this::convertToPlaylistDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(playlistDTOS);
    }

    @GetMapping("/getAllWithPage")
    public ResponseEntity<Page<PlaylistDTO>> getAllPageAndSearchPlaylists(
            @RequestParam(value = "playlistName", defaultValue = "") String playlistName,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        if (page < 0) {
            page = 0;
        }
        Page<Playlist> playlists = playlistService.searchAllByPlaylistName(playlistName, PageRequest.of(page, 5));
        Page<PlaylistDTO> playlistDTOS = playlists.map(this::convertToPlaylistDTO);
        return ResponseEntity.ok(playlistDTOS);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistDTO> getPlaylistById(@PathVariable Long playlistId) {
        Playlist playlist = playlistService.findById(playlistId);
        if (playlist == null) {
            return ResponseEntity.notFound().build();
        }
        PlaylistDTO playlistDTO = convertToPlaylistDTO(playlist);
        return ResponseEntity.ok(playlistDTO);
    }



    private PlaylistDTO convertToPlaylistDTO(Playlist playlist) {
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setPlaylistId(playlist.getPlaylistId());
        playlistDTO.setPlaylistName(playlist.getPlaylistName());
        playlistDTO.setPlaylistCode(playlist.getPlaylistCode());
        playlistDTO.setCoverImageUrl(playlist.getCoverImageUrl());
        playlistDTO.setDateCreate(playlist.getDateCreate());
        playlistDTO.setPlaylistStatus(playlist.isPlaylistStatus());
        playlistDTO.setDescription(playlist.getDescription());

        if (playlist.getPlayListSongs() != null) {
            List<SongOfPlaylistDTO> songOfPlaylistDTOS = playlist.getPlayListSongs().stream()
                    .map(playListSong -> {
                        //Convert Song to songPlaylistDTO
                        SongDTO songDTO = SongDTO.builder()
                                .songId(playListSong.getSong().getSongId())
                                .title(playListSong.getSong().getTitle())
                                .dateCreate(playListSong.getSong().getDateCreate())
                                .lyrics(playListSong.getSong().getLyrics())
                                .songUrl(playListSong.getSong().getSongUrl())
                                .duration(playListSong.getSong().getDuration())
                                .coverImageUrl(playListSong.getSong().getCoverImageUrl())
                                .genres(playListSong.getSong().getGenres())
                                .artists(playListSong.getSong().getArtists().stream()
                                        .map(artist -> ArtistOfSongDTO.builder()
                                                .artistId(artist.getArtistId())
                                                .artistName(artist.getArtistName())
                                                .build())
                                        .toList())
                                .build();

                        return SongOfPlaylistDTO.builder()
                                .id(playListSong.getId())
                                .songs(songDTO)
                                .dateAdd(playListSong.getDateAdd())
                                .build();
                    })
                    .toList();
            playlistDTO.setSongOfPlaylist(songOfPlaylistDTOS);
        }

        if (playlist.getAppUser() != null) {
            UserOfPlaylistDTO userOfPlaylistDTO = UserOfPlaylistDTO.builder()
                    .userId(playlist.getAppUser().getUserId())
                    .fullName(playlist.getAppUser().getFullName())
                    .build();
            playlistDTO.setAppUser(userOfPlaylistDTO);
        }

        return playlistDTO;
    }

}
