package com.project.musicwebbe.entities;

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
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long albumId;

    private String title;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    private String coverImageUrl; // URL tới ảnh bìa album

    @OneToMany(mappedBy = "album", fetch = FetchType.LAZY)
    private List<Song> songs;

    @Column(name = "provide")
    private String provide;

    @ManyToMany()
    @JoinTable(
            name = "artist_album",
            joinColumns = @JoinColumn(name = "album_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<Artist> artists;
}