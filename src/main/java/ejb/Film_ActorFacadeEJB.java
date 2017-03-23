package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.Film_ActorFacade;
import model.Film_Actor;

@Stateless
public class Film_ActorFacadeEJB extends AbstractFacade<Film_Actor> implements Film_ActorFacade {
	
	
	@PersistenceContext(unitName = "sakilaPU")
	private EntityManager em;
	
	public Film_ActorFacadeEJB() {
		super(Film_Actor.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}
