/*
	Servicio de Ejercicio
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.EjercicioDAO;

// VARIOS
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Ejercicio;

/**
*
* @Autor: BBKMG
*/
public class EjercicioServicio {
	private EjercicioDAO ejjpac;
	
	public EjercicioServicio() {
		ejjpac = new EjercicioDAO();
	}
	
	// Cargar Ejercicio
	public void cargarEjercicio(Ejercicio ejercicio) {
		// MANEJO DE FALLO [ ENTIDAD EXISTENTE EN LA BD ]
		if (ejjpac.obtenerEntidadEjercicio(ejercicio.getIdEjercicio()) != null) {
			System.out.printf("[ ERROR ] > El ejercicio %d ya se encuentra en el sistema!%n", ejercicio.getIdEjercicio());
			return;
		}
		
		// MANEJO DE FALLO [ ATRIBUTO GM NULL ]
		if (ejercicio.getGrupoMuscular() == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no tiene grupo muscular asignado o este es nulo!%n", ejercicio.getIdEjercicio());
			return;
		}
		
		// MODIFICA EL PARAMETRO A MINUSCULA
		ejercicio.setNombreEjercicio(ejercicio.getNombreEjercicio().toLowerCase());
		
		// CARGA EL OBJETO A LA BD
		ejjpac.crearEntidadEjercicio(ejercicio);
	}
	
	// Buscar Ejercicio
	public Ejercicio obtenerEjercicio(int id) {
		Ejercicio ejercicio = ejjpac.obtenerEntidadEjercicio(id);
		
		if (ejercicio == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id);
		}
		return ejercicio;
	}
	
	// Obtener todos los Ejercicios cargados
	public List<Ejercicio> obtenerTodosLosEjercicios(){
		List<Ejercicio> ejer = ejjpac.obtenerEntidadesEjercicio();
		if (ejer == null) {
			System.out.printf("[ ERROR ] > No hay Ejercicios en el sistema!%n");
		}
		return ejer;
	}

	// Actualizar Ejercicio
	public void actualizarEstadoEjercicio(int id, String nombreEj) {
		Ejercicio ejercicio = ejjpac.obtenerEntidadEjercicio(id);
		
		if (ejercicio == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		// ALGUNOS PARAMETROS SE MODIFICAN A MAYUSCULA
		ejjpac.actualizarEntidadEjercicio(ejercicio, nombreEj.toLowerCase());
	}

	// Eliminar Ejercicio
	public void eliminarEjercicio(int id) {
		Ejercicio ejercicio = ejjpac.obtenerEntidadEjercicio(id);
		
		if (ejercicio == null) {
			System.out.printf("[ ERROR ] > El ejercicio %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		ejjpac.eliminarEntidadEjercicio(ejercicio);
	}
	
	// CERRAR CONEXION
	public void finalizarConexion() {
		if (ejjpac != null && ejjpac.hayConexion()) {
			ejjpac.cerrarEMF();			
		}
	}
}