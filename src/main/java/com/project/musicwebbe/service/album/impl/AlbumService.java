package com.project.musicwebbe.service.album.impl;

import com.project.musicwebbe.entities.Album;
import com.project.musicwebbe.repository.AlbumRepository;
import com.project.musicwebbe.service.album.IAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService implements IAlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    @Override
    public Page<Album> findAll(Pageable pageable) {
        return albumRepository.findAll(pageable);
    }

    @Override
    public List<Album> findByAlbumName(String albumName) {
        return albumRepository.searchAllByTitleContaining(albumName);
    }

    @Override
    public Page<Album> searchAllByTitleAndArtistName(String title, String artistName, Pageable pageable) {
        return albumRepository.searchAllByTitleAndArtistName(title, artistName, pageable);
    }

    @Override
    public Album findById(Long id) {

        return albumRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Album album) {

        albumRepository.save(album);
    }

    @Override
    public void remove(Long id) {

        albumRepository.deleteById(id);
    }

    @Override
    public Album saveD(Album album) {
        return albumRepository.save(album);
    }
}
