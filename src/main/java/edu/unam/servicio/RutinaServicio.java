/*
	Servicio de Entidad Rutina
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.RutinaJPAController;

// Varios
import java.util.List;

// Entidad
import edu.unam.modelo.Rutina;

/**
*
* @Autor: BBKMG
*/
public class RutinaServicio {
	private RutinaJPAController rjpac;
	
	// Constructor
	public RutinaServicio() {
		rjpac = new RutinaJPAController();
	}
	
	// Crear y cargar una Rutina al sistema
	public void crearRutina(Rutina rutina) {
		if (rjpac.obtenerEntidad(rutina.getIdRutina(), Rutina.class) != null) {
			System.out.printf("[ ERROR ] > La rutina %d ya se encuentra en el sistema!%n", rutina.getIdRutina());
		} else {
			rjpac.crearEntidad(rutina);
		}
		
		rjpac.cerrarEMF();
	}
	
	// Obtener una Rutina existente en el sistema
	public Rutina obtenerRutina(int id) {
		Rutina rutina = rjpac.obtenerEntidad(id, Rutina.class);
		
		if (rutina == null) {
			System.out.printf("[ ERROR ] > La ruutina %d no se encuentra en el sistema!%n", id);
		}
		
		rjpac.cerrarEMF();
		return rutina;
	}
	
	// Obtiene todos las Rutinas en el sistema
	public List<Rutina> obtenerTodasLasRutinas(){
		String entidadString = "Rutina";
		List<Rutina> rut = rjpac.obtenerEntidades(entidadString, Rutina.class);
		
		if (rut == null) {
			System.out.printf("[ ERROR ] > No hay rutinas en el sistema!%n");
		}
		
		rjpac.cerrarEMF();
		return rut;
	}
	
	// Eliminar una Rutina del sistema
	public void eliminarRutina(int id) {
		Rutina rutina = rjpac.obtenerEntidad(id, Rutina.class);
		
		if (rutina != null) {
			rjpac.eliminarEntidad(rutina);
		} else {
			System.out.printf("[ ERROR ] > La rutina %d  no se encuentra en el sistema!%n", id);
		}
		
		rjpac.cerrarEMF();
	}
	
	// Actualizar datos de una Rutina del sistema
	public void actualizarInfRutina(int id, int cantSeries, int cantRep) {
		Rutina rutina = rjpac.obtenerEntidad(id, Rutina.class);
		
		if (rutina != null) {
			rjpac.actualizarEntidad(rutina, cantSeries, cantRep);
		} else {
			System.out.printf("[ ERROR ] > La rutina %d no se encuentra en el sistema!%n", id);
		}
		
		rjpac.cerrarEMF();
	}
}
