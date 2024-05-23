package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;

import java.util.List;

public interface PlaylistService {

    void createPlaylist(String name, String mail);

    void deletePlaylist(Long playlistId,String mail);

    void addSongToPlaylist(Long songId, Long playlistId, String mail);

    void removeSongFromPlaylist(Long songId, Long playlistId, String mail);

    void updatePlaylistName(Long playlistId, String nameSong, String mail);

    List<Playlist> getPlaylists();

    List<Song> getSongsFromPlaylist(Long playlistId);

    Playlist getPlaylistByID(Long playlistId);

}
