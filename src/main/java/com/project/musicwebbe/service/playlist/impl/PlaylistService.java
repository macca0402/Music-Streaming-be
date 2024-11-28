package com.project.musicwebbe.service.playlist.impl;

import com.project.musicwebbe.entities.Playlist;
import com.project.musicwebbe.repository.PlaylistRepository;
import com.project.musicwebbe.service.playlist.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService implements IPlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Override
    public List<Playlist> findAll() {

        return playlistRepository.findAll();
    }

    @Override
    public Page<Playlist> findAll(Pageable pageable) {

        return playlistRepository.findAll(pageable);
    }

    @Override
    public Playlist findById(Long id) {

        return playlistRepository.findById(id).orElse(null);
    }

    @Override
    public void remove(Long id) {

        playlistRepository.deleteById(id);
    }

    @Override
    public void save(Playlist playlist) {

        playlistRepository.save(playlist);
    }
    @Override
    public Page<Playlist> searchAllByPlaylistName(String playlistName, Pageable pageable) {
        return playlistRepository.searchAllByPlaylistNameContaining(playlistName, pageable);
    }
}
