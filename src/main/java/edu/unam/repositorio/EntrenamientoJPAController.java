/*
	JpaController - Entidad Entrenamiento
*/

package edu.unam.repositorio;

// JPA
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.Persistence;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Entrenamiento;

/**
*
* @Autor: BBKMG
*/
public class EntrenamientoJPAController extends JPAController {
	// Atribs.
	// private EntityManagerFactory emf;
	// private EntityManager manager;
		
	// Constructor
	public EntrenamientoJPAController() {
		// super();
		// emf = Persistence.createEntityManagerFactory("persistencia");
		// System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Operaciones CRUD:
	// Crear -> Clase padre
	// Obtener -> Clase padre
	// Eliminar -> Clase padre
	
	// Actualizar
	public void actualizarEntidad(Entrenamiento entidadEntrenamiento,
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
}
