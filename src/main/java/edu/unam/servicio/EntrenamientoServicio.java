/*
	Servicio de Entrenamiento
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.EntrenamientoDAO;

// VARIOS
import java.time.LocalDate;
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Entrenamiento;

/**
*
* @Autor: BBKMG
*/
public class EntrenamientoServicio {
	private EntrenamientoDAO ejpac;
	
	// Constructor
	public EntrenamientoServicio(){
		ejpac = new EntrenamientoDAO();
	}
	
	// Cargar Entrenamiento
	public void cargarEntrenamiento(Entrenamiento entrenamiento) {
		// MANEJO DE FALLO [ ENTIDAD EXISTENTE EN LA BD ]
		if (ejpac.obtenerEntidadEntrenamiento(entrenamiento.getIdEntrenamiento()) != null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d ya se encuentra en el sistema!%n", entrenamiento.getIdEntrenamiento());
			return;
		}
		
		// MANEJO DE FALLO [ ATRIBUTOS CLIENTE Y TUTOR NULL ]
		if (entrenamiento.getCliente() == null && entrenamiento.getTutor() == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no tiene cliente y tutor asignado o estos son nulos!%n", entrenamiento.getIdEntrenamiento());
			return;
		}
		
		// NOTA -> SIEMPRE ES ENCESARIO TENER UN CLIENTE Y TUTOR PARA CREAR UN ENTRENAMIENTO
		// CARGA EL OBJETO A LA BD
		ejpac.crearEntidadEntrenamiento(entrenamiento);
	}
	
	// Buscar Entrenamiento
	public Entrenamiento obtenerEntrenamiento(int id) {
		Entrenamiento entrenamiento = ejpac.obtenerEntidadEntrenamiento(id);
		// MANEJO DE FALLO [ ENTIDAD NO EXISTENTE EN LA BD ]
		if (entrenamiento == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no se encuentra en el sistema!%n", id);
		}
		return entrenamiento;
	}
	
	// Obtiene todos los Entrenamientos del sistema
	public List<Entrenamiento> obtenerTodosLosEntrenamientos(){
		List<Entrenamiento> entr = ejpac.obtenerEntidadesEntrenamiento();
		
		// MANEJO DE FALLO [ ENTIDADES NO EXISTENTES EN LA BD ]
		if (entr == null) {
			System.out.printf("[ ERROR ] > No hay entrenamientos en el sistema!%n");
		}
		return entr;
	}

	// Actualizar Entrenamiento (NO TERMINADO)
	public void actualizarInfEntrenamiento(int id, int puntaje,
											LocalDate fechaInicio,
											LocalDate fechaFin) {
		
		Entrenamiento entrenamiento = ejpac.obtenerEntidadEntrenamiento(id);
		
		// MANEJO DE FALLO [ ENTIDAD NO EXISTENTE EN LA BD ]
		if (entrenamiento == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		ejpac.actualizarEntidadEntrenamiento(entrenamiento, puntaje, fechaInicio, fechaFin);
	}

	// Eliminar Entrenamiento
	public void eliminarEntrenamiento(int id) {
		Entrenamiento entrenamiento = ejpac.obtenerEntidadEntrenamiento(id);
		
		// MANEJO DE FALLO [ ENTIDAD NO EXISTENTE EN LA BD ]
		if (entrenamiento == null) {
			System.out.printf("[ ERROR ] > El entrenamiento %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		ejpac.eliminarEntidadEntrenamiento(entrenamiento);
	}
	
	// CERRAR CONEXION
	public void finalizarConexion() {
		if (ejpac != null && ejpac.hayConexion()) {
			ejpac.cerrarEMF();			
		}
	}
}
