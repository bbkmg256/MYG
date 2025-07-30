/*
	Servicio de Seguimiento
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.SeguimientoDAO;
import jakarta.persistence.EntityManager;

import java.lang.reflect.Field;
import java.time.LocalDate;
// Varios
import java.util.List;

import utilidades.bd.EMFSingleton;
import utilidades.parametros.ParametrosSeguimiento;
// Entidad
import edu.unam.modelo.Seguimiento;
import edu.unam.modelo.Ejercicio;
import edu.unam.modelo.Entrenamiento;


/*
 * 
 * NOTAS:
 * 
 * QUEDA A CONFIRMAR SI EL ADMINISTRADOR O USUARIO QUE MANEJE LA APLICACION PUEDE NO INGRESAR
 * SEGUIMIENTOS DEL ENTRENAMIENTO ALGUNOS DIAS, ES DECIR, QUE EN VEZ DE TENER QUE SI O SI INGRESAR
 * DATOS TODOS LOS DIAS, PUEDEN HABER DIAS EN LOS QUE EL CLIENTE NO ENTRENE, POR ENDE ESE DIA NO SE
 * SE REGISTRA, PERO CUENTA COMO DIA DE ENTRENAMIENTO REALIZADO (OSEA, CONTANTO DENTRO DEL PERIODO DE
 * DIAS DENTRO DE LAS 5 SEMANAS DE ENTRENAMIENTO, OSEA, COMO SI FUERA UN DIA ALPEDO BASICAMENTE).
 * 
 * (AGUANTE SODA VIEJO!!!)
 * 
 * CREO QUE VOY A TENER QUE VERIFICAR QUE LA FECHA INGRESADA EN EL SEGUIMIENTO NO ESTE POR FUERA DEL PLAZO
 * DE FECHA DE INICIO Y FIN DEL ENTRENAMIENTO DEFINIDO AL QUE SE RELACIONA O AL QUE LE HACE EL SEGUIMIENTO
 * DIARIO, SERÍA PROBLEMATICO (AUN QUE ES POSIBLE MODIFICARLO, PERO IGUAL) INGRESAR UNA FECHA ERRONEA Y QUE
 * EL USUARIO NO SE DE CUENTA.
 * 
 */


/**
*
* @Autor: BBKMG
*/
public class SeguimientoServicio {
	private SeguimientoDAO segDao;
	private EntityManager manager;
	private Entrenamiento entre;
	private EntrenamientoServicio sentre;
	private EjercicioServicio sejer;
	private Ejercicio ej;
	
	
	public SeguimientoServicio() {
		this.segDao = new SeguimientoDAO();
		this.sentre = new EntrenamientoServicio();
		this.sejer = new EjercicioServicio();
	}
	
	// Cargar Seguimiento
	public void cargarSeguimiento(Seguimiento seg) {		
		// MANEJO DE FALLO [ ATRIBUTOS NULL ]
		if (seg.getEntrenamiento() == null || this.sentre.obtenerEntrenamiento(seg.getEntrenamiento().getIdEntrenamiento()) == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no tiene un entrenamiento asignado o este es nulo!%n", seg.getIdSeguimiento());
			return;
		}
		
		if (seg.getEjercicioRealizado() == null || sejer.obtenerEjercicio(seg.getEjercicioRealizado().getIdEjercicio()) == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no tiene un ejercicio asignado o este es nulo!%n", seg.getIdSeguimiento());
			return;
		}
		
		if (seg.getFechaHoy() == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no tiene fecha asignada o este es nulo!%n", seg.getIdSeguimiento());
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			this.segDao.crearEntidadSeguimiento(manager, seg);
			
			// ENLACE ENTRENAMIENTO
			this.entre = this.manager.merge(seg.getEntrenamiento());
			this.entre.getSeguimientos().add(seg);
			
			// ENLACE EJERCICIO
			this.ej = this.manager.merge(seg.getEjercicioRealizado());
			this.ej.getSeguimientos().add(seg);
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El seguimiento %d cargado correctamente!%n", seg.getIdSeguimiento());
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al cargar el seguimiento!");
		} finally {
			this.manager.close();
		}
	}
	
