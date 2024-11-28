package com.project.musicwebbe.controller.publicController;

import com.project.musicwebbe.entities.Song;
import com.project.musicwebbe.entities.SongListen;
import com.project.musicwebbe.service.song.impl.SongListenService;
import com.project.musicwebbe.service.song.impl.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/public/song-listens")
public class SongListenRestController {

    @Autowired
    private SongListenService songListenService;

    @Autowired
    private SongService songService;

    @PutMapping
    public ResponseEntity<SongListen> updateListen(@RequestBody Song song) {
        SongListen songListen = songListenService.findBySongIdToday(song.getSongId());
        if (songListen == null) {
            SongListen newSongListen = new SongListen();
            newSongListen.setSong(song);
            newSongListen.setDateCreate(LocalDate.now());
            newSongListen.setTotal(1);
            songListenService.save(newSongListen);
        } else {
            songListen.setTotal(songListen.getTotal() + 1);
            songListenService.save(songListen);
        }
        return ResponseEntity.ok(songListen);
    }

}
