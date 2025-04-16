/*
	JpaController - Entidad Seguimiento
*/

package edu.unam.repositorio;

// JPA
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.Persistence;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Seguimiento;

/**
*
* @Autor: BBKMG
*/
public class SeguimientoJPAController extends JPAController {
	// Atribs.
	// private EntityManagerFactory emf;
	// private EntityManager manager;
		
	// Constructor
	public SeguimientoJPAController(){
		// super();
		// emf = Persistence.createEntityManagerFactory("persistencia");
		// System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Operaciones CRUD:
	// Crear -> Clase padre
	// Obtener -> Clase padre
	// Eliminar -> Clase padre

	// Actualizar
	public void actualizarEntidad(Seguimiento entidadSeguimiento, LocalDate fechaActual,
									int cantSerieRealizado, int cantRepeticionesRealizado,
									String ejercicioRealizado, double pesoTrabajado) {
		
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadSeguimiento = manager.merge(entidadSeguimiento); // Reconecta el objeto al contexto de persistencia
			
			entidadSeguimiento.setFechaHoy(fechaActual);
			entidadSeguimiento.setCantSerieRealizado(cantSerieRealizado);
			entidadSeguimiento.setCantRepeticionRealizado(cantRepeticionesRealizado);
			entidadSeguimiento.setEjercicioRealizado(ejercicioRealizado);
			entidadSeguimiento.setPesoTrabajado(pesoTrabajado);
			
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
}
