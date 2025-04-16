/*
	Servicio de Seguimiento
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.SeguimientoDAO;

// Varios
import java.time.LocalDate;
import java.util.List;

// Entidad
import edu.unam.modelo.Seguimiento;

/**
*
* @Autor: BBKMG
*/
public class SeguimientoServicio {
	private SeguimientoDAO sjpac;
	
	public SeguimientoServicio() {
		sjpac = new SeguimientoDAO();
	}
	
	// Cargar Seguimiento
	public void cargarSeguimiento(Seguimiento seg) {
		// MANEJO DE FALLO [ ENTIDAD EXISTENTE EN LA BD ]
		if (sjpac.obtenerEntidadSeguimiento(seg.getIdSeguimiento()) != null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no se encuentra en el sistema!%n", seg.getIdSeguimiento());
			return;
		}
		
		// MANEJO DE FALLO [ ATRIBUTO GM NULL ]
		if (seg.getEntrenamiento() == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no tiene un entrenamiento asignado o este es nulo!%n", seg.getIdSeguimiento());
			return;
		}
		
		// MODIFICA EL ATRIBUTO A MINUSCULA
		seg.setEjercicioRealizado(seg.getEjercicioRealizado().toLowerCase());
		
		// CARGA EL OBJETO A LA BD
		sjpac.crearEntidadSeguimiento(seg);
	}
	
	// Obtener Seguimiento
	public Seguimiento obtenerSeguimiento(int id) {
		Seguimiento seg = sjpac.obtenerEntidadSeguimiento(id);
		
		if (seg == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d ", id);
		}
		return seg;
	}
	
	// Obtiene todos los Seguimientos del sistema
	public List<Seguimiento> obtenerTodosLosSeguimientos(){
		List<Seguimiento> seg = sjpac.obtenerEntidadesSeguimiento();
		if (seg == null) {
			System.out.printf("[ ERROR ] > No hay seguimientos en el sistema!%n");
		}
		return seg;
	}
	
	// Actualizar Seguimiento (NO TERMINADO)
	public void actualizarEstadoSeguimiento(int id, LocalDate fechaActual, int cantSerieRealizado,
											int cantRepeticionesRealizado,String ejercicioRealizado,
											double pesoTrabajado) {
		
		Seguimiento seg = sjpac.obtenerEntidadSeguimiento(id);
		
		if (seg == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		sjpac.actualizarEntidadSeguimiento(seg, fechaActual, cantSerieRealizado,
											cantRepeticionesRealizado,
											ejercicioRealizado, pesoTrabajado);
	}
	
	// Eliminar Seguimiento
	public void eliminarSeguimiento(int id) {
		Seguimiento seg = sjpac.obtenerEntidadSeguimiento(id);
		
		if (seg == null) {
			System.out.printf("[ ERROR ] > El seguimiento %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		sjpac.eliminarEntidadSeguimiento(seg);
	}

	// CERRAR CONEXION
	public void finalizarConexion() {
		if (sjpac != null && sjpac.hayConexion()) {
			sjpac.cerrarEMF();			
		}
	}
}