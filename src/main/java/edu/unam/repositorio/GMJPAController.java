/*
	JpaController - Entidad Grupo Muscular
*/

package edu.unam.repositorio;

// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Entidad
import edu.unam.modelo.GrupoMuscular;

/**
*
* @author bbkmg
*/
public class GMJPAController {
	// Atribs.
	private EntityManagerFactory emf;
	private EntityManager manager;
		
	// Constructor
	public GMJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Operaciones CRUD:
	// Crear
	public void crearGM(GrupoMuscular entidadGM) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadGM);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
		
	// Obtener
	public GrupoMuscular obtenerGM(int id) {
		GrupoMuscular regGM = null;
		manager = emf.createEntityManager();
		
		try {
			regGM = manager.find(GrupoMuscular.class, id);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}
		return regGM;
	}
	
	// Eliminar
	public void eliminarGM(GrupoMuscular entidadGM) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadGM = manager.merge(entidadGM);
			manager.remove(entidadGM);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Actualizar
	public void actualizarGM(GrupoMuscular entidadGM, String nombreGM) {
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
	
	public void cerrarEMF() {
		if (emf != null && emf.isOpen()) {
			emf.close(); // Cierra el Entity Manager Factory
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
}