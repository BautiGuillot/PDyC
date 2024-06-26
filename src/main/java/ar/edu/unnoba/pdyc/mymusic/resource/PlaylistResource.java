package ar.edu.unnoba.pdyc.mymusic.resource;

import ar.edu.unnoba.pdyc.mymusic.dto.PlaylistDTO;
import ar.edu.unnoba.pdyc.mymusic.dto.SongDTO;
import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.service.PlaylistService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Path("/playlists")
public class PlaylistResource {

    @Autowired
    private PlaylistService playlistService;

    //Consultar playlists, Retorna JSON con el listado con el nombre de las playlists y la cantidad de canciones que tiene
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists() {
        List<Playlist> playlists = playlistService.getPlaylists();

        List<PlaylistDTO> playlistsInfo = playlists.stream()        //convertir la lista de playlists a una lista de PlaylistDTO
                .map(this::mapToPlaylistInfo)                       //usando el metodo mapToPlaylistInfo
                .collect(Collectors.toList());                      //y coleccionando los resultados en una lista

        return Response.ok(playlistsInfo).build();                  //devolver la lista de PlaylistDTO

    }

    //Mapear una playlist a un PlaylistDTOP con el nombre y la cantidad de canciones que tiene la playlist
    private PlaylistDTO mapToPlaylistInfo(Playlist playlist) {
        PlaylistDTO playlistInfo = new PlaylistDTO();            //crear un DTO de playlist
        playlistInfo.setId(playlist.getId());                    //setear el id de la playlist en el DTO con el id de la playlist
        playlistInfo.setName(playlist.getName());              //setear el nombre de la playlist en el DTO con el nombre de la playlist
        playlistInfo.setSongCount(playlist.getSongs().size()); //setear la cantidad de canciones de la playlist en el DTO con la cantidad de canciones de la playlist
        return playlistInfo;
    }


    //crear una playlist
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlaylist(PlaylistDTO playlistDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtener el usuario autenticado
        String mail = authentication.getName(); //obtener el nombre del usuario autenticado (mail)
        playlistService.createPlaylist(playlistDTO.getName(), mail); //crear la playlist con el nombre y el propietario
        return Response.status(Response.Status.CREATED).build();
    }

    //agregar una cancion a una playlist el id de la playlist se pasa por parametro en la URL y el id de la cancion se pasa en el body
    @POST
    @Path("/{id}/songs/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSongToPlaylistRes(@PathParam("id") Long playlistId, SongDTO songDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtener el usuario autenticado
        String mail = authentication.getName(); //obtener el nombre del usuario autenticado (mail)
           playlistService.addSongToPlaylist(songDTO.getId(), playlistId, mail);
            return Response.status(Response.Status.CREATED).build();
    }

    //eliminar una canción de una playlist
    @DELETE
    @Path("/{id}/songs/{song_id}")
    public Response removeSongFromPlaylistRes(@PathParam("id") Long playlistId, @PathParam("song_id") Long songId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtener el usuario autenticado
        String mail = authentication.getName(); //obtener el nombre del usuario autenticado (mail)
        playlistService.removeSongFromPlaylist(songId, playlistId, mail);
        return Response.status(Response.Status.OK).build();
    }

    //cambiar el nombre de una playlist por su id en la url y el nuevo nombre en el body
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlaylist(@PathParam("id") Long id, String newName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtener el usuario autenticado
        String mail = authentication.getName(); //obtener el nombre del usuario autenticado (mail)
        playlistService.updatePlaylistName(id, newName, mail);
        return Response.status(Response.Status.OK).build();
    }

    //consultar canciones de una playlist
    @GET
    @Path("/{id}/songs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSongsFromPlaylist(@PathParam("id") Long playlistId) {
        List<Song> songs = playlistService.getSongsFromPlaylist(playlistId); //obtener las canciones de la playlist

        List<SongDTO> songsInfo = songs.stream() //convertir la lista de canciones a una lista de CreateSongDTO con el nombre, autor y genero de la cancion
                .map(this::mapToSongInfo)
                .collect(Collectors.toList());
        return Response.ok(songsInfo).build();
    }

    private SongDTO mapToSongInfo(Song song) { //metodo para mapear una cancion a un SongDTO con el id, nombre, autor y genero de la cancion
        SongDTO songInfo = new SongDTO();
        songInfo.setId(song.getId());
        songInfo.setName(song.getName());
        songInfo.setAutor(song.getAutor());
        songInfo.setGenre(song.getGenre());
        return songInfo;
    }


    //eliminar una playlist
    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtener el usuario autenticado
        String mail = authentication.getName(); //obtener el nombre del usuario autenticado (mail)
        playlistService.deletePlaylist(id, mail);
        return Response.status(Response.Status.OK).build();
    }

    //consultar playlists de un usuario autenticado
    @GET
    @Path("/misPlaylists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyPlaylists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtener el usuario autenticado
        String mail = authentication.getName(); //obtener el nombre del usuario autenticado (mail)
        List<Playlist> playlists = playlistService.getPlaylistsByUser(mail);

        List<PlaylistDTO> playlistsInfo = playlists.stream()        //convertir la lista de playlists a una lista de PlaylistDTO
                .map(this::mapToPlaylistInfo)                       //usando el metodo mapToPlaylistInfo
                .collect(Collectors.toList());                      //y coleccionando los resultados en una lista

        return Response.ok(playlistsInfo).build();                  //devolver la lista de PlaylistDTO

    }
}
