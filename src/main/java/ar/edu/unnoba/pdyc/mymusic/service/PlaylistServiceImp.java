package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.repository.PlaylistRepository;
import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImp implements PlaylistService {

    @Autowired
    private PlaylistRepository repository;

    @Autowired
    private SongService songService;

    @Override
    public void createPlaylist(String name) {
        Playlist playlist = new Playlist(name);
        repository.save(playlist);
    }

    @Override
    public void deletePlaylist(Long playlistId) {
        repository.deleteById(playlistId);
    }

    @Transactional  // This annotation is needed to avoid LazyInitializationException
    @Override
    public void addSongToPlaylist(Long songId, Long playlistId) {
        Song song = songService.getSong(songId);
        Playlist playlist = repository.findById(playlistId).get();
        playlist.getSongs().size(); // Initialize the songs collection
        playlist.addSongToPlaylist(song);
        repository.save(playlist);
    }

    @Transactional
    @Override
    public void removeSongFromPlaylist(Long songId, Long playlistId) {
        Playlist playlist = repository.findById(playlistId).get();
        Song song = songService.getSong(songId);
        playlist.getSongs().remove(song);
        repository.save(playlist);
    }

    @Override
    public void updatePlaylistName(Long playlistId, String name) {
        Playlist playlist = repository.findById(playlistId).get();
        playlist.setName(name);
        repository.save(playlist);
    }

    @Transactional
    @Override
    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = repository.findAll();
        for (Playlist playlist : playlists) {
            playlist.getSongs().size(); // Forzamos la carga de las canciones para evitar LazyInitializationException en el recurso PlaylistResource cuando se mapea a PlaylistDTO en el método mapToPlaylistInfo de PlaylistResource
        }
        return playlists;
    }


    @Transactional
    @Override
    public List<Song> getSongsFromPlaylist(Long playlistId) {
        Playlist playlist = repository.findById(playlistId).get();
        playlist.getSongs().size(); // Forzamos la carga de las canciones
        return playlist.getSongs();
    }

    @Override
    public Playlist getPlaylistByID(Long playlistId) {
        return repository.findById(playlistId).get();
    }
}
