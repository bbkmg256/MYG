/*
	Servicio de Ejercicio
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.EjercicioJPAController;

// Entidad
import edu.unam.modelo.Ejercicio;

/**
*
* @Autor: BBKMG
*/
public class EjercicioServicio {
	private EjercicioJPAController ejjpac;
	
	public EjercicioServicio() {
		ejjpac = new EjercicioJPAController();
	}
	
	// Cargar Ejercicio
	public void cargarEjercicio(Ejercicio ejercicio) {
		if (ejjpac.obtenerEntidad(ejercicio.getIdEjercicio(), Ejercicio.class) != null) {
			System.out.printf("[ERROR] > El ejercicio %d no se encuentra en el sistema!%n", ejercicio.getIdEjercicio());
		} else {
			ejjpac.crearEntidad(ejercicio);
		}
		
		ejjpac.cerrarEMF();
	}
	
	// Buscar Ejercicio
	public Ejercicio obtenerEjercicio(int id) {
		Ejercicio ejercicio = ejjpac.obtenerEntidad(id, Ejercicio.class);
		
		if (ejercicio == null) {
			System.out.printf("[ERROR] > El ejercicio %d no se encuentra en el sistema!%n", id);
		}
		
		ejjpac.cerrarEMF();
		return ejercicio;
	}

	// Actualizar Ejercicio
	public void actualizarInfEjercicio(int id, String nombreEj) {
		Ejercicio ejercicio = ejjpac.obtenerEntidad(id, Ejercicio.class);
		
		if (ejercicio != null) {
			ejjpac.actualizarEntidad(ejercicio, nombreEj);
		} else {
			System.out.printf("[ERROR] > El ejercicio %d no se encuentra en el sistema!%n", id);
		}
		
		ejjpac.cerrarEMF();
	}

	// Eliminar Ejercicio
	public void eliminarEjercicio(int id) {
		Ejercicio ejercicio = ejjpac.obtenerEntidad(id, Ejercicio.class);
		
		if (ejercicio != null) {
			ejjpac.eliminarEntidad(ejercicio);
		} else {
			System.out.printf("[ERROR] > El ejercicio %d no se encuentra en el sistema!%n", id);
		}
		
		ejjpac.cerrarEMF();
	}
}