package ar.edu.unnoba.pdyc.mymusic;

import ar.edu.unnoba.pdyc.mymusic.resource.PlaylistResource;
import ar.edu.unnoba.pdyc.mymusic.resource.SongResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(PlaylistResource.class);
        register(SongResource.class);
        // Puedes registrar más recursos aquí si los tienes
    }
}