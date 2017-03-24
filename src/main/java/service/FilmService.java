package service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.ActorFacade;
import facade.FilmFacade;
import facade.Film_ActorFacade;
import model.Actor;
import model.Film;
import model.Film_Actor;

@Path("/films")
public class FilmService {
	
	@EJB 
	FilmFacade filmFacadeEJB;

	@EJB 
	ActorFacade actorFacadeEJB;

	@EJB 
	Film_ActorFacade film_actorFacadeEJB;
	
	Logger logger = Logger.getLogger(FilmService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Film> findAll(){
		return filmFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Film find(@PathParam("id") Integer id) {
        return filmFacadeEJB.find(id);
    }

    // AQUI ESTA EL SEGUNDO GET DE ACTORES PRESENTES EN LA PELICULA "X"
    @GET
    @Path("{id}/actors")
    @Produces({"application/xml", "application/json"})
    public List<Actor> ActorsOfFilm(@PathParam("id") Integer id) {

    	/*
            Bueno la idea fue obtener todos los datos necesarios por las querys básicas ya implementadas
            y de ahi hacer todo el proceso por java manualmente.

            Como se trabajo en las tables actor, film y film_actor, entonces se instancio el
            facade de estos, para poder utilizar sus funciones, asi como find() o findAll().

            La logica es la misma para el get de las peliculas en que participó un actor, pero como se pide
            lo contrario, lo unico que se hizo fue cambiar todos los film por actor y viceversa

        */
   
        List<Film_Actor> l_fa = film_actorFacadeEJB.findAll();
        List<Actor> l_actores_originales = actorFacadeEJB.findAll();
        List<Actor> l_actores_resultantes = new ArrayList<Actor>();
        Actor actor_actual = null;
        int cantidad = l_fa.size();
        int i, id_filme, id_actor;
        Film_Actor fa = null;
        for (i = 0; i < cantidad; i++) {
        	fa = l_fa.get(i);
        	id_actor = fa.getActorId();
        	id_filme = fa.getFilmId();
        	if(id_filme == id){

        		//Si el id del film es igual al entregado por URL, procedo a obtener ese actor deseado
        		actor_actual = l_actores_originales.get(id_actor-1);
        		l_actores_resultantes.add(actor_actual);

        	}
        }
        return l_actores_resultantes;

    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Film entity) {
        filmFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Film entity) {
    	entity.setFilmId(id.intValue());
        filmFacadeEJB.edit(entity);
    }
	

}
