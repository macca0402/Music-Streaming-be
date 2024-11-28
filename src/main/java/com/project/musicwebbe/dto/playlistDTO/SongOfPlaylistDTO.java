package com.project.musicwebbe.dto.playlistDTO;


import com.project.musicwebbe.dto.songDTO.SongDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class SongOfPlaylistDTO {

    private Long id;

    private LocalDateTime dateAdd;

    private SongDTO songs;

}
