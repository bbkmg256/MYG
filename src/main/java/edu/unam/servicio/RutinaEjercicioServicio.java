/*
 * Servicio para entidad (intermedia) RutinaEjercicio
 */

package edu.unam.servicio;

// LIBRERIAS
// DAO
import edu.unam.repositorio.RutinaEjercicioDAO;

// JPA
import jakarta.persistence.EntityManager;
import utilidades.EMFSingleton;

// VARIOS
 import java.util.List;
import utilidades.ParametrosRutinaEjercicio;

// ENTIDAD
import edu.unam.modelo.RutinaEjercicio;
//import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.Rutina;


/*
 * 
 * NOTA:
 * 
 * SOLO SE DEJA FUNCIONANDO LA RELACION BIDIRECCIONAL CON RUTINA
 * 
 */


/**
*
* @Autor: BBKMG
*/
public class RutinaEjercicioServicio {
	// ATRIBUTOS
	private RutinaEjercicioDAO reDao;
	private EntityManager manager;
	private Rutina rt;
//	private Ejercicio ejer;
	private RutinaServicio srt;
	private EjercicioServicio sej;
	
	
	// CONSTRUCTOR
	public RutinaEjercicioServicio() {
		this.reDao = new RutinaEjercicioDAO();
		this.srt = new RutinaServicio();
		this.sej = new EjercicioServicio();
	}

	// Crear y cargar una RutinaEjercicio al sistema
	public void crearRE(RutinaEjercicio re) {
		// COMPRUEBA QUE LA ENTIDAD NO SE HAYA INTANCIADO VACIA O CON ATRIBUTOS ERRONEOS
		if (re.getRutina() == null || this.srt.obtenerRutina(re.getRutina().getIdRutina()) == null) {
			System.out.println("[ ERROR ] > La entidad no tiene rutina asignada, es null o no existe en la BD!");
			return;
		}
		
		if (re.getEjercicio() == null || this.sej.obtenerEjercicio(re.getEjercicio().getIdEjercicio()) == null) {
			System.out.println("[ ERROR ] > La entidad no tiene ejercicio asignado, es null, o no existe en la BD!");
			return;
		}
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.reDao.crearEntidadRutinaEjercicio(this.manager, re);
			
			// ENLACE CON RUTINA
			this.rt = this.manager.merge(re.getRutina());
			this.rt.getRutinaEjercicio().add(re);
			
			// ENLACE CON EJERCICIO
//			this.ejer = this.manager.merge(re.getEjercicio());
//			this.ejer.getRutinaEjercicio().add(re);
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > La entidad %d cargada correctamente!%n", re.getId());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.print(e);
			System.out.println("[ ERROR ] > Falla al cargar la entidad!");
		} finally {
			this.manager.close();
		}
	}
	
	// Obtener una RutinaEjercicio existente en el sistema
	public RutinaEjercicio obtenerRE(int id) {	
		RutinaEjercicio regRE = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regRE= this.reDao.obtenerEntidadRutinaEjercicio(this.manager, id);
		} catch (Exception e) {
			System.out.print(e);
			System.out.println("[ ERROR ] > Falla al obtener el registro!");
		} finally {
			this.manager.close();
		}
		
		return regRE;
	}
	
	// Obtiene todos las RutinaEjejercicio del sistema
	public List<RutinaEjercicio> obtenerTodasLasRutinaEjercicio(){		
		String consulta = String.format("SELECT %c FROM %s %c", 'r', "RutinaEjercicio", 'r'); // Consulta JPQL
		List<RutinaEjercicio> reList = null;
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			reList = this.reDao.obtenerEntidadesRutinaEjercicio(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener las entidades!");
		} finally {
			this.manager.close();
		}
		
		return reList;
	}

	// Actualizar datos de una Rutina del sistema (FUNCIONA CORRECTAMENTE, NO ENTIENDO POR QUE XD)
	// EN REALIDAD FUNCIONA POR QUE HIBERNATE ACTUALIZA O HACE ALGUNA MAMADA ASÍ CON LAS ENTIDADES AL CERRAR EL "EM" EN UNA TRANSACCION.
	public void actualizarEstadoRutina(int id, ParametrosRutinaEjercicio paramRE) {
		RutinaEjercicio regRE = this.obtenerRE(id);
		Rutina rtAntigua = null;
//		Ejercicio ejerAntigua = null;
		
		if (regRE == null) {
			System.out.printf("[ ERROR ] > La entidad %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ALMACENA LOS OBJETOS ANTIGUOS
		rtAntigua = regRE.getRutina();
//		ejerAntigua = regRE.getEjercicio();
		
		// MODIFICACION DE ATRIBUTOS
		if (paramRE.rutina != null) {
			if (this.srt.obtenerRutina(paramRE.rutina.getIdRutina()) == null) {
				System.out.println("[ ERROR ] > El parámetro rutina no es valido!");
				return;
			}
			regRE.setRutina(paramRE.rutina);
		}
		
		if (paramRE.ejercicio != null) {
			if (this.sej.obtenerEjercicio(paramRE.ejercicio.getIdEjercicio()) == null) {
				System.out.println("[ ERROR ] > El parámetro ejercicio no es valido!");
				return;				
			}
			regRE.setEjercicio(paramRE.ejercicio);
		}
		
		if (paramRE.serie != 0) {
			regRE.setSerie(paramRE.serie);
		}
		
		if (paramRE.repeticion != 0) {
			regRE.setRepeticion(paramRE.repeticion);
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			regRE = this.reDao.actualizarEntidadRutinaEjercicio(this.manager, regRE);
			
			if (paramRE.rutina != null) {
				// ENLACE RUTINA NUEVA
				this.rt = this.manager.merge(regRE.getRutina());
				this.rt.getRutinaEjercicio().add(regRE);
				// DESENLACE RUTINA VIEJA
				rtAntigua = this.manager.merge(rtAntigua);
				rtAntigua.getRutinaEjercicio().remove(regRE);
			}
//			
//			if (paramRE.ejercicio != null) {
//				// ENLACE EJERCICIO NUEVO
//				this.ejer = this.manager.merge(regRE.getEjercicio());
//				this.ejer.getRutinaEjercicio().add(regRE);
//				// DESENLACE EJERCICIO VIEJO
//				ejerAntigua = this.manager.merge(ejerAntigua);
//				ejerAntigua.getRutinaEjercicio().remove(regRE);
//			}
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > La entidad %d actualizada correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar la entidad!");
		} finally {
			this.manager.close();
		}
	}
	
	// Eliminar una RutinaEjercicio del sistema
	public void eliminarRE(int id) {
		RutinaEjercicio regRE = this.obtenerRE(id);
		
		if (regRE == null) {
			System.out.printf("[ ERROR ] > El registro %d  no se encuentra en el sistema!%n", id);
			return;
		}
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			regRE = this.manager.merge(regRE);
			this.reDao.eliminarEntidadRutinaEjercicio(this.manager, regRE);
			
			// DESENLACE RUTINA
			this.rt = this.manager.merge(regRE.getRutina());
			this.rt.getRutinaEjercicio().remove(regRE);
			
			// DESENLACE EJERCICIO
//			this.ejer = this.manager.merge(regRE.getEjercicio());
//			this.ejer.getRutinaEjercicio().remove(regRE);
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > La entidad %d eliminada correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.print(e);
			System.out.println("Falla al eliminar la entidad!");
		} finally {
			this.manager.close();
		}
	}
}