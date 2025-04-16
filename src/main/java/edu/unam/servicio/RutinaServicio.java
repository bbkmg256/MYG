/*
	Servicio de Entidad Rutina
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.RutinaDAO;

// VARIOS
import java.util.List;

// ENTIDAD
import edu.unam.modelo.Rutina;

/**
*
* @Autor: BBKMG
*/
public class RutinaServicio {
	private RutinaDAO rjpac;
	
	// Constructor
	public RutinaServicio() {
		rjpac = new RutinaDAO();
	}
	
	// Crear y cargar una Rutina al sistema
	public void crearRutina(Rutina rutina) {
		if (rjpac.obtenerEntidadRutina(rutina.getIdRutina()) != null) {
			System.out.printf("[ ERROR ] > La rutina %d ya se encuentra en el sistema!%n", rutina.getIdRutina());
			return;
		}
		
		rjpac.crearEntidadRutina(rutina);
	}
	
	// Obtener una Rutina existente en el sistema
	public Rutina obtenerRutina(int id) {
		Rutina rutina = rjpac.obtenerEntidadRutina(id);
		
		if (rutina == null) {
			System.out.printf("[ ERROR ] > La ruutina %d no se encuentra en el sistema!%n", id);
		}
		return rutina;
	}
	
	// Obtiene todos las Rutinas en el sistema
	public List<Rutina> obtenerTodasLasRutinas(){
		List<Rutina> rut = rjpac.obtenerEntidadesRutina();
		
		if (rut == null) {
			System.out.printf("[ ERROR ] > No hay rutinas en el sistema!%n");
		}
		return rut;
	}
	
	// Eliminar una Rutina del sistema
	public void eliminarRutina(int id) {
		Rutina rutina = rjpac.obtenerEntidadRutina(id);
		
		if (rutina == null) {
			System.out.printf("[ ERROR ] > La rutina %d  no se encuentra en el sistema!%n", id);
			return;
		}
		
		rjpac.eliminarEntidadRutina(rutina);
	}
	
	// Actualizar datos de una Rutina del sistema
	public void actualizarEstadoRutina(int id, int cantSeries, int cantRep) {
		Rutina rutina = rjpac.obtenerEntidadRutina(id);
		
		if (rutina == null) {
			System.out.printf("[ ERROR ] > La rutina %d no se encuentra en el sistema!%n", id);
			return;
		}
		
		rjpac.actualizarEntidadRutina(rutina, cantSeries, cantRep);
	}

	// CERRAR CONEXION
	public void finalizarConexion() {
		if (rjpac != null && rjpac.hayConexion()) {
			rjpac.cerrarEMF();			
		}
	}
}
