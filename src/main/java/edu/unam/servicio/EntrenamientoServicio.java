/*
	Servicio de Entrenamiento
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.EntrenamientoJPAController;

// Varios
import java.time.LocalDate;

// Entidad
import edu.unam.modelo.Entrenamiento;

/**
*
* @Autor: BBKMG
*/
public class EntrenamientoServicio {
	private EntrenamientoJPAController ejpac;
	
	// Constructor
	public EntrenamientoServicio(){
		ejpac = new EntrenamientoJPAController();
	}
	
	// Cargar Entrenamiento
	public void cargarEntrenamiento(Entrenamiento entrenamiento) {
		if (ejpac.obtenerEntidad(entrenamiento.getIdEntrenamiento(), Entrenamiento.class) != null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d ya se encuentra en el sistema!%n", entrenamiento.getIdEntrenamiento());
		} else {
			ejpac.crearEntidad(entrenamiento);
		}
		
		ejpac.cerrarEMF();
	}
	
	// Buscar Entrenamiento
	public Entrenamiento obtenerEntrenamiento(int id) {
		Entrenamiento entrenamiento = ejpac.obtenerEntidad(id, Entrenamiento.class);
		
		if (entrenamiento == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no se encuentra en el sistema!%n", id);
		}
		
		ejpac.cerrarEMF();
		return entrenamiento;
	}

	// Actualizar Entrenamiento
	public void actualizarInfEntrenamiento(int id, int puntaje,
										LocalDate fechaInicio,
										LocalDate fechaFin) {
		
		Entrenamiento entrenamiento = ejpac.obtenerEntidad(id, Entrenamiento.class);
		
		if (entrenamiento != null) {
			ejpac.actualizarEntidad(entrenamiento, puntaje, fechaInicio, fechaFin);
		} else {
			System.out.printf("[ ERROR ] > El entrenamiento %d no se encuentra en el sistema!%n", id);
		}
		
		ejpac.cerrarEMF();
	}

	// Eliminar Entrenamiento
	public void eliminarEntrenamiento(int id) {
		Entrenamiento entrenamiento = ejpac.obtenerEntidad(id, Entrenamiento.class);
		
		if (entrenamiento != null) {
			ejpac.eliminarEntidad(entrenamiento);
		} else {
			System.out.printf("[ ERROR ] > El entrenamiento %d no se encuentra en el sistema!%n", id);
		}
		
		ejpac.cerrarEMF();
	}
}
