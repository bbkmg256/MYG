/*
	JpaController - entidad Rutina
*/

package edu.unam.repositorio;

// JPA
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.Persistence;

// Entidad
import edu.unam.modelo.Rutina;

/**
*
* @Autor: BBKMG
*/
public class RutinaJPAController extends JPAController {
	// Atributos
	// private EntityManagerFactory emf;
	// private EntityManager manager;
	
	// Constructor
	public RutinaJPAController() {
		// super();
		// emf = Persistence.createEntityManagerFactory("persistencia");
		// System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Crear -> Clase padre
	// Obtener -> Clase padre
	// Eliminar -> Clase padre

	// Actualizar
	public void actualizarEntidad(Rutina entidadRutina, int cantSeries, int cantRep) {
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
}
