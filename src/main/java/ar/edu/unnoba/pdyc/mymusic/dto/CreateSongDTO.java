package ar.edu.unnoba.pdyc.mymusic.dto;

import ar.edu.unnoba.pdyc.mymusic.model.Genre;

public class CreateSongDTO {
    private String name;
    private String autor;
    private Genre genre;

    public CreateSongDTO() {
    }

    public CreateSongDTO(String name, String autor, Genre genre) {
        this.name = name;
        this.autor = autor;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public String getAutor() {
        return autor;
    }

    public Genre getGenre() {
        return genre;
    }


}
