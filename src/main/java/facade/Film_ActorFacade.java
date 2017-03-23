package facade;

import java.util.List;

import javax.ejb.Local;

import model.Film_Actor;

@Local
public interface Film_ActorFacade {

	public void create(Film_Actor entity);

	public void edit(Film_Actor entity);

	public void remove(Film_Actor entity);

	public Film_Actor find(Object id);

	public List<Film_Actor> findAll();

	public List<Film_Actor> findRange(int[] range);

	public int count();

}