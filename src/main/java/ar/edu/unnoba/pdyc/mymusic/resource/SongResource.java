package ar.edu.unnoba.pdyc.mymusic.resource;

import ar.edu.unnoba.pdyc.mymusic.dto.CreateSongDTO;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.service.SongService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;


@Path("/songs")
public class SongResource {

    @Autowired
    private SongService songService;

    //Consultar las canciones disponibles
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSongs() {
        List<Song> songs = songService.getSongs();

        List<CreateSongDTO> songsInfo = songs.stream()
                .map(this::mapToSongInfo)
                .collect(Collectors.toList());
        return Response.ok(songsInfo).build();
    }

    //Mapear una cancion a un CreateSongDTO con el nombre, autor y genero de la cancion
    private CreateSongDTO mapToSongInfo(Song song) {
        CreateSongDTO songInfo = new CreateSongDTO();
        songInfo.setName(song.getName());
        songInfo.setAutor(song.getAutor());
        songInfo.setGenre(song.getGenre());
        return songInfo;
    }

    //crear una cancion
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSong(CreateSongDTO songDTO) {
        try {
            songService.createSong(songDTO.getName(), songDTO.getAutor(), songDTO.getGenre());
            return Response.status(Response.Status.CREATED).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El género proporcionado no es válido. Los géneros válidos son: rock, techno, pop, jazz, folk, classical.")
                    .build();
        }
    }




}
