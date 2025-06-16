package org.unl.music.base.service;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.unl.music.base.controller.dao.dao_models.DaoAlbum;

import org.unl.music.base.controller.dao.dao_models.DaoBanda;
import org.unl.music.base.models.Album;
import org.unl.music.base.models.Banda;


import java.util.*;



@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class AlbumService {
    private static final Logger log = LogManager.getLogger(AlbumService.class);
    private DaoAlbum da;

    public AlbumService() {
        da = new DaoAlbum();

    }

    public void createAlbum(@NotEmpty String nombre, @NotNull Date fecha, @NotNull Integer id_banda) throws Exception {
        da.getObj().setNombre(nombre);
        da.getObj().setFecha(fecha);
        da.getObj().setId_banda(id_banda);
        log.info("esta es la:{}", fecha);

        if (!da.save())
            throw new Exception("No se pudo guardar los datos del genero");
    }

    public List<Album> list(Pageable pageable) {
        return Arrays.asList(da.listAll().toArray());
    }

    public List<Album> listAll() {
        List<Album> albums = Arrays.asList(da.listAll().toArray());
        Banda[] bandas = new DaoBanda().listAll().toArray();

        for (Album album : albums) {
            Integer bandaId = album.getId_banda();
            for (Banda b : bandas) {
                if (b.getId().equals(bandaId)) {
                    album.setBanda(b);
                    break;
                }
            }
        }

        return albums;
    }

    public void updateAlbum(@NotNull Integer id, @NotEmpty String nombre, @NotNull Date fecha, @NotNull Integer id_banda) throws Exception {
        log.info("DAtos recibidos{} ", id);
        int pos = id - 1;
        Album de = new Album();
        de = da.get(pos);
        de.setNombre(nombre);
        de.setFecha(fecha);
        de.setId_banda(id_banda);
        da.update(de, pos);
    }

}



