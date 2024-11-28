package com.project.musicwebbe.service.artist;

import com.project.musicwebbe.entities.Artist;
import com.project.musicwebbe.service.IGeneralService;

import java.util.List;

public interface IArtistService extends IGeneralService<Artist> {
    Artist saveD(Artist artist);

    List<Artist> searchAllByArtistName(String artistName);

    Artist findByName(String artistName);
}
