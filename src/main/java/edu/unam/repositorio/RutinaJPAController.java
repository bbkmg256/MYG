package edu.unam.repositorio;

// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Entidad
import edu.unam.modelo.Rutina;

/**
*
* @author bbkmg
*/
public class RutinaJPAController {
	// Atributos
	private EntityManagerFactory emf;
	private EntityManager manager;
	
	// Constructor
	public RutinaJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Crear
	public void crearRutina(Rutina entidadRutina) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadRutina);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Obtener
	public Rutina obtenerEjercicio(int id) {
		Rutina regRutina = null;
		manager = emf.createEntityManager();
		
		try {
			regRutina = manager.find(Rutina.class, id);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}
		return regRutina;
	}
	
	// Eliminar
	public void eliminarRutina(Rutina entidadRutina) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadRutina = manager.merge(entidadRutina);
			manager.remove(entidadRutina);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Actualizar
	public void actualizarEjercicio(Rutina entidadRutina, int cantSeries, int cantRep) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadRutina = manager.merge(entidadRutina);
			
			entidadRutina.setCantSeries(cantSeries);
			entidadRutina.setCantRepeticiones(cantRep);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Destruir el EMF
	public void cerrarEMF() {
		if (emf != null && emf.isOpen()) {
			emf.close();
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
}
