/*
	Servicio de Grupo Muscular
*/

package edu.unam.servicio;

// JPA
import edu.unam.repositorio.GMJPAController;

// Entidad
import edu.unam.modelo.GrupoMuscular;

/**
*
* @Autor: BBKMG
*/
public class GMServicio {
	private GMJPAController gmjpac;
	
	// Constructor
	public GMServicio() {
		gmjpac = new GMJPAController();
	}
	
	// Cargar Grupo Muscular al sistema
	public void cargarGM(GrupoMuscular gm) {
		if (gmjpac.obtenerEntidad(gm.getIdGM(), GrupoMuscular.class) != null) {
			System.out.printf("[ERROR] > El grupo muscular %d ya se encuentra en el sistema!%n", gm.getIdGM());
		} else {
			gmjpac.crearEntidad(gm);
		}
		
		gmjpac.cerrarEMF();
	}
	
	// Buscar Grupo Muscular en el sistema
	public GrupoMuscular obtenerGM(int id) {
		GrupoMuscular gm = gmjpac.obtenerEntidad(id, GrupoMuscular.class);
		
		if (gm == null) {
			System.out.printf("[ERROR] > El grupo muscular %d no se encuentra en el sistema!%n", id);
		}
		
		gmjpac.cerrarEMF();
		return gm;
	}
	
	// Actualizar Grupo Muscular en el sistema
	public void actualizarInfGM(int id, String nombreGM) {
		GrupoMuscular gm = gmjpac.obtenerEntidad(id, GrupoMuscular.class);
		
		if (gm != null) {
			gmjpac.actualizarEntidad(gm, nombreGM);
		} else {
			System.out.printf("[ERROR] > El grupo muscular %d no se encuentra en el sistema!%n", id);
		}
		
		gmjpac.cerrarEMF();
	}
	
	// Eliminar Grupo Muscular del Sistema
	public void eliminarGM(int id) {
		GrupoMuscular gm = gmjpac.obtenerEntidad(id, GrupoMuscular.class);
		
		if (gm != null) {
			gmjpac.eliminarEntidad(gm);
		} else {
			System.out.printf("[ERROR] > El grupo muscular %d no se encuentra en el sistema!%n", id);
		}
		
		gmjpac.cerrarEMF();
	}
}
