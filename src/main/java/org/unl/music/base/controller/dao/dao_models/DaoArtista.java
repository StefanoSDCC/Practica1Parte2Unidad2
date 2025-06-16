package org.unl.music.base.controller.dao.dao_models;

import org.unl.music.base.models.Artista;

import org.unl.music.base.controller.dao.AdapterDao;
import org.unl.music.base.models.Banda;
import org.unl.music.base.models.RolArtistaEnum;

import java.util.Date;
import java.util.HashMap;

public class DaoArtista extends AdapterDao<Artista> {
    private Artista obj;


    public DaoArtista() {
        super(Artista.class);
        //obje = new Banda();
        // TODO Auto-generated constructor stub
    }

    public Artista getObj() {
        if (obj == null)
            this.obj = new Artista();
        return this.obj;
    }

    public void setObj(Artista obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getLength() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }

    public Boolean updateId(Integer id) {
        try {
            this.update(obj, id);
            return true;
        } catch (Exception e) {
            //TODO
            return false;
            // TODO: handle exception
        }
    }
}


