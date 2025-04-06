/*
	Servicio de Ejercicio
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.EjercicioJPAController;

// Varios
import java.util.List;

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
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", ejercicio.getIdEjercicio());
		} else {
			ejjpac.crearEntidad(ejercicio);
		}
		
		ejjpac.cerrarEMF();
	}
	
	// Buscar Ejercicio
	public Ejercicio obtenerEjercicio(int id) {
		Ejercicio ejercicio = ejjpac.obtenerEntidad(id, Ejercicio.class);
		
		if (ejercicio == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id);
		}
		
		ejjpac.cerrarEMF();
		return ejercicio;
	}
	
	// Obtener todos los Ejercicios cargados
	public List<Ejercicio> obtenerTodosLosEjercicios(){
		String entidadString = "Ejercicio";
		List<Ejercicio> ejer = ejjpac.obtenerEntidades(entidadString, Ejercicio.class);
		if (ejer == null) {
			System.out.printf("[ ERROR ] > No hay Ejercicios en el sistema!%n");
		}
		
		ejjpac.cerrarEMF();
		return ejer;
	}

	// Actualizar Ejercicio
	public void actualizarInfEjercicio(int id, String nombreEj) {
		Ejercicio ejercicio = ejjpac.obtenerEntidad(id, Ejercicio.class);
		
		if (ejercicio != null) {
			ejjpac.actualizarEntidad(ejercicio, nombreEj);
		} else {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id);
		}
		
		ejjpac.cerrarEMF();
	}

	// Eliminar Ejercicio
	public void eliminarEjercicio(int id) {
		Ejercicio ejercicio = ejjpac.obtenerEntidad(id, Ejercicio.class);
		
		if (ejercicio != null) {
			ejjpac.eliminarEntidad(ejercicio);
		} else {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id);
		}
		
		ejjpac.cerrarEMF();
	}
}