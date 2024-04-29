package ar.edu.unnoba.pdyc.mymusic.resource;

import ar.edu.unnoba.pdyc.mymusic.dto.PlaylistDTO;
import ar.edu.unnoba.pdyc.mymusic.dto.PlaylistSongsDTO;
import ar.edu.unnoba.pdyc.mymusic.dto.SongDTO;
import ar.edu.unnoba.pdyc.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc.mymusic.model.Song;
import ar.edu.unnoba.pdyc.mymusic.service.PlaylistService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

        List<PlaylistDTO> playlistsInfo = playlists.stream()
                .map(this::mapToPlaylistInfo)
                .collect(Collectors.toList());

        return Response.ok(playlistsInfo).build();

    }

    //Mapear una playlist a un PlaylistDTOP con el nombre y la cantidad de canciones que tiene la playlist
    private PlaylistDTO mapToPlaylistInfo(Playlist playlist) {
        PlaylistDTO playlistInfo = new PlaylistDTO();
        playlistInfo.setName(playlist.getName());
        playlistInfo.setSongCount(playlist.getSongs().size());
        return playlistInfo;
    }


    //crear una playlist
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPlaylist(PlaylistDTO playlistDTO) {
        playlistService.createPlaylist(playlistDTO.getName());
        return Response.status(Response.Status.CREATED).build();
    }

    //agregar una cancion a una playlist el id de la playlist se pasa por parametro en la URL y el id de la cancion se pasa en el body
    @POST
    @Path("/{id}/songs/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSongToPlaylistRes(@PathParam("id") Long playlistId, SongDTO songDTO) {
           playlistService.addSongToPlaylist(songDTO.getId(), playlistId);
            return Response.status(Response.Status.CREATED).build();
    }

    //eliminar una canción de una playlist
    @DELETE
    @Path("/{id}/songs/{song_id}")
    public Response removeSongFromPlaylistRes(@PathParam("id") Long playlistId, @PathParam("song_id") Long songId) {
        playlistService.removeSongFromPlaylist(songId, playlistId);
        return Response.status(Response.Status.OK).build();
    }

    //cambiar el nombre de una playlist por su id en la url y el nuevo nombre en el body
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePlaylist(@PathParam("id") Long id, PlaylistDTO playlistDTO) {
        playlistService.updatePlaylistName(id, playlistDTO.getName());
        return Response.status(Response.Status.OK).build();
    }

    //consultar canciones de una playlist
    @GET
    @Path("/{id}/songs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSongsFromPlaylist(@PathParam("id") Long playlistId) {
        Playlist playlist = playlistService.getPlaylistByID(playlistId);
        List<String> songNames = playlistService.getSongsFromPlaylist(playlistId).stream()
                .map(Song::getName)
                .collect(Collectors.toList());
        PlaylistSongsDTO playlistSongs = new PlaylistSongsDTO(playlist.getName(), songNames);
        return Response.ok(playlistSongs).build();
    }


    //eliminar una playlist
    @DELETE
    @Path("/{id}")
    public Response deletePlaylist(@PathParam("id") Long id) {
        playlistService.deletePlaylist(id);
        return Response.status(Response.Status.OK).build();
    }
}
