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

import facade.Film_ActorFacade;
import model.Film_Actor;

@Path("/films_actors")
public class Film_ActorService {
	
	@EJB 
	Film_ActorFacade film_actorFacadeEJB;
	
	Logger logger = Logger.getLogger(Film_ActorService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Film_Actor> findAll(){
		return film_actorFacadeEJB.findAll();
	}
	

}
