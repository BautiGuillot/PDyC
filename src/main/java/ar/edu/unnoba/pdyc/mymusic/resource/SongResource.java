package ar.edu.unnoba.pdyc.mymusic.resource;

import ar.edu.unnoba.pdyc.mymusic.dto.CreateSongDTO;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.service.SongService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;



@Path("/songs")
public class SongResource {

    @Autowired
    private SongService songService;

    //Consultar las canciones disponibles
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSongs() {                                    //esta bien que no haga el mapper?
        List<Song> songs = songService.getSongs();
        return Response.ok(songs).build();
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
