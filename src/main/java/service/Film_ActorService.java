package service;

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

@Path("/films_actors")
public class Film_ActorService {

	@EJB
	Film_ActorFacade film_actorFacadeEJB;

	@EJB
	FilmFacade filmFacadeEJB;

	@EJB
	ActorFacade actorFacadeEJB;

	Logger logger = Logger.getLogger(Film_ActorService.class.getName());


	@GET
	@Produces({"application/xml", "application/json"})
	public List<Film_Actor> findAll(){
		return film_actorFacadeEJB.findAll();
	}

	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Film find(@PathParam("id") Integer id) {
        return filmFacadeEJB.find(id);
    }

    // FUNCION ENCARGADA DE LINKEAR PELICULA 1 A ACTOR 2
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Film_Actor entity) {

        //List<Actor> l_actores_originales = actorFacadeEJB.findAll();
		
		//List<Film> l_peliculas_originales = filmFacadeEJB.  findAll();

		Film pelicula = filmFacadeEJB.find(entity.getFilmId());
		
        Actor actot = actorFacadeEJB.find(entity.getActorId());

		if(pelicula != null && actot != null)
		{
			film_actorFacadeEJB.create(entity);
		}else{
			System.out.println("Pablo");
		}

	}

	/*
    // FUNCION ENCARGADA DE LINKEAR PELICULA 1 A ACTOR 2
    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Film_Actor entity) {
		film_actorFacadeEJB.create(entity);
	}*/

}
