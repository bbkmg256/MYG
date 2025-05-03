/*
	Servicio de Entidad Rutina
*/

package edu.unam.servicio;

// LIBRERIAS
// DAO
import edu.unam.repositorio.RutinaDAO;

// JPA
import jakarta.persistence.EntityManager;

// EMF Singleton
import utilidades.EMFSingleton;

// VARIOS
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Rutina;

/**
*
* @Autor: BBKMG
*/
public class RutinaServicio {
	private RutinaDAO rutinaDao;
	private EntityManager manager;
	
	// Constructor
	public RutinaServicio() {
		rutinaDao = new RutinaDAO();
	}
	
	// Crear y cargar una Rutina al sistema
	public void crearRutina(Rutina rutina) {		
		// VERIFICA QUE LA RUTINA NO EXISTA EN LA BD (COMO NO SE INGRESA EL ID MANUALMENTE, CREO QUE ESTO NO VA)
		if (this.obtenerRutina(rutina.getIdRutina()) != null) {
			System.out.printf("[ ERROR ] > La rutina %d ya se encuentra en el sistema!%n", rutina.getIdRutina());
			return;
		}
		
		// COMPRUEBA EL ATRIBUTO DE NOMBRERUTINA
		if (rutina.getNombreRutina() == null) {
			System.out.println("[ ERROR ] > La rutina no tiene nombre, no se persistirá!");
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.rutinaDao.crearEntidadRutina(this.manager, rutina);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > La rutina %d cargada correctamente!%n", rutina.getIdRutina());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar la rutina!");
		} finally {
			this.manager.close();
		}
	}
	
	// Obtener una Rutina existente en el sistema
	public Rutina obtenerRutina(int id) {		
		Rutina rutinaReg = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			rutinaReg = this.rutinaDao.obtenerEntidadRutina(this.manager, id);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener la rutina!");
		} finally {
			this.manager.close();
		}
		
		return rutinaReg;
	}
	
	// Obtiene todos las Rutinas en el sistema
	public List<Rutina> obtenerTodasLasRutinas(){		
		String consulta = String.format("SELECT %c FROM %s %c", 'r', "Rutina", 'r'); // Consulta JPQL
		List<Rutina> regRutinas = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regRutinas = this.rutinaDao.obtenerEntidadesRutina(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener todas las rutinas!");
		} finally {
			this.manager.close();
		}
		
		return regRutinas;
	}
	
	// Actualizar datos de una Rutina del sistema
	public void actualizarEstadoRutina(int id, String nombreRutina) {		
		Rutina rutina = this.obtenerRutina(id);
		
		if (rutina == null) {
			System.out.printf("[ ERROR ] > La rutina %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// EL ATRIBUTO SE FORMATEA A MINUSCULA
		rutina.setNombreRutina(nombreRutina.toLowerCase());
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.rutinaDao.actualizarEntidadRutina(this.manager, rutina);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > La rutina %d actualizada correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar la entidad!");
		} finally {
			this.manager.close();
		}
	}
	
	// Eliminar una Rutina del sistema
	public void eliminarRutina(int id) {
		Rutina rutina = this.obtenerRutina(id);
		
		if (rutina == null) {
			System.out.printf("[ ERROR ] > La rutina %d  no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			rutina = this.manager.merge(rutina);
			this.rutinaDao.eliminarEntidadRutina(this.manager, rutina);
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > La rutina %d eliminada correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar la rutina!%n");
		} finally {
			this.manager.close();
		}
	}
}