package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;

import java.util.List;

public interface PlaylistService {

    void createPlaylist(String name);

    void deletePlaylist(Long playlistId);

    void addSongToPlaylist(Long songId, Long playlistId);

    void removeSongFromPlaylist(Long songId, Long playlistId);

    void updatePlaylistName(Long playlistId, String name);

    List<Playlist> getPlaylists();

    List<Song> getSongsFromPlaylist(Long playlistId);

    Playlist getPlaylistByID(Long playlistId);

}
