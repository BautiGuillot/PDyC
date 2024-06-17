package ar.edu.unnoba.pdyc.mymusic.repository;

import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
    List<Playlist> findByOwner_Id(Long id); //buscar las playlists de un usuario por su id
}
