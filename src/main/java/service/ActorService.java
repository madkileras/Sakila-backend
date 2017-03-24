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

@Path("/actors")
public class ActorService {
	
	@EJB 
	ActorFacade actorFacadeEJB;

	@EJB 
	FilmFacade filmFacadeEJB;

	@EJB 
	Film_ActorFacade film_actorFacadeEJB;
	
	Logger logger = Logger.getLogger(ActorService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Actor> findAll(){
		return actorFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Actor find(@PathParam("id") Integer id) {
        return actorFacadeEJB.find(id);
    }

    // AQUI ESTA EL PRIMER GET DE PELICULAS EN LAS QUE HA PARTICIPADO EL ACTOR "X"
    @GET
    @Path("{id}/films")
    @Produces({"application/xml", "application/json"})
    public List<Film> FilmsOfActor(@PathParam("id") Integer id) {
        
        /*
            Bueno la idea fue obtener todos los datos necesarios por las querys básicas ya implementadas
            y de ahi hacer todo el proceso por java manualmente.

            Como se trabajo en las tables actor, film y film_actor, entonces se instancio el
            facade de estos, para poder utilizar sus funciones, asi como find() o findAll().

            Obtengo todos los elementos de la tabla intermedia film_actor, y comparo todos los 
            actor_id de esta tabla, con el obtenido por la consulta de URL.

            Cada vez que haya un match, obtengo el film_id correspondiente a ese actor.

            Luego obtengo todos los elementos de la tabla film, y extraigo el que tenga su film_id
            igual al que obtuve anteriormente, y lo voy agregando a una lista

            Finalmente despliego esta lista, donde estan todas las films que hicieron match con
            el id del actor entregado por URL
        */

        List<Film_Actor> l_fa = film_actorFacadeEJB.findAll();
        List<Film> l_filmes_originales = filmFacadeEJB.findAll();
        List<Film> l_filmes_resultantes = new ArrayList<Film>();
        Film film_actual = null;
        int cantidad = l_fa.size();
        int i, id_filme, id_actor;
        Film_Actor fa = null;
        for (i = 0; i < cantidad; i++) {
        	fa = l_fa.get(i);
        	id_actor = fa.getActorId();
        	id_filme = fa.getFilmId();
        	if(id_actor == id){  

                //Si el id del actor es igual al entregado por URL, procedo a obtener ese film deseado
        		film_actual = l_filmes_originales.get(id_filme-1);
        		l_filmes_resultantes.add(film_actual);
                
        	}
        }
        return l_filmes_resultantes;

    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Actor entity) {
        actorFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Actor entity) {
    	entity.setActorId(id.intValue());
        actorFacadeEJB.edit(entity);
    }
	
}
