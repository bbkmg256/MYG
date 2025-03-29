/*
	JpaController - Entidad Entrenamiento
*/

package edu.unam.repositorio;

// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Entrenamiento;

/**
*
* @author bbkmg
*/
public class EntrenamientoJPAController {
	// Atribs.
	private EntityManagerFactory emf;
	private EntityManager manager;
		
	// Constructor
	public EntrenamientoJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Operaciones CRUD:
	// Crear
	public void crearEntrenamiento(Entrenamiento entidadEntrenamiento) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadEntrenamiento);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
		
	// Obtener
	public Entrenamiento obtenerEntrenamiento(int id) {
		Entrenamiento regEnt = null;
		manager = emf.createEntityManager();
		
		try {
			regEnt = manager.find(Entrenamiento.class, id);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}
		return regEnt;
	}
	
	// Eliminar
	public void eliminarEntrenamiento(Entrenamiento entidadEntrenamiento) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadEntrenamiento = manager.merge(entidadEntrenamiento);
			manager.remove(entidadEntrenamiento);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Actualizar
	public void actualizarEntrenamiento(Entrenamiento entidadEntrenamiento,
										int puntaje, LocalDate fechaInicio,
										LocalDate fechaFin) {
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadEntrenamiento = manager.merge(entidadEntrenamiento); // Reconecta el objeto al contexto de persistencia
			
			// Todavia faltan atributos a modificar
			entidadEntrenamiento.setPuntaje(puntaje);
			entidadEntrenamiento.setFechaInicio(fechaInicio);
			entidadEntrenamiento.setFechaFin(fechaFin);	
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	public void cerrarEMF() {
		if (emf != null && emf.isOpen()) {
			emf.close(); // Cierra el Entity Manager Factory
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
}
