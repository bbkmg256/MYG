/*
	JpaController - Entidad Grupo Muscular
*/

package edu.unam.repositorio;

// JPA
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.Persistence;

// Entidad
import edu.unam.modelo.GrupoMuscular;

/**
*
* @Autor: BBKMG
*/
public class GMJPAController extends JPAController {
	// Atribs.
	// private EntityManagerFactory emf;
	// private EntityManager manager;
		
	// Constructor
	public GMJPAController() {
		// super();
		// emf = Persistence.createEntityManagerFactory("persistencia");
		// System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Operaciones CRUD:
	// Crear -> Clase padre	
	// Obtener -> Clase padre
	// Eliminar -> Clase padre

	// Actualizar
	public void actualizarEntidad(GrupoMuscular entidadGM, String nombreGM) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadGM = manager.merge(entidadGM); // Reconecta el objeto al contexto de persistencia
			
			entidadGM.setNombreGrupo(nombreGM);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
}