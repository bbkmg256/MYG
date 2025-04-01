/*
	Servicio de Seguimiento
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.SeguimientoJPAController;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Seguimiento;

/**
*
* @Autor: BBKMG
*/
public class SeguimientoServicio {
	private SeguimientoJPAController sjpac;
	
	public SeguimientoServicio() {
		sjpac = new SeguimientoJPAController();
	}
	
	// Cargar Seguimiento
	public void cargarSeguimiento(Seguimiento seg) {
		if (sjpac.obtenerEntidad(seg.getIdSeguimiento(), Seguimiento.class) != null) {
			System.out.printf("[ERROR] > El seguimiento %d no se encuentra en el sistema!%n", seg.getIdSeguimiento());
		} else {
			sjpac.crearEntidad(seg);
		}
		
		sjpac.cerrarEMF();
	}
	
	// Obtener Seguimiento
	public Seguimiento obtenerSeguimiento(int id) {
		Seguimiento seg = sjpac.obtenerEntidad(id, Seguimiento.class);
		
		if (seg == null) {
			System.out.printf("[ERROR] > El seguimiento %d ", id);
		}
		
		sjpac.cerrarEMF();
		return seg;
	}
	// Actualizar Seguimiento
	public void actualizarInfSeguimiento(int id, LocalDate fechaActual, int cantSerieRealizado,
			int cantRepeticionesRealizado,String ejercicioRealizado, double pesoTrabajado) {
		
		Seguimiento seg = sjpac.obtenerEntidad(id, Seguimiento.class);
		
		if (seg != null) {
			sjpac.actualizarEntidad(seg, fechaActual, cantSerieRealizado,
									cantRepeticionesRealizado,
									ejercicioRealizado, pesoTrabajado);
		} else {
			System.out.printf("[ERROR] > El seguimiento %d no se encuentra en el sistema!%n", id);
		}
		
		sjpac.cerrarEMF();
	}
	
	// Eliminar Seguimiento
	public void eliminarSeguimiento(int id) {
		Seguimiento seg = sjpac.obtenerEntidad(id, Seguimiento.class);
		
		if (seg != null) {
			sjpac.eliminarEntidad(seg);
		} else {
			System.out.printf("[ERROR] > El seguimiento %d no se encuentra en el sistema!%n", id);
		}
		
		sjpac.cerrarEMF();
	}
}