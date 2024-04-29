package ar.edu.unnoba.pdyc.mymusic.dto;

public class PlaylistDTO {
    private String name;
    private int songCount;

    public PlaylistDTO(String name, int songCount) {
        this.name = name;
        this.songCount = songCount;
    }

    public PlaylistDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }
}