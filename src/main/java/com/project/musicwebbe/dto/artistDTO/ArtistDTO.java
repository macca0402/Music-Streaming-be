package com.project.musicwebbe.dto.artistDTO;

import com.project.musicwebbe.entities.Artist;
import com.project.musicwebbe.entities.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDTO {
    private Long artistId;

    private String artistName;

    private String avatar;

    private String biography;

    private List<Genre> genres;

    private List<SongOfArtistDTO> songs;

    private List<AlbumOfArtistDTO> albums;

    public ArtistDTO convertToArtistDTO(Artist artist) {
        List<Genre> genres = artist.getGenres();

        List<SongOfArtistDTO> songOfArtistDTOS = artist.getSongs().stream()
                .map(song -> SongOfArtistDTO.builder()
                        .songId(song.getSongId())
                        .title(song.getTitle())
                        .dateCreate(song.getDateCreate())
                        .lyrics(song.getLyrics())
                        .songUrl(song.getSongUrl())
                        .duration(song.getDuration())
                        .coverImageUrl(song.getCoverImageUrl())
                        .build())
                .toList();
        // Chuyển đổi các nghệ sĩ (artists) liên kết với album sang AlbumArtistDTO
        List<AlbumOfArtistDTO> artistAlbumDTOs = artist.getAlbums().stream()
                .map(album -> AlbumOfArtistDTO.builder()
                        .albumId(album.getAlbumId())
                        .title(album.getTitle())
                        .dateCreate(album.getDateCreate())
                        .provide(album.getProvide())
                        .build())
                .toList();

        // Tạo và trả về AlbumDTO
        return ArtistDTO.builder()
                .artistId(artist.getArtistId())
                .artistName(artist.getArtistName())
                .avatar(artist.getAvatar())
                .biography(artist.getBiography())
                .genres(genres)
                .songs(songOfArtistDTOS)
                .albums(artistAlbumDTOs)
                .build();
    }
}
