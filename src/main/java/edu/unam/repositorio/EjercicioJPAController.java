/*
	JPAController - Entidad Ejercicio
*/

package edu.unam.repositorio;

// JPA
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

// Entidad
import edu.unam.modelo.Ejercicio;

/**
*
* @author bbkmg
*/
public class EjercicioJPAController {
	// Atributos
	private EntityManagerFactory emf;
	private EntityManager manager;
	
	// Constructor
	public EjercicioJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Crear
	public void crearEjercicio(Ejercicio entidadEjercicio) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadEjercicio);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Obtener
	public Ejercicio obtenerEjercicio(int id) {
		Ejercicio regEjer = null;
		manager = emf.createEntityManager();
		
		try {
			regEjer = manager.find(Ejercicio.class, id);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}
		return regEjer;
	}
	
	// Eliminar
	public void eliminarEjercicio(Ejercicio entidadEjercicio) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadEjercicio = manager.merge(entidadEjercicio);
			manager.remove(entidadEjercicio);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Actualizar
	public void actualizarEjercicio(Ejercicio entidadEjercicio, String nombreEj) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadEjercicio = manager.merge(entidadEjercicio);
			
			entidadEjercicio.setNombreEjercicio(nombreEj);
			
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
