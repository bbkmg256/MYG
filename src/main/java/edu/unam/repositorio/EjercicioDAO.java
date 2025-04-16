/*
	JPAController - Entidad Ejercicio
*/

package edu.unam.repositorio;

// JPA
// import jakarta.persistence.Persistence;
// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.EntityManager;

// Entidad
import edu.unam.modelo.Ejercicio;

/**
*
* @Autor: BBKMG
*/
public class EjercicioJPAController extends JPAController {
	// Atributos
	// private EntityManagerFactory emf;
	// private EntityManager manager;
	
	// Constructor
	public EjercicioJPAController() {
		// super();
		// emf = Persistence.createEntityManagerFactory("persistencia");
		// System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Crear -> Clase padre
	// Obtener -> Clase padre
	// Eliminar -> Clase padre

	// Actualizar
	public void actualizarEntidad(Ejercicio entidadEjercicio, String nombreEj) {
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
}