	// Obtener Seguimiento
	public Seguimiento obtenerSeguimiento(int id) {		
		Seguimiento regSeg = null;
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regSeg = this.segDao.obtenerEntidadSeguimiento(this.manager, id);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener el seguimiento!");
		} finally {
			this.manager.close();
		}
		
		return regSeg;
	}
	
	// Obtener Seguimiento (CON SUS OBJETOS)
	public Seguimiento obtenerSeguimientoYSusObjetos(int id) {		
		Seguimiento regSeg = null;
		String consulta = String.format(
				"SELECT %c FROM %s %c " + 
				"FETCH JOIN %c.entrenamiento " +
				"WHERE %c.idSeguimiento = :id",
				's', "Seguimiento", 's', 's', 's'
		); // Consulta JPQL
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			regSeg = this.segDao.obtenerEntidadSeguimiento(this.manager, id, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener el seguimiento!");
		} finally {
			this.manager.close();
		}
		
		return regSeg;
	}
	
	// Obtiene todos los Seguimientos del sistema
	public List<Seguimiento> obtenerTodosLosSeguimientos() {		
		List<Seguimiento> segList= null;
		String consulta = String.format("SELECT %c FROM %s %c", 's', "Seguimiento", 's'); // Consulta JPQL
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			segList = this.segDao.obtenerEntidadesSeguimiento(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los seguimientos!");
		} finally {
			this.manager.close();
		}
		
		return segList;
	}
	
	// OBTENER SEGUIMIENTOS POR FILTRO
	private <T> List<Seguimiento> obtenerListaFiltrada(String atributoNombre, T valorFiltro) {
		List<Seguimiento> segList= null;
		
		String consulta =
				"SELECT s FROM Seguimiento s " +
				"JOIN FETCH s.entrenamiento " +
				"JOIN FETCH s.ejercicioRealizado " +
				"WHERE s." + atributoNombre + " = :val";
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			segList = this.segDao.obtenerEntidadesSeguimiento(this.manager, consulta, valorFiltro);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los seguimientos!");
		} finally {
			this.manager.close();
		}
		
		return segList;
	}
	
	// HAY QUE VER DESPUES QUE FILTROS SE VAN A USAR Y QUE NO //
	
	public List<Seguimiento> obtenerListaFiltradaPorEntrenamiento(Entrenamiento ent) {
		return this.obtenerListaFiltrada("entrenamiento", ent);
	}
	
	public List<Seguimiento> obtenerListaFiltradaPorEjercicio(Ejercicio ej) {
		return this.obtenerListaFiltrada("ejercicicoRealizado", ej);
	}
	
	// POR EL MOMENTO FILTRO POR UNA SOLA FECHA //
	public List<Seguimiento> obtenerListaFiltradaPorFecha(LocalDate fecha) {
		return this.obtenerListaFiltrada("fechaHoy", fecha);
	}
	
	public List<Seguimiento> obtenerListaFiltradaPorSerie(int serie) {
		return this.obtenerListaFiltrada("cantSerieRealizado", serie);
	}
	
	public List<Seguimiento> obtenerListaFiltradaPorRepeticion(int rep) {
		return this.obtenerListaFiltrada("cantRepeticionesRealizado", rep);
	}
	
	public List<Seguimiento> obtenerListaFiltradaPorPeso(double peso) {
		return this.obtenerListaFiltrada("pesoTrabajado", peso);
	}
	
	// Obtiene todos los Seguimientos del sistema (CON SUS OBJETOS)
	public List<Seguimiento> obtenerTodosLosSeguimientosYSusObjetos() {
		List<Seguimiento> segList= null;
		String consulta = String.format(
				"SELECT %c FROM %s %c " +
				"JOIN FETCH %c.entrenamiento "+
				"JOIN FETCH %c.ejercicioRealizado",
				's', "Seguimiento", 's', 's', 's'
		); // Consulta JPQL
		
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			segList = this.segDao.obtenerEntidadesSeguimiento(this.manager, consulta);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al obtener los seguimientos!");
		} finally {
			this.manager.close();
		}
		
		return segList;
	}
	
	// Actualizar Seguimiento (NO TERMINADO)
	public void actualizarEstadoSeguimiento(int id, ParametrosSeguimiento paramSeg) {
		Seguimiento segReg = this.obtenerSeguimiento(id);
		Entrenamiento entreAntiguo = null;
		Ejercicio ejerAntiguo = null;
		
		if (segReg == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// PARA VERIFICAR CUALES ATRIBUTOS SE MODIFICAN, Y CUALES NO
		if (paramSeg.entrenamiento != null) {
			if (this.sentre.obtenerEntrenamiento(paramSeg.entrenamiento.getIdEntrenamiento()) == null) {
				System.out.println("[ ERROR ] > El parámetro entrenamiento no es valido");
				return;
			}
			segReg.setEntrenamiento(paramSeg.entrenamiento);
		}
		
		if (paramSeg.cantSerieRealizado > 0) {
			segReg.setCantSerieRealizado(paramSeg.cantSerieRealizado);
		}
		
		if (paramSeg.cantRepeticionesRealizado > 0) {
			segReg.setCantRepeticionRealizado(paramSeg.cantRepeticionesRealizado);
		}
		
		if (paramSeg.ejercicioRealizado != null) {
			if (sejer.obtenerEjercicio(paramSeg.ejercicioRealizado.getIdEjercicio()) == null) {
				System.out.println("[ ERROR ] > El parámetro ejercicio no es válido!");
				return;
			}
			segReg.setEjercicioRealizado(paramSeg.ejercicioRealizado);
		}
		
		if (paramSeg.pesoTrabajado > 0) {
			segReg.setPesoTrabajado(paramSeg.pesoTrabajado);
		}
		
		if (paramSeg.fechaHoy != null) {
			segReg.setFechaHoy(paramSeg.fechaHoy);
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			segReg = this.segDao.actualizarEntidadSeguimiento(this.manager, segReg);
			
			if (paramSeg.entrenamiento != null) {
				// ENLACE (NUEVO) Y DESENLACE (ANTIGUO) DE ENTRENAMIENTO
				this.entre = this.manager.merge(segReg.getEntrenamiento());
				this.entre.getSeguimientos().add(segReg);
				
				entreAntiguo = this.manager.merge(entreAntiguo);
				entreAntiguo.getSeguimientos().remove(segReg);				
			}
			
			if (paramSeg.ejercicioRealizado != null) {
				// ENLACE (NUEVO) Y DESENLACE (ANTIGUO) DE EJERCICIO
				this.ej = this.manager.merge(segReg.getEjercicioRealizado());
				this.ej.getSeguimientos().add(segReg);
				
				ejerAntiguo = this.manager.merge(ejerAntiguo);
				ejerAntiguo.getSeguimientos().remove(segReg);
			}
						
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El seguimiento %d actualizado correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al actualizar el seguimiento!");
		} finally {
			this.manager.close();
		}
	}
	
	// Eliminar Seguimiento
	public void eliminarSeguimiento(int id) {		
		Seguimiento regSeg = this.obtenerSeguimiento(id);

		if (regSeg == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ADMINISTRADOR DE ENTIDADES
		this.manager = EMFSingleton.getInstancia().getEMF().createEntityManager();
		
		try {
			this.manager.getTransaction().begin();
			regSeg = this.manager.merge(regSeg);
			this.segDao.eliminarEntidadSeguimiento(this.manager, regSeg);
			
			// DESENLACE DE ENTRENAMIENTO
			this.entre = this.manager.merge(regSeg.getEntrenamiento());
			this.entre.getSeguimientos().remove(regSeg);
			
			// DESENLACE DE EJERCICIO
			this.ej = this.manager.merge(regSeg.getEjercicioRealizado());
			this.ej.getSeguimientos().remove(regSeg);
			
			this.manager.getTransaction().commit();
			System.out.printf("[ EXITO ] > El seguimiento %d eliminado correctamente!%n", id);
		} catch (Exception e) {
			this.manager.getTransaction().rollback();
			System.out.println(e);
			System.out.println("[ ERROR ] > Falla al eliminar el seguimiento!");
		} finally {
			this.manager.close();
		}
	}
}