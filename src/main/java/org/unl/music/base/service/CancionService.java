package org.unl.music.base.service;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.unl.music.base.controller.dao.dao_models.DaoAlbum;
import org.unl.music.base.controller.dao.dao_models.DaoCancion;
import org.unl.music.base.controller.dao.dao_models.DaoGenero;
import org.unl.music.base.controller.data_struct.list.LinkedList;
import org.unl.music.base.models.*;


import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class CancionService {
    private DaoCancion db;
    public CancionService(){
        db = new DaoCancion();
    }

    public void create(@NotEmpty String nombre, Integer id_genero, Integer duracion,
                    @NotEmpty String url, @NotEmpty String tipo, Integer id_album) throws Exception {
        if(nombre.trim().length() > 0 && url.trim().length() > 0 &&
                tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_album > 0) {
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setId_album(id_album);
            db.getObj().setId_genero(id_genero);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            if(!db.save())
                throw new  Exception("No se pudo guardar los datos de la banda");
        }
    }

    public void update(Integer id, @NotEmpty String nombre, Integer id_genero, Integer duracion, @NotEmpty String url, @NotEmpty String tipo, Integer id_album) throws Exception {
        if(nombre.trim().length() > 0 && url.trim().length() > 0 && tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_album > 0) {
            db.setObj(db.listAll().get(id - 1));
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setId_album(id_album);
            db.getObj().setId_genero(id_genero);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            if(!db.update(id - 1))
                throw new  Exception("No se pudo modificar los datos de la banda");
        }
    }

    public List<HashMap> listaAlbumCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoAlbum da = new DaoAlbum();
        if(!da.listAll().isEmpty()) {
            Album [] arreglo = da.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listaAlbumGenero() {
        List<HashMap> lista = new ArrayList<>();
        DaoGenero da = new DaoGenero();
        if(!da.listAll().isEmpty()) {
            Genero [] arreglo = da.listAll().toArray();
            for(int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<String> listTipo() {
        List<String> lista = new ArrayList<>();
        for(TipoArchivoEnum r: TipoArchivoEnum.values()) {
            lista.add(r.toString());
        }
        return lista;
    }

    public List<HashMap> listCancion() throws Exception {
        LinkedList<HashMap<String, String>> lista = db.all(db.listAll());
        return Arrays.asList(lista.toArray());

    }

    public List<HashMap> order(String atributo, Integer type) throws Exception {
        System.out.println(atributo + "  " + type);
        return switch (atributo.toLowerCase()){
            case "nombre" -> Arrays.asList(db.orderName(type).toArray());
            case "url" -> Arrays.asList(db.orderUrl(type).toArray());
            case "duracion" -> Arrays.asList(db.orderDuracion(type).toArray());
            case "tipo" -> Arrays.asList(db.orderTipo(type).toArray());
            case "genero" -> Arrays.asList(db.orderGenero(type).toArray());
            case "album" -> Arrays.asList(db.orderAlbum(type).toArray());
            default -> Arrays.asList((HashMap) listCancion());

        };
    }


    public List<HashMap> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = db.search(attribute, text, type);
        if(!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }

    public List<HashMap> searchBinaryLineal(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = db.searchBinary(attribute, text, type);
        if(!lista.isEmpty())
            return Arrays.asList(lista.toArray());
        else
            return new ArrayList<>();
    }

    public static void main(String[] args) throws Exception {
        CancionService cancionService = new CancionService();
        DaoAlbum daoAlbum = new DaoAlbum();
        List<HashMap> busquedaMapa = cancionService.search("nombre","hola",1);
        System.out.println("Lista buscada");
        for (HashMap cancion : busquedaMapa){
            System.out.println(cancion.get("nombre"));

        }
    }
}






