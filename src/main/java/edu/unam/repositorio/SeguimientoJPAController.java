/*
	JpaController - Entidad Seguimiento
*/

package edu.unam.repositorio;

// JPA
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Seguimiento;

/**
*
* @author bbkmg
*/
public class SeguimientoJPAController {
	// Atribs.
	private EntityManagerFactory emf;
	private EntityManager manager;
		
	// Constructor
	public SeguimientoJPAController() {
		emf = Persistence.createEntityManagerFactory("persistencia");
		System.out.println("[ EXITO ] > EMF iniciado correctamente!");
	}
	
	// Operaciones CRUD:
	// Crear
	public void crearSeguimiento(Seguimiento entidadSeguimiento) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(entidadSeguimiento);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
		
	// Obtener
	public Seguimiento obtenerSeguimiento(int id) {
		Seguimiento regSeg = null;
		manager = emf.createEntityManager();
		
		try {
			regSeg = manager.find(Seguimiento.class, id);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			manager.close();
		}
		return regSeg;
	}
	
	// Eliminar
	public void eliminarSeguimiento(Seguimiento entidadSeguimiento) {
		manager = emf.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			entidadSeguimiento = manager.merge(entidadSeguimiento);
			manager.remove(entidadSeguimiento);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println(e);
		} finally {
			manager.close();
		}
	}
	
	// Actualizar
	public void actualizarSeguimiento(Seguimiento entidadSeguimiento, LocalDate fechaActual,
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
	
	public void cerrarEMF() {
		if (emf != null && emf.isOpen()) {
			emf.close(); // Cierra el Entity Manager Factory
			System.out.println("[ EXITO ] > EMF finalizado correctamente!");
		}
	}
}
