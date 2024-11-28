package com.project.musicwebbe.entities;

import com.project.musicwebbe.entities.permission.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlists")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "playlist_name")
    private String playlistName;

    private String playlistCode;

    private String coverImageUrl; // URL tới ảnh bìa playlist

    private boolean playlistStatus = false;

    @Column(name = "playList_desc", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "playlist")
    private List<PlayListSong> playListSongs;
}