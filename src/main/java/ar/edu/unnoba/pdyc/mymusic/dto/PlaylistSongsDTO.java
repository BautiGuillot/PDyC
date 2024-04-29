package ar.edu.unnoba.pdyc.mymusic.dto;

import java.util.List;

public class PlaylistSongsDTO {
    private String name;
    private List<String> songs;

    public PlaylistSongsDTO(String name, List<String> songs) {
        this.name = name;
        this.songs = songs;
    }

    public PlaylistSongsDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }
}