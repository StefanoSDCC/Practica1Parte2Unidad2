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
import org.unl.music.base.controller.dao.dao_models.DaoGenero;
import org.unl.music.base.models.Genero;

import java.util.Arrays;
import java.util.List;

@BrowserCallable
@Transactional(propagation = Propagation.REQUIRES_NEW)
@AnonymousAllowed

public class GeneroService {
    private static final Logger log = LogManager.getLogger(GeneroService.class);
    private DaoGenero da;
    public GeneroService() {
        da = new DaoGenero();
    }

    public void createGenero(@NotEmpty String nombre) throws Exception{
        da.getObj().setNombre(nombre);
        if(!da.save())
            throw new  Exception("No se pudo guardar los datos del genero");
    }

    public List<Genero> list(Pageable pageable) {
        return Arrays.asList(da.listAll().toArray());
    }
    public List<Genero> listAll() {

        return (List<Genero>)Arrays.asList(da.listAll().toArray());
    }



   public void updateGenero(@NotNull Integer id, @NotEmpty String nombre)throws Exception {

       da.setObj(da.listAll().get(id - 1));
       da.getObj().setNombre(nombre);
       if(!da.update(id - 1))
           throw new  Exception("No se pudo modificar los datos del genero");
   }

}
