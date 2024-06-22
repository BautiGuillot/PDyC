package ar.edu.unnoba.pdyc.mymusic.service;

import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.model.User;
import ar.edu.unnoba.pdyc.mymusic.repository.PlaylistRepository;
import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PlaylistServiceImp implements PlaylistService {

    @Autowired
    private PlaylistRepository repository;

    @Autowired
    private SongService songService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createPlaylist(String name, String mail) {
        User owner = userRepository.findByEmail(mail);
        Playlist playlist = new Playlist(name, owner);
        repository.save(playlist);
    }

    @Override
    public void deletePlaylist(Long playlistId, String mail) {
        Playlist playlist = repository.findById(playlistId).get();
        if (!isOwner(playlist, mail))
            throw new RuntimeException("You are not the owner of the playlist");
        repository.deleteById(playlistId);
    }

    @Transactional
    @Override
    public void addSongToPlaylist(Long songId, Long playlistId, String mail) {
        Song song = songService.getSong(songId);
        Playlist playlist = repository.findById(playlistId).get();
        if(!isOwner(playlist, mail))
            throw new RuntimeException("You are not the owner of the playlist");
        if (validateSongPresence(playlist, song))
            throw new RuntimeException("The song is already in the playlist");
        playlist.getSongs().size();
        playlist.addSongToPlaylist(song);
        repository.save(playlist);
    }

    @Transactional
    @Override
    public void removeSongFromPlaylist(Long songId, Long playlistId, String mail) {
        Playlist playlist = repository.findById(playlistId).get();
        Song song = songService.getSong(songId);
        if(!isOwner(playlist, mail))
            throw new RuntimeException("You are not the owner of the playlist");
        if (!validateSongPresence(playlist, song))
            throw new RuntimeException("The song is not in the playlist");
        playlist.getSongs().remove(song);
        repository.save(playlist);
    }

    @Override
    public void updatePlaylistName(Long playlistId, String newName, String nameOwner) {
        Playlist playlist = repository.findById(playlistId).get();
        if (!isOwner(playlist, nameOwner))
            throw new RuntimeException("You are not the owner of the playlist");
        playlist.setName(newName);
        repository.save(playlist);
    }

    @Transactional
    @Override
    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = repository.findAll();
        for (Playlist playlist : playlists) {
            playlist.getSongs().size();
        }
        return playlists;
    }

    @Transactional
    @Override
    public List<Song> getSongsFromPlaylist(Long playlistId) {
        Playlist playlist = repository.findById(playlistId).get();
        playlist.getSongs().size();
        return playlist.getSongs();
    }

    @Override
    public Playlist getPlaylistByID(Long playlistId) {
        return repository.findById(playlistId).get();
    }

    @Transactional
    @Override
    public List<Playlist> getPlaylistsByUser(String mail) {
        User user = userRepository.findByEmail(mail);
        if (user != null) {
            List<Playlist> playlists = repository.findByOwner_Id(user.getId()); //buscar las playlists de un usuario por su id
            playlists.forEach(playlist -> {    //inicializa la lista de canciones de cada playlist
                Hibernate.initialize(playlist.getSongs());
            });
            return playlists;
        }
        return null;
    }

    private boolean isOwner(Playlist playlist, String mail) {       //retorna true si el mail pasado por parametro es igual al mail del propietario de la playlist
        return Objects.equals(playlist.getOwner().getEmail(), mail);
    }

    private boolean validateSongPresence(Playlist playlist, Song song) {    //retorna true si la playlist contiene la cancion
        return playlist.getSongs().contains(song);
    }


}


