package com.project.musicwebbe.service.song;

import com.project.musicwebbe.entities.Song;
import com.project.musicwebbe.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISongService extends IGeneralService<Song> {
    List<Song> searchAllByTitle(String title);

    Page<Song> findAllTopSongByNational(String national, Pageable pageable);
}
