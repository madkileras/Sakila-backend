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
    @Path("actors/{id}/films/{id2}")
    @Produces({"application/xml", "application/json"})
    public void createFilmActor(@PathParam("id") Integer id,@PathParam("id2") Integer id2) {

        //List<Film_Actor> l_fa = film_actorFacadeEJB.findAll();
        List<Actor> l_actores_originales = actorFacadeEJB.findAll();
        //List<Actor> l_actores_resultantes = new ArrayList<Actor>();
				List<Film> l_peliculas_originales = filmFacadeEJB.findAll();
        //List<Film> peliculas_resultantes = new ArrayList<Film>();
        Actor actor_actual = null;
				Film film_actual = null;
        int cantidad1 = l_actores_originales.size();
				int cantidad2 = l_peliculas_originales.size();

        int i, id_film, id_actor;

				//Se revisa la lista de actores, buscando el actor con el ID designado
        for (i = 0; i < cantidad1; i++) {

					id_actor = l_actores_originales.get(i).getActorId();

        	if(id_actor == id){

        		//Si el id del film es igual al entregado por URL, procedo a obtener ese actor deseado
        		actor_actual = l_actores_originales.get(id_actor-1);
						i=cantidad1;

        	}
        }

				//Se revisa la lista de peliculas, buscando la pelicula con el ID designado
				for (i = 0; i < cantidad2 ; i++) {

					id_film = l_peliculas_originales.get(i).getFilmId();

						if(id_film == id2)
						{
							film_actual = l_peliculas_originales.get(id_film-1);
							i = cantidad2;
						}
				}

				//Si existen ambos, se procede con el método POST
				if(actor_actual != null && film_actual != null)
				{
						//POST
						//getClichedMessage();
				}
				else
				{
					String msje = "Error, no existe pelicula o actor.\n";
					//return msje;
				}

        //return l_actores_resultantes;

    }
}
