/*
 * CLASE SERVICIO PARA ENTIDAD INTERMEDIA RUTINAENTRENAMIENTO
 */

package edu.unam.servicio;

import edu.unam.modelo.Rutina;
import edu.unam.modelo.RutinaEntrenamiento;

import java.util.List;

import edu.unam.modelo.Entrenamiento;
import edu.unam.repositorio.RutinaEntrenamientoDAO;
import jakarta.persistence.EntityManager;
import utilidades.bd.EMFSingleton;

/*
 * 
 */

public class RutinaEntrenamientoServicio {
	// ATRIBUTOS
	private Rutina rutina;
	private RutinaServicio srt;
	private Entrenamiento entrenamiento;
	private EntrenamientoServicio sentre;
	private RutinaEntrenamientoDAO reDAO;
	private EntityManager manager;
	
	
	// CONSTRUCTOR
	public RutinaEntrenamientoServicio() {
		this.reDAO = new RutinaEntrenamientoDAO();
		this.srt = new RutinaServicio();
		this.sentre = new EntrenamientoServicio();
	}
	
	// CREAR RUTINAENTRENMIENTO
	public void crearRE(RutinaEntrenamiento re) {
		if (re.getRutina() == null || this.srt.obtenerRutina(re.getRutina().getIdRutina()) == null) {
			System.err.println("[ ERROR ] > La entidad no tiene rutina asignada, es null o no existe!");
			return;
		}
		
		if (re.getEntrenamiento() == null || this.sentre.obtenerEntrenamiento(re.getEntrenamiento().getIdEntrenamiento()) == null) {
			System.err.println("[ ERROR ] > La entidad no tiene entrenamientos asignado, es null o no existe!");
			return;
		}
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.reDAO.crearEntidadRutinaEntrenamiento(this.manager, re);
			
			// ENLACE CON RUTINA
			this.rutina = this.manager.merge(re.getRutina());
			this.rutina.getRutinaEntrenamientos().add(re);
			
			// ENLACE CON ENTRENAMIENTO
			this.entrenamiento = this.manager.merge(re.getEntrenamiento());
			this.entrenamiento.getRutinaEntrenamientos().add(re);
			System.out.printf("[ EXITO ] > La entidad %d cargada correctamente!%n", re.getId());
			this.manager.getTransaction().commit();
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.err.print(e);
			System.err.println("[ ERROR ] > Falla al cargar la entidad!");
		} finally {
			this.manager.close();
		}
	}
	
	// LEER RUTINAENTRENMIENTO
	public RutinaEntrenamiento obtenerRE(int id) {
		RutinaEntrenamiento regRE = null;
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regRE = this.reDAO.obtenerEntidadRutinaEntrenamiento(this.manager, id);
		} catch (Exception e) {
			System.err.print(e);
			System.err.println("[ ERROR ] > Falla al obtener el registro!");
		} finally {
			this.manager.close();
		}
		
		return regRE;
	}
	
	// LEER RUTINAENTRENMIENTO (CON OBJETOS)
	public RutinaEntrenamiento obtenerREYSusObjetos(int id) {
		RutinaEntrenamiento regRE = null;
		String consulta =
				"SELECT r FROM RutinaEntrenamiento r " +
				"JOIN FETCH r.entrenamiento " +
				"JOIN FETCH r.rutina " +
				"WHERE r.id = :id";
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regRE = this.reDAO.obtenerEntidadRutinaEntrenamiento(this.manager, id, consulta);
		} catch (Exception e) {
			System.err.print(e);
			System.err.println("[ ERROR ] > Falla al obtener el registro!");
		} finally {
			this.manager.close();
		}
		
		return regRE;
	}
	
	// OBTENER TODOS LOS RUTINAENTRENMIENTO
	public List<RutinaEntrenamiento> obtenerTodosLosRE() {
		String consulta = "SELECT r FROM RutinaEntrenamiento r"; // JPQL
		List<RutinaEntrenamiento> reList = null;
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			reList = this.reDAO.obtenerTodasLasEntidadesRutinaEntrenamiento(this.manager, consulta);
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("[ ERROR ] > Falla al obtener las entidades!");
		} finally {
			this.manager.close();
		}
		
		return reList;
	}
	
	// OBTENER TODOS LOS RUTINAENTRENMIENTO (CON SUS OBJETOS)
	public List<RutinaEntrenamiento> obtenerTodosLosREYSusObjetos() {
		String consulta = 
				"SELECT r FROM RutinaEntrenamiento r " +
				"JOIN FETCH r.entrenamiento " +
				"JOIN FETCH r.rutina"; // JPQL
		List<RutinaEntrenamiento> reList = null;
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			reList = this.reDAO.obtenerTodasLasEntidadesRutinaEntrenamiento(this.manager, consulta);
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("[ ERROR ] > Falla al obtener las entidades!");
		} finally {
			this.manager.close();
		}
		
		return reList;
	}
	
	// OBTENER TODOS LOS RUTINAENTRENMIENTO RESPECTO A UN ENTRENAMIENTO (CON SUS OBJETOS)
	public List<RutinaEntrenamiento> obtenerTodosLosREYSusObjetos(Entrenamiento ent) {
		String consulta = 
				"SELECT r FROM RutinaEntrenamiento r " +
				"JOIN FETCH r.entrenamiento " +
				"JOIN FETCH r.rutina " +
				"WHERE r.entrenamiento = :obj"; // JPQL
		List<RutinaEntrenamiento> reList = null;
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			reList = this.reDAO.obtenerTodasLasEntidadesRutinaEntrenamiento(this.manager, consulta, ent);
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("[ ERROR ] > Falla al obtener las entidades!");
		} finally {
			this.manager.close();
		}
		
		return reList;
	}
	
	// MODIFICAR RUTINAENTRENMIENTO
	public boolean actualizarEstadoRE(int id, Rutina rut) {
		RutinaEntrenamiento regRE = this.obtenerREYSusObjetos(0);
		Rutina rutinaAntigua = null;
		
		if (regRE == null) {
			System.err.printf("[ ERROR ] > La entidad %d no se encuentra en el sistema!%n", id);
			return false;
		}
		
		// ALMACENA LOS OBJETOS ANTIGUOS
		rutinaAntigua = regRE.getRutina();
		
		if (rut != null && rut != regRE.getRutina()) {
			if (this.srt.obtenerRutina(rut.getIdRutina()) == null) {
				System.out.println("[ ERROR ] > El parámetro ejercicio no es valido!");
				return false;
			}
			regRE.setRutina(rut);
		}
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			regRE = this.reDAO.actualizarEntidadRutinaEntrenamiento(this.manager, regRE);
			
			// ENLACE RUTINA NUEVA
			this.rutina = this.manager.merge(regRE.getRutina());
			this.rutina.getRutinaEntrenamientos().add(regRE);
			
			// DESENLACE RUTINA VIEJA
			rutinaAntigua = this.manager.merge(rutinaAntigua);
			rutinaAntigua.getRutinaEntrenamientos().remove(regRE);
			
			// ENTRENAMIENTO NO NECESITA MODIFICARSE //
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > La entidad %d actualizada correctamente!%n", id);
			return true;
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar la entidad!");
			return false;
		} finally {
			this.manager.close();
		}
	}
	
	// ELIMINAR RUTINAENTRENMIENTO
	public void eliminarRE(int id) {
		RutinaEntrenamiento regRE = this.obtenerRE(id);
		
		if (regRE == null) {
			System.err.printf("[ ERROR ] > El registro %d  no se encuentra en el sistema!%n", id);
			return;
		}
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			regRE = this.manager.merge(regRE); // <- POR ESTO NO ES NECESARIO TRAER TODOS LOS OBJETOS RELACIONADOS A LA INSTANCIA regRE
			this.reDAO.eliminarEntidadRutinaEntrenamiento(this.manager, regRE);
			
			// DESENLACE RUTINA
			this.rutina = this.manager.merge(regRE.getRutina());
			this.rutina.getRutinaEntrenamientos().remove(regRE);
			
			// DESENLACE ENTRENAMIENTO
			this.entrenamiento = this.manager.merge(regRE.getEntrenamiento());
			this.entrenamiento.getRutinaEntrenamientos().remove(regRE);
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > La entidad %d eliminada correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.err.print(e);
			System.err.println("Falla al eliminar la entidad!");
		} finally {
			this.manager.close();
		}
	}
}
